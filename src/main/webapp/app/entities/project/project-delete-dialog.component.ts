import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Project } from './project.model';
import { ProjectPopupService } from './project-popup.service';
import { ProjectService } from './project.service';

@Component({
    selector: 'app-project-delete-dialog',
    templateUrl: './project-delete-dialog.component.html'
})
export class ProjectDeleteDialogComponent {

    project: Project;

    constructor(
        private projectService: ProjectService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.projectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'projectListModification',
                content: 'Deleted an project'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'app-project-delete-popup',
    template: ''
})
export class ProjectDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectPopupService: ProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.projectPopupService
                .open(ProjectDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
