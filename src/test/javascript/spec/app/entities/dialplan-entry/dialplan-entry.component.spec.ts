/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcTestTestModule } from '../../../test.module';
import { DialplanEntryComponent } from 'app/entities/dialplan-entry/dialplan-entry.component';
import { DialplanEntryService } from 'app/entities/dialplan-entry/dialplan-entry.service';
import { DialplanEntry } from 'app/shared/model/dialplan-entry.model';

describe('Component Tests', () => {
    describe('DialplanEntry Management Component', () => {
        let comp: DialplanEntryComponent;
        let fixture: ComponentFixture<DialplanEntryComponent>;
        let service: DialplanEntryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [DialplanEntryComponent],
                providers: []
            })
                .overrideTemplate(DialplanEntryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DialplanEntryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DialplanEntryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DialplanEntry(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dialplanEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
