import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRegistration } from 'app/shared/model/registration.model';
import { RegistrationService } from './registration.service';

@Component({
    selector: 'jhi-registration-update',
    templateUrl: './registration-update.component.html'
})
export class RegistrationUpdateComponent implements OnInit {
    registration: IRegistration;
    isSaving: boolean;

    constructor(private registrationService: RegistrationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ registration }) => {
            this.registration = registration;
        });
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
}
