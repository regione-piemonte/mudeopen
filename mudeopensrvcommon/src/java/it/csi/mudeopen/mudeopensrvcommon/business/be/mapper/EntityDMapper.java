/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper;

import java.util.ArrayList;
import java.util.List;

public interface EntityDMapper<EntityType, VOType> {

    VOType mapEntityToVO(EntityType dto);

    default List<VOType> mapListEntityToListVO(List<EntityType> tl) {
		if (null == tl)
			return null;
		List<VOType> v = new ArrayList<>();
		for (EntityType t : tl) {
			v.add(mapEntityToVO(t));
		}
		return v;
	}

    default List<VOType> mapListEntityToListSlimVO(List<EntityType> tl) {
		if (null == tl)
			return null;
		List<VOType> v = new ArrayList<>();
		for (EntityType t : tl) {
			v.add(mapEntityToSlimVO(t));
		}
		return v;
	}

    default VOType mapEntityToSlimVO(EntityType dto) {
    	return null;
    }

}