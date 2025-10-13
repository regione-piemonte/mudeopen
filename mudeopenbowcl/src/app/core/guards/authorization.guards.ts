import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { take, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { PermissionService } from '../services/permission.service';

@Injectable()
export class AuthorizationGuard implements CanActivate, CanActivateChild {
  constructor(
    private userPermissionService: PermissionService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this._checkActivation(route, state);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this._checkActivation(route, state);
  }

  private _checkActivation(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    if (!this.userPermissionService.hasPermissions()) {
      this.router.navigate(['backoffice', 'no-role-assigned']);
    }

    let permission: string;

    if (route.data.permission !== undefined) {
      permission = route.data.permission;
    }

    if (!permission) {
      throw new Error('AuthGuard: resource not found');
    }

    return this.userPermissionService.checkPermission(permission).pipe(
      tap((authorized) => {
        if (!authorized) {
          this.router.navigate(['backoffice', 'not-authorized']);
        }
      }),
      take(1)
    );
  }
}
