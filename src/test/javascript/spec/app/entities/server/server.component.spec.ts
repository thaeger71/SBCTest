/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbcTestTestModule } from '../../../test.module';
import { ServerComponent } from 'app/entities/server/server.component';
import { ServerService } from 'app/entities/server/server.service';
import { Server } from 'app/shared/model/server.model';

describe('Component Tests', () => {
    describe('Server Management Component', () => {
        let comp: ServerComponent;
        let fixture: ComponentFixture<ServerComponent>;
        let service: ServerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbcTestTestModule],
                declarations: [ServerComponent],
                providers: []
            })
                .overrideTemplate(ServerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Server(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.servers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
