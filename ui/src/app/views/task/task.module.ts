import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

import {BaseLayout} from '../base/base.layout';
import {TaskListComponent} from './task-list/task-list.component';

import {AuthGuard} from '../../auth.guard';

const routes: Routes = [
    {path: '', redirectTo: 'tasks', pathMatch: 'full'},
    {
        path: 'tasks',
        component: BaseLayout,
        children: [
            {
                path: '',
                component: TaskListComponent
            },
            {
                path: ':project',
                component: TaskListComponent
            }
        ],
        canActivate: [AuthGuard]
    }
];

@NgModule({
    declarations: [
        TaskListComponent
    ],
    providers: [AuthGuard],
    imports: [
        CommonModule,
        RouterModule.forRoot(routes)
    ]
})
export class TaskModule {

}
