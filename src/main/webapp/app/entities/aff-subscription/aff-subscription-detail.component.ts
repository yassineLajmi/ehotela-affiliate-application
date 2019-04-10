import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffSubscription } from 'app/shared/model/aff-subscription.model';

@Component({
    selector: 'jhi-aff-subscription-detail',
    templateUrl: './aff-subscription-detail.component.html'
})
export class AffSubscriptionDetailComponent implements OnInit {
    affSubscription: IAffSubscription;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ affSubscription }) => {
            this.affSubscription = affSubscription;
        });
    }

    previousState() {
        window.history.back();
    }
}
