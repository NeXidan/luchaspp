import { Routes } from '@angular/router';

import {
    passwordRoute,
    registerRoute,
} from './';

const ACCOUNT_ROUTES = [
   passwordRoute,
   registerRoute,
];

export const accountState: Routes = [{
    path: '',
    children: ACCOUNT_ROUTES
}];
