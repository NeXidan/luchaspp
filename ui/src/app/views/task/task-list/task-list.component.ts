import {Component, OnInit} from '@angular/core';

// import {Project} from '../../../models/project.model';
// import {ProjectService} from '../../../models/project.service';

@Component({
    templateUrl: './task-list.component.html',
    styleUrls: ['./task-list.component.less'],
})
export class TaskListComponent implements OnInit {
    // projects: Project[] = [];

    // constructor(private _projectService: ProjectService) {

    // }

    ngOnInit() {
    //     return this._projectService.fetch()
    //         .subscribe(
    //             (projects) => this.projects = projects
    //         );
    }
}
