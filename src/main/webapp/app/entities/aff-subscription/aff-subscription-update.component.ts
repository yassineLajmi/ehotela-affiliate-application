import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAffSubscription } from 'app/shared/model/aff-subscription.model';
import { AffSubscriptionService } from './aff-subscription.service';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate';

@Component({
    selector: 'jhi-aff-subscription-update',
    templateUrl: './aff-subscription-update.component.html'
})
export class AffSubscriptionUpdateComponent implements OnInit {
    affSubscription: IAffSubscription;
    isSaving: boolean;

    affiliates: IAffiliate[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected affSubscriptionService: AffSubscriptionService,
        protected affiliateService: AffiliateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ affSubscription }) => {
            this.affSubscription = affSubscription;
        });
        this.affiliateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAffiliate[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAffiliate[]>) => response.body)
            )
            .subscribe((res: IAffiliate[]) => (this.affiliates = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.affSubscription.id !== undefined) {
            this.subscribeToSaveResponse(this.affSubscriptionService.update(this.affSubscription));
        } else {
            this.subscribeToSaveResponse(this.affSubscriptionService.create(this.affSubscription));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffSubscription>>) {
        result.subscribe((res: HttpResponse<IAffSubscription>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAffiliateById(index: number, item: IAffiliate) {
        return item.id;
    }
}
