import {NgModule, Type} from '@angular/core';
import {SharedModule} from '@shared';
import {NzCalendarModule} from 'ng-zorro-antd/calendar';
import {ProductsModule} from '../products/products.module';
import {CompanyLocationSearchComponent} from './_components/company-location-search/company-location-search.component';
import {CompanyUserSearchComponent} from './_components/company-user-search/company-user-search.component';
import {CompanyBasicSetupComponent} from './company-basic-setup/company-basic-setup.component';
import {CompanyExpensesSetupComponent} from './company-expenses-setup/company-expenses-setup.component';
import {CompanyLocationSetupComponent} from './company-location-setup/company-location-setup.component';
import {CompanyPaymentOptionButtonComponent} from './company-payment-options/company-payment-option-button/company-payment-option-button.component';
import {
    CompanyPaymentOptionFormTableComponent
} from './company-payment-options/company-payment-option-form-table/company-payment-option-form-table.component';
import {CompanyPaymentOptionFormComponent} from './company-payment-options/company-payment-option-form/company-payment-option-form.component';
import {CompanyPaymentOptionTableComponent} from './company-payment-options/company-payment-option-table/company-payment-option-table.component';
import {CompanyPaymentOptionsComponent} from './company-payment-options/company-payment-options.component';
import {CompanyPeopleCustomerComponent} from './company-people/company-people-customer/company-people-customer.component';
import {CompanyPeopleEmployeesComponent} from './company-people/company-people-employees/company-people-employees.component';
import {CompanyPeopleSupplierComponent} from './company-people/company-people-supplier/company-people-supplier.component';
import {CompanyRoutingModule} from './company-routing.module';
import {CompanyTaxSetupComponent} from './company-tax-setup/company-tax-setup.component';

export const COMPANY_COMPONENTS: Array<Type<void>> = [
    CompanyLocationSearchComponent,
    CompanyUserSearchComponent,
    CompanyBasicSetupComponent,
    CompanyTaxSetupComponent,
    CompanyPaymentOptionsComponent,
    CompanyPeopleCustomerComponent,
    CompanyPeopleEmployeesComponent,
    CompanyPeopleSupplierComponent,
    CompanyLocationSetupComponent,
    CompanyExpensesSetupComponent,
    CompanyPaymentOptionFormComponent,
    CompanyPaymentOptionTableComponent,
    CompanyPaymentOptionButtonComponent
];

@NgModule({
    imports: [CompanyRoutingModule, SharedModule, NzCalendarModule, ProductsModule],
    declarations: [
        ...COMPANY_COMPONENTS,
        CompanyPaymentOptionFormTableComponent

    ],
    exports: [
        ...COMPANY_COMPONENTS
    ]
})
export class CompanyModule {
}
