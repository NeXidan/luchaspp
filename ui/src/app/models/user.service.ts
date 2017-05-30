import {Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';

import {environment} from '../../environments/environment';
import {User} from './user.model';
import {AbstractService} from './abstract.service';

export class UserService extends AbstractService<User> {
    protected _url = environment.api.root + '/user/';

    _create(options): User {
        return new User(options);
    }

    fetchMe(): Observable<User> {
        return this._http.get(this._url + 'me')
            .map(this._parseOne.bind(this))
            .catch(this._onError.bind(this));
    }
}
