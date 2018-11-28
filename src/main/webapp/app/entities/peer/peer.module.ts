import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbcTestSharedModule } from 'app/shared';
import {
    PeerComponent,
    PeerDetailComponent,
    PeerUpdateComponent,
    PeerDeletePopupComponent,
    PeerDeleteDialogComponent,
    peerRoute,
    peerPopupRoute
} from './';

const ENTITY_STATES = [...peerRoute, ...peerPopupRoute];

@NgModule({
    imports: [SbcTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PeerComponent, PeerDetailComponent, PeerUpdateComponent, PeerDeleteDialogComponent, PeerDeletePopupComponent],
    entryComponents: [PeerComponent, PeerUpdateComponent, PeerDeleteDialogComponent, PeerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbcTestPeerModule {}
