/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AffiliateTestModule } from '../../../test.module';
import { AffSubscriptionDetailComponent } from 'app/entities/aff-subscription/aff-subscription-detail.component';
import { AffSubscription } from 'app/shared/model/aff-subscription.model';

describe('Component Tests', () => {
    describe('AffSubscription Management Detail Component', () => {
        let comp: AffSubscriptionDetailComponent;
        let fixture: ComponentFixture<AffSubscriptionDetailComponent>;
        const route = ({ data: of({ affSubscription: new AffSubscription(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AffiliateTestModule],
                declarations: [AffSubscriptionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AffSubscriptionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffSubscriptionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.affSubscription).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
