import {NgModule} from '@angular/core';
import {Http, RequestOptions} from '@angular/http';
import {AuthHttp, AuthConfig} from 'angular2-jwt';

import {AuthService} from './auth.service';

export function authHttpServiceFactory(
    http: Http,
    options: RequestOptions,
    authService: AuthService
) {
    return new AuthHttp(new AuthConfig({
        headerName: 'Authorization',
        noTokenScheme: true,
        tokenName: 'token',
        tokenGetter: (() => {
            return authService.getToken();
        })
    }), http, options);
}

@NgModule({
    providers: [{
        provide: AuthHttp,
        useFactory: authHttpServiceFactory,
        deps: [Http, RequestOptions, AuthService]
    }]
})
export class AuthModule {}
