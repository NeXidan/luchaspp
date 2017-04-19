import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {BaseModule} from './base/base.module';
import {ProjectModule} from './project/project.module';

const MODULES = [
    BaseModule, ProjectModule
];

@NgModule({
    imports: MODULES,
    exports: [
        ...MODULES,
        RouterModule
    ]
})
export class RoutingModule {

}
