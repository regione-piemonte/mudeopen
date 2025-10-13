import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';
import * as model from 'mudeopen-common';

@Component({
  selector: 'app-aggiungi-abilitazione-utente',
  templateUrl: './aggiungi-abilitazione-utente.component.html',
  styleUrls: ['./aggiungi-abilitazione-utente.component.scss']
})
export class AggiungiAbilitazioneUtenteComponent extends FormUtils implements OnInit {

  public loggedUser: model.UtenteVO;
  public user: any;
  public permisison: model.AbilitazioneVO;

  public idFascicolo: number;
  public idIstanza: number;
  public idTemplate: number;
  public excludeIDs: string = null;
  public isReadonly: boolean;

  userSelectionType = UserSelectionType.ACCREDITED;

  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  constructor(public mudeopenFoBeService: MudeopenFoBeService,
                private modalService: NgbActiveModal,
                public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);

    this.datatable.limit = 1000;
  }

  subjectList: any;
  permissionList: model.AbilitazioneFunzioneCustomVO[];
  quadriList: model.QuadroVO[];
  quadriRootList: any;
  permissionSelected: model.AbilitazioneFunzioneCustomVO;

  noPermissionsAvailable: boolean;
  ngOnInit() {
    if(this.user?.id) {
      this.onUserSelected(this.user); }
    if(this.permisison) {
      this.mudeopenFoBeService.loadFunzioniByAbilitazione(this.permisison.codice_abilitazione).subscribe((perm) => {
        this.permissionSelected = perm;
        this._init();
      }); }
    else {
      this.mudeopenFoBeService.loadFunzioniAbilitazioni(this.idFascicolo, this.idIstanza).subscribe((permissionList) => {
        this.noPermissionsAvailable = !permissionList || !permissionList.length;
        if(this.noPermissionsAvailable) { return; }

        this.permissionList = permissionList;
        this._init();
      }); }
  }

  _init() {
    if(!this.idIstanza) { return; }

    this.mudeopenFoBeService.retrieveAllQuadriInTemplate(this.idTemplate, this.idIstanza, this.user?.id).subscribe((quadriList) => {
      this.quadriList = quadriList;
      this.quadriRootList = this.quadriList.filter(x => !x.id_quadro_parent && x.tipo_quadro.cod_tipo_quadro != 'QDR_SOGGETTO_ABILIT' );
      quadriList.forEach(q => q.json_configura_quadro = JSON.parse(q.json_configura_quadro));

      if(this.permisison) {
        this.onPermSelected(); }

//retrieves the creator too: if($$.debug) this.excludeIDs = null;
      this.mudeopenFoBeService.getRuoliIstanza(this.idIstanza, this.excludeIDs, true).subscribe((subjectList) => { 
        this.subjectList = this.loadSubjectFromRoles(subjectList);
        this.subjectList = (Object.keys(this.subjectList).length && this.subjectList) || null;
      });
    });
  }

  getQuadroLabel(quadro) {
    return quadro._label || quadro.json_configura_quadro?.label;
  }

  getQuadroFromTab(tab) {
    const idQuadro = tab.id_quadro.replace(/[~]/g, '');
    const quadro = this.quadriList.find(q => q.id_quadro == idQuadro);
    (quadro as any)._label = tab.label + (!tab.title?'' :  ('. '+tab.title))
    return quadro;
  }

  idUser: number;
  userSelectedInfo: any;
  onUserSelected(user: any) {
    this.idUser = user.contact?.id_user_accreditato || user.id;
    this.userSelectedInfo = user.contact?.anagrafica || user?.contatto?.anagrafica;
  }

  isUnregistered(user): boolean {
    return !user.contact?.id_user_accreditato;
  }

  get isPMPermission(): boolean {
    return this.permissionSelected.abilitazione.codice_abilitazione.startsWith('PM_RUP');
  }

  permissionFunctions: string;
  onPermSelected() {
    this.permissionFunctions = '<em class="fa fa-check-square-o" aria-hidden="true"></em>&nbsp;' + this.permissionSelected.funzioni.map(x => x.desc_funzione).join('<br><em class="fa fa-check-square-o" aria-hidden="true"></em> ');
    (this.quadriList || []).forEach(q => {
      // is there a "funzioni_richieste" associated to this quadro?
      (q as any)._disabled = !this.permissionSelected.funzioni.filter(p => (q.tipo_quadro.funzioni_richieste || '').indexOf(p.codice_funzione) > -1).length;

      // set value to true in case there is a record set in mudeopen_r_istanza_utente_quadro
    });
  }

  onQuadroCheck(quadro: any, forceValue: any = null) {
    if(quadro._disabled) { return; }

    quadro.flg_modificabile = forceValue!=null? forceValue : !quadro.flg_modificabile;
    (quadro._subQuadri || []).forEach(q => {
      this.onQuadroCheck(q, quadro.flg_modificabile);
    });
  }

  _getQuadroTab(tab) {
    const id_quadro_par = (tab.label + tab.title).split('').reduce((a,b)=>{a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);
    return {
      _label: tab.label+' '+tab.title,
      id_quadro: id_quadro_par,
      _subQuadri: [],
      _disabled: false,
      _hideSubQuadri: false,
      flg_modificabile: false
    }
  }

  get _allSub() {
    return {
      allSubDisabled: true,
      allSubChecked: true,
      allSubHideSubQuadri: true
    }
  }

  get _allTab() {
    return {
      allDisabled: true,
      allChecked: true,
      allHideSubQuadri: true
    }
  }

  _compileAllSub(allsub, subtab, quadroTab) {
    const subquadro = this.getQuadroFromTab(subtab);

    // just for "quadro asseverativo"
    if(!(subquadro as any)._disabled && subquadro.tipo_quadro?.id_categoria_quadro === 3 /* Sezione asseverativa */) {
      allsub.allHideSubQuadri = allsub.allSubHideSubQuadri = false; }

    allsub.allSubDisabled = allsub.allSubDisabled && (subquadro as any)._disabled;
    allsub.allSubChecked = allsub.allSubChecked && subquadro.flg_modificabile;

    quadroTab._subQuadri.push(subquadro);
  }

  _handleTab(alltab, tab, quadro) {
    const subquadro = this.getQuadroFromTab(tab);
    if((subquadro as any)._disabled && subquadro.tipo_quadro?.id_categoria_quadro === 3 /* Sezione asseverativa */) {
      alltab.allHideSubQuadri = false; }

    alltab.allDisabled = alltab.allDisabled && (subquadro as any)._disabled;
    alltab.allChecked = alltab.allChecked && subquadro.flg_modificabile;

    quadro._subQuadri.push(subquadro);
  }

  _handleNoTabs(alltab, quadro) {
    alltab.allDisabled = alltab.allDisabled && (quadro as any)._disabled;
    alltab.allChecked = alltab.allChecked && quadro.flg_modificabile;
  }

  _setAllSubTab(alltab, quadroTab, allsub) {
    quadroTab._disabled = allsub.allSubDisabled;
    alltab.allDisabled = (quadroTab._disabled) && alltab.allDisabled;

    quadroTab.flg_modificabile = allsub.allSubChecked
    alltab.allChecked = (quadroTab.flg_modificabile) && alltab.allChecked;

    quadroTab._hideSubQuadri = allsub.allSubHideSubQuadri;
    alltab.allHideSubQuadri = (quadroTab._hideSubQuadri) && alltab.allHideSubQuadri;
  }


  getSubquadri(quadro) {
    if(!quadro._subQuadri) {
      quadro._subQuadri = [];
      if(quadro.json_configura_quadro?.tabs) {
        const alltab = this._allTab;

        quadro.json_configura_quadro.tabs.forEach(tab => {
          if(tab.subtabs) {
            const quadroTab: any = this._getQuadroTab(tab);
            quadro._subQuadri.push(quadroTab);

            const allsub: any = this._allSub;

            tab.subtabs.forEach(subtab => {
              this._compileAllSub(allsub, subtab, quadroTab);
            });

            this._setAllSubTab(alltab, quadroTab, allsub);
          }
          else if(tab.id_quadro) {
            this._handleTab(alltab, tab, quadro);
          }
          else {
            this._handleNoTabs(alltab, quadro);
          }
        });

        quadro._disabled = alltab.allDisabled;
        quadro.flg_modificabile = alltab.allChecked;
        quadro._hideSubQuadri = alltab.allHideSubQuadri;
      }
    }

    return quadro._subQuadri;
  }

  get quadroSelectionRequired(): boolean {
    return !!this.permissionFunctions && this.permissionSelected.abilitazione.necessaria_selezione_quadro;
  }

  errorMsg: boolean;
  onSubmit() {
    this.errorMsg = !this.permissionSelected;
    if(this.errorMsg) { return; }

    if(false && this.quadroSelectionRequired && !this.quadriList.filter(q => q.flg_modificabile).length) {
      this.confirmDialog('Attenzione: non è stato selezionato nessun quadro. Vuoi comunque aggiungere l\'abilitazione?', (x) => {
        this._onSubmit();
      }, 'AGGIUNGI'); }
    else {
      this._onSubmit(); }
  }

  _onSubmit() {
    const fn = this.permissionSelected.funzioni.find(x => x.codice_funzione === 'UPLOAD_ALLEG');
    const res: model.AbilitazioneFunzioneCustomVO = {
      abilitazione: this.permissionSelected.abilitazione,
      funzioni: (fn && [ fn ]) || null,
      id_utente: this.idUser,
      quadri: (fn && this.quadriList.filter((q: any) => {
          delete q._label;
          delete q._disabled;
          delete q._hideSubQuadri;
          delete q._subQuadri;
          delete q._idQuadroPar;
          delete q.json_configura_quadro;
          return q.flg_modificabile;
      })) || null
    }
  
    this.confirmEvent.emit(res);
  }

  onExit() {
    this.modalService.dismiss();
  }
}
