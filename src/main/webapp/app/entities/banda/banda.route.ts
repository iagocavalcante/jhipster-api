import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BandaComponent } from './banda.component';
import { BandaDetailComponent } from './banda-detail.component';
import { BandaPopupComponent } from './banda-dialog.component';
import { BandaDeletePopupComponent } from './banda-delete-dialog.component';

import { Principal } from '../../shared';


export const bandaRoute: Routes = [
  {
    path: 'banda',
    component: BandaComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.banda.home.title'
    }
  }, {
    path: 'banda/:id',
    component: BandaDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.banda.home.title'
    }
  }
];

export const bandaPopupRoute: Routes = [
  {
    path: 'banda-new',
    component: BandaPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.banda.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'banda/:id/edit',
    component: BandaPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.banda.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'banda/:id/delete',
    component: BandaDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'testeJhipApp.banda.home.title'
    },
    outlet: 'popup'
  }
];
