import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { RicercaService } from '../../../shared/services/ricerca.service';
import { MudeopenFoBeService } from 'mudeopen-common';
import { IstanzaService } from './istanza.service';

@Injectable({
  providedIn: 'root',
})

export class IstanzaResolverService implements Resolve<any> {
    constructor(
      private service: RicercaService,
      private mudeBoService: MudeopenFoBeService,
      private router: Router,
      private istanzaService: IstanzaService
    ) {
    }
    resolve(route: ActivatedRouteSnapshot): Observable<any> | Promise<any> | any {
        console.log("resolve", route.paramMap.get('id'))
        return this.istanzaService.getIstanza(route.paramMap.get('id')).pipe(
          catchError((error) => {
            this.mudeBoService.spinnerHide();
            this.router.navigate(['backoffice', 'lista-istanze']);
            return of(null);
          }),
        );
      }
    }