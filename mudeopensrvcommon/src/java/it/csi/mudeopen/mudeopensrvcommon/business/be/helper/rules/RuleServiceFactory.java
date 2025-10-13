/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.rules;

public class RuleServiceFactory {

	boolean validateRule(RuleCustomInterface customRule) {
		boolean isValid=customRule.isValid();
		
		return isValid;
	}
	
	
}
