import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs/operators';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { IstanzaPermissionsManagerComponent } from '../components/istanza-permissions-manager/istanza-permissions-manager.component';
import { SelezionaNuovaInstazaComponent } from '../components/seleziona-nuova-instaza/seleziona-nuova-instaza.component';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;






@Injectable({
  providedIn: 'root'
})
export class FormsService {

  profilo: model.ProfiloResponse = [] as model.ProfiloResponse;

  _currentContext: string;
  _isReadOnlyMode: boolean = false;
  fascicolo: model.FascicoloVO = [] as model.FascicoloVO;
  istanza: model.IstanzaVO = [] as model.IstanzaVO;
  istanzaParent: model.IstanzaVO = [] as model.IstanzaVO;

  showAllFormIOKeys: boolean = false;
  removeAllFormIOControls: boolean = false;
  doNotCheckFormIOModify: boolean = false;

  constructor(private modalService: NgbModal,
    private mudeopenFoBeService: MudeopenFoBeService,
    private router: Router) {
    
  }

	/* 
	* nome metodo "resetContext"; method description: 
	* @param ()
	* @returns 
	*/ 

  resetContext() {
    this._currentContext = null;
    this._isReadOnlyMode = false;

    this.fascicolo = null;
    this.istanza = null;
    this.istanzaParent = null;
  }


	/* 
	* nome metodo "setContext"; method description: 
	* @param (currentContext: string, readOnly: boolean, fascicolo: any = null, istanza: any = null)
	* @returns void
	*/ 

  setContext(currentContext: string, readOnly: boolean, fascicolo: any = null, istanza: any = null, istanzaParent: any = null): void {
    this._currentContext = currentContext;
    this._isReadOnlyMode = readOnly;

    this.fascicolo = fascicolo;
    this.istanza = istanza;
    this.istanzaParent = istanzaParent;
  }

  get id_fascicolo(): number {
    return this.fascicolo?.id_fascicolo || this.istanza?.id_fascicolo;
  }

  get id_istanza(): number {
    return this.istanza?.id_istanza;
  }

  get pullCurrentContextName(): string {
    const context = this._currentContext;
    this._currentContext = null;

    return context;
  }

  get isReadOnlyMode(): boolean {
    return this._isReadOnlyMode;
  }

  public _boTemplateOverride: any;
  get boTemplateOverride(): string {
    return this._boTemplateOverride?.parameter;
  }

  set boTemplateOverride(val: string) {
    if(!val) { 
      this._boTemplateOverride = null;   }
    else {
      this._boTemplateOverride = { parameter: val };
      this._boTemplateOverride.template_ver = val.replace(/^([0-1]*)_(.*)$/, '$1');
      this._boTemplateOverride.tipologia_istanza = val.replace(/^([0-1]*)_(.*)$/, '$2');
    }
  }


	/* 
	* nome metodo "addIstanzaInFascicolo"; method description: 
	* @param (fascicolo: model.FascicoloVO, entry: string = 'nuova-istanza')
	* @returns 
	*/ 

  addIstanzaInFascicolo(fascicolo: model.FascicoloVO, istanzaParent: model.IstanzaVO = null) {
    // NEW "ISTSUCC"
    const modal = this.modalService.open(SelezionaNuovaInstazaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.idComune = fascicolo.comune.id;
    modal.componentInstance.idIstanza = istanzaParent;
    modal.componentInstance.idFascicolo = fascicolo.id_fascicolo;

    modal.componentInstance.confirmEvent.subscribe((newIstanzaInfo) => {
      if(newIstanzaInfo) {
        if(newIstanzaInfo.tipologiaIstanza) {
          fascicolo.tipologia_istanza = newIstanzaInfo.tipologiaIstanza; }
    
        this.setContext('nuova-istanza', false, fascicolo, null, newIstanzaInfo.istanzaParent);
        this.router.navigate(['/fascicolo/gestione/dettagli-fascicolo']);
      }

      modal.dismiss();
    });
  }


	/* 
	* nome metodo "editFascicolo"; method description: 
	* @param (fascicolo: any, readonly: boolean = false)
	* @returns 
	*/ 

  editFascicolo(fascicolo: any, readonly: boolean = false) {
    this.setContext('view-folder', readonly, fascicolo);
    this.router.navigate(['/fascicolo/gestione/dettagli-fascicolo']);
  }


	/* 
	* nome metodo "viewFascicolo"; method description: 
	* @param (fascicolo: any)
	* @returns 
	*/ 

  viewFascicolo(fascicolo: any) {
    this.editFascicolo(fascicolo, true);
  }


	/* 
	* nome metodo "editIstanza"; method description: 
	* @param (istanza: model.IstanzaVO, readonly: boolean = false)
	* @returns 
	*/ 

  editIstanza(istanza: model.IstanzaVO, readonly: boolean = false) {
    this.setContext('view-instance', readonly, null, istanza);
    this.router.navigate(['/fascicolo/gestione/istanza']);
  }

  checkStatus(istanza: model.IstanzaVO, status: string) {
    if(status == 'APA')
      status += ',SAI,ASR,ITS,ARC,ANN,ACO,CON,ACC,ICI,AIC,SIC,VIG,IAI,INC,ATI,INT,AFL,ICO,COC,RES,RSU,COL,DRE,SIA';

    return status.indexOf(istanza?.stato_istanza?.id || '-') > -1;
  }

  canDisplayDetails(istanza: model.IstanzaVO, idFonte: string = null):boolean {
    return istanza.visualizza && (!idFonte || istanza.fonte == idFonte);
  }

  showNonMudeDetails(istanza: model.IstanzaVO, idFonte: string):boolean {
    return istanza.visualizza && istanza.fonte != idFonte ;
  }

  isThereIdfFile(istanza: model.IstanzaVO):boolean {
    return !!istanza.idf_index_node;
  }

	/* 
	* nome metodo "viewIstanza"; method description: 
	* @param (istanza: model.IstanzaVO)
	* @returns 
	*/ 

  viewIstanza(istanza: model.IstanzaVO) {
    this.editIstanza(istanza, true)
  }
  
  displayPermissionsManagerPopup(loggedUser: model.UtenteVO, idFascicolo: number, idIstanza: number = null, idTemplate: number = null) {
    const modal = this.modalService.open(IstanzaPermissionsManagerComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.loggedUser = loggedUser;
    modal.componentInstance.idFascicolo = idFascicolo;
    modal.componentInstance.idIstanza = idIstanza;
    modal.componentInstance.idTemplate = idTemplate;
    modal.componentInstance.mode = idFascicolo?'fascicolo':'istanza';
    modal.componentInstance.doesStatusAllowChanges = !!idFascicolo || "DPS,RPA,APA,SAI,ASR,ITS,ARC,ANN,ACO,CON,ACC,ICI,AIC,SIC,VIG,IAI,INC,ATI,INT,AFL,ICO,COC,RES,RSU,COL,DRE,SIA".indexOf(this.istanza?.stato_istanza?.id || '-') == -1;
    
    return modal.componentInstance.confirmEvent.pipe(tap(x => modal.dismiss() ));
  }

  hasUserPermission(funzione: string = null, abilitazione: string = null): boolean {
    return this.istanza?.abilitazioni 
          && this.mudeopenFoBeService.hasUserPermission(funzione, abilitazione, this.istanza?.abilitazioni);
  }

  getEnteProperty(property: string = null): string {
    return (this.istanza.proprieta||[]).find(_ => _.id == property)?.value;
  }

  canConsolidateInstance(istanza: model.IstanzaVO = null): boolean {
    if(!istanza) { istanza = this.istanza}
    return /*istanza.consolida && */istanza.stato_istanza.id == 'BZZ' || istanza.stato_istanza.id == 'RPA';
  }
  
  consolidateInstance(istanza: model.IstanzaVO = null) {
    if(!istanza) { istanza = this.istanza}

  }

  canBackInstanceToBzz(istanza: model.IstanzaVO): boolean {
    return istanza.torna_bozza && (istanza.stato_istanza.id == 'DFR' || istanza.stato_istanza.id == 'FRM');
  }
  
  backToBzz(istanza) {
    return this.mudeopenFoBeService.cambiaStatoIstanza(istanza.id_istanza, 'BZZ', {torna_bozza: true});
  }

}