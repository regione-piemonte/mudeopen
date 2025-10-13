/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import java.util.List;

public class Content{
    List content;

    public Content(List content) {
		super();
		this.content = content;
	}

    public List getContent() {
		return content;
	}

    public void setContent(List content) {
		this.content = content;
	}
	
}