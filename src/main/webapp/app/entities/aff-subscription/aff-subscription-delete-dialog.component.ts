import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAffSubscription } from 'app/shared/model/aff-subscription.model';
import { AffSubscriptionService } from './aff-subscription.service';

@Component({
    selector: 'jhi-aff-subscription-delete-dialog',
    templateUrl: './aff-subscription-delete-dialog.component.html'
})
export class AffSubscriptionDeleteDialogComponent {
    affSubscription: IAffSubscription;

    constructor(
        protected affSubscriptionService: AffSubscriptionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.affSubscriptionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'affSubscriptionListModification',
                content: 'Deleted an affSubscription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aff-subscription-delete-popup',
    template: ''
})
export class AffSubscriptionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ affSubscription }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AffSubscriptionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.affSubscription = affSubscription;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/aff-subscription', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/aff-subscription', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
