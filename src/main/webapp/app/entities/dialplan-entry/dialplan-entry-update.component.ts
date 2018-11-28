import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';
import { DialplanEntryService } from './dialplan-entry.service';
import { IPeer } from 'app/shared/model/peer.model';
import { PeerService } from 'app/entities/peer';

@Component({
    selector: 'jhi-dialplan-entry-update',
    templateUrl: './dialplan-entry-update.component.html'
})
export class DialplanEntryUpdateComponent implements OnInit {
    dialplanEntry: IDialplanEntry;
    isSaving: boolean;

    peers: IPeer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dialplanEntryService: DialplanEntryService,
        private peerService: PeerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dialplanEntry }) => {
            this.dialplanEntry = dialplanEntry;
        });
        this.peerService.query().subscribe(
            (res: HttpResponse<IPeer[]>) => {
                this.peers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dialplanEntry.id !== undefined) {
            this.subscribeToSaveResponse(this.dialplanEntryService.update(this.dialplanEntry));
        } else {
            this.subscribeToSaveResponse(this.dialplanEntryService.create(this.dialplanEntry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDialplanEntry>>) {
        result.subscribe((res: HttpResponse<IDialplanEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPeerById(index: number, item: IPeer) {
        return item.id;
    }
}
