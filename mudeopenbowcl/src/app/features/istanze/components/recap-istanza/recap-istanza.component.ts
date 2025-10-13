import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IstanzaVO } from 'mudeopen-common';
import { IstanzaBO } from '../../../../shared/model/istanzaBO.model';

@Component({
  selector: 'app-recap-istanza',
  templateUrl: './recap-istanza.component.html',
  styleUrls: ['./recap-istanza.component.css']
})
export class RecapIstanzaComponent implements OnInit {

  istanza: IstanzaBO;

  constructor(
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.istanza = this.activatedRoute.snapshot.data['istanza'];

    if(!(this.istanza as any).extra_columns)
      (this.istanza as any).extra_columns = {};
    else if(typeof (this.istanza as any).extra_columns == 'string')
      (this.istanza as any).extra_columns = JSON.parse((this.istanza as any).extra_columns);
  }

  searchSelected: number = 0;
  selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

  get additionalTabs(): any {
    const res = Object.keys((this.istanza as any).extra_columns || {})
          .filter(key => key.match(/^TB[0-9]$/))
          .map(key => { 
            return { type: key, label: (this.istanza as any).extra_columns[key]};
    });

    return res;
  }

  getColumns(type) {
    const raw = (this.istanza as any).extra_columns || {};
    const filtered = Object.keys(raw)
    .reduce((obj, key) => {
      if(key.startsWith(type + '-')) {
        obj[key.substring(4)] = raw[key];
      }

      return obj;
    }, {});

    return filtered;
  }

  isIdfPresent(istanza: any): boolean {
    return !!istanza.idf_index_node;
  }

  isIdfApproved(istanza: any): string {
    return istanza?.idf_autorizzato != 'S'? '' : (istanza?.idf_autorizzato?'concessa':'negata');
  }
}
