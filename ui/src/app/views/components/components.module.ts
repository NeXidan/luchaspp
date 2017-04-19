import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ButtonComponent} from './button/button.component';
import {InputComponent} from './input/input.component';

const COMPONENTS = [
    ButtonComponent, InputComponent
];

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: COMPONENTS,
    exports: COMPONENTS
})
export class ComponentsModule {

}
