import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'buildfiles/environment.local-rp-01';
import { AllegatoVO, IstanzaVO, MudeopenFoBeService } from 'mudeopen-common';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { SelectVO } from 'mudeopen-common/lib/swagger-api/model/selectVO';

class CustomHttpUrlEncodingCodec extends HttpUrlEncodingCodec {
  encodeKey(k) {
    k = super.encodeKey(k);
    return k.replace(/\+/gi, '%2B');
  }

  encodeValue(v) {
    v = super.encodeValue(v);
    return v.replace(/\+/gi, '%2B');
  }
}

@Injectable({
  providedIn: 'root',
})
export class IstanzaService {
  form:any;
  constructor(
    private httpClient: HttpClient,
    private mudeopenFoBeService: MudeopenFoBeService
  ) {}

  getIstanza(idIstanza: string) {
    this.mudeopenFoBeService.logApi('getIstanza', [idIstanza]);

    return this.httpClient
      .get(`${environment.basePath}/istanze/${idIstanza}?scope=backoffice`)
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getIstanza', response)
        )
      );
  }


  getGeneraTracciato(idIstanza: number) {
    this.mudeopenFoBeService.logApi('getGeneraTracciato', [idIstanza]);

    return this.httpClient
      .get(`${environment.basePath}/istanze/istanze-tracciato/${idIstanza}`)
      .pipe(tap((response) =>
          this.mudeopenFoBeService.logApiResult('getGeneraTracciato', response)
        )
      );
  }


  getListaNotifiche(idIstanza: any, page: number, size: number){
    this.mudeopenFoBeService.logApi('getListaNotifiche', [idIstanza]);

    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('page', page?.toString())
      .set('size',size?.toString());
    return this.httpClient
      .get(
        `${ environment.basePath }/notifiche/id-istanza/${ idIstanza }`,
        {
          params: params,
          observe: 'response',
        },
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getListaNotifiche', response),
        ),
      );
  }

  getAllegati(idIstanza:any, page = 0, limit = 10) {
    this.mudeopenFoBeService.logApi('getAllegati', [idIstanza]);

    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('page', page?.toString())
      .set('size', limit?.toString());
    return this.httpClient
      .get<AllegatoVO[]>(
        `${environment.basePath}/allegati/id-istanza/${idIstanza}`,
        {
          params: params,
          observe: 'response',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getAllegati', response)
        )
      );
  }

  downloadIstanza(firma: any, idIstanza: number) {
    this.mudeopenFoBeService.logApi('downloadIstanza', []);
    const params = new HttpParams({
      encoder: new CustomHttpUrlEncodingCodec(),
    }).set('con_firma', firma);

    return this.httpClient
      .get(`${environment.basePath}/istanze/download/${idIstanza}`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        catchError((err) => {
          console.log(err);
          //  TODO handle error 403
          return of(null);
        }),
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('downlaodIstanza', response)
        )
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  downloadAllegato(firma: any, uuid: any) {
    this.mudeopenFoBeService.logApi('downloadAllegato', []);
    const params = new HttpParams({
      encoder: new CustomHttpUrlEncodingCodec(),
    }).set('con_firma', firma);

    return this.httpClient
      .get(
        `${environment.basePath}/allegati/download/${uuid}?scope=backoffice`,
        {
          params: params,
          responseType: 'blob',
          observe: 'response',
        }
      )
      .pipe(
        catchError((err) => {
          console.log(err);
          //  TODO handle error 403
          return of(null);
        }),
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('downlaodAllegato', response)
        )
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  downloadRicevuta(idIstanza: any) {
    const params = new HttpParams({
      encoder: new CustomHttpUrlEncodingCodec(),
    });

    this.mudeopenFoBeService.logApi('downloadRicevuta', []);
    return this.httpClient
      .get(
       /* `${environment.basePath}/istanze/ricevuta-pdf/${idIstanza}?user-cf=${userCF}`,*/
        `${environment.basePath}/istanze/ricevuta-pdf/${idIstanza}`,

        {
          responseType: 'blob',
          observe: 'response',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('downloadRicevuta', response)
        )
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  downloadExcelAllegati(idIstanza: any) {
    //const filter=
    const params = new HttpParams({
      encoder: new CustomHttpUrlEncodingCodec(),
    });

    this.mudeopenFoBeService.logApi('exportExcel', []);
    return this.httpClient
      .get(
        `${environment.basePath}/allegati/exportExcel/id-istanza/${idIstanza}`,
        {
          responseType: 'blob',
          observe: 'response',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('exportExcel', response)
        )
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  getPossibiliStatiIstanza(idIstanza: string): Observable<SelectVO[]> {
    this.mudeopenFoBeService.logApi('getPossibiliStatiIstanza', [idIstanza]);

    return this.httpClient
      .get<SelectVO[]>(
        `${environment.basePath}/istanze/${idIstanza}/possibile-stato`
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult(
            'getPossibiliStatiIstanza',
            response
          )
        )
      );
  }
  getNotificationsType(): Observable<SelectVO[]> {
    this.mudeopenFoBeService.logApi('getNotificationsType', null);

    return this.httpClient
      .get<SelectVO[]>(
        `${environment.basePath}/notifiche/tipi-notifica`
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult(
            'getNotificationsType',
            response
          )
        )
      );
          }

  getFormIOStato(
    istanza: IstanzaVO,
    nuovoStato: string
  ): Observable<SelectVO[]> {
    this.mudeopenFoBeService.logApi('getFormIOStato', [
      istanza.id_istanza,
      nuovoStato,
    ]);
    // TODO replace with new API name /istanze/${ istanza.id_istanza }/template-cambia-stato/${ nuovoStato }
    return this.httpClient
      .get<SelectVO[]>(
        `${environment.basePath}/istanze/${istanza.id_istanza}/template-cambia-stato/${nuovoStato}`
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getFormIOStato', response)
        )
      );
  }

  cambiaStato(istanza: IstanzaVO, formNotifica: any, nuovoStato: string) {
    this.mudeopenFoBeService.logApi('cambiaStato', [
      istanza.id_istanza,
      formNotifica,
      nuovoStato,
    ]);

    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');

    return this.httpClient
      .put(
        `${environment.basePath}/istanze/${istanza.id_istanza}/cambia-stato/${nuovoStato}?scope=backoffice`,
        JSON.stringify({ json_data: JSON.stringify({ ...formNotifica }) }),
        { headers: headers }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('cambiaStato', response)
        )
      );
  }
  getFormIONotifica(
    idIstanza: number,
    idTipoNotifica:any
  
  ): Observable<SelectVO[]> {
    this.mudeopenFoBeService.logApi('getFormIONotifica', []);
    //let params = new HttpParams();
   
    return this.httpClient
      .get<any>(
        `${environment.basePath}/notifiche/${idIstanza}/template-nuova-notifica/${idTipoNotifica}`,
        //{ params: params }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getFormIONotifica', response)
        )
      );
  }

  setFormItems(form: any) {
  
    this.form = JSON.stringify(form);
    console.log(this.form)
    
      }
      getFormItems() {
        return JSON.parse(this.form ? this.form : null );
      }


submitFormIO(idIstanza: any, formNotifica,idTipoNotifica:any) {
  this.mudeopenFoBeService.logApi('submitFormIO', [
   idIstanza
  ]);

  let headers = new HttpHeaders();
  headers = headers.set('Content-Type', 'application/json; charset=utf-8');

  return this.httpClient
    .put(
      `${environment.basePath}/notifiche/${idIstanza}/nuova-notifica/${idTipoNotifica}`,
      JSON.stringify({ json_data: JSON.stringify({ ...formNotifica }) }),
      { headers: headers }
    )
    .pipe(
      tap((response) =>
        this.mudeopenFoBeService.logApiResult('submitFormIO', response)
      )
    );
}
  private _saveData(response: any) {
    const a = document.createElement('a');
    document.body.appendChild(a);
    const dataType = response.body.type;
    const binaryData = [];
    binaryData.push(response.body);

    const blob = new Blob(binaryData, { type: dataType });

    const url = window.URL.createObjectURL(blob);

    a.href = url;
    a.download = this._getFilename(response.headers.get('Content-Disposition'));
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  }

  private _getFilename(contentDisposition: string) {
    const args = contentDisposition.split(';');
    const filename = args.find((el) => el.includes('filename='));
    const parts = filename.split('=');
    const withoutSpecial = parts[1].substring(1, parts[1].length - 1);
    return withoutSpecial;
  }
}
