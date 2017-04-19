import {Component} from '@angular/core';
import {NgForm} from '@angular/forms';

import {environment} from '../../../../environments/environment';
import {AuthService} from '../../../auth.service';

@Component({
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
    providers: [AuthService]
})
export class LoginComponent {
    constructor(private _authService: AuthService) {

    }

    onLogin(form: NgForm) {
        this._authService.login(form.value);
    }
}
