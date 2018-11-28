/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbcTestTestModule } from '../../../test.module';
import { RegistrationDetailComponent } from 'app/entities/registration/registration-detail.component';
import { Registration } from 'app/shared/model/registration.model';

describe('Component Tests', () => {
    describe('Registration Management Detail Component', () => {
        let comp: RegistrationDetailComponent;
        let fixture: ComponentFixture<RegistrationDetailComponent>;
        const route = ({ data: of({ registration: new Registration(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [RegistrationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegistrationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegistrationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.registration).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
