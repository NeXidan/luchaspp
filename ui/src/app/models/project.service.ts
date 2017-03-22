import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import {environment} from '../../environments/environment';
import {Project} from './project.model';

@Injectable()
export class ProjectService {
    private url = environment.api.root + '/projects';

    constructor (private http: Http) {

    }

    fetch(): Observable<Project[]> {
        return this.http.get(this.url)
            .map(this._parse)
            .catch(this._onError);
    }

    private _parse(response: Response) {
        let data = response.json() || [];
        return data.map((options) => new Project(options));
    }

    private _onError(response: Response | any) {
        let message: string;

        if (response instanceof Response) {
            const {error = ''} = response.json() || {};
            message = `Error: ${response.status} - ${response.statusText || ''} ${error}`;
        } else {
            message = response.message ? response.message : response.toString();
        }

        console.error(message);

        return Observable.throw(message);
    }
}
