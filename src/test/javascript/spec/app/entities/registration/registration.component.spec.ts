/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcTestTestModule } from '../../../test.module';
import { RegistrationComponent } from 'app/entities/registration/registration.component';
import { RegistrationService } from 'app/entities/registration/registration.service';
import { Registration } from 'app/shared/model/registration.model';

describe('Component Tests', () => {
    describe('Registration Management Component', () => {
        let comp: RegistrationComponent;
        let fixture: ComponentFixture<RegistrationComponent>;
        let service: RegistrationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [RegistrationComponent],
                providers: []
            })
                .overrideTemplate(RegistrationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegistrationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistrationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Registration(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.registrations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
