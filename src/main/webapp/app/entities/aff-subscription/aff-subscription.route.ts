import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AffSubscription } from 'app/shared/model/aff-subscription.model';
import { AffSubscriptionService } from './aff-subscription.service';
import { AffSubscriptionComponent } from './aff-subscription.component';
import { AffSubscriptionDetailComponent } from './aff-subscription-detail.component';
import { AffSubscriptionUpdateComponent } from './aff-subscription-update.component';
import { AffSubscriptionDeletePopupComponent } from './aff-subscription-delete-dialog.component';
import { IAffSubscription } from 'app/shared/model/aff-subscription.model';

@Injectable({ providedIn: 'root' })
export class AffSubscriptionResolve implements Resolve<IAffSubscription> {
    constructor(private service: AffSubscriptionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAffSubscription> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AffSubscription>) => response.ok),
                map((affSubscription: HttpResponse<AffSubscription>) => affSubscription.body)
            );
        }
        return of(new AffSubscription());
    }
}

export const affSubscriptionRoute: Routes = [
    {
        path: '',
        component: AffSubscriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'affiliateApp.affSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AffSubscriptionDetailComponent,
        resolve: {
            affSubscription: AffSubscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'affiliateApp.affSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AffSubscriptionUpdateComponent,
        resolve: {
            affSubscription: AffSubscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'affiliateApp.affSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AffSubscriptionUpdateComponent,
        resolve: {
            affSubscription: AffSubscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'affiliateApp.affSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const affSubscriptionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AffSubscriptionDeletePopupComponent,
        resolve: {
            affSubscription: AffSubscriptionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'affiliateApp.affSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
