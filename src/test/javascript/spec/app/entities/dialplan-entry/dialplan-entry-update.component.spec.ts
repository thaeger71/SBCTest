/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SbcTestTestModule } from '../../../test.module';
import { DialplanEntryUpdateComponent } from 'app/entities/dialplan-entry/dialplan-entry-update.component';
import { DialplanEntryService } from 'app/entities/dialplan-entry/dialplan-entry.service';
import { DialplanEntry } from 'app/shared/model/dialplan-entry.model';

describe('Component Tests', () => {
    describe('DialplanEntry Management Update Component', () => {
        let comp: DialplanEntryUpdateComponent;
        let fixture: ComponentFixture<DialplanEntryUpdateComponent>;
        let service: DialplanEntryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [DialplanEntryUpdateComponent]
            })
                .overrideTemplate(DialplanEntryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DialplanEntryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DialplanEntryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DialplanEntry(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dialplanEntry = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DialplanEntry();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dialplanEntry = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
