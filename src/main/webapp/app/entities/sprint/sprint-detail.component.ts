import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Sprint } from './sprint.model';
import { SprintService } from './sprint.service';

@Component({
    selector: 'app-sprint-detail',
    templateUrl: './sprint-detail.component.html'
})
export class SprintDetailComponent implements OnInit, OnDestroy {

    sprint: Sprint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private sprintService: SprintService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSprints();
    }

    load(id) {
        this.sprintService.find(id).subscribe((sprint) => {
            this.sprint = sprint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSprints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sprintListModification',
            (response) => this.load(this.sprint.id)
        );
    }
}
