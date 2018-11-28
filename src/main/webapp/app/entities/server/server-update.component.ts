import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IServer } from 'app/shared/model/server.model';
import { ServerService } from './server.service';

@Component({
    selector: 'jhi-server-update',
    templateUrl: './server-update.component.html'
})
export class ServerUpdateComponent implements OnInit {
    server: IServer;
    isSaving: boolean;

    constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ server }) => {
            this.server = server;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.server.id !== undefined) {
            this.subscribeToSaveResponse(this.serverService.update(this.server));
        } else {
            this.subscribeToSaveResponse(this.serverService.create(this.server));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServer>>) {
        result.subscribe((res: HttpResponse<IServer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
