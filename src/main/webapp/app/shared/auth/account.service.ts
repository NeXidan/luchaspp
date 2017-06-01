import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { AuthServerProvider } from './auth-jwt.service';

@Injectable()
export class AccountService  {
    constructor(private http: Http, private authServerProvider: AuthServerProvider) { }

    get(): Observable<any> {
        return this.http.get('api/account')
            .map((res: Response) => {
                const {authorities = [], ...rest} = res.json() || {};
                return {authorities: authorities.map((authority) => authority.name), ...rest};
            })
            .catch((err: Response | any) => {
                if (err.status === 401 || err.status === 403) {
                    return this.authServerProvider.logout().map(() => {
                        return Observable.throw(err);
                    });
                }

                return Observable.throw(err);
            });
    }

    save(account: any): Observable<Response> {
        return this.http.post('api/account', account);
    }
}
