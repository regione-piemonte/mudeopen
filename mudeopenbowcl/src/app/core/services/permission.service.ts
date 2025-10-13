import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthStoreService, SelectVO } from 'mudeopen-common';
import { UtenteBO } from '../models/utenteBO.model';

@Injectable()
export class PermissionService {
  private _userActions$: BehaviorSubject<Record<string, string>[]> = new BehaviorSubject([]);
  private _userRoles$: BehaviorSubject<SelectVO[]> = new BehaviorSubject([]);
  private _hasPermission = false;


  constructor(
    private authStoreService: AuthStoreService,
  ) {
    this._init();
  }

  private _init() {
    const boUser = (this.authStoreService.getUser() as UtenteBO);
    if (!boUser && !boUser?.funzioniUtente) {
      this._userActions$.next([]);
      this._userRoles$.next([]);
      return;
    }

    this._hasPermission = true;
    this._userActions$.next(boUser.funzioniUtente);
    this._userRoles$.next(boUser.ruoliUtente);
  }

  private _getUserActions(): Observable<Record<string, string>[]> {
    return this._userActions$.asObservable();
  }

  private _getUserRoles(): Observable<SelectVO[]> {
    return this._userRoles$.asObservable();
  }

  hasPermissions(): boolean {
    return this._hasPermission;
  }

  checkPermission(tag: string): Observable<boolean> {
    return this._getUserActions().pipe(
      map((permissions: Record<string, string>[]) => {
        const hasPermissions = permissions.find((action: Record<string, string>) => action.cod_funzione === tag);
        return !!hasPermissions;
        }
      )
    );
  }

  checkRole(tag: string): Observable<boolean> {
    return this._getUserRoles().pipe(
      map((roles: SelectVO[]) => {
        return roles.some((role: SelectVO) => role.id === tag);
      })
    );
  }
}
