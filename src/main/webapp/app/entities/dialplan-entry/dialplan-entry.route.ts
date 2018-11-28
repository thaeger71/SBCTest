import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DialplanEntry } from 'app/shared/model/dialplan-entry.model';
import { DialplanEntryService } from './dialplan-entry.service';
import { DialplanEntryComponent } from './dialplan-entry.component';
import { DialplanEntryDetailComponent } from './dialplan-entry-detail.component';
import { DialplanEntryUpdateComponent } from './dialplan-entry-update.component';
import { DialplanEntryDeletePopupComponent } from './dialplan-entry-delete-dialog.component';
import { IDialplanEntry } from 'app/shared/model/dialplan-entry.model';

@Injectable({ providedIn: 'root' })
export class DialplanEntryResolve implements Resolve<IDialplanEntry> {
    constructor(private service: DialplanEntryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DialplanEntry> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DialplanEntry>) => response.ok),
                map((dialplanEntry: HttpResponse<DialplanEntry>) => dialplanEntry.body)
            );
        }
        return of(new DialplanEntry());
    }
}

export const dialplanEntryRoute: Routes = [
    {
        path: 'dialplan-entry',
        component: DialplanEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DialplanEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dialplan-entry/:id/view',
        component: DialplanEntryDetailComponent,
        resolve: {
            dialplanEntry: DialplanEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DialplanEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dialplan-entry/new',
        component: DialplanEntryUpdateComponent,
        resolve: {
            dialplanEntry: DialplanEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DialplanEntries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dialplan-entry/:id/edit',
        component: DialplanEntryUpdateComponent,
        resolve: {
            dialplanEntry: DialplanEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DialplanEntries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dialplanEntryPopupRoute: Routes = [
    {
        path: 'dialplan-entry/:id/delete',
        component: DialplanEntryDeletePopupComponent,
        resolve: {
            dialplanEntry: DialplanEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DialplanEntries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
