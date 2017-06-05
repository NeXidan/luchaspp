import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { UserModalService } from './user-modal.service';
import {  User, UserService } from '../../shared';

@Component({
    selector: 'app-user-mgmt-dialog',
    templateUrl: './user-management-dialog.component.html'
})
export class UserMgmtDialogComponent implements OnInit {

    user: User;
    languages: any[];
    authorities: any[];
    isSaving: Boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private userService: UserService,
        private eventManager: EventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.authorities = [];
        this.userService.authorities().subscribe((authorities) => {
            this.authorities = authorities;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            this.userService.create(this.user).subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    compareAuthority(val1, val2): boolean {
        return val1 && val2 ? val1.name === val2.name : val1 === val2;
    }
}

@Component({
    selector: 'app-user-dialog',
    template: ''
})
export class UserDialogComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userModalService: UserModalService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['login'] ) {
                this.modalRef = this.userModalService.open(UserMgmtDialogComponent, params['login']);
            } else {
                this.modalRef = this.userModalService.open(UserMgmtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
