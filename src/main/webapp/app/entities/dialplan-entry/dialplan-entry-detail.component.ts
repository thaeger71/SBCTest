import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';

@Component({
    selector: 'jhi-dialplan-entry-detail',
    templateUrl: './dialplan-entry-detail.component.html'
})
export class DialplanEntryDetailComponent implements OnInit {
    dialplanEntry: IDialplanEntry;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dialplanEntry }) => {
            this.dialplanEntry = dialplanEntry;
        });
    }

    previousState() {
        window.history.back();
    }
}
