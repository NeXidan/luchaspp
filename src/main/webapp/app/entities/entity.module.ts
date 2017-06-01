import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SppProjectModule } from './project/project.module';
import { SppTagModule } from './tag/tag.module';
import { SppSprintModule } from './sprint/sprint.module';
import { SppTaskModule } from './task/task.module';

@NgModule({
    imports: [
        SppProjectModule,
        SppTagModule,
        SppSprintModule,
        SppTaskModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SppEntityModule {}
