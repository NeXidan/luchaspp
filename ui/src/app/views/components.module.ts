import {NgModule} from '@angular/core';

import {ButtonsModule} from 'ngx-bootstrap/buttons';
import {CollapseModule} from 'ngx-bootstrap/collapse';

const COMPONENTS = [
    // ButtonsModule, CollapseModule
];

@NgModule({
    imports: COMPONENTS,
    exports: COMPONENTS
})
export class ComponentsModule {

}
