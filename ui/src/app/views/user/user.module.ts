import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

import {BaseLayout} from '../base/base.layout';

import {AuthGuard} from '../../auth.guard';

const routes: Routes = [
    {
        path: 'user/:user',
        component: BaseLayout,
        canActivate: [AuthGuard]
    }
];

@NgModule({
    declarations: [
    ],
    providers: [AuthGuard],
    imports: [
        CommonModule,
        RouterModule.forRoot(routes)
    ]
})
export class UserModule {

}
