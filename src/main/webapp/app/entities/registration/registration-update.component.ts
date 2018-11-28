import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRegistration } from 'app/shared/model/registration.model';
import { RegistrationService } from './registration.service';
import { IPeer } from 'app/shared/model/peer.model';
import { PeerService } from 'app/entities/peer';

@Component({
    selector: 'jhi-registration-update',
    templateUrl: './registration-update.component.html'
})
export class RegistrationUpdateComponent implements OnInit {
    registration: IRegistration;
    isSaving: boolean;

    peers: IPeer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private registrationService: RegistrationService,
        private peerService: PeerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ registration }) => {
            this.registration = registration;
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
        if (this.registration.id !== undefined) {
            this.subscribeToSaveResponse(this.registrationService.update(this.registration));
        } else {
            this.subscribeToSaveResponse(this.registrationService.create(this.registration));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegistration>>) {
        result.subscribe((res: HttpResponse<IRegistration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
