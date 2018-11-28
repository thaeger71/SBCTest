import { NgModule } from '@angular/core';

import { SbcTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SbcTestSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SbcTestSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SbcTestSharedCommonModule {}
