import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SppSharedModule, UserRouteAccessService } from './shared';
import { SppAdminModule } from './admin/admin.module';
import { SppAccountModule } from './account/account.module';
import { SppEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    SppHomeModule,
    AppMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ErrorComponent,
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'app', separator: '-'}),
        SppSharedModule,
        SppHomeModule,
        SppAdminModule,
        SppAccountModule,
        SppEntityModule
    ],
    declarations: [
        AppMainComponent,
        NavbarComponent,
        ErrorComponent,
        FooterComponent
    ],
    providers: [
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ AppMainComponent ]
})
export class SppAppModule {}
