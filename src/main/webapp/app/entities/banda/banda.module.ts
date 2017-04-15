import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesteJhipSharedModule } from '../../shared';

import {
    BandaService,
    BandaPopupService,
    BandaComponent,
    BandaDetailComponent,
    BandaDialogComponent,
    BandaPopupComponent,
    BandaDeletePopupComponent,
    BandaDeleteDialogComponent,
    bandaRoute,
    bandaPopupRoute,
} from './';

let ENTITY_STATES = [
    ...bandaRoute,
    ...bandaPopupRoute,
];

@NgModule({
    imports: [
        TesteJhipSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BandaComponent,
        BandaDetailComponent,
        BandaDialogComponent,
        BandaDeleteDialogComponent,
        BandaPopupComponent,
        BandaDeletePopupComponent,
    ],
    entryComponents: [
        BandaComponent,
        BandaDialogComponent,
        BandaPopupComponent,
        BandaDeleteDialogComponent,
        BandaDeletePopupComponent,
    ],
    providers: [
        BandaService,
        BandaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteJhipBandaModule {}
