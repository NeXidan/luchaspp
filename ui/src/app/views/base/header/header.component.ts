import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute, NavigationEnd} from '@angular/router';

import {AuthService} from '../../../auth.service';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/filter';

import {User} from '../../../models/user.model';
import {UserService} from '../../../models/user.service';
import {Project} from '../../../models/project.model';
import {ProjectService} from '../../../models/project.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.less'],
    providers: [UserService, ProjectService]
})
export class HeaderComponent implements OnInit {
    paramsSubscribe: any;

    user: User;
    selectedProject: String;
    selectedUser: String;
    projects: Project[] = [];

    constructor(
        private _userService: UserService,
        private _projectService: ProjectService,
        private _authService: AuthService,
        private _router: Router,
        private _route: ActivatedRoute
    ) {

    }

    ngOnInit() {
        if (!this.isLoggedIn()) {
            return;
        }

        this._userService.fetchMe()
            .subscribe((user) => this.user = user);

        this._projectService.fetch()
            .subscribe((projects) => {
                this.projects = projects;
            });

        this._router.events
            .filter((event) => event instanceof NavigationEnd)
            .subscribe((event) => {
                let snapshot = this._route.root.snapshot;

                do {
                    let {user, project} = snapshot.params;
                    this.selectedProject = project;
                    this.selectedUser = user;

                    snapshot = snapshot.firstChild;
                } while (snapshot)
            });
    }

    isLoggedIn() {
        return this._authService.isLoggedIn();
    }

    isActive(instruction: any[]): boolean {
        return this._router.isActive(this._router.createUrlTree(instruction.filter(Boolean)), false);
    }

    logout() {
        this._authService.logout();
    }
}
