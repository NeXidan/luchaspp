import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

import {getItem} from './libs/LocalStorage';
import {AuthService} from './auth.service';

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(private _router: Router, private _authService: AuthService) {

    }

    canActivate(route) {
        let canActivate = this._authService.isLoggedIn();

        if (!canActivate) {
            this._router.navigate(
                ['/login'],
                {queryParams: {
                    repath: route.url.join('')
                }}
            );
        }

        return canActivate;
    }
}
