/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSelectedCllbk;

@Repository
public interface MudeTGeecoSelectedCllbkRepository extends BaseRepository<MudeTGeecoSelectedCllbk, Long> {

/**
with lastselected as (
	select max(id_selected) id_selected,sessione_geeco 
	from mudeopen.mudeopen_t_geeco_selected_cllbk
	group by sessione_geeco
)
select ss.* from mudeopen.mudeopen_t_geeco_selected_cllbk ss 
inner join lastselected mm on mm.sessione_geeco=ss.sessione_geeco and mm.id_selected=ss.id_selected
where 1=1
and ss.sessione_geeco='TVVERV9NVURFT1BFTl9NVURFT1BFTl8xNjUxODQxODgzOTA3'
	 * 
	 * 
	 * @param sessioneGeeco
	 
	 */
	
	@Query(
	value = "with lastselected as (\r\n"
			+ "  select max(id_selected) id_selected,sessione_geeco \r\n"
			+ "  from mudeopen.mudeopen_t_geeco_selected_cllbk\r\n"
			+ "  group by sessione_geeco\r\n"
			+ ")\r\n"
			+ "select ss.* from mudeopen.mudeopen_t_geeco_selected_cllbk ss \r\n"
			+ "inner join lastselected mm on mm.sessione_geeco=ss.sessione_geeco and mm.id_selected=ss.id_selected\r\n"
			+ "where 1=1\r\n"
			+ "and ss.sessione_geeco=:sessioneGeeco", nativeQuery = true)	
	List<MudeTGeecoSelectedCllbk> findBySessioneGeeco(@Param("sessioneGeeco")  String sessioneGeeco);

	@Query(value = "SELECT callback_result\\:\\:varchar FROM mudeopen_t_geeco_selected_cllbk WHERE sessione_geeco = :geecosessionid", nativeQuery = true)
	String recuperaDatiGeeco(@Param("geecosessionid") String geecosessionid);


    @Query(value = "SELECT mtgsc.* "
			    		+ " FROM mudeopen_t_geeco_session mtgs "
			    		+ "     INNER JOIN mudeopen_t_geeco_selected_cllbk mtgsc ON mtgsc.sessione_geeco = mtgs.sessione_geeco AND callback_result IS NOT NULL "
			    		+ " WHERE id_istanza  = :idIstanza "
			    		+ " ORDER BY id_session DESC "
			    		+ " LIMIT 1", nativeQuery = true)
    MudeTGeecoSelectedCllbk getLatestSessionPosition(@Param("idIstanza") Long idIstanza);
	
    
    /*
	SELECT ST_AsText(ST_Transform(ST_GeomFromText('POLYGON((743238 2967416,743238 2967450,
	  743265 2967450,743265.625 2967416,743238 2967416))',2249),4326)) As wgs_geom
	  
	SELECT 
		ST_Transform(
				ST_GeomFromText('POINT(743238 2967416)',2249)
				,4326) AS wgs_point,
		ST_XMax(
			ST_Transform(
				ST_GeomFromText('POINT(743238 2967416)',2249)
				,4326)
		) as longitude,
		ST_YMax(
			ST_Transform(
				ST_GeomFromText('POINT(743238 2967416)',2249)
				,4326)
		) as latitude
		
	SELECT ST_X(geom) as longitude, ST_Y(geom) as latitude FROM 
	 (SELECT  ST_Transform(ST_SetSrid(ST_MakePoint(396641.08, 4994078.03), 32632), 4326) as geom) g
	
	SELECT ST_AsText('POINT(-0.685239554498251 51.4940418030595)'::geometry, 4)
       st_astext
 
     */
    
    @Query(value = "SELECT CONCAT_WS(',', ST_X(geom), ST_Y(geom)) FROM "
    		+ " (SELECT  ST_Transform(ST_SetSrid(ST_MakePoint(:xPoint, :yPoint), :fromType), :toType) as geom) g", nativeQuery = true)
    String convertPoint(@Param("xPoint") Double xPoint, @Param("yPoint") Double yPoint, @Param("fromType") Integer fromType, @Param("toType") Integer toType);
	
}
