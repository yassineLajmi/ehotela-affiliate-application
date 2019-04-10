import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'affiliate',
                loadChildren: './affiliate/affiliate.module#AffiliateAffiliateModule'
            },
            {
                path: 'rubric',
                loadChildren: './rubric/rubric.module#AffiliateRubricModule'
            },
            {
                path: 'aff-subscription',
                loadChildren: './aff-subscription/aff-subscription.module#AffiliateAffSubscriptionModule'
            },
            {
                path: 'rubric',
                loadChildren: './rubric/rubric.module#AffiliateRubricModule'
            },
            {
                path: 'aff-subscription',
                loadChildren: './aff-subscription/aff-subscription.module#AffiliateAffSubscriptionModule'
            },
            {
                path: 'affiliate',
                loadChildren: './affiliate/affiliate.module#AffiliateAffiliateModule'
            },
            {
                path: 'aff-subscription',
                loadChildren: './aff-subscription/aff-subscription.module#AffiliateAffSubscriptionModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AffiliateEntityModule {}
