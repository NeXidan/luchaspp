import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

import {ComponentsModule} from '../components.module';
import {ProjectListComponent} from './project-list/project-list.component';
import {ProjectComponent} from './project/project.component';

import {AuthGuard} from '../../auth.guard';

const routes: Routes = [
    {path: 'projects', component: ProjectListComponent, canActivate: [AuthGuard]},
    {path: 'projects/:id', component: ProjectComponent, canActivate: [AuthGuard]}
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
