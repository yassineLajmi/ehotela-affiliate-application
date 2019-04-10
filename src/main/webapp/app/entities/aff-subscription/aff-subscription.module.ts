import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AffiliateSharedModule } from 'app/shared';
import {
    AffSubscriptionComponent,
    AffSubscriptionDetailComponent,
    AffSubscriptionUpdateComponent,
    AffSubscriptionDeletePopupComponent,
    AffSubscriptionDeleteDialogComponent,
    affSubscriptionRoute,
    affSubscriptionPopupRoute
} from './';

const ENTITY_STATES = [...affSubscriptionRoute, ...affSubscriptionPopupRoute];

@NgModule({
    imports: [AffiliateSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AffSubscriptionComponent,
        AffSubscriptionDetailComponent,
        AffSubscriptionUpdateComponent,
        AffSubscriptionDeleteDialogComponent,
        AffSubscriptionDeletePopupComponent
    ],
    entryComponents: [
        AffSubscriptionComponent,
        AffSubscriptionUpdateComponent,
        AffSubscriptionDeleteDialogComponent,
        AffSubscriptionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AffiliateAffSubscriptionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
