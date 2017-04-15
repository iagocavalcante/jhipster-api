import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MusicaComponent } from './musica.component';
import { MusicaDetailComponent } from './musica-detail.component';
import { MusicaPopupComponent } from './musica-dialog.component';
import { MusicaDeletePopupComponent } from './musica-delete-dialog.component';

import { Principal } from '../../shared';


export const musicaRoute: Routes = [
  {
    path: 'musica',
    component: MusicaComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.musica.home.title'
    }
  }, {
    path: 'musica/:id',
    component: MusicaDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.musica.home.title'
    }
  }
];

export const musicaPopupRoute: Routes = [
  {
    path: 'musica-new',
    component: MusicaPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.musica.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'musica/:id/edit',
    component: MusicaPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.musica.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'musica/:id/delete',
    component: MusicaDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.musica.home.title'
    },
    outlet: 'popup'
  }
];
