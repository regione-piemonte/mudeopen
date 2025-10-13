/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/



declare var $$: any;


import { Location } from '@angular/common';
import { AfterContentInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { forkJoin } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { FormsService } from '../services/forms.service';
import { AuthStoreService, ModalDialogComponent } from 'mudeopen-common';
import { FormUtils } from 'mudeopen-common';
import { WizardAdempimentoVO, WizardElementoVO, WizardOperaVO } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { FascicoloVO, ProvinciaVO } from 'mudeopen-common';
import { SalvaFascicoloRequest } from 'mudeopen-common';
import { SalvaIstanzaRequest } from 'mudeopen-common';
import { SelectVO } from 'mudeopen-common';
import { TipoIstanzaResponse } from 'mudeopen-common';

import * as model from 'mudeopen-common';
import { SelezionaNuovaInstazaComponent } from '../components/seleziona-nuova-instaza/seleziona-nuova-instaza.component';

import { ListaAllegatiComponent } from '../allegati/lista-allegati/lista-allegati.component';

@Component({
  selector: 'intro-nuova-istanza',
  templateUrl: './intro-nuova-istanza.component.html',
  styleUrls: ['./intro-nuova-istanza.component.scss']
})
export class IntroFascicoloIstanzaComponent extends FormUtils implements OnInit, OnDestroy, AfterContentInit {
    
    @ViewChild('selectTipoIstanza') selectTipoIstanza: any;

    // Intro nuova istanza.
    tipologieIstanza: SelectVO[];
    tipologieIstanzaComplete: model.TipoIstanzaVO[];

    tipologiePresentatore: model.TipoIstanzaVO[];

    provincePiemonte: SelectVO[];

    // Wizard
    introStepIndex: number = 0; // -1 in order to enable wizard selection
    introStepIndexChoice: number = 0; // -1 in order to enable wizard selection

    showTutela: number = -1;
    showPrestazioni: number = -1;
    wizardElementi: Array<WizardElementoVO>;
    wizardSelected: Array<WizardElementoVO> = [];
    adempimentiTutela: WizardAdempimentoVO[] = [];
    adempimentiTutelaSelected: WizardAdempimentoVO[] = [];
    adempimentiPrestazioni: WizardAdempimentoVO[] = [];
    adempimentiPrestazioniSelected: WizardAdempimentoVO[] = [];
    tipologiaIstanzaSelected: SelectVO;

    ruoliSoggettiFascicolo: any[] = [];

    introContext: number = 0; // 0: nuova-istanza, 1: nuova-istanza

    public parentFormGroup: FormGroup;
    sub: any;
    isReadOnlyMode: boolean = false;

    folderSaved: boolean = false;
    tipoIstanzaLockedFromBO: string = null;
    entryPoint: string;

    constructor(mudeopenFoBeService: MudeopenFoBeService, 
                private authService: AuthStoreService,
                private modalService: NgbModal,
                private router: Router,
                public _location: Location,
                private messages: MessageService,
                public formsService: FormsService) {
        super(mudeopenFoBeService, messages);
        this.disableCountryLoading = true;

        this.mudeopenFoBeService.getTipologieIstanza().then(x => this.tipologieIstanzaComplete = x);
        this.mudeopenFoBeService.getTipologiePresentatore().subscribe(x => this.tipologiePresentatore = x);

        if($$.isBOUser && $$.getUrlParamValue('boTemplateOverride')) {
            formsService.boTemplateOverride = $$.getUrlParamValue('boTemplateOverride');
            this.tipoIstanzaLockedFromBO = formsService._boTemplateOverride.tipologia_istanza;
        }

        this.introStepIndexChoice = 0; // choice between create or wizard
        if(formsService.fascicolo?.id_fascicolo 
                            && (this.entryPoint = formsService.pullCurrentContextName)) {
            if((this.entryPoint === 'nuova-istanza')) {
                this.introContext = this.introStepIndex = 1; } // istanza
            else if((this.entryPoint === 'view-folder')) {
                this.introContext = this.introStepIndex = 0; } // fascicolo
        }
        else {
            formsService.resetContext(); }
    }

    _tipologiePresentatore: SelectVO[];
    get listaTipologiePresentatore(): model.TipoIstanzaVO[] {
        if(!this._tipologiePresentatore) {
            // escludes BRANCHES configured in table mudeopen_d_tipo_istanza
            if(this.selectCurrentSelectedTipoIstanza?.escludi_branch) {
                this.tipologiePresentatore = this.tipologiePresentatore.filter(x => this.selectCurrentSelectedTipoIstanza.escludi_branch.indexOf('BRANCH'+x.id) == -1);  }

            if(this.isChangeOwnerLocked && this.cfIntestFascicolo && this.authService.getUser().contatto.anagrafica.codiceFiscale != this.cfIntestFascicolo) {
                // handle 'cambio_intestatario' and warningCambioIntestatario
                // first branh not allowed in case of "cfIntestFascicolo"  different than logged user and "cambio_intestatario" false
                this._tipologiePresentatore = this.tipologiePresentatore.filter(x => x.id != '1' /* first branch */); 


            }
            else {
                this._tipologiePresentatore = this.tipologiePresentatore; }
        }

        return this._tipologiePresentatore;
    }

    get isChangeOwnerLocked(): boolean {
        return !this.tipologieIstanzaComplete.find(t => t.id == /* "tipo istanza" of this istance, not from folder! */this.formsService.fascicolo?.tipologia_istanza?.id 
                                                                    && t.cambio_intestatario);
    }

    get cfIntestFascicolo():string {
        return this.formsService.fascicolo?.intestatario?.anagrafica?.codiceFiscale /*
                || this.formsService.fascicolo?.intestatario?.anagrafica?.codiceFiscaleLegaleRappresentante */;
    }

    get warningCambioIntestatario(): string {
        if(this.parentFormGroup.get('tipologiaPresentatore').get('id').value == 1 
                    && this.cfIntestFascicolo && this.authService.getUser().contatto.anagrafica.codiceFiscale != this.cfIntestFascicolo) {
            return this.formsService.fascicolo?.intestatario?.anagrafica?.codiceFiscale?
                    this.getSubjectInfo('name', this.formsService.fascicolo?.intestatario?.anagrafica)
                    : this.getSubjectInfo('name-ceo', this.formsService.fascicolo?.intestatario?.anagrafica);
        }

        return null;
    }

    get selectCurrentSelectedTipoIstanza(): any {
        return this.tipologieIstanzaComplete.find(t => t.id == /* "tipo istanza" of this istance, not from folder! */this.formsService.fascicolo?.tipologia_istanza?.id);
    }

    ngOnInit(): void {
        //USE THIS FOR DEBUG if($$.debug > 0) { this.router.navigate(['/fascicolo/istanza']); }

        this.mudeopenFoBeService.getProvinceForComuniRegistered().subscribe(x => { 
            this.provincePiemonte = x; 
        });

        this.parentFormGroup = this.createFascicoloIstanzaForm();
        !!this.formsService.fascicolo?.id_fascicolo && this._initFascicoliSoggetti();

        if(this.formsService.fascicolo) {
            this.patchFascicoloForm();

            this.folderSaved = this.formsService.isReadOnlyMode;
            if(this.folderSaved) {
                this.parentFormGroup.disable(); }
            else {
                this.parentFormGroup.enable(); }
        }
        else if($$.isLocal && !this.tipoIstanzaLockedFromBO) {
            this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: 'CILA', value: null }, {onlySelf: true});
            // NOINT:dismissed    this.parentFormGroup.controls['tipologiaIntervento'].setValue({ id: 'INT0000003', value: null }, {onlySelf: true});
        }

        // overwrite in case it arrives from BO
        if(this.tipoIstanzaLockedFromBO) {
            this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: this.tipoIstanzaLockedFromBO, value: null }, {onlySelf: true});
        }
    }

    createFascicoloIstanzaForm(): FormGroup {
        return new FormGroup({
          idIstanza: new FormControl(),
          keywords: new FormControl(),
          provincia: new FormGroup({
            id: new FormControl(null, [Validators.required]),
            value: new FormControl()
          }),
          comune: new FormGroup({
            codBelfiore: new FormControl(),
            id: new FormControl(null, [Validators.required]),
            value: new FormControl()
          }),
          tipologiaIstanza: new FormGroup({
            id: new FormControl(null, [Validators.required]),
            value: new FormControl()
          }),
          /* // NOINT:dismissed    
          tipologiaIntervento: new FormGroup({
            id: new FormControl(null, [Validators.required]),
            value: new FormControl()
          }),
          */
          tipologiaPresentatore: new FormGroup({
            id: new FormControl(),
            value: new FormControl()
          })
        });
      }
    
    ngAfterContentInit() {
        super.ngAfterContentInit();
        if(!$$.debug) { return; }

        if(this.introContext +'' === '1') {
            this.patchFascicoloForm(); }
    }

    get pageTitle(): string {
        return this.entryPoint?"Fascicolo":"Nuovo fascicolo";
    }

    get pageState(): string {
        if(this.introStepIndex == -1) {
            return "Nuova istanza da presentare"; }
        if(this.introStepIndex > 10) {
            return "Componi istanza"; }

        return (this.formsService.fascicolo ? this.tipologieInterventoSelected 
                    : "Scegli istanza");
    };

    get tipologieIstanzaSelected(): string{
        return (this.tipologieIstanzaComplete || []).filter(x => 
            x.id == this.parentFormGroup.value.tipologiaIstanza?.id)[0]?.value;
    }

    get tipologieInterventoSelected(): string {
        return '';
    }

    get breadcrumb():string {
        if(this.tipologieIstanza && this.introStepIndex === 1 && this.parentFormGroup.value.tipologiaIstanza.id) {
            return 'ISTANZA' }

        return 'INTERVENTO EDILIZIO';
    }

    patchFascicoloForm() {
        this.salvaFascicoloRequest = this.getFascicoloRequest(this.formsService.fascicolo);

        this.parentFormGroup.controls['provincia'].setValue(this.salvaFascicoloRequest.provincia, {onlySelf: true});
        this.parentFormGroup.controls['comune'].setValue(this.salvaFascicoloRequest.comune, {onlySelf: true});
        this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: this.salvaFascicoloRequest.tipologia_istanza.id, value: this.salvaFascicoloRequest.tipologia_istanza.value }, {onlySelf: true});
        this.onProvinciaSelected(''+this.salvaFascicoloRequest.provincia.id);
    }
    
    initialComuneLoaded: boolean;
    initialTipoIstanzaLoaded: boolean;
    comuniForProvincia: SelectVO[] = []; 
    onProvinciaSelected(idProvincia: string) {
        this.mudeopenFoBeService.findComuniRegisteredForProvincia(parseInt(idProvincia, 10)).subscribe(x => { 
            this.comuniForProvincia = x; 

            if(!this.initialComuneLoaded && this.salvaFascicoloRequest?.comune?.id) {
                this.parentFormGroup.controls['comune'].setValue(this.salvaFascicoloRequest.comune, {onlySelf: true});
                this.initialComuneLoaded = true;
                this.onComuneSelected(); 
            }
            else {
                this.parentFormGroup.controls['comune'].setValue({ codBelfiore: null, id: null, value: null });
                this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: null, value: null });
            }
        });
    }

    onComuneSelected() {
        this.mudeopenFoBeService.recuperaTipologieIstanze(this.parentFormGroup.controls['comune'].value.id).subscribe(x => {
            this.tipologieIstanza = x

            if(!this.initialTipoIstanzaLoaded && this.salvaFascicoloRequest?.tipologia_istanza?.id) {
                this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: this.salvaFascicoloRequest.tipologia_istanza.id, value: this.salvaFascicoloRequest.tipologia_istanza.value }, {onlySelf: true});
                this.initialTipoIstanzaLoaded = true;
            }
            else {
                this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: null, value: null });
            }
        });
    }

    idTipologiaPresentatore: number;
    onChangeTipologiaPresentatore(idTipologiaPresentatore: any): void {
        this.idTipologiaPresentatore = idTipologiaPresentatore;
        this.parentFormGroup.controls['tipologiaPresentatore'].get('id').setValue(this.idTipologiaPresentatore);
    }

    get isTipoIstanzaRequired(): boolean {
        return this.formsService.boTemplateOverride && (this.selectTipoIstanza && !(this.selectTipoIstanza.selectedIndex >= 0));
    }

    salvaFascicoloRequest: SalvaFascicoloRequest;
    salvaIstanzaRequest: SalvaIstanzaRequest;
    noBranchSelected: boolean;
    noTipoIstanzaSelected: boolean;
    onSubmit(): void {
        this.noTipoIstanzaSelected = this.isTipoIstanzaRequired;

        if(this.parentFormGroup.invalid || this.noTipoIstanzaSelected) {
            this.commonUtils.validateForm(this.parentFormGroup); }
        else {
            if(this.tipoIstanzaLockedFromBO) {
                this.parentFormGroup.get('tipologiaIstanza').enable(); }

            this.formsService._isReadOnlyMode = false;

            if(this.introStepIndex == 0) { // Tipologia istanza.
                this.salvaFascicoloRequest = {
                                        "comune": this.parentFormGroup.controls['comune'].value,
                                        "provincia": this.parentFormGroup.controls['provincia'].value,
                                        "tipologia_istanza": this.parentFormGroup.controls['tipologiaIstanza'].value,
                                        "tipologia_intervento":  null
                                    };

                this.mudeopenFoBeService.modificaFascicolo(this.salvaFascicoloRequest, this.formsService.id_fascicolo).subscribe(fascicolo => {
                    this.folderSaved = true;
                    this.formsService.fascicolo = fascicolo;
                    this.introStepIndex = 1; // istanza
                });
            } else {
                this._doSubmit();
            }
        }
    }

    _doSubmit() {
        this.noBranchSelected = !this.idTipologiaPresentatore;
        if(!(this.noBranchSelected)) { // branch ('Tipologia presentatore') selected
            const id_presentatore = this.parentFormGroup.get('tipologiaPresentatore').get('id').value;

            this.salvaIstanzaRequest = {
                "comune": this.salvaFascicoloRequest.comune,
                "provincia": this.salvaFascicoloRequest.provincia,
                "tipologia_istanza": this.parentFormGroup.controls['tipologiaIstanza'].value,
                "id_istanza_riferimento": this.formsService.istanzaParent?.id_istanza,
                "id_fascicolo": this.formsService.fascicolo.id_fascicolo,
                "tipologia_presentatore": { id: id_presentatore, value: null },
                "bo_template_override": this.formsService._boTemplateOverride?.template_ver,
                "keywords": this.parentFormGroup.controls['keywords'].value,
                "ruoli": this.ruoliSoggettiFascicolo.filter(item => item.checked).map(item => {
                    return {
                        'id': item.id,
                        'soggetti': item.subjects.filter(item2 => item2.checked).map(subj => { return subj.id })
                    };
                })
            };
            this.mudeopenFoBeService.salvaIstanza(this.salvaIstanzaRequest).subscribe(istanza => {
                this.formsService.istanza = istanza;
                this.salvaIstanzaRequest.id_istanza = istanza.id_istanza;
                this.formsService.istanza.tipologia_presentatore = this.salvaIstanzaRequest.tipologia_presentatore;

                this.router.navigate(['/fascicolo/istanza']);
            });
        }
    }

    createInstance() {
        this.introStepIndex = 1;
    }

    // Wizard tipologia istanza.
    attivaWizard(){
        
        this.wizardSelected = [];
        this.adempimentiTutelaSelected = [];
        this.adempimentiPrestazioniSelected = [];
        this.showPrestazioni = this.showTutela = 0;

        forkJoin([
            this.mudeopenFoBeService.recuperaElementi(),
            this.mudeopenFoBeService.recuperaAdempimenti("TUTELA"),
            this.mudeopenFoBeService.recuperaAdempimenti("PRESTAZIONI")
          ])
            .pipe(takeUntil(this.destroy$))
            .subscribe((result) => {
                this.wizardElementi = <WizardElementoVO[]>result[0];
                this.adempimentiTutela = <WizardAdempimentoVO[]>result[1];
                this.adempimentiPrestazioni = <WizardAdempimentoVO[]>result[2];

              },
              (error) => {
                console.log(error);
              }
            );
    }
  
    onElementoCheck(elemento: WizardElementoVO) {
        this.toggleItemFromArray(this.wizardSelected, elemento);
    }

    onOperaCheck(opera: WizardOperaVO) {
        opera.checked = !opera.checked;
    }
            
            
    onAdempimentoTutelaCheck(adempimentiTutela: WizardAdempimentoVO) {
        this.toggleItemFromArray(this.adempimentiTutelaSelected, adempimentiTutela);
    }

    onAdempimentoPrestazioniCheck(adempimentiPrestazioni: WizardAdempimentoVO){     
        this.toggleItemFromArray(this.adempimentiPrestazioniSelected, adempimentiPrestazioni);
    }

    canGoNextStep(): boolean {
        switch(this.introStepIndex) {
            case 11: // step elemento
            case 12: 
                return this.wizardSelected.length > 0;
            case 13: // step opere
                let go = false;
                this.wizardSelected.forEach(element => {
                    const arr = element.opere.find(x => !!x.checked);
                    go = go || !!arr;
                });
                return go;
            case 14: // step adempimenti tutela
            case 15: // step adempimenti tutela
            case 16:
                return true;
        }
    }

    wizardInstanceFound: string = '';
    goNextStep() {
        if(++this.introStepIndex === 16) {
            const maxOperaPriorityFound = 0;
            let idRegimeGiuridico: number = 0;
            const maxRegimePriorityFound = 0;
            let idRegimeAggiuntivo: number = 1;
    
            this.wizardSelected.forEach(element => {
                idRegimeGiuridico = idRegimeGiuridico || this._opera(element, maxOperaPriorityFound);
            });
    
            this.adempimentiTutelaSelected.forEach(adempimento => {
                idRegimeAggiuntivo = idRegimeAggiuntivo || this._adempimenti(adempimento, maxRegimePriorityFound);
            });

            this.adempimentiPrestazioniSelected.forEach(adempimento => {
                idRegimeAggiuntivo = idRegimeAggiuntivo || this._regimeAggiuntivo(adempimento, maxRegimePriorityFound);
            });
    
            this.mudeopenFoBeService.recuperaTipoIstanza(idRegimeGiuridico, idRegimeAggiuntivo).subscribe((tipoRes: TipoIstanzaResponse) => {
                if(tipoRes.id === '0') {
                    this.wizardInstanceFound = 'NONEEDED'; }
                else {
                    this.wizardInstanceFound = tipoRes.denominazione;
                    this.parentFormGroup.controls['tipologiaIstanza'].get('id').setValue(tipoRes.id);
                }
            });
        }
        else if(this.introStepIndex === 17) {
            this.introStepIndex = 0; }
    }

    _opera(element, maxOperaPriorityFound) {
        const opera = element.opere.find(x => !!x.checked && this.getRegimeGiuridico(x).priorita > maxOperaPriorityFound);
        if(opera) {
            return this.getIdRegime(this.getRegimeGiuridico(opera)); }
    }

    _adempimenti(adempimento, maxRegimePriorityFound) {
        if(this.getRegimeAggiuntivo(adempimento).priorita > maxRegimePriorityFound) {
            return this.getIdRegime(this.getRegimeAggiuntivo(adempimento)); }
    }

    _regimeAggiuntivo(adempimento, maxRegimePriorityFound) {
        if(this.getRegimeAggiuntivo(adempimento).priorita > maxRegimePriorityFound) {
            return this.getIdRegime(this.getRegimeAggiuntivo(adempimento)); }
    }

    getIdRegime(obj: any) {
        return obj.idRegime || obj.id_regime;
    }

    getRegimeGiuridico(opera: any) {
        return opera.categoria.regime_giuridico;
    }

    getRegimeAggiuntivo(adem: any) {
        return adem.regime_aggiuntivo
    }

    toggleItemFromArray(arr: Array<any>, item: any) {
        if(item.checked = !item.checked) {
            arr.push(item); }
        else {
            arr.splice(arr.indexOf(item), 1); }
    }
    
    wizardLev1FilterText: string = '';
    checkWizardFilter(txt: string): boolean {
        return !this.wizardLev1FilterText || new RegExp(this.wizardLev1FilterText, 'ig').test(txt);
    }
    
    selectCreationType() {
        this.introStepIndex = this.introStepIndexChoice;
        if(this.introStepIndex > 10) {
            this.attivaWizard(); }
    }

    messageBoxDialogTitle: string = '';
    messageBoxDialogText: string = '';
    showMessageBox(title: string, msg: string) {
        this.messageBoxDialogTitle = title;
        this.messageBoxDialogText = msg;
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        if(this.sub) { this.sub.unsubscribe(); }
    }

    istanzeFascicoloList: any = null;
    get _istanzeFascicoloList(): any {
        if(!this.istanzeFascicoloList) {
            this.istanzeFascicoloList = [];

            this.mudeopenFoBeService.istanzeFascicolo(this.formsService.fascicolo.id_fascicolo).subscribe(arr => { 
                this.istanzeFascicoloList = arr;
            });
        }

        return this.istanzeFascicoloList;
    }

    deleteIstanza(istanza: any) {
        const modal = this.modalService.open(ModalDialogComponent);
        modal.componentInstance.text = "Sivuole eliminare l'istanza selezionata?";
        modal.componentInstance.title = "Eliminazione";
    
        modal.componentInstance.cancelEvent.subscribe(() => modal.dismiss());
        modal.componentInstance.confirmEvent.subscribe(() => {
          modal.dismiss()
    
          this.mudeopenFoBeService.eliminaIstanza(istanza.id_istanza).subscribe(x => {
            this.messages.showTopMessage("Istanza eliminata con succsso");
            this.istanzeFascicoloList = null;
          });
        });
    }
 
    backFromPresentatore(index: number) {
        this.introStepIndex = index;
        if(this.entryPoint) {
            this._location.back(); }
    }
 

    addIstanzaInCurrentFascicolo() {
        if(this.entryPoint) {
            const modal = this.modalService.open(SelezionaNuovaInstazaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
            modal.componentInstance.idComune = this.formsService.fascicolo.comune.id;
            modal.componentInstance.idIstanza = null;
            modal.componentInstance.idFascicolo = this.formsService.fascicolo.id_fascicolo;
        
            modal.componentInstance.confirmEvent.subscribe((newIstanzaInfo) => {
              if(newIstanzaInfo) {
                if(newIstanzaInfo.tipologiaIstanza) {
                    this.formsService.fascicolo.tipologia_istanza = newIstanzaInfo.tipologiaIstanza; 
                    this.parentFormGroup.controls['tipologiaIstanza'].setValue({ id: newIstanzaInfo.tipologiaIstanza.id, value: null }, {onlySelf: true});
                }
            
                this.formsService.setContext('nuova-istanza', false, this.formsService.fascicolo, null, newIstanzaInfo.istanzaParent);
                this.introContext = this.introStepIndex = 1; // istanza
              }
        
              modal.dismiss();
            });
        
            /*
            this.introContext = this.introStepIndex = 1; // istanza
            this.entryPoint = 'nuova-istanza';

            this.patchFascicoloForm()
            this.parentFormGroup.enable();
            */
        }
        else {
            this.introStepIndex = 1; }
    }

    getFascicoloRequest(f: FascicoloVO) {
        return {
            comune: f.comune,
            provincia: f.provincia,
            tipologia_istanza: { 
                id: this.tipoIstanzaLockedFromBO || f.tipologia_istanza.id,
                value: f.tipologia_istanza.value 
            },
            tipologia_intervento: null /*{
                id: f.tipologia_intervento.id,
                value: f.tipologia_intervento.value
            } */
        };
    }


    onItemsSelected(items) {
        if(!items) {
            items = null; } // SQ crap;
    }
 
    displayFolderPermissionsManagerPopup(idFascicolo) {
       this.formsService.displayPermissionsManagerPopup(this.authService.getUser(), idFascicolo).subscribe(res => {
        if(res == undefined) { res = null; } // SQ crap!
       });
    }

    areFolderSubjectsAvailable(): boolean {
        return !this.formsService.fascicolo?.tipologia_istanza?.soggetti_bloccati && !!this.ruoliSoggettiFascicolo?.length;
    }

    reloadSoggetti() {
        $$.isLocal && this._initFascicoliSoggetti();
    }
 
    _initFascicoliSoggetti() {
        this.ruoliSoggettiFascicolo = [];

        this.mudeopenFoBeService.getRuoliFascicolo(this.formsService.fascicolo.id_fascicolo).subscribe((_roles: Array<model.FascicoloRuoloVO>) => {
            if(!_roles.length) {return;}

            let roles = [
                _roles.find(x => x.ruolo.id == 'IN'),
                ...(_roles.filter(x => x.ruolo.id != 'IN'))
            ];

            roles.forEach((role: model.FascicoloRuoloVO, index) => {
                try {
                    this.ruoliSoggettiFascicolo.push({
                        'id': role.ruolo.id,
                        'value': role.ruolo.value,
                        'static': role.ruolo.id == 'IN',
                        'subjects': role.soggetti.map((subject: model.FascicoloSoggettoVO, index) => {
                            return {
                                'id': subject.id_fascicolo_soggetto,
                                'value': this.getSubjectInfo('name', subject.contatto.anagrafica),
                                'static': role.ruolo.id == 'IN',
                                'instances': subject.istanze_soggetto.map((instance: model.IstanzaVO, index) => {
                                    return instance.tipologia_istanza.value
                                }),
                                'start': subject.data_inizio,
                                'end': subject.data_fine
                            }
                        })
                    });
                }
                catch(skipDisabledContact) {}
            });

            // make static all roles in which all subjects have "end"!=null
            this.ruoliSoggettiFascicolo.forEach(role => {
                role.static = role.subjects.filter(s => s.end || s.static).length == role.subjects.length;
            });
        });
    }

    get boTemplateInfo(): string {
        return this.formsService._boTemplateOverride.tipologia_istanza +' (ver: '+this.formsService._boTemplateOverride.template_ver+')';
    }

    openAttachmentsList(istanza: model.IstanzaVO) {
        const modal = this.modalService.open(ListaAllegatiComponent, { size: 'xl', backdrop: 'static', keyboard: false });
        modal.componentInstance.idIstanza = istanza.id_istanza;
    }
    
    downloadPDF(istanza: model.IstanzaVO) {
        this.mudeopenFoBeService.downloadIstanza(istanza.id_istanza).subscribe(x => {
          this.download2user(x);
        });
    }
    
    downloadReceiptPDF(istanza: model.IstanzaVO) {
        this.mudeopenFoBeService.downloadRicevutaPdf(istanza.id_istanza).subscribe(x => {
          this.download2user(x);
        });
    }

    downloadIdfFile(istanza: model.IstanzaVO) {
        this.mudeopenFoBeService.downloadAllegato(istanza.idf_index_node, 'idf').subscribe(x => {
            this.download2user(x);
        });
    }
}