import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcTestSharedModule } from 'app/shared';
import {
    DialplanEntryComponent,
    DialplanEntryDetailComponent,
    DialplanEntryUpdateComponent,
    DialplanEntryDeletePopupComponent,
    DialplanEntryDeleteDialogComponent,
    dialplanEntryRoute,
    dialplanEntryPopupRoute
} from './';

const ENTITY_STATES = [...dialplanEntryRoute, ...dialplanEntryPopupRoute];

@NgModule({
    imports: [SbcTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DialplanEntryComponent,
        DialplanEntryDetailComponent,
        DialplanEntryUpdateComponent,
        DialplanEntryDeleteDialogComponent,
        DialplanEntryDeletePopupComponent
    ],
    entryComponents: [
        DialplanEntryComponent,
        DialplanEntryUpdateComponent,
        DialplanEntryDeleteDialogComponent,
        DialplanEntryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbcTestDialplanEntryModule {}
