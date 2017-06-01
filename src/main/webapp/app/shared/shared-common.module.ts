import { NgModule, Sanitizer } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AlertService } from 'ng-jhipster';
import {
    SppSharedLibsModule,
    AppAlertComponent,
    AppAlertErrorComponent,
    DocumentsComponent
} from './';

export function alertServiceProvider(sanitizer: Sanitizer) {
    const isToast = false;
    return new AlertService(sanitizer, isToast);
}

@NgModule({
    imports: [
        SppSharedLibsModule
    ],
    declarations: [
        AppAlertComponent,
        AppAlertErrorComponent,
        DocumentsComponent
    ],
    providers: [
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer]
        },
        Title
    ],
    exports: [
        SppSharedLibsModule,
        AppAlertComponent,
        AppAlertErrorComponent,
        DocumentsComponent
    ]
})
export class SppSharedCommonModule {}
