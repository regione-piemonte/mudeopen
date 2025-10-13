/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouteReuseStrategy, RouterModule } from '@angular/router';
import { environment } from 'buildfiles/environment.local-rp-01';
import { SidebarModule } from 'ng-sidebar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {
  AuthStoreService,
  MudeCommonModule,
  CustomReuseStrategy,
  BASE_PATH
} from 'mudeopen-common';
import { AppSharedModule } from './shared/appshared.module';


export function initializeServices(auth: AuthStoreService): Function 
{
  return () => { return auth.checkAuthentication() }; 
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AppSharedModule,

    BrowserModule,
    BrowserAnimationsModule,

    HttpClientModule,
    MudeCommonModule,
    SidebarModule.forRoot(),
    AppRoutingModule,
    FormsModule
  ],
  // entryComponents: [AppComponent],
  providers: [
    AuthStoreService,
    { provide: BASE_PATH, useValue: environment.basePath },
    { provide: APP_INITIALIZER, useFactory: initializeServices, deps: [AuthStoreService], multi: true },
    { provide: RouteReuseStrategy, useClass: CustomReuseStrategy}

  ],
  bootstrap: [AppComponent],
})
export class AppModuleFO {
  public static forRoot(): ModuleWithProviders<AppModuleFO> {
    return {
      ngModule: AppModuleFO,
      providers: []
    };
  }  

}
