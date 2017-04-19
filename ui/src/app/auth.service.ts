import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Router, ActivatedRoute} from '@angular/router';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../environments/environment';
import {getItem, setItem, removeItem} from './libs/LocalStorage';

@Injectable()
export class AuthService {
    repath: string = '/'

    constructor (
        private _http: Http,
        private _router: Router,
        private _route: ActivatedRoute
    ) {
        let repath = this._route.queryParams.subscribe((params) => {
            this.repath = params['repath'];
        });
    }

    login(data) {
        this._http.post(environment.api.root + 'login', JSON.stringify(data))
            .map((response: Response) => {
                let token = response.headers.get('Authorization');

                if (token) {
                    setItem('token', token);
                    this._router.navigateByUrl(this.repath);
                } else {
                    this.logout();
                }
            })
            .subscribe(() => {
                // no-op
            });
    }

    logout() {
        removeItem('token');
    }

    isLoggedIn() {
        return Boolean(this.getToken() !== null);
    }

    getToken() {
        return getItem('token');
    }
}
