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
export class SaveResponseService {

  saveData(response: any) {
    const a = document.createElement('a');
    document.body.appendChild(a);
    const dataType = response.body.type;
    const binaryData = [];
    binaryData.push(response.body);

    const blob = new Blob(binaryData, { type: dataType });

    const url = window.URL.createObjectURL(blob);

    a.href = url;
    a.download = this.getFilename(response.headers.get('Content-Disposition'));
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  }

  getFilename(contentDisposition: string) {
    const args = contentDisposition.split(';');
    const filename = args.find((el) => el.includes('filename='));
    const parts = filename.split('=');
    const withoutSpecial = parts[1].substring(1, parts[1].length - 1);
    return withoutSpecial;
  }

}
