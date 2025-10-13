/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot, CanLoad,
  Route, Router, RouterStateSnapshot, UrlSegment
} from '@angular/router';

import { AuthStoreService } from 'mudeopen-common';
import { Observable } from 'rxjs';


@Injectable()
export class AuthGuard implements CanLoad {
  constructor(
    private router: Router,
    private authStoreService: AuthStoreService,
  ) {}

  canLoad(
    route: Route,
    segments: UrlSegment[]
  ): boolean | Observable<boolean> | Promise<boolean> {
    if (this.authStoreService.isBOAuthenticted()) { return true; }
    return false;
  }


  /*
  * nome metodo "canActivate"; method description:
  * @param (route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
  * @returns
  */

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this._checkActivation(route, state);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    return this._checkActivation(route, state);
  }

  private _checkActivation(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    if (this.authStoreService.isBOAuthenticted()) { return true; }
    this.router.navigate(['user-not-found']);
    return false;
  }
}
