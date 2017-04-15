import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteJhipSharedModule } from '../../shared';

import {
    MusicaService,
    MusicaPopupService,
    MusicaComponent,
    MusicaDetailComponent,
    MusicaDialogComponent,
    MusicaPopupComponent,
    MusicaDeletePopupComponent,
    MusicaDeleteDialogComponent,
    musicaRoute,
    musicaPopupRoute,
} from './';

let ENTITY_STATES = [
    ...musicaRoute,
    ...musicaPopupRoute,
];

@NgModule({
    imports: [
        TesteJhipSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MusicaComponent,
        MusicaDetailComponent,
        MusicaDialogComponent,
        MusicaDeleteDialogComponent,
        MusicaPopupComponent,
        MusicaDeletePopupComponent,
    ],
    entryComponents: [
        MusicaComponent,
        MusicaDialogComponent,
        MusicaPopupComponent,
        MusicaDeleteDialogComponent,
        MusicaDeletePopupComponent,
    ],
    providers: [
        MusicaService,
        MusicaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteJhipMusicaModule {}
