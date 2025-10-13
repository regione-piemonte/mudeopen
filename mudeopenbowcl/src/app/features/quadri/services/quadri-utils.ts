/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


export enum ComponentCategories {
  REACTIVE_FORMS = 'R',
  COMPLEX = 'C',
  FORMIO = 'F'
}

export enum AngularComponents {
  SoggettiCoinvoltiComponent = 'SoggettiCoinvoltiComponent',
  SoggettoAbilitatoComponent = 'SoggettoAbilitatoComponent',
  RiferimentoIstanzeComponent = 'RiferimentoIstanzeComponent',
  AllegatiComponent = 'AllegatiComponent',
  PresentaIstanzaComponent = 'PresentaIstanzaComponent',
  CompilaIstanzaComponent = 'CompilaIstanzaComponent',
  FormioComponent = 'FormioComponent'
}

export function getAlertTypes(justErrors: boolean = false): any {
  const errs = [ 
    { 'id': 'INFO', 'value': 'Messaggio informaztivo (blue)' },
    { 'id': 'WARNING', 'value': 'Messaggio di avviso (giallo)' },
    { 'id': 'ERROR', 'value': 'Messaggio di errore (rosso)' }
  ];

  if(!justErrors) {
    errs.push({ 'id': 'SUCCESS', 'value': 'Messaggio di conferma (verde)' }); }

  return errs;
}

export function getComponentCategory(): any {
  return [
    { 'id': ComponentCategories.REACTIVE_FORMS, 'value': 'Reactive form (nativo)' },
    { 'id': ComponentCategories.COMPLEX, 'value': 'Complesso (visualizzazione tab)' },
    { 'id': ComponentCategories.FORMIO, 'value': 'FormIO (template dinamico)' }
  ];
}

export function getComponentNanmes(): any {
  return [
    { id: AngularComponents.SoggettiCoinvoltiComponent, value: 'Soggetti Coinvolti' },
    { id: AngularComponents.SoggettoAbilitatoComponent, value: 'Soggetto Abilitato' },
    { id: AngularComponents.RiferimentoIstanzeComponent, value: 'Istanze di riferimento' },
    { id: AngularComponents.AllegatiComponent, value: 'Allegati' },
    { id: AngularComponents.PresentaIstanzaComponent, value: 'Presenta Istanza' }
  ];
}

