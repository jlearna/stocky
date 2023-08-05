import {AsyncPipe, CommonModule, UpperCasePipe} from '@angular/common';
import {NgModule, Type} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {DelonACLModule} from '@delon/acl';
import {DelonFormModule} from '@delon/form';
import {AlainThemeModule} from '@delon/theme';
//
import {DisableAutocompleteDirective} from './directive/disable-autocomplete.directive';
import {SHARED_COMPONENTS} from './shared-components.module';

import {SHARED_DELON_MODULES} from './shared-delon.module';
import {SHARED_ZORRO_MODULES} from './shared-zorro.module';


const THIRD_MODULES: Array<Type<void>> = [];
const COMPONENTS: Array<Type<void>> = [];

const DIRECTIVES: Array<Type<void>> = [
    DisableAutocompleteDirective
];
const ICONS: Array<Type<void>> = [];

const PROVIDERS: Array<Type<void>> = [AsyncPipe, UpperCasePipe];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        ReactiveFormsModule,
        AlainThemeModule.forChild(),
        DelonACLModule,
        DelonFormModule,
        ...SHARED_DELON_MODULES,
        ...SHARED_ZORRO_MODULES,
        ...THIRD_MODULES,
        ...ICONS
    ],
    declarations: [
        ...SHARED_COMPONENTS,
        ...COMPONENTS,
        ...DIRECTIVES
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        AlainThemeModule,
        DelonACLModule,
        DelonFormModule,
        ...SHARED_DELON_MODULES,
        ...SHARED_ZORRO_MODULES,
        ...THIRD_MODULES,
        ...SHARED_COMPONENTS,
        ...COMPONENTS,
        ...DIRECTIVES,
        ...PROVIDERS
    ],
    providers: [...PROVIDERS]
})
export class SharedModule {
}
