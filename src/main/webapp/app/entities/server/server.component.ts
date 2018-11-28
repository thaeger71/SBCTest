import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServer } from 'app/shared/model/server.model';
import { Principal } from 'app/core';
import { ServerService } from './server.service';

@Component({
    selector: 'jhi-server',
    templateUrl: './server.component.html'
})
export class ServerComponent implements OnInit, OnDestroy {
    servers: IServer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private serverService: ServerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.serverService.query().subscribe(
            (res: HttpResponse<IServer[]>) => {
                this.servers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInServers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IServer) {
        return item.id;
    }

    registerChangeInServers() {
        this.eventSubscriber = this.eventManager.subscribe('serverListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
