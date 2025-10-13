/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';
import { Permissions } from './core/enum/permissions.enum';
import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterEvent } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'buildfiles/environment.local-rp-01';
import { filter } from 'rxjs/operators';
import { AuthStoreService, BaseComponent, ModalDialogComponent, MudeopenFoBeService } from 'mudeopen-common';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent extends BaseComponent implements OnInit {
  iconBasePath = 'assets/icon-sidebar-';

  sidebarMenuItems: {
    id: string,
    icon: string,
    link: string,
    label: string,
    tagAcl: string,
  }[] = [];

  modeList = ['over', 'push', 'slide'];
  positionList = ['left', 'right', 'top', 'bottom'];
  sidebarMode;
  sidebarPosition = this.positionList[0];
  opened = true;
  closeOnClickOutside = false;
  closeOnClickBackdrop = true;
  showBackdrop = false;
  animate = false;
  trapFocus = false;
  autoFocus = true;
  autoCollapseHeight = 500;
  autoCollapseWidth = 500;
  ariaLabel = 'Lateral Navigation Bar';
  dock = false;
  selected = 'home';

  routeSubscription;


  /*
  * nome metodo "constructor"; descrizione:
  * @param (
              private router: Router,
              private authService: AuthStoreService,
              private mudeopenFoBeService: MudeopenFoBeService,
              private modalService: NgbModal)
  * @returns
  */

  constructor(
    private readonly router: Router,
    public readonly authService: AuthStoreService,
    private readonly mudeopenFoBeService: MudeopenFoBeService,
    private readonly modalService: NgbModal,
    private httpClient: HttpClient) {
    super();

    $$.log2('MUDE AppVersion: ' + $$.version);
  }


  // isBackOffice: boolean = false;

  /*
  * nome metodo "ngOnInit"; descrizione:
  * @param ()
  * @returns
  */

  ngOnInit() {
    this._initMenu();
    // workaround to avoid sidebar showing up even if it is initially closed
    setTimeout(() => {
      this.animate = true;
    });
    this.setSidebarProperties();

    this.routeSubscription = this.router.events
      .pipe(filter((event: RouterEvent) => {
        $$.setDebugEnv();
        return event instanceof NavigationEnd;
      }))
      .subscribe((event: NavigationEnd) => {
        $$.isBackoffice = this.authService.isBackOffice = event.url.indexOf('/backoffice') > -1;

        let i;
        const url = event.url.split('?')[0];
        if ((i = url.lastIndexOf('/')) > -1) {
          this.selected = url.substring(i + 1).toLowerCase();
        } else {
          this.selected = url;
        }

        if (!this.selected || this.selected === 'backoffice') {
          this.selected = 'home';
        }
      });
  }

  // side menu
  @HostListener('window:resize', [])

  /*
  * nome metodo "onResize"; descrizione:
  * @param ()
  * @returns
  */

  onResize() {
    this.setSidebarProperties();
  }


  /*
  * nome metodo "setSidebarProperties"; descrizione:
  * @param ()
  * @returns
  */

  setSidebarProperties() {
    this.dock = window.innerWidth > 800 ? this.dock : false;
    this.sidebarMode =
      window.innerWidth > 800 ? this.modeList[1] : this.modeList[0];
    this.opened = window.innerWidth > 800 ? true : false;
    this.showBackdrop = window.innerWidth > 800 ? false : true;
  }


  /*
  * nome metodo "toggleOpen"; descrizione:
  * @param ()
  * @returns
  */

  toggleOpen() {
    this.opened = !this.opened;
  }


  /*
  * nome metodo "toggleExpansion"; descrizione:
  * @param ()
  * @returns
  */

  toggleExpansion() {
    this.dock = !this.dock;
  }


  /*
  * nome metodo "selectItem"; descrizione:
  * @param (name: string, state: any = null)
  * @returns
  */

  selectItem(name: string, state: any = null) {
    if (this.sidebarMode === this.modeList[0]) {
      this.opened = false;
    }

    if (state) {
      this.router.navigate([name], { state: state });
    } else {
      this.router.navigate([name]);
    }
  }


  /*
  * nome metodo "getUserName"; descrizione:
  * @param ()
  * @returns string
  */

  getUserName(): string {
    return this.authService.getUserInitials() || 'Usr';
  }


  /*
  * nome metodo "doLogout"; descrizione:
  * @param ()
  * @returns
  */

  doLogout() {
    const modal = this.modalService.open(ModalDialogComponent);
    modal.componentInstance.text = 'Sei sicuro di voler uscire?';
    modal.componentInstance.title = 'Log-out';

    modal.componentInstance.cancelEvent.subscribe(() => modal.dismiss());
    modal.componentInstance.confirmEvent.subscribe(() => {
      modal.dismiss();

      let headers = new HttpHeaders();
      headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    
      this.httpClient.get(
        `${environment.basePath}/utenti/localLogout`,
        { headers: headers }
      ).subscribe(x => {
        window.location.href = environment.logoutUrl;
      });
  
    });
  }

  private _initMenu() {
    this.sidebarMenuItems.push({
      id: 'tipi',
      icon: 'assets/images/drw_rubricasoggetti.png',
      link: '/backoffice/quadri/tipi',
      label: 'Gestione tipi quadro',
      tagAcl: Permissions.EDIT_QUADRI,
    });

    this.sidebarMenuItems.push({
      id: 'gestione',
      icon: 'assets/images/drw_nuovofacicolo.png',
      link: '/backoffice/quadri/gestione',
      label: 'Lista quadri',
      tagAcl: Permissions.EDIT_QUADRI,
    });

    this.sidebarMenuItems.push({
      id: 'templates',
      icon: 'assets/images/drw_scadenzari.png',
      link: '/backoffice/quadri/templates',
      label: 'Template istanze',
      tagAcl: Permissions.EDIT_TEMPLATE,
    });

    this.sidebarMenuItems.push({
      id: 'scrivania',
      icon: 'assets/images/drw_imieifascicoli.png',
      link: '/backoffice/scrivania',
      label: 'Scrivania istanze depositate',
      tagAcl: Permissions.VIEW_SCRIVANIA,
    });

    this.sidebarMenuItems.push({
      id: 'ricerca-istanze',
      icon: 'assets/images/drw_ricercafascicoli.png',
      link: '/backoffice/lista-istanze',
      label: 'Ricerca istanze',
      tagAcl: Permissions.SEARCH_ISTANZA,
    });

    this.sidebarMenuItems.push({
      id: 'ricerca-fascicoli',
      icon: 'assets/images/drw_ricercafascicoli.png',
      link: '/backoffice/lista-fascicoli',
      label: 'Ricerca fascicoli',
      tagAcl: Permissions.SEARCH_FASCICOLO,
    });

    this.sidebarMenuItems.push({
      id: 'ricerca-pratiche',
      icon: 'assets/images/drw_ricercafascicoli.png',
      link: '/backoffice/lista-pratiche',
      label: 'Ricerca pratiche',
      tagAcl: Permissions.SEARCH_PRATICA,
    });

    this.sidebarMenuItems.push({
      id: 'istanze',
      icon: 'assets/images/drw_ricercafascicoli.png',
      link: '/backoffice/gestione/istanze',
      label: 'Gestione istanze, pratiche',
      tagAcl: Permissions.BO_GESTIONE_ISTANZE,
    });

    this.sidebarMenuItems.push({
      id: 'ds_istanze',
      icon: 'assets/images/drw_imieifascicoli.png',
      link: '/backoffice/scrivania-ds',
      label: 'Controllo a campione',
      tagAcl: Permissions.BO_DS_ISTANZE,
    });

    this.sidebarMenuItems.push({
      id: 'ricerca-pratiche',
      icon: 'assets/images/drw_ricercafascicoli.png',
      link: '/backoffice/pratiche-ds',
      label: 'Ricerca pratiche sismiche regionali',
      tagAcl: Permissions.BO_DS_PRATICHE,
    });

  }
  navigateToHome(){
    this.router.navigateByUrl('/backoffice')
  }
}
