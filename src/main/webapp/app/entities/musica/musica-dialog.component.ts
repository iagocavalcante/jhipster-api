import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Musica } from './musica.model';
import { MusicaPopupService } from './musica-popup.service';
import { MusicaService } from './musica.service';
import { Album, AlbumService } from '../album';
@Component({
    selector: 'jhi-musica-dialog',
    templateUrl: './musica-dialog.component.html'
})
export class MusicaDialogComponent implements OnInit {

    musica: Musica;
    authorities: any[];
    isSaving: boolean;

    albums: Album[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private musicaService: MusicaService,
        private albumService: AlbumService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['musica']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.albumService.query().subscribe(
            (res: Response) => { this.albums = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.musica.id !== undefined) {
            this.musicaService.update(this.musica)
                .subscribe((res: Musica) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.musicaService.create(this.musica)
                .subscribe((res: Musica) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Musica) {
        this.eventManager.broadcast({ name: 'musicaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackAlbumById(index: number, item: Album) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-musica-popup',
    template: ''
})
export class MusicaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private musicaPopupService: MusicaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.musicaPopupService
                    .open(MusicaDialogComponent, params['id']);
            } else {
                this.modalRef = this.musicaPopupService
                    .open(MusicaDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
