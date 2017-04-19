import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {RoutingModule} from './views/routing.module';
import {AuthModule} from './auth.module';

import {AppComponent} from './views/app.component';
import {AuthService} from './auth.service';

@NgModule({
    declarations: [
        AppComponent
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
