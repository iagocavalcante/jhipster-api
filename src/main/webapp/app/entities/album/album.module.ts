import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteJhipSharedModule } from '../../shared';

import {
    AlbumService,
    AlbumPopupService,
    AlbumComponent,
    AlbumDetailComponent,
    AlbumDialogComponent,
    AlbumPopupComponent,
    AlbumDeletePopupComponent,
    AlbumDeleteDialogComponent,
    albumRoute,
    albumPopupRoute,
} from './';

let ENTITY_STATES = [
    ...albumRoute,
    ...albumPopupRoute,
];

@NgModule({
    imports: [
        TesteJhipSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AlbumComponent,
        AlbumDetailComponent,
        AlbumDialogComponent,
        AlbumDeleteDialogComponent,
        AlbumPopupComponent,
        AlbumDeletePopupComponent,
    ],
    entryComponents: [
        AlbumComponent,
        AlbumDialogComponent,
        AlbumPopupComponent,
        AlbumDeleteDialogComponent,
        AlbumDeletePopupComponent,
    ],
    providers: [
        AlbumService,
        AlbumPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteJhipAlbumModule {}
