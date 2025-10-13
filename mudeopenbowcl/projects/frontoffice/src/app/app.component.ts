/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';

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
  selected: string = 'home';
  navigationEndUrl: string = null;

  routeSubscription;

  _logoutUrl: string;

	/* 
	* nome metodo "constructor"; method description: 
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

    $$.log2("MUDE AppVersion: " + $$.version + ' - BPATH: ' + environment.basePath);

    mudeopenFoBeService.getProperties().then(x => this._logoutUrl = x?.find(y => y.id == 'MUDE_LOGOUT_URL')?.value);

  }


  // isBackOffice: boolean = false;

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns 
	*/ 

  ngOnInit() {
    // workaround to avoid sidebar showing up even if it is initially closed
    setTimeout(() => {
      this.animate = true;
    });
    this.setSidebarProperties();

    this.routeSubscription = this.router.events
      .pipe(filter((event: RouterEvent) => {
        $$.setDebugEnv();
        return event instanceof NavigationEnd
      }))
      .subscribe((event: NavigationEnd) => {
        // this has been disabled after the move of the BO: $$.isBackoffice = this.authService.isBackOffice = event.url.indexOf('/backoffice') > -1;

        let i;
        this.navigationEndUrl = event.url.split('?')[0];
        if((i = this.navigationEndUrl.lastIndexOf('/')) > -1) {
          this.selected = this.navigationEndUrl.substring(i+1).toLowerCase();  }
        else {
          this.selected = this.navigationEndUrl; }

        if(!this.selected || this.selected === 'backoffice') {
          this.selected = 'home'; }
      });
  }

  // side menu
  @HostListener('window:resize', [])

	/* 
	* nome metodo "onResize"; method description: 
	* @param ()
	* @returns 
	*/ 

  onResize() {
    this.setSidebarProperties();
  }


	/* 
	* nome metodo "setSidebarProperties"; method description: 
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
	* nome metodo "toggleOpen"; method description: 
	* @param ()
	* @returns 
	*/ 

  toggleOpen() {
    this.opened = !this.opened;
  }


	/* 
	* nome metodo "toggleExpansion"; method description: 
	* @param ()
	* @returns 
	*/ 

  toggleExpansion() {
    this.dock = !this.dock;
  }


	/* 
	* nome metodo "selectItem"; method description: 
	* @param (name: string, state: any = null)
	* @returns 
	*/ 

  selectItem(name: string, state: any = null) {
    if (this.sidebarMode === this.modeList[0]) {
      this.opened = false;
    }

    if(state) {
      this.router.navigate([name], { state: state }); }
    else {
      this.router.navigate([name]); }
  }


	/* 
	* nome metodo "getUserName"; method description: 
	* @param ()
	* @returns string
	*/ 

  getUserNameInitials(): string {
    return this.authService.getUserInitials() || "Usr";
  }

  getUserName(): string {
    return this.authService.getUserName() || 'NON AUTENTICATO';
  }

	/* 
	* nome metodo "doLogout"; method description: 
	* @param ()
	* @returns 
	*/ 

  logoutPopup: boolean = false;
  openLogoutPopup: boolean = false;
  showLogout() {
    this.openLogoutPopup = true;
  }

  doLogout() {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
  
    this.httpClient.get(
        `${environment.basePath}/utenti/localLogout`,
        { headers: headers }
      ).subscribe(x => {
        window.location.href = this._logoutUrl || environment.logoutUrl;
      });
  }

  closePopups() {
    this.logoutPopup = this.openLogoutPopup? !this.logoutPopup : this.openLogoutPopup;
    this.openLogoutPopup = false;
  }

}
