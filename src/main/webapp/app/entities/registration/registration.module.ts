import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcTestSharedModule } from 'app/shared';
import {
    RegistrationComponent,
    RegistrationDetailComponent,
    RegistrationUpdateComponent,
    RegistrationDeletePopupComponent,
    RegistrationDeleteDialogComponent,
    registrationRoute,
    registrationPopupRoute
} from './';

const ENTITY_STATES = [...registrationRoute, ...registrationPopupRoute];

@NgModule({
    imports: [SbcTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegistrationComponent,
        RegistrationDetailComponent,
        RegistrationUpdateComponent,
        RegistrationDeleteDialogComponent,
        RegistrationDeletePopupComponent
    ],
    entryComponents: [
        RegistrationComponent,
        RegistrationUpdateComponent,
        RegistrationDeleteDialogComponent,
        RegistrationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbcTestRegistrationModule {}
