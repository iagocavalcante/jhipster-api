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
import { BandaDetailComponent } from '../../../../../../main/webapp/app/entities/banda/banda-detail.component';
import { BandaService } from '../../../../../../main/webapp/app/entities/banda/banda.service';
import { Banda } from '../../../../../../main/webapp/app/entities/banda/banda.model';

describe('Component Tests', () => {

    describe('Banda Management Detail Component', () => {
        let comp: BandaDetailComponent;
        let fixture: ComponentFixture<BandaDetailComponent>;
        let service: BandaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [BandaDetailComponent],
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
                    BandaService
                ]
            }).overrideComponent(BandaDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BandaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BandaService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Banda(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.banda).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
