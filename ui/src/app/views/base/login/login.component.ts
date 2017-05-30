import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';

import {AuthService} from '../../../auth.service';

@Component({
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
    providers: [AuthService]
})
export class LoginComponent implements OnInit {
    constructor(private _authService: AuthService) {

    }

    ngOnInit() {
        this._authService.logout({navigate: false});
    }

    onLogin(form: NgForm) {
        this._authService.login(form.value);
    }
}
