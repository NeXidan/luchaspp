import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Sprint } from './sprint.model';
import { SprintPopupService } from './sprint-popup.service';
import { SprintService } from './sprint.service';
import { Project, ProjectService } from '../project';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'app-sprint-dialog',
    templateUrl: './sprint-dialog.component.html'
})
export class SprintDialogComponent implements OnInit {

    sprint: Sprint;
    authorities: any[];
    isSaving: boolean;

    projects: Project[];
    fromDateDp: any;
    toDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private sprintService: SprintService,
        private projectService: ProjectService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.projectService.query()
            .subscribe((res: ResponseWrapper) => { this.projects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sprint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sprintService.update(this.sprint));
        } else {
            this.subscribeToSaveResponse(
                this.sprintService.create(this.sprint));
        }
    }

    private subscribeToSaveResponse(result: Observable<Sprint>) {
        result.subscribe((res: Sprint) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Sprint) {
        this.eventManager.broadcast({ name: 'sprintListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'app-sprint-popup',
    template: ''
})
export class SprintPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sprintPopupService: SprintPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.sprintPopupService
                    .open(SprintDialogComponent, params['id']);
            } else {
                this.modalRef = this.sprintPopupService
                    .open(SprintDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
