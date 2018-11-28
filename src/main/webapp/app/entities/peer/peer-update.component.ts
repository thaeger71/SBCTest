import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPeer } from 'app/shared/model/peer.model';
import { PeerService } from './peer.service';
import { IServer } from 'app/shared/model/server.model';
import { ServerService } from 'app/entities/server';

@Component({
    selector: 'jhi-peer-update',
    templateUrl: './peer-update.component.html'
})
export class PeerUpdateComponent implements OnInit {
    peer: IPeer;
    isSaving: boolean;

    servers: IServer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private peerService: PeerService,
        private serverService: ServerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ peer }) => {
            this.peer = peer;
        });
        this.serverService.query().subscribe(
            (res: HttpResponse<IServer[]>) => {
                this.servers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackServerById(index: number, item: IServer) {
        return item.id;
    }
}
