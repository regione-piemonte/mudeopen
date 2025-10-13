import {
  HttpClient,
  HttpParams,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'buildfiles/environment.local-rp-01';
import { Form } from 'formiojs';
import { FascicoloVO, IstanzaVO, MudeopenFoBeService } from 'mudeopen-common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

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
export class RicercaService {
  constructor(
    private mudeopenFoBeService: MudeopenFoBeService,
    private httpClient: HttpClient,
  ) {
  }

  searchIstanze(fields: any, page: number, limit: number): Observable<any> {
    const filter = this._createFilterIstanze(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('searchistanze', []);
    return this.httpClient
      .get(`${ environment.basePath }/istanze/search`, {
        params: params,
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('searchIstanze', response),
        ),
      );
  }

  searchFascicoli(fields: any, page, limit) {
    const filter = this._createFilterFascicoli(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page?.toString())
      .set('size', limit?.toString());

    this.mudeopenFoBeService.logApi('searchFascicoli', JSON.stringify(fields));
    return this.httpClient
      .get(`${ environment.basePath }/fascicoli?`, {
        params: params,
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('searchFascicoli', response),
        ),
      );
  }

  searchPraticheDS(fields: any, page?: number, limit?: number) {
    const filter = this._createFilterPratiche(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page?.toString())
      .set('size', limit?.toString());

    this.mudeopenFoBeService.logApi('searchPraticheDS', JSON.stringify(fields));
    return this.httpClient
      .get(`${ environment.basePath }/pratiche?`, {
        params: params,
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('searchPratiche', response),
        ),
      );
  }

  filtriVeloci() {
    this.mudeopenFoBeService.logApi('filtriVeloci', []);
    return this.httpClient
      .get(`${ environment.basePath }/istanze/tipi-stato-filtro-veloce`, {})
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('filtriVeloci', response),
        ),
      );
  }

  excelPraticheDS(fields: any, page: number, limit: number) {
    const filter = this._createFilterPratiche(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('exportExcel', []);
    return this.httpClient
      .get(`${ environment.basePath }/pratiche/export-excel?`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('exportExcel', response),
        ),
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  excelFascicoli(fields: any, page: number, limit: number) {
    const filter = this._createFilterFascicoli(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('exportExcel', []);
    return this.httpClient
      .get(`${ environment.basePath }/fascicoli/exportExcel?`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('exportExcel', response),
        ),
      )
      .subscribe((response) => {
        this._saveData(response);
      });
  }

  excelIstanze(fields: any, page: number, limit: number) {
    const filter = this._createFilterIstanze(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', filter)
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('exportExcel', []);
    return this.httpClient
      .get(`${ environment.basePath }/istanze/export-excel?`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('exportExcel', response),
        ),
      )
      .subscribe((response) => {
        this._saveData(response);
      });
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

  private _createFilterPratiche(fields: any) {
    return JSON.stringify({
      id_intestatario_pf: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pf,
      },
      id_intestatario_pg: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pg,
      },
      numero_protocollo: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.numero_protocollo,
      },
      id_pratica_comunale: {
        eq:
          fields === null || fields === void 0 ? void 0 : fields.id_comune?.id,
      },
      anno: { eq: fields === null || fields === void 0 ? void 0 : fields.anno },
      data_protocollo: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_protocollo_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_protocollo_to,
      },
      data_registrazione_pratica: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_registrazione_pratica_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_registrazione_pratica_to,
      },
      num_pratica: {
        eq: fields === null || fields === void 0 ? void 0 : fields.numero_pratica
      },
     id_pratica: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pratica
      },
      id_fascicolo: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_fascicolo,
      },
      codice_istanza: {
        eq:
          fields === null || fields === void 0 ? void 0 : fields.codice_istanza,
      },
      id_pm: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pm,
      },
      id_comune: {
        eq:
          fields === null || fields === void 0 ? void 0 : fields.id_comune?.id,
      },
      id_provincia: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_provincia?.id,
      },
      id_dug: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_dug?.id,
      },
      duf: { eq: fields === null || fields === void 0 ? void 0 : fields.duf },
      data_creazione: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_to,
      },
      stato: {
        eq: fields === null || fields === void 0 ? void 0 : fields.stato?.id,
      },
      id_tipo_istanza: { eq: "DENUNCIA-SISMICA" },
      _defaultSPs: { in: 'SPE00RP211,SPE00RP184,SPE00RP207,SPE00RP208' }

    });
  }

  private _createFilterFascicoli(fields: any) {
    return JSON.stringify({
      id_tipo_intervento: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_tipo_intervento,
      },
      id_intestatario_pf: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pf,
      },
      id_intestatario_pg: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pg,
      },

      codice_fascicolo: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.codice_fascicolo,
      },
      id_pm: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pm,
      },
      id_comune: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_comune.id,
      },
      id_provincia: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_provincia.id,
      },
      id_dug: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_dug.id,
      },
      duf: { eq: fields === null || fields === void 0 ? void 0 : fields.duf },
      data_creazione: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_to,
      },
      stato: {
        eq: fields === null || fields === void 0 ? void 0 : fields.stato.id,
      },
    
    });
  }

  private _createFilterIstanze(fields: any) {
    return JSON.stringify({
      id_tipo_istanza: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_tipo_istanza?.id,
      },
      id_intestatario_pf: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pf,
      },
      id_intestatario_pg: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_intestatario_pg,
      },
      numero_protocollo: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.numero_protocollo,
      },
      data_dps: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.data_dps,
      },
      id_pratica_comunale: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_comune?.id,
      },
      anno: { eq: fields === null || fields === void 0 ? void 0 : fields.anno },
      data_protocollo: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_protocollo_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_protocollo_to,
      },
      data_registrazione_pratica: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_registrazione_pratica_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_registrazione_pratica_to,
      },
      num_pratica: {
        eq: fields === null || fields === void 0 ? void 0 : fields.num_pratica,
      },
      id_pratica: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pratica
      },
      id_fascicolo: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_fascicolo,
      },

      codice_istanza: {
        eq:
          fields === null || fields === void 0 ? void 0 : fields.codice_istanza,
      },
      id_pm: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pm,
      },
      id_comune: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_comune?.id,
      },
      id_provincia: {
        eq:
          fields === null || fields === void 0
            ? void 0
            : fields.id_provincia?.id,
      },
      id_dug: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_dug?.id,
      },
      duf: { eq: fields === null || fields === void 0 ? void 0 : fields.duf },
      data_creazione: {
        gte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_from,
        lte:
          fields === null || fields === void 0
            ? void 0
            : fields.data_creazione_to,
      },
      stato: {
        eq: fields === null || fields === void 0 ? void 0 : fields.stato?.id,
      },
    });
  }
}
