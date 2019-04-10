import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAffSubscription } from 'app/shared/model/aff-subscription.model';
import { AccountService } from 'app/core';
import { AffSubscriptionService } from './aff-subscription.service';

@Component({
    selector: 'jhi-aff-subscription',
    templateUrl: './aff-subscription.component.html'
})
export class AffSubscriptionComponent implements OnInit, OnDestroy {
    affSubscriptions: IAffSubscription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected affSubscriptionService: AffSubscriptionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.affSubscriptionService
            .query()
            .pipe(
                filter((res: HttpResponse<IAffSubscription[]>) => res.ok),
                map((res: HttpResponse<IAffSubscription[]>) => res.body)
            )
            .subscribe(
                (res: IAffSubscription[]) => {
                    this.affSubscriptions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAffSubscriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAffSubscription) {
        return item.id;
    }

    registerChangeInAffSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe('affSubscriptionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
