import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MusicaDetailComponent } from '../../../../../../main/webapp/app/entities/musica/musica-detail.component';
import { MusicaService } from '../../../../../../main/webapp/app/entities/musica/musica.service';
import { Musica } from '../../../../../../main/webapp/app/entities/musica/musica.model';

describe('Component Tests', () => {

    describe('Musica Management Detail Component', () => {
        let comp: MusicaDetailComponent;
        let fixture: ComponentFixture<MusicaDetailComponent>;
        let service: MusicaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MusicaDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    MusicaService
                ]
            }).overrideComponent(MusicaDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MusicaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MusicaService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Musica(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.musica).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
