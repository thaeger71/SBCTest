import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';
import { DialplanEntryService } from './dialplan-entry.service';

@Component({
    selector: 'jhi-dialplan-entry-update',
    templateUrl: './dialplan-entry-update.component.html'
})
export class DialplanEntryUpdateComponent implements OnInit {
    dialplanEntry: IDialplanEntry;
    isSaving: boolean;

    constructor(private dialplanEntryService: DialplanEntryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dialplanEntry }) => {
            this.dialplanEntry = dialplanEntry;
        });
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
}
