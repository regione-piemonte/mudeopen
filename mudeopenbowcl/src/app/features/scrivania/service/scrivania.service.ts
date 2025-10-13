import {
  HttpClient,
  HttpParams,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'buildfiles/environment.local-rp-01';
import { MudeopenFoBeService } from 'mudeopen-common';
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
export class ScrivaniaService {
  constructor(
    private httpClient: HttpClient,
    private mudeopenFoBeService: MudeopenFoBeService
  ) {}

  // getIstanzeDepositate(page, limit) {
  //   return this.mudeopenFoBeService.cercaIstanzeByFilter(
  //     JSON.stringify({
  //       stato: {
  //         in: 'DPS,PRC',
  //       },
  //     }),
  //     page,
  //     limit
  //   );
  // }

  getIstanzeDepositate(page, limit) {
    const fields = JSON.stringify({
      stato: {
        in: 'DPS,PRC',
      },
    });
  //  const filter = this._createFilterIstanze(fields);
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', fields)
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('getIstanzeDepositate', []);
    return this.httpClient
      .get(`${environment.basePath}/istanze/scrivania`, {
        params: params,
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult(
            'getIstanzeDepositate',
            response
          )
        )
      );
  }
  downloadExcel() {
    const filter = JSON.stringify({
      stato: {
        in: 'DPS,PRC',
      },
    });
    this.mudeopenFoBeService.logApi('exportExcel', []);
    const params = new HttpParams().set('filter', filter);
    return this.httpClient
      .get(`${environment.basePath}/istanze/export-excel`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('exportExcel', response)
        )
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
        eq: fields === null || fields === void 0 ? void 0 : fields.num_pratica,
      },
      id_pratica: {
        eq: fields === null || fields === void 0 ? void 0 : fields.id_pratica,
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
    });
  }
}
