import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SbcTestServerModule } from './server/server.module';
import { SbcTestPeerModule } from './peer/peer.module';
import { SbcTestRegistrationModule } from './registration/registration.module';
import { SbcTestDialplanEntryModule } from './dialplan-entry/dialplan-entry.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SbcTestServerModule,
        SbcTestPeerModule,
        SbcTestRegistrationModule,
        SbcTestDialplanEntryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbcTestEntityModule {}
