import {Component, OnInit} from '@angular/core';

import {Project} from '../../../models/project.model';
import {ProjectService} from '../../../models/project.service';

@Component({
    templateUrl: './project-list.component.html',
    styleUrls: ['./project-list.component.css'],
    providers: [ProjectService]
})
export class ProjectListComponent implements OnInit {
    projects: Project[] = [];
    error: string;

    constructor(private projectService: ProjectService) {

    }

    ngOnInit() {
        return this.projectService.fetch()
            .subscribe(
                (projects) => this.projects = projects,
                (error) =>  this.error = <any>error
            );
    }
}
