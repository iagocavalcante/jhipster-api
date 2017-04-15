import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Banda } from './banda.model';
import { BandaPopupService } from './banda-popup.service';
import { BandaService } from './banda.service';
import { Album, AlbumService } from '../album';
@Component({
    selector: 'jhi-banda-dialog',
    templateUrl: './banda-dialog.component.html'
})
export class BandaDialogComponent implements OnInit {

    banda: Banda;
    authorities: any[];
    isSaving: boolean;

    albums: Album[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private bandaService: BandaService,
        private albumService: AlbumService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['banda']);
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
        if (this.banda.id !== undefined) {
            this.bandaService.update(this.banda)
                .subscribe((res: Banda) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.bandaService.create(this.banda)
                .subscribe((res: Banda) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Banda) {
        this.eventManager.broadcast({ name: 'bandaListModification', content: 'OK'});
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
    selector: 'jhi-banda-popup',
    template: ''
})
export class BandaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private bandaPopupService: BandaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.bandaPopupService
                    .open(BandaDialogComponent, params['id']);
            } else {
                this.modalRef = this.bandaPopupService
                    .open(BandaDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
