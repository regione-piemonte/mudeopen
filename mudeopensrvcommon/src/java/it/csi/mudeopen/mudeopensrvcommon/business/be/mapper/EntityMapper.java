/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;

import java.util.ArrayList;
import java.util.List;

public interface EntityMapper<EntityType, VOType> {

    default VOType mapEntityToVO(EntityType dto, MudeTUser user) {
    	return mapEntityToVO(dto, user, null);
	}

    EntityType mapVOtoEntity(VOType vo, MudeTUser user);

    default List<EntityType> mapListVOtoListEntity(List<VOType> vl, MudeTUser user) {
		if (null == vl) return null;
		
		List<EntityType> t = new ArrayList<>();
		for (VOType v : vl)
			t.add(mapVOtoEntity(v, user));

		return t;
	}

    default List<VOType> mapListEntityToListVO(List<EntityType> tl, MudeTUser user, String filters) {
		if (null == tl) return null;
		
		List<VOType> v = new ArrayList<>();
		for (EntityType t : tl)
			v.add(mapEntityToVO(t, user, filters));

		return v;
    }

    default List<VOType> mapListEntityToListVO(List<EntityType> tl, MudeTUser user) {
    	return mapListEntityToListVO(tl, user, null);
	}

    default List<VOType> mapListEntityToListSlimVO(List<EntityType> tl, MudeTUser user, String filters) {
		if (null == tl) return null;
		
		List<VOType> v = new ArrayList<>();
		for (EntityType t : tl)
			v.add(mapEntityToSlimVO(t, user, filters));

		return v;
	}

    default List<VOType> mapListEntityToListSlimVO(List<EntityType> tl, MudeTUser user) {
    	return mapListEntityToListSlimVO(tl, user, null);
    }

    default VOType mapEntityToSlimVO(EntityType dto, MudeTUser user) {
    	return mapEntityToSlimVO(dto, user, null);
    }

    default VOType mapEntityToSlimVO(EntityType dto, MudeTUser user, String filters) {
    	return null;
    }

	default VOType mapEntityToVO(EntityType dto, MudeTUser user, String filters) {
		return mapEntityToVO(dto, user);
	}

   default boolean hasFilter(String filters, String filter) {
    	return filters != null && filters.indexOf(filter) > -1;
    }
}