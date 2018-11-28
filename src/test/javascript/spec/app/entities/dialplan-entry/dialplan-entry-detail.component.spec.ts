/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcTestTestModule } from '../../../test.module';
import { DialplanEntryDetailComponent } from 'app/entities/dialplan-entry/dialplan-entry-detail.component';
import { DialplanEntry } from 'app/shared/model/dialplan-entry.model';

describe('Component Tests', () => {
    describe('DialplanEntry Management Detail Component', () => {
        let comp: DialplanEntryDetailComponent;
        let fixture: ComponentFixture<DialplanEntryDetailComponent>;
        const route = ({ data: of({ dialplanEntry: new DialplanEntry(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [DialplanEntryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DialplanEntryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DialplanEntryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dialplanEntry).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
