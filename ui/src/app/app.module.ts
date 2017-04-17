import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {RouterModule, Routes} from '@angular/router';

import {BaseLayout} from './views/pages/base/base.layout';
import {ProjectListComponent} from './views/components/project-list/project-list.component';
import {ProjectComponent} from './views/components/project/project.component';

const appRoutes: Routes = [
  {path: 'projects', component: ProjectListComponent}
];

@NgModule({
    declarations: [
        BaseLayout,
        ProjectListComponent,
        ProjectComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        RouterModule.forRoot(appRoutes)
    ],
    providers: [],
    bootstrap: [BaseLayout]
})
export class AppModule {

}
