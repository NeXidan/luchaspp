import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {RoutingModule} from './views/routing.module';
import {AuthModule} from './auth.module';

import {AppComponent} from './views/app.component';
import {BaseLayout} from './views/base/base.layout';
import {HeaderComponent} from './views/base/header/header.component';
import {FooterComponent} from './views/base/footer/footer.component';
import {AuthService} from './auth.service';

@NgModule({
    declarations: [
        AppComponent,
        BaseLayout,
        HeaderComponent,
        FooterComponent
    ],
    imports: [
        BrowserModule,
        AuthModule,
        RoutingModule
    ],
    providers: [AuthService],
    bootstrap: [AppComponent]
})
export class AppModule {

}
