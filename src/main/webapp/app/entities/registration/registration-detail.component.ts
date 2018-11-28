import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistration } from 'app/shared/model/registration.model';

@Component({
    selector: 'jhi-registration-detail',
    templateUrl: './registration-detail.component.html'
})
export class RegistrationDetailComponent implements OnInit {
    registration: IRegistration;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ registration }) => {
            this.registration = registration;
        });
    }

    previousState() {
        window.history.back();
    }
}
