import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPeer } from 'app/shared/model/peer.model';
import { PeerService } from './peer.service';

@Component({
    selector: 'jhi-peer-update',
    templateUrl: './peer-update.component.html'
})
export class PeerUpdateComponent implements OnInit {
    peer: IPeer;
    isSaving: boolean;

    constructor(private peerService: PeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ peer }) => {
            this.peer = peer;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.peer.id !== undefined) {
            this.subscribeToSaveResponse(this.peerService.update(this.peer));
        } else {
            this.subscribeToSaveResponse(this.peerService.create(this.peer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPeer>>) {
        result.subscribe((res: HttpResponse<IPeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
