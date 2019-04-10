import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';

@Component({
    selector: 'jhi-affiliate-update',
    templateUrl: './affiliate-update.component.html'
})
export class AffiliateUpdateComponent implements OnInit {
    affiliate: IAffiliate;
    isSaving: boolean;

    constructor(protected affiliateService: AffiliateService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ affiliate }) => {
            this.affiliate = affiliate;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.affiliate.id !== undefined) {
            this.subscribeToSaveResponse(this.affiliateService.update(this.affiliate));
        } else {
            this.subscribeToSaveResponse(this.affiliateService.create(this.affiliate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliate>>) {
        result.subscribe((res: HttpResponse<IAffiliate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
