/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { FormControl, FormGroup } from '@angular/forms';



/* 
* nome metodo "initRicercaPersonaFisicaForm"; method description: 
* @param ()
* @returns FormGroup
*/ 

export function initRicercaPersonaFisicaForm(): FormGroup {
    return new FormGroup({
        nome: new FormControl(),
        cognome: new FormControl(),
        codiceFiscale: new FormControl(),
        
        titolare: new FormControl(),
        impresa_lavori: new FormControl(),
        professionista: new FormControl()
    });
}




/* 
* nome metodo "initRicercaPersonaGiuridicaForm"; method description: 
* @param ()
* @returns FormGroup
*/ 

export function initRicercaPersonaGiuridicaForm(): FormGroup {
    return new FormGroup({
        ragioneSociale: new FormControl(),
        partitaIva: new FormControl(),
        partitaIvaComunitaria: new FormControl(),
        titolare: new FormControl(),
        impresa_lavori: new FormControl(),
        professionista: new FormControl()
    });
}
