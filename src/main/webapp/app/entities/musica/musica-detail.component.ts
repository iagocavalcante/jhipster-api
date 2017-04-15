import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Musica } from './musica.model';
import { MusicaService } from './musica.service';

@Component({
    selector: 'jhi-musica-detail',
    templateUrl: './musica-detail.component.html'
})
export class MusicaDetailComponent implements OnInit, OnDestroy {

    musica: Musica;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private musicaService: MusicaService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['musica']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.musicaService.find(id).subscribe(musica => {
            this.musica = musica;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
