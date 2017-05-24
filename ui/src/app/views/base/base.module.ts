import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {RouterModule, Routes} from '@angular/router';

import {ComponentsModule} from '../components.module';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
    {path: 'login', component: LoginComponent}
];

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        HttpModule,
        ComponentsModule,
        RouterModule.forRoot(routes)
    ]
})
export class BaseModule {

}
