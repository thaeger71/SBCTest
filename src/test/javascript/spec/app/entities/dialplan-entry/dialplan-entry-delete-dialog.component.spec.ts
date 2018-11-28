/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbcTestTestModule } from '../../../test.module';
import { DialplanEntryDeleteDialogComponent } from 'app/entities/dialplan-entry/dialplan-entry-delete-dialog.component';
import { DialplanEntryService } from 'app/entities/dialplan-entry/dialplan-entry.service';

describe('Component Tests', () => {
    describe('DialplanEntry Management Delete Component', () => {
        let comp: DialplanEntryDeleteDialogComponent;
        let fixture: ComponentFixture<DialplanEntryDeleteDialogComponent>;
        let service: DialplanEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [DialplanEntryDeleteDialogComponent]
            })
                .overrideTemplate(DialplanEntryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DialplanEntryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DialplanEntryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
