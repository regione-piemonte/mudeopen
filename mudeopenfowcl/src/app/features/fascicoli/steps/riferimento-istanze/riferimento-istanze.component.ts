import { Component, Inject, Injector, OnInit } from '@angular/core';
import * as model from 'mudeopen-common';
import { CONFIG, MudeopenFoBeService, StepConfig } from 'mudeopen-common';
import { OnStepInit, StepComponent } from '../step.component';
import { AggiungiRiferimentoIstanzaComponent } from './aggiungi-riferimento-istanza/aggiungi-riferimento-istanza.component';

@Component({
  selector: 'app-riferimento-istanze',
  templateUrl: './riferimento-istanze.component.html',
  styleUrls: ['./riferimento-istanze.component.scss']
})
export class RiferimentoIstanzeComponent extends StepComponent implements OnStepInit {

  rows: any = [];
  columnsIstanze: any = [
    {name: 'Titolo Edilizio o altra istanza di riferimento', prop: 'descrizione_istanza', width: '148'},
    {name: 'Numero MUDE', prop: 'codice_istanza', width: '148'},
    {name: 'Numero Protocollo istanza', prop: 'numero_pratica', width: '128'},
    {name: 'Data protocollo istanza', prop: 'data_pratica', width: '108'},
    {name: 'Protocollo / repertorio pratica comunale', prop: 'numero_protocollo_comune', width: '125'},
    {name: 'Data Protocollo / Repertorio pratica comunale', prop: 'data_protocollo_comune', width: '88'}
  ];

  constructor(mudeopenFoBeService: MudeopenFoBeService,
              injector: Injector,
              @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);

    this.datatable.messages.emptyMessage = 'Inserire le istanze di riferimento';
  }

  public title: string = "17. ISTANZE DI RIFERIMENTO";
  public description: string = "Estremi del Titolo Abilitativo e altre istanze a cui si riferisce la presente istanza";

  onStepLoader(jsondata: any, isNew: boolean) {
    if(this.quadro.json_configura_quadro?.title) {
      this.title = this.quadro.json_configura_quadro.title; }

    if(this.quadro.json_configura_quadro?.description) {
      this.description = this.quadro.json_configura_quadro.description; }

    if(isNew) {
      jsondata.rifeimento_istanze = []; // set reference instances

      let  ist2import = [];
      if(this.formsService.istanza?.istanza_riferimento) {
        ist2import.push(this.formsService.istanza?.istanza_riferimento); }
      if(this.formsService.istanza?.istanze_collegate) {
        ist2import = [ ... ist2import, ... this.formsService.istanza?.istanze_collegate ]; }

      ist2import.forEach(istRef => {
        if(istRef.tipologia_istanza.id == 'INT-DOC') return;

        jsondata.rifeimento_istanze.push(this._addFromIstanza(istRef)); 
      });

      if(jsondata.rifeimento_istanze.length) {
        this.storeJsonDataDetailed(null, true, this.isStepValidationOK)?.subscribe((res) => {
          this.rows = this.stepQuadroData.rifeimento_istanze;    
        });
      }

    }

    this.rows = jsondata.rifeimento_istanze || [];
    return false;
  }

  public addNewReference() {
    const modal = this.modalService.open(AggiungiRiferimentoIstanzaComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    modal.componentInstance.istanza = this.formsService.istanza;
    modal.componentInstance.excludeIstances = this.rows.map(x => { 
      if(x.id_istanza)
        return x.id_istanza;
    }).filter(x => !!x);
    modal.componentInstance.onConfirm.subscribe((data: any) => {
      let istRef: any = this.rows.find(x => x.codice_istanza == data.codice_istanza || x.numero_protocollo_comune == data.numero_protocollo_comune);
      if(!istRef){
        istRef = {};
        this.rows.push(istRef); 
      }

      if(data.codice_istanza) { // is from IstanzaVO fascicolo
        this._addFromIstanza(data, istRef); }
      else { // istanza from outside MUDEOPEN
        istRef.descrizione_istanza = data.descrizione || '';
        istRef.codice_istanza = '';

        istRef.numero_protocollo_comune = data.numero_protocollo_comune || '';
        istRef.data_protocollo_comune = data.data_protocollo_comune || '';
      }

      this.storeJsonDataDetailed(null, true, this.isStepValidationOK)?.subscribe((res) => {
        // refresh datatable
        this.rows = this.stepQuadroData.rifeimento_istanze;
        modal.dismiss();
      });
    });
  }

  _addFromIstanza(istanza: model.IstanzaVO, istRef: any = {}): model.IstanzaVO {
    istRef.descrizione_istanza = istanza.tipologia_istanza.value;
    istRef.codice_istanza = istanza.codice_istanza;

    const jdata: any = istanza.json_data;
    const praticaInfo = ((jdata?.CAMBIO_STATO_APA?.numPratica || '') || ((jdata?.CAMBIO_STATO_APA?.numPraticaNew || '' ) + ' - ' + jdata?.CAMBIO_STATO_APA?.anno)) + ' ';

    if(istanza.id_istanza)
      istRef.id_istanza = istanza.id_istanza;

    istRef.numero_pratica = istanza.numero_protocollo || jdata?.CAMBIO_STATO_APA?.numero || '';
    istRef.data_pratica = istanza.data_protocollo || jdata?.CAMBIO_STATO_APA?.data_delibera || '';

    istRef.numero_protocollo_comune = praticaInfo.substring(0, praticaInfo.indexOf(' '));
    istRef.data_protocollo_comune = jdata?.CAMBIO_STATO_APA?._data_creazione_protocollo || praticaInfo.substring(praticaInfo.lastIndexOf(' ')+1);

    return istRef;
  }

  canDeleteIstanza(rowIndex) {
    return rowIndex > 0 || !this.formsService.istanza?.istanza_riferimento;
  }

  deleteIstanza(row: model.IstanzaVO) {
    this.confirmDialog("Vuoi eliminare il riferimento?", () => {
      this.rows.splice(this.rows.indexOf(row), 1);
      this.storeJsonDataDetailed(null, true, this.isStepValidationOK)?.subscribe((res) => {
        this.rows = this.stepQuadroData.rifeimento_istanze;    
      });
    });
  }

  get isStepValidationOK(): boolean {
    return this.setStepCompleteState(!this.isApplicaSi || !!this.rows.length);
  };

  onApplica(isChecked, chkVal) {
    this.stepQuadroData.applica = chkVal != '0'? true : false;
    if(chkVal == '0') {
      if(!!this.formsService.istanza.id_istanza_riferimento && this.rows.length >= 1) {
        this.rows = [ this.rows[0] ]; }
      else {
        this.rows = []; }

      this.stepQuadroData.rifeimento_istanze = this.rows;
    }

    this.storeJsonDataDetailed(null, true, this.isStepValidationOK)?.subscribe((res) => {
      if(res == undefined) { res = null; } // SQ crap
    });

}

  get isApplicaSi(): boolean {
    return this.stepQuadroData?.applica || this.stepQuadroData?.applica == undefined;
  }

  get hasReferenceInstance(): boolean {
    return !!this.formsService.istanza.id_istanza_riferimento 
              && (this.formsService.istanza?.tipologia_istanza?.id != 'REL-ILL-DS' || !!this.formsService.istanza?.istanza_riferimento?.id_istanza_riferimento);
  }


}
