import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpResponse,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'buildfiles/environment.local-rp-01';
import {
  AllegatoVO,
  FascicoloVO,
  IstanzaVO,
  MudeopenFoBeService,
  SelectVO,
} from 'mudeopen-common';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { SaveResponseService } from '../../../shared/services/save-response.service';

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
export class PraticheService {
  form:any
  constructor(
    private mudeopenFoBeService: MudeopenFoBeService,
    private httpClient: HttpClient,
    private saveResponse: SaveResponseService
  ) {}

  dettaglioPratica(id: string) {
    return this.mudeopenFoBeService;
  }

  documentiPratica(
    idPratica: number,
    page = 0,
    limit = 10
  ): Observable<HttpResponse<AllegatoVO[]>> {
    this.mudeopenFoBeService.logApi('documentiPratica', [idPratica]);

    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('page', page?.toString())
      .set('size', limit?.toString());

    return this.httpClient
      .get<AllegatoVO[]>(
        `${environment.basePath}/pratiche/documenti/id-pratica/${idPratica}`,
        {
          params: params,
          observe: 'response',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('documentiPratica', response)
        )
      );
  }

  deleteDocumento(idDocumento: any): Observable<any> {
    this.mudeopenFoBeService.logApi('deleteDocumento', [idDocumento]);

    return this.httpClient
      .delete(
        `${environment.basePath}/pratiche/documenti/${encodeURIComponent(
          String(idDocumento)
        )}`,
        {
          //observe: 'response',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('deleteDocumento', response)
        )
      );
  }

  downloadDocumento(idDocumento: string) {
    this.mudeopenFoBeService.logApi('downloadDocumento', [idDocumento]);

    return this.httpClient
      .get(
        `${
          environment.basePath
        }/pratiche/documenti/download/${encodeURIComponent(
          String(idDocumento)
        )}`,
        {
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
          this.mudeopenFoBeService.logApiResult('documentiPratica', response)
        )
      )
      .subscribe((response) => {
        this.saveResponse.saveData(response);
      });
  }

  searchIstanze(pratica: any) {
    return this.mudeopenFoBeService.cercaIstanzeByFilter(
      JSON.stringify({ id_pratica: pratica.id_pratica }),
      0,
      10000
    );
  }

  getPraticaUpload() {
    this.mudeopenFoBeService.logApi('getPraticaUpload', []);
    return this.httpClient
      .get(`${environment.basePath}/pratiche/tipo-doc`)
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getPraticaUpload', response)
        )
      );
  }

  getIstanzaperPratica(idPratica: number) {
    this.mudeopenFoBeService.logApi('getIstanzaPratica', []);
    let params = new HttpParams();
    params = params.append('size', '99999');
    params = params.append('page', '0');
    return this.httpClient
      .get(`${environment.basePath}/istanze/pratica/${idPratica}`, {
        params: params,
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getIstanzaPratica', response)
        )
      );
  }

  getFormIOUpload(
    idPratica: number,
    tipoFile: string,
    nomeFile: string
  ): Observable<SelectVO[]> {
    this.mudeopenFoBeService.logApi('getFormIOUpload', []);
    let params = new HttpParams();
    params = params.append('nomeFile', nomeFile);
    params = params.append('tipoFile', tipoFile);
    return this.httpClient
      .get<any>(
        `${environment.basePath}/pratiche/${idPratica}/template-upload-doc`,
        { params: params }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getFormIOUpload', response)
        )
      );
  }

  /*canConsumeForm(consumes) {
    const form = 'multipart/form-data';
    for (const consume of consumes) {
      if (form === consume) {
        return true;
      }
    }
    return false;
  }*/

  uploadAllegato(idPratica: any, tipo_file: any, file: any, numero_protocollo: string = null, data_protocollo: string = null) {
    this.mudeopenFoBeService.logApi('uploadDocumento', []);
    console.log(tipo_file, file);

    let headers = new HttpHeaders();
   // headers = headers.set('Content-Type', 'multipart/form-data;');

    let formParams;

    formParams = new FormData();

    if (tipo_file !== undefined) {
      formParams =
        formParams.append('documentoPratica',tipo_file) ||
        formParams;
    }
    if (numero_protocollo) {
      formParams =
        formParams.append('numeroProtocollo',numero_protocollo) ||
        formParams;
    }
    if (data_protocollo) {
      formParams =
        formParams.append('dataProtocollo',data_protocollo) ||
        formParams;
    }
    console.log(formParams);
    if (file !== undefined) {
      formParams = formParams.append('file', file) || formParams;
    }
    console.log(formParams.getAll('file'));

    return this.httpClient
      .post(
        `${environment.basePath}/pratiche/documenti/${idPratica}`,
        formParams,
        {
          headers: headers,
          observe: 'body',
        }
      )
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('uploadDocumento', response)
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


}
