import {Response} from '@angular/http';

import {environment} from '../../environments/environment';
import {Project} from './project.model';
import {AbstractService} from './abstract.service';

export class ProjectService extends AbstractService<Project> {
    protected _url = environment.api.root + '/projects/';

    _create(options): Project {
        return new Project(options);
    }
}
