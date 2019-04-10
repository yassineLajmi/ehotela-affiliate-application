/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AffiliateTestModule } from '../../../test.module';
import { AffSubscriptionDeleteDialogComponent } from 'app/entities/aff-subscription/aff-subscription-delete-dialog.component';
import { AffSubscriptionService } from 'app/entities/aff-subscription/aff-subscription.service';

describe('Component Tests', () => {
    describe('AffSubscription Management Delete Component', () => {
        let comp: AffSubscriptionDeleteDialogComponent;
        let fixture: ComponentFixture<AffSubscriptionDeleteDialogComponent>;
        let service: AffSubscriptionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AffiliateTestModule],
                declarations: [AffSubscriptionDeleteDialogComponent]
            })
                .overrideTemplate(AffSubscriptionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffSubscriptionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffSubscriptionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
