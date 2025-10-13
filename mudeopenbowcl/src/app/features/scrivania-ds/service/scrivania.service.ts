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

  getIstanzeDepositate(filters: any, page: number, limit: number) {
    const params = new HttpParams({ encoder: new CustomHttpUrlEncodingCodec() })
      .set('filter', JSON.stringify(filters))
      .set('page', page.toString())
      .set('size', limit.toString());
    this.mudeopenFoBeService.logApi('getIstanzeDepositate', []);
    return this.httpClient.get(`${environment.basePath}/istanze/scrivania`, {
        params: params,
        observe: 'response'
      })
      .pipe(
        tap((response) =>
          this.mudeopenFoBeService.logApiResult('getIstanzeDepositate', response)
        )
      );
  }

  downloadExcel(filters: any) {
    this.mudeopenFoBeService.logApi('exportExcel', []);
    const params = new HttpParams().set('filter', filters);
    return this.httpClient.get(`${environment.basePath}/istanze/export-excel`, {
        params: params,
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        tap((response) => this.mudeopenFoBeService.logApiResult('exportExcel', response))
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
}
