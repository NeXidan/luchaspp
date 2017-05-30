import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {BaseModule} from './base/base.module';
import {ProjectModule} from './project/project.module';
import {TaskModule} from './task/task.module';
import {UserModule} from './user/user.module';

const MODULES = [
    BaseModule, ProjectModule, TaskModule, UserModule
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
