import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';
import { Principal } from 'app/core';
import { DialplanEntryService } from './dialplan-entry.service';

@Component({
    selector: 'jhi-dialplan-entry',
    templateUrl: './dialplan-entry.component.html'
})
export class DialplanEntryComponent implements OnInit, OnDestroy {
    dialplanEntries: IDialplanEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dialplanEntryService: DialplanEntryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dialplanEntryService.query().subscribe(
            (res: HttpResponse<IDialplanEntry[]>) => {
                this.dialplanEntries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDialplanEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDialplanEntry) {
        return item.id;
    }

    registerChangeInDialplanEntries() {
        this.eventSubscriber = this.eventManager.subscribe('dialplanEntryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
