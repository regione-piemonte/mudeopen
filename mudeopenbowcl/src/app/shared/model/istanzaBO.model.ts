import { IstanzaVO } from 'mudeopen-common';

export interface IstanzaBO extends IstanzaVO {
  pm: string;
  responsabile_procedimento: string;
  data_stato: string;
  funzioniUtente: []
  genera_tracciato: boolean;
  data_protocollo: string;
  intestatario:any
}
