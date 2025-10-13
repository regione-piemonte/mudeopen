import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { RicercaService } from '../../../shared/services/ricerca.service';
import { MudeopenFoBeService } from 'mudeopen-common';

@Injectable({
  providedIn: 'root',
})
export class PraticaResolverService implements Resolve<any> {
  constructor(
    private service: RicercaService,
    private mudeBoService: MudeopenFoBeService,
    private router: Router,
  ) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<any> | Promise<any> | any {
    return this.service.searchPraticheDS({ id_pratica: route.paramMap.get('id') }, 0, 2).pipe(
      catchError((error) => {
        this.mudeBoService.spinnerHide();
        this.router.navigate(['backoffice', 'lista-pratiche']);
        return of(null);
      }),
    );
  }
}
