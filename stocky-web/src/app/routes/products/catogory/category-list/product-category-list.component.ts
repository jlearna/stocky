import {Component, OnInit} from '@angular/core';
import {UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {Observable, of, shareReplay} from 'rxjs';
import {Crumbs} from 'src/app/shared/components/breadcrumbs/breadcrumbs.component';
import {PRODUCT_CATEGORY_LIST_CRUMBS} from '../../../../data/constant/crumb.constant';
import {FileConstant} from '../../../../data/constant/file.constant';
import {PopOverConstant} from '../../../../data/constant/message.constant';
import {FileType, TableButtonEnum} from '../../../../data/payload/common.enum';
import {UploadComponentInput} from '../../../../data/payload/common.interface';
import {TableEditCacheMap} from '../../../../data/payload/common.types';
import {TableCol} from '../../../../shared/components/table/table.component';
import {UploadImportService} from '../../../../shared/utils/upload-import.service';
import {
    handleAppendToObservableListIfResponse,
    handleCancelEditingTableItem,
    handleRemoveFromObservableListIfStatus,
    handleUpdateObservableListIfResponse,
    handleUsecaseRequest,
    isFormInvalid
} from '../../../../shared/utils/util';
import {ProductCategoryPayload} from '../../_data/product.payload';
import {ProductCategoryUsecase} from '../../_usecase/product-category.usecase';

@Component({
    selector: 'app-product-category-list',
    templateUrl: './product-category-list.component.html',
    styleUrls: ['./product-category-list.component.css']
})
export class ProductCategoryListComponent implements OnInit {
    public isExpanded = true;
    public isLoading = false;
    public isLoadingSearch = false;
    public isAllChecked = false;
    public indeterminate = false;
    public showForm = false;
    public categoryForm!: UntypedFormGroup;
    public crumbs: Crumbs[] = PRODUCT_CATEGORY_LIST_CRUMBS;
    public popParentHint = PopOverConstant.PRODUCT_CATEGORY_PARENT;
    public popTitle = PopOverConstant.POP_TITLE;
    public editObj: TableEditCacheMap<ProductCategoryPayload> = {};
    public categories?: Observable<ProductCategoryPayload[]>;
    public cols: TableCol[] = [
        {title: 'Title'},
        {title: 'Description'},
        {title: 'Parent'},
        {title: 'Status'},
        {title: 'Action'}
    ];
    public isSubCategory = false;
    public pageTitle = 'Add New Category';
    public searchTerm?: string;
    public hasError = false;
    protected readonly TableButtonEnum = TableButtonEnum;

    constructor(
        private fb: UntypedFormBuilder,
        private notification: NzNotificationService,
        private usecase: ProductCategoryUsecase,
        private uploadService: UploadImportService
    ) {}

    public ngOnInit(): void {
        this.initForm();
        this.usecase.trigger$.subscribe(value => this.loadTableData());
    }

    public initForm() {
        this.categoryForm = this.fb.group({
            title: [null, [Validators.required]],
            description: [null],
            parent: [null]
        });
    }

    public onOpenModal = () => {
        this.showForm = true;
    };

    public onCloseModal = () => {
        this.showForm = false;
    };

    public onSearchCategory = async () => {
        if (!this.searchTerm) {
            this.hasError = true;
            return;
        }

        this.isLoadingSearch = true;
        const res = await handleUsecaseRequest(this.usecase.search(this.searchTerm), this.notification);
        if (res.ok) {
            this.categories = of(res.body!);
        }
        this.isLoadingSearch = false;
    };

    public onResetSearch = () => {
        this.onCancelSearch();
    };

    public onCancelSearch = () => {
        this.isLoadingSearch = false;
        this.hasError = false;
        this.categories = of([]);
    };

    public onCreate = async () => {
        const isInvalid = isFormInvalid(this.categoryForm);
        if (isInvalid) {
            return;
        }
        this.isLoading = true;
        const category = this.categoryForm.value;
        let response = await handleUsecaseRequest(this.usecase.save(category), this.notification);
        this.categories = handleAppendToObservableListIfResponse(this.categories!, response);
        this.onResetPayload();
    };

    public onCancelEdit = async (item: ProductCategoryPayload) => {
        if (item) {
            this.editObj = await handleCancelEditingTableItem(item as any, this.categories!, this.editObj);
        }
    };

    public onToggleEdit = (item: ProductCategoryPayload) => {
        if (item) this.editObj[item.id!] = {edit: true, data: {...item}, updating: false, loading: false};
    };

    public onSaveEdit = async (item: ProductCategoryPayload) => {
        this.editObj[item.id!].updating = true;
        const data = this.editObj[item.id!].data;
        const response = await handleUsecaseRequest(this.usecase.update(data), this.notification);
        this.categories = handleUpdateObservableListIfResponse(this.categories!, response);
        this.editObj[item.id!].edit = false;
    };

    public onConfirmDelete = async (id: number) => {
        this.editObj[id] = {edit: false, data: {}, updating: false, loading: true};
        const response = await handleUsecaseRequest(this.usecase.delete(id), this.notification);
        this.categories = handleRemoveFromObservableListIfStatus(
            this.categories!,
            {key: 'id', value: id},
            response
        );
        this.editObj[id].loading = false;
    };

    public onCancelDelete = async () => {};

    public onParentCategorySelected = (parent: ProductCategoryPayload) => {
        if (parent) {
            this.categoryForm.get('parent')?.setValue(parent);
        }
    };

    public onCacheValueChange = (change: any, item: ProductCategoryPayload, field: string) => {
        const data: any = this.editObj[item.id!].data;
        data[field] = change;
    };

    public canEditItem(item: any) {
        return this.editObj[item.id] && this.editObj[item.id].edit;
    }

    public updateHasError(event: any) {
        const value = event.target.value;
        this.hasError = !value;

    }

    public onConfirmToggleStatus = async (id: number) => {
        this.editObj[id] = {edit: false, data: {}, updating: false, loading: true};
        const response = await handleUsecaseRequest(this.usecase.toggleActiveStatus(id), this.notification);
        this.editObj[id].loading = false;
        this.notifyChange();
    };

    public handleExportData = (arg?: FileType) => {
        this.uploadService.download();
    };

    public handleDownloadTemplate = () => {};

    public handleUpload = () => {
        const arg: UploadComponentInput = {
            maxFileSizeInMB: FileConstant.MAX_UPLOAD_FILE_SIZE_MB,
            allowedFileTypes: [FileType.EXCEL],
            url: this.usecase.getProductCategoryUploadURL(),
            type: 'drag',
            canUploadMultipleFiles: false,
            canDownloadTemplate: true,
            onDownloadTemplate: this.handleDownloadTemplate
        };
        this.uploadService.upload(arg, 'Upload Product Category File');
    };

    private loadTableData() {
        this.categories = this.usecase.getMany().pipe(shareReplay());
    }

    private onResetPayload() {
        this.initForm();
        this.isLoading = false;
        this.showForm = false;
    }

    private notifyChange() {
        this.usecase.setTrigger(true);
    }
}
