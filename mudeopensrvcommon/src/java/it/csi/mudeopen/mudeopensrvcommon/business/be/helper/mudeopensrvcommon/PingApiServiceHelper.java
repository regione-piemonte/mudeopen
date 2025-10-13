/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PropertyUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.ping.PingVO;
@Component
public class PingApiServiceHelper {
    public static final Logger logger = Logger.getLogger(PingApiServiceHelper.class.getCanonicalName());
	
	@Autowired
	private PropertyUtil propertyUtil;

    public List<PingVO> ping() {
		List<PingVO> result  = new ArrayList<>();
		try {
			MudeCProprieta proprieta = propertyUtil.getPropertyValue(PropertyUtil.VERSION_ID);
			if(proprieta != null) {
				PingVO ping = new PingVO();
				ping.setLabel(proprieta.getId() +" - "+ proprieta.getNome());
				ping.setStatus(proprieta.getValore());
				result.add(ping);
			}else {
				throw new BusinessException("no property version configured!");
			}
		}

		catch(Throwable e) {
			logger.debug("[ApiManagerServiceHelper::ping] EXCEPTION : " + e.getMessage());
			if(e instanceof BusinessException) {
				throw e;
			}else {
				PingVO errorPing = new PingVO();
				errorPing.setLabel("Ping Remote Service");
				errorPing.setStatus("KO");
				result.add(errorPing);
			}
		}

		return result;
	}
	
	/**********************************************
	 * ADEMPIMENTO
	 **********************************************/
	
	
	
}