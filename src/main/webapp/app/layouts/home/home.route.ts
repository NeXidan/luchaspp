import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: [],
        pageTitle: 'Spp'
    }
};
