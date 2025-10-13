import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { RicercaService } from '../../../shared/services/ricerca.service';
import { PraticheService } from './pratiche.service';
import { MudeopenFoBeService } from 'mudeopen-common';

@Injectable({
  providedIn: 'root',
})
export class DocumentiPraticaResolverService implements Resolve<any> {
  constructor(
    private service: PraticheService,
    private mudeBoService: MudeopenFoBeService,
    private router: Router,
  ) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<any> | Promise<any> | any {
    return this.service.documentiPratica(+route.paramMap.get('id')).pipe(
      catchError((error) => {
        this.mudeBoService.spinnerHide();
        this.router.navigate(['backoffice', 'lista-pratiche']);
        return of(null);
      }),
    );
  }
}
