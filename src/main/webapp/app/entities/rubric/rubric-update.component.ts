import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRubric } from 'app/shared/model/rubric.model';
import { RubricService } from './rubric.service';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate';

@Component({
    selector: 'jhi-rubric-update',
    templateUrl: './rubric-update.component.html'
})
export class RubricUpdateComponent implements OnInit {
    rubric: IRubric;
    isSaving: boolean;

    affiliates: IAffiliate[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rubricService: RubricService,
        protected affiliateService: AffiliateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rubric }) => {
            this.rubric = rubric;
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
        if (this.rubric.id !== undefined) {
            this.subscribeToSaveResponse(this.rubricService.update(this.rubric));
        } else {
            this.subscribeToSaveResponse(this.rubricService.create(this.rubric));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRubric>>) {
        result.subscribe((res: HttpResponse<IRubric>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
