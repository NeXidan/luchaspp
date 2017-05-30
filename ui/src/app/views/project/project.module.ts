import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

import {BaseLayout} from '../base/base.layout';

import {ComponentsModule} from '../components.module';
import {ProjectListComponent} from './project-list/project-list.component';
import {ProjectComponent} from './project/project.component';

import {AuthGuard} from '../../auth.guard';

const routes: Routes = [
    // {
    //     path: 'projects',
    //     component: BaseLayout,
    //     children: [
    //         {
    //             path: '',
    //             component: ProjectListComponent,
    //             outlet: 'content'
    //         }
    //     ],
    //     canActivate: [AuthGuard]
    // }
    {
        path: 'project/:project',
        component: ProjectComponent,
        canActivate: [AuthGuard]
    }
];

@NgModule({
    declarations: [
        ProjectListComponent,
        ProjectComponent
    ],
    providers: [AuthGuard],
    imports: [
        CommonModule,
        ComponentsModule,
        RouterModule.forRoot(routes)
    ]
})
export class ProjectModule {

}
