import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Musica } from './musica.model';
import { MusicaPopupService } from './musica-popup.service';
import { MusicaService } from './musica.service';

@Component({
    selector: 'jhi-musica-delete-dialog',
    templateUrl: './musica-delete-dialog.component.html'
})
export class MusicaDeleteDialogComponent {

    musica: Musica;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private musicaService: MusicaService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['musica']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.musicaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'musicaListModification',
                content: 'Deleted an musica'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-musica-delete-popup',
    template: ''
})
export class MusicaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private musicaPopupService: MusicaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.musicaPopupService
                .open(MusicaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
