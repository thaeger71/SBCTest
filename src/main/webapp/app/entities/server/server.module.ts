import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcTestSharedModule } from 'app/shared';
import {
    ServerComponent,
    ServerDetailComponent,
    ServerUpdateComponent,
    ServerDeletePopupComponent,
    ServerDeleteDialogComponent,
    serverRoute,
    serverPopupRoute
} from './';

const ENTITY_STATES = [...serverRoute, ...serverPopupRoute];

@NgModule({
    imports: [SbcTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ServerComponent, ServerDetailComponent, ServerUpdateComponent, ServerDeleteDialogComponent, ServerDeletePopupComponent],
    entryComponents: [ServerComponent, ServerUpdateComponent, ServerDeleteDialogComponent, ServerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbcTestServerModule {}
