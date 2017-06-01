import { Routes } from '@angular/router';

import {
    passwordRoute,
    registerRoute,
    settingsRoute
} from './';

const ACCOUNT_ROUTES = [
   passwordRoute,
   registerRoute,
   settingsRoute
];

export const accountState: Routes = [{
    path: '',
    children: ACCOUNT_ROUTES
}];
