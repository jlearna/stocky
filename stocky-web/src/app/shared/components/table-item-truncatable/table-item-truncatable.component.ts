import {Component, Input, TemplateRef} from '@angular/core';

@Component({
    selector: 'app-table-item-truncatable',
    templateUrl: './table-item-truncatable.component.html',
    styles: []
})
export class TableItemTruncatableComponent {

    @Input()
    public content?: string | TemplateRef<any>;

    @Input()
    public limit: number = 10;

    public get slicedContent() {
        const content = <string>this.content;
        return content.slice(0, this.limit) + '...';

    };

    public get isTemplateRef() {
        return this.content instanceof TemplateRef;
    }

    public get contentRef(): TemplateRef<any> {

        return <TemplateRef<any>>this.content;
    }

}