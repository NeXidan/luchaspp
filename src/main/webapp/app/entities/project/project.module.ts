import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SppSharedModule } from '../../shared';
import { SppAdminModule } from '../../admin/admin.module';
import { SppTaskModule } from '../task/task.module';
import {
    ProjectService,
    ProjectPopupService,
    ProjectComponent,
    ProjectDetailComponent,
    ProjectDialogComponent,
    ProjectPopupComponent,
    ProjectDeletePopupComponent,
    ProjectDeleteDialogComponent,
    projectRoute,
    projectPopupRoute,
    ProjectResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...projectRoute,
    ...projectPopupRoute,
];

@NgModule({
    imports: [
        SppSharedModule,
        SppAdminModule,
        SppTaskModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true})
    ],
    declarations: [
        ProjectComponent,
        ProjectDetailComponent,
        ProjectDialogComponent,
        ProjectDeleteDialogComponent,
        ProjectPopupComponent,
        ProjectDeletePopupComponent,
    ],
    entryComponents: [
        ProjectComponent,
        ProjectDialogComponent,
        ProjectPopupComponent,
        ProjectDeleteDialogComponent,
        ProjectDeletePopupComponent,
    ],
    providers: [
        ProjectService,
        ProjectPopupService,
        ProjectResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SppProjectModule {}
