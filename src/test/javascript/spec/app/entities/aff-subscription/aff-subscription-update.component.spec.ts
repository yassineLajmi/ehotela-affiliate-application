/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AffiliateTestModule } from '../../../test.module';
import { AffSubscriptionUpdateComponent } from 'app/entities/aff-subscription/aff-subscription-update.component';
import { AffSubscriptionService } from 'app/entities/aff-subscription/aff-subscription.service';
import { AffSubscription } from 'app/shared/model/aff-subscription.model';

describe('Component Tests', () => {
    describe('AffSubscription Management Update Component', () => {
        let comp: AffSubscriptionUpdateComponent;
        let fixture: ComponentFixture<AffSubscriptionUpdateComponent>;
        let service: AffSubscriptionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AffiliateTestModule],
                declarations: [AffSubscriptionUpdateComponent]
            })
                .overrideTemplate(AffSubscriptionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AffSubscriptionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffSubscriptionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AffSubscription(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.affSubscription = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AffSubscription();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.affSubscription = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
