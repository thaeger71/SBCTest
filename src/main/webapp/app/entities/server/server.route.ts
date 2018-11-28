import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Server } from 'app/shared/model/server.model';
import { ServerService } from './server.service';
import { ServerComponent } from './server.component';
import { ServerDetailComponent } from './server-detail.component';
import { ServerUpdateComponent } from './server-update.component';
import { ServerDeletePopupComponent } from './server-delete-dialog.component';
import { IServer } from 'app/shared/model/server.model';

@Injectable({ providedIn: 'root' })
export class ServerResolve implements Resolve<IServer> {
    constructor(private service: ServerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Server> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Server>) => response.ok),
                map((server: HttpResponse<Server>) => server.body)
            );
        }
        return of(new Server());
    }
}

export const serverRoute: Routes = [
    {
        path: 'server',
        component: ServerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'server/:id/view',
        component: ServerDetailComponent,
        resolve: {
            server: ServerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'server/new',
        component: ServerUpdateComponent,
        resolve: {
            server: ServerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'server/:id/edit',
        component: ServerUpdateComponent,
        resolve: {
            server: ServerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serverPopupRoute: Routes = [
    {
        path: 'server/:id/delete',
        component: ServerDeletePopupComponent,
        resolve: {
            server: ServerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
