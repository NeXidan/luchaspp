import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SppSharedModule } from '../shared';

import {
    Register,
    Password,
    RegisterComponent,
    PasswordComponent,
    SettingsComponent,
    accountState
} from './';

@NgModule({
    imports: [
        SppSharedModule,
        RouterModule.forRoot(accountState, {useHash: true})
    ],
    declarations: [
        RegisterComponent,
        PasswordComponent,
        SettingsComponent
    ],
    providers: [
        Register,
        Password
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SppAccountModule {}
