import {NgModule} from '@angular/core';

import {ButtonsModule} from 'ngx-bootstrap/buttons';

const COMPONENTS = [
    ButtonsModule
];

@NgModule({
    imports: COMPONENTS,
    exports: COMPONENTS
})
export class ComponentsModule {

}
