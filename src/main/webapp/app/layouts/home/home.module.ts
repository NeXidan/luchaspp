import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SppSharedModule } from '../../shared';

import {HomeComponent} from './home.component';
import {HOME_ROUTE} from './home.route';

@NgModule({
    imports: [
        SppSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], {useHash: true})
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SppHomeModule {}
