import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRubric } from 'app/shared/model/rubric.model';
import { AccountService } from 'app/core';
import { RubricService } from './rubric.service';

@Component({
    selector: 'jhi-rubric',
    templateUrl: './rubric.component.html'
})
export class RubricComponent implements OnInit, OnDestroy {
    rubrics: IRubric[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected rubricService: RubricService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.rubricService
            .query()
            .pipe(
                filter((res: HttpResponse<IRubric[]>) => res.ok),
                map((res: HttpResponse<IRubric[]>) => res.body)
            )
            .subscribe(
                (res: IRubric[]) => {
                    this.rubrics = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRubrics();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRubric) {
        return item.id;
    }

    registerChangeInRubrics() {
        this.eventSubscriber = this.eventManager.subscribe('rubricListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
