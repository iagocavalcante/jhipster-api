import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TesteJhipAlbumModule } from './album/album.module';
import { TesteJhipBandaModule } from './banda/banda.module';
import { TesteJhipMusicaModule } from './musica/musica.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TesteJhipAlbumModule,
        TesteJhipBandaModule,
        TesteJhipMusicaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesteJhipEntityModule {}
