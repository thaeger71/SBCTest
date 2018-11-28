import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';
import { DialplanEntryService } from './dialplan-entry.service';

@Component({
    selector: 'jhi-dialplan-entry-delete-dialog',
    templateUrl: './dialplan-entry-delete-dialog.component.html'
})
export class DialplanEntryDeleteDialogComponent {
    dialplanEntry: IDialplanEntry;

    constructor(
        private dialplanEntryService: DialplanEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dialplanEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dialplanEntryListModification',
                content: 'Deleted an dialplanEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dialplan-entry-delete-popup',
    template: ''
})
export class DialplanEntryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dialplanEntry }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DialplanEntryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dialplanEntry = dialplanEntry;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
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
