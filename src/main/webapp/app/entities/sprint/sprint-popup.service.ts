import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Sprint } from './sprint.model';
import { SprintService } from './sprint.service';
@Injectable()
export class SprintPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sprintService: SprintService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sprintService.find(id).subscribe((sprint) => {
                if (sprint.fromDate) {
                    sprint.fromDate = {
                        year: sprint.fromDate.getFullYear(),
                        month: sprint.fromDate.getMonth() + 1,
                        day: sprint.fromDate.getDate()
                    };
                }
                if (sprint.toDate) {
                    sprint.toDate = {
                        year: sprint.toDate.getFullYear(),
                        month: sprint.toDate.getMonth() + 1,
                        day: sprint.toDate.getDate()
                    };
                }
                this.sprintModalRef(component, sprint);
            });
        } else {
            return this.sprintModalRef(component, new Sprint());
        }
    }

    sprintModalRef(component: Component, sprint: Sprint): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sprint = sprint;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
