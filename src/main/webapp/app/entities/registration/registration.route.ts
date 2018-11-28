import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Registration } from 'app/shared/model/registration.model';
import { RegistrationService } from './registration.service';
import { RegistrationComponent } from './registration.component';
import { RegistrationDetailComponent } from './registration-detail.component';
import { RegistrationUpdateComponent } from './registration-update.component';
import { RegistrationDeletePopupComponent } from './registration-delete-dialog.component';
import { IRegistration } from 'app/shared/model/registration.model';

@Injectable({ providedIn: 'root' })
export class RegistrationResolve implements Resolve<IRegistration> {
    constructor(private service: RegistrationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Registration> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Registration>) => response.ok),
                map((registration: HttpResponse<Registration>) => registration.body)
            );
        }
        return of(new Registration());
    }
}

export const registrationRoute: Routes = [
    {
        path: 'registration',
        component: RegistrationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration/:id/view',
        component: RegistrationDetailComponent,
        resolve: {
            registration: RegistrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration/new',
        component: RegistrationUpdateComponent,
        resolve: {
            registration: RegistrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registrations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'registration/:id/edit',
        component: RegistrationUpdateComponent,
        resolve: {
            registration: RegistrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registrations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const registrationPopupRoute: Routes = [
    {
        path: 'registration/:id/delete',
        component: RegistrationDeletePopupComponent,
        resolve: {
            registration: RegistrationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Registrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
