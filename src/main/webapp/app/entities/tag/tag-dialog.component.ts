import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Tag } from './tag.model';
import { TagPopupService } from './tag-popup.service';
import { TagService } from './tag.service';
import { Project, ProjectService } from '../project';
import { Task, TaskService } from '../task';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'app-tag-dialog',
    templateUrl: './tag-dialog.component.html'
})
export class TagDialogComponent implements OnInit {

    tag: Tag;
    authorities: any[];
    isSaving: boolean;

    projects: Project[];

    tasks: Task[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private tagService: TagService,
        private projectService: ProjectService,
        private taskService: TaskService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.projectService.query()
            .subscribe((res: ResponseWrapper) => { this.projects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.taskService.query()
            .subscribe((res: ResponseWrapper) => { this.tasks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tagService.update(this.tag));
        } else {
            this.subscribeToSaveResponse(
                this.tagService.create(this.tag));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tag>) {
        result.subscribe((res: Tag) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tag) {
        this.eventManager.broadcast({ name: 'tagListModification', content: 'OK'});
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

    trackTaskById(index: number, item: Task) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'app-tag-popup',
    template: ''
})
export class TagPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagPopupService: TagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.tagPopupService
                    .open(TagDialogComponent, params['id']);
            } else {
                this.modalRef = this.tagPopupService
                    .open(TagDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
