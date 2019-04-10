/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AffiliateTestModule } from '../../../test.module';
import { AffSubscriptionComponent } from 'app/entities/aff-subscription/aff-subscription.component';
import { AffSubscriptionService } from 'app/entities/aff-subscription/aff-subscription.service';
import { AffSubscription } from 'app/shared/model/aff-subscription.model';

describe('Component Tests', () => {
    describe('AffSubscription Management Component', () => {
        let comp: AffSubscriptionComponent;
        let fixture: ComponentFixture<AffSubscriptionComponent>;
        let service: AffSubscriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AffiliateTestModule],
                declarations: [AffSubscriptionComponent],
                providers: []
            })
                .overrideTemplate(AffSubscriptionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AffSubscriptionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffSubscriptionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AffSubscription(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.affSubscriptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
