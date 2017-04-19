import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Router} from '@angular/router';
import {AuthHttp} from 'angular2-jwt';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/empty';

@Injectable()
export abstract class AbstractService<ModelClass> {
    protected _url = '/';

    constructor (private _http: AuthHttp, private _router: Router) {

    }

    fetch(): Observable<ModelClass[]> {
        return this._http.get(this._url)
            .map(this._parse.bind(this))
            .catch(this._onError.bind(this));
    }

    protected _parse(response: Response) {
        let data = response.json() || [];
        return data.map(this._create.bind(this));
    }

    protected abstract _create(options): ModelClass;

    protected _onError(response: Response | any) {
        let message: string;

        if (response instanceof Response) {
            const {error = ''} = response.json() || {};
            message = `Error: ${response.status} - ${response.statusText || ''} ${error}`;
        } else {
            message = response.message ? response.message : response.toString();
        }

        return Observable.throw(message);
    }
}
