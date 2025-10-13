/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;

public interface ContattoService {

    ContattoVO saveContatto(ContattoVO contattoVO, Long idIstanza, MudeTUser mudeTUser);

//    /**
//     * Save persona giuridica contatto pgvo.
//     *
//     * @param contatto  the contatto
//     * @param mudeTUser the mude t user
//     * @return the contatto pgvo
//     */
//    ContattoVO savePersonaGiuridica(ContattoVO contatto, MudeTUser mudeTUser);
//
//    /**
//     * Save persona fisica contatto pfvo.
//     *
//     * @param contatto  the contatto
//     * @param mudeTUser the mude t user
//     * @return the contatto pfvo
//     */
//    ContattoVO savePersonaFisica(ContattoVO contatto, MudeTUser mudeTUser);

    Response findContattoByParam(String filter, MudeTUser mudeTUser, int page, int size, String sortExp);

/*    ContattoPFVO findPfByUUID(UUID uuid, String userCf);

    ContattoPGVO findPgByUUID(UUID uuid, String userCf);*/

    void deleteContatto(String piva, String cf, MudeTUser mudeTUser);

    Boolean isContattoDeletable(String piva, String cf, String userCf);

    ContattoVO findContattoByID(Long id);

    ContattoVO findByPiva(String piva, String userCF);

    ContattoVO findByCF(String cf, String userCF);

    ContattoVO findAccreditedContactByIdUser(Long idUser);

    ContattoVO findPrivateContactByAccreditedIdUser(Long idUser, MudeTUser mudeTUser);

    String recuperaDestinatariNotifiche(Long idIstanza,Long idUser);

    void refreshInstanceContacts(MudeTIstanza istanza, MudeTUser mudeTUser);
}