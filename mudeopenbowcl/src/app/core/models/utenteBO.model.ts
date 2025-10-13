import { UtenteVO, SelectVO } from 'mudeopen-common';

export interface UtenteBO extends UtenteVO {
  ruoliUtente: SelectVO[];
  funzioniUtente: Record<string, string>[];
}
