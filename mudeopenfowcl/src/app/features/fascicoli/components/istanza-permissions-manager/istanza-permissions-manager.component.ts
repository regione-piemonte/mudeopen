import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { AggiungiAbilitazioneUtenteComponent } from './aggiungi-abilitazione-utente/aggiungi-abilitazione-utente.component';
import { USER_FUNC, USER_PROFILE } from '../../../../shared/enums';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;








@Component({
  selector: 'app-istanza-permissions-manager',
  templateUrl: './istanza-permissions-manager.component.html',
  styleUrls: ['./istanza-permissions-manager.component.scss']
})
export class IstanzaPermissionsManagerComponent extends FormUtils implements OnInit {
  public loggedUser: model.UtenteVO;
  public idFascicolo: number;
  public idIstanza: number;
  public idTemplate: number;
  public doesStatusAllowChanges: boolean = true;

  public mode: string = 'istanza';

  @ViewChild('table') table: any;

  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public isReadonly: boolean;

  users: any = [];
  saveMessage: boolean;
  
  constructor(public mudeopenFoBeService: MudeopenFoBeService,
          private modalService: NgbModal,
          public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);

    //this.disableCountryLoading = true;

    this.datatable.limit = 50;
    this.datatable.messages.emptyMessage = 'Non sono stati inseriti utenti con abilitazioni per ' + (this.mode? 'l\'istanza' : ' il fascicolo');
  }
  
  permissionList: model.AbilitazioneFunzioneCustomVO[];

  ngOnInit() {
    this.onSearch();
  }
  
  get someoneHasPMAssigned(): boolean {
    return !!this.permissionAssigned.find(x => x.abilitazione.codice_abilitazione == 'PM_RUP_PM_OBB');
  }

  isInitialized: boolean;
  someoneHadPMAssigned: boolean = false;
  loggedUserPermisionList: any;
  permissionAssigned: any;
  onSearch() {
    this.mudeopenFoBeService[this.mode=='istanza'? 'recuperaAbilitazioniIstanza':'recuperaAbilitazioniFascicolo'](this.idIstanza || this.idFascicolo).subscribe(abilitazioniAssegnate => {
      this.permissionAssigned = abilitazioniAssegnate;
      if(this.permissionAssigned) {
        if(!this.isInitialized) {
          this.isInitialized = true;
          this.someoneHadPMAssigned = this.someoneHasPMAssigned;
        }

        this.mudeopenFoBeService.loadFunzioniAbilitazioni(this.idFascicolo, this.idIstanza, true).subscribe((permissionList) => {
          this.permissionList = permissionList;

          const userMap = {};
          abilitazioniAssegnate.forEach(abilit => {
            userMap[abilit.utente.contatto.anagrafica.codiceFiscale] = (userMap[abilit.utente.contatto.anagrafica.codiceFiscale] || []);
            userMap[abilit.utente.contatto.anagrafica.codiceFiscale].push(abilit);
          });
  
          this.users = Object.keys(userMap).map(x => { 
            if(this.loggedUser.contatto.anagrafica.codiceFiscale == userMap[x][0].utente.contatto.anagrafica.codiceFiscale) {
              this.loggedUserPermisionList = userMap[x].map(abilit => {
                abilit.funzioni = permissionList.find(perm => perm.abilitazione.id_abilitazione == abilit.abilitazione.id_abilitazione).funzioni
                                .map(f => f.codice_funzione);
                
                return abilit;
              }); }

            return { 
              "user": userMap[x][0].utente, 
              "abilitazioniEx": userMap[x] 
            } 
          }); 
  
          setTimeout(x => { 
            this.table.rowDetail.expandAllRows(); 
          });
        }); }
    })
  }

  addNewUser() {
    this.openEditUser(null, null, true);
  }

  addNewPermission(row) {
    this.openEditUser(row.user, null, true);
  }

  userDelete(row, perm: any) {
    this.confirmDialog('Vuoi eliminare l\'abilitazione per l\`utente?', (x) => {
      this.mudeopenFoBeService[this.mode=='istanza'? 'deleteIstanzaUtente':'deleteFascicoloUtente'](perm.id_istanza_utente || perm.id_fascicolo_utente).subscribe((permissionList) => {
        this.isDataChanged = this.saveMessage = true;
        this.onSearch();
      });
    }, 'ELIMINA');

  }

  userModify(row, perm: any) {
    this.openEditUser(row.user, perm.abilitazione);
  }

  openEditUser(user:any = null, permisison: model.AbilitazioneVO = null, changeAllowed: boolean = true) {
    const modal = this.modalService.open(AggiungiAbilitazioneUtenteComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.permisison = permisison;
    modal.componentInstance.user = user;
    modal.componentInstance.loggedUser = this.loggedUser;

    modal.componentInstance.idFascicolo = this.idFascicolo;
    modal.componentInstance.idIstanza = this.idIstanza;
    modal.componentInstance.idTemplate = this.idTemplate;
    modal.componentInstance.excludeIDs = this.users.map(x => x.user.contatto.anagrafica.codiceFiscale).join(',');
    
    modal.componentInstance.confirmEvent.subscribe((data: model.AbilitazioneFunzioneCustomVO) => {
      if(!this.isReadonly && data) {
        const fn = this.mode=='istanza'? 'saveAbilitazioniFunzioniPerIstanzaUtente':'saveAbilitazioniFunzioniPerFascicoloUtente';
        this.mudeopenFoBeService[fn](this.idIstanza || this.idFascicolo, data).subscribe((res) => {
          this.isDataChanged = this.saveMessage = true;
          this.onSearch();
        });
      }

      modal.dismiss();
      this.onSearch();
    });
  }

  isModifyAllowed(user: any, perm: any): boolean {
    if(perm.abilitazione.codice_abilitazione == USER_PROFILE.CONSULTATORE) {
      return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.NOMINA_CONSULT, null, this.loggedUserPermisionList); }

    if(this.doesStatusAllowChanges) {
      switch(perm.abilitazione.codice_abilitazione) {
        case USER_PROFILE.FASCIC_CONSULTATORE:
          return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.CONS_IST_ALL_FASCIC, null, this.loggedUserPermisionList);
        case USER_PROFILE.FASCIC_CREATORE_IST:
          return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.ABILITA_CREA_IST, null, this.loggedUserPermisionList);
        case USER_PROFILE.PM_RUP_PM_OPZ:
        case USER_PROFILE.PM_RUP_PM_OBB:
          return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.IND_PM, null, this.loggedUserPermisionList);
        case USER_PROFILE.COLLAB_PM_RUP_PM_OPZ:
        case USER_PROFILE.COLLAB_PM_RUP_PM_OBB:
          return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.IND_COLLAB_PM, null, this.loggedUserPermisionList);
        case USER_PROFILE.PROF_SPEC:
          return this.mudeopenFoBeService.hasUserPermission(USER_FUNC.NOMINA_PROF_SPEC, null, this.loggedUserPermisionList);
      }
    }

    return false;
  }

  canAddPermissionToUser(row) {
    return this.loggedUserPermisionList && (this.mode=='istanza'?
                  this.mudeopenFoBeService.hasUserPermission('(NOMINA_.*|IND_.*)', null, this.loggedUserPermisionList)
                : (this.mudeopenFoBeService.hasUserPermission(null, 'FASCIC_CREATORE', this.loggedUserPermisionList))
                    && !this.mudeopenFoBeService.hasUserPermission(null, 'FASCIC_CREATORE', row.abilitazioniEx));
  }

  isDeleteAllowed(user: any, perm: any): boolean {
    return this.isModifyAllowed(user, perm);
  }

  isPermissionEditable(perm): boolean {
    return !perm.id_fascicolo_utente || perm.abilitazione?.necessaria_selezione_quadro;
  }

  isDataChanged: boolean;
  onExit() {
    this.confirmEvent.emit({ 
      isDataChanged: this.isDataChanged,
      pmRemoved: !this.someoneHasPMAssigned && this.someoneHadPMAssigned 
    });
    this.modalService.dismissAll();
  }

  get canAddPermissions(): boolean {
    return !this.isReadonly
        && (((this.loggedUserPermisionList||[]).length == 1 
                && !this.mudeopenFoBeService.hasUserPermission(null, '.*CONSULTATORE.*', this.loggedUserPermisionList))
              || this.mudeopenFoBeService.hasUserPermission('(NOMINA_.*|IND_.*)', null, this.loggedUserPermisionList));
  } 

}
