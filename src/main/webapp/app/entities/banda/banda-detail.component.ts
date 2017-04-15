import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Banda } from './banda.model';
import { BandaService } from './banda.service';

@Component({
    selector: 'jhi-banda-detail',
    templateUrl: './banda-detail.component.html'
})
export class BandaDetailComponent implements OnInit, OnDestroy {

    banda: Banda;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private bandaService: BandaService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['banda']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.bandaService.find(id).subscribe(banda => {
            this.banda = banda;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
