/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { takeUntil } from 'rxjs/operators';
import { AuthStoreService, MessageService } from 'mudeopen-common';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';
import { FormUtils, RegexUtil } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { InserisciPersonaFisicaComponent } from '../../../shared/components';





@Component({
  selector: 'app-accreditamento',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent extends FormUtils implements OnInit {
  @ViewChild("subjectComp") subjectComp: InserisciPersonaFisicaComponent;

  disclaimer: string;
  savedOk: boolean = false;
  agreeRequired: boolean = false;

	/* 
	* nome metodo "constructor"; method description: 
	* @param (
    private router: Router,
    private modalService: NgbModal, 
    private authService: AuthStoreService,
    private route: ActivatedRoute,
    mudeopenFoBeService: MudeopenFoBeService, 
    private fb: FormBuilder,
    private ms: MessageService,
  )
	* @returns 
	*/ 

  constructor(private authService: AuthStoreService,
          mudeopenFoBeService: MudeopenFoBeService, 
          private ms: MessageService) {
    super(mudeopenFoBeService, ms);
    this.disableCountryLoading = false;
  }
  
	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 
  profiloResponse: model.ProfiloResponse;
  ngOnInit(): void {
    this.mudeopenFoBeService.getAssetResourceAsText('html/disclaimer.html', (html) => {
      this.disclaimer = html;
    });    

    this.mudeopenFoBeService.getInfoProfilo().subscribe(x => {
      this.profiloResponse = x;

      if($$.isLocal && !x.profiloCompleto) {
        x.infoUtente.contatto.anagrafica.nome = 'NOME';
        x.infoUtente.contatto.anagrafica.cognome = 'COGNOME';
      }
    });
  }



	/* 
	* nome metodo "annulla"; method description: 
	* @param ()
	* @returns 
	*/ 

  annulla() {
    // todo : check what to do here
    window.history.go(this.profiloResponse.profiloCompleto? -1 : -2);
  }

	/* 
	* nome metodo "accredita"; method description: 
	* @param (agree)
	* @returns 
	*/ 

  redirecting: boolean = false;
  accredita(agree: boolean) {
    if(this.redirecting) { return; }

    this.agreeRequired = !agree;
    this.savedOk = false;

    if(this.subjectComp._validate() && agree) {
      this.profiloResponse.infoUtente.contatto = Object.assign(this.profiloResponse.infoUtente.contatto, this.subjectComp.getContact());

      this.mudeopenFoBeService.salvaInfoProfilo(this.profiloResponse.infoUtente).subscribe((outUtenteVO) => {
        this.mudeopenFoBeService.setSessionStorageInfoProfilo().subscribe((userInfo: model.ProfiloResponse) => {
          this.authService.setUser(userInfo.infoUtente);

          if(userInfo.profiloCompleto) {
            this.savedOk = true; }
          
            if(!this.profiloResponse.profiloCompleto) {
              this.redirecting = true;

              setTimeout(x => { 
                window.location.href = "/mudeopen" }, 3000); }
        });
      });
    }
  }
}
