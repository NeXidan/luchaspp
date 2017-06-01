import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Sprint } from './sprint.model';
import { SprintPopupService } from './sprint-popup.service';
import { SprintService } from './sprint.service';

@Component({
    selector: 'app-sprint-delete-dialog',
    templateUrl: './sprint-delete-dialog.component.html'
})
export class SprintDeleteDialogComponent {

    sprint: Sprint;

    constructor(
        private sprintService: SprintService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sprintService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sprintListModification',
                content: 'Deleted an sprint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'app-sprint-delete-popup',
    template: ''
})
export class SprintDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sprintPopupService: SprintPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sprintPopupService
                .open(SprintDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
