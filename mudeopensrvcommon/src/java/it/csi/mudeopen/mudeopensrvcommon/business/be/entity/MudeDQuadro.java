/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_quadro")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeDQuadro extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4504777659665007782L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quadro")
    private Long idQuadro;

    @ManyToOne
    @JoinColumn(name = "id_tipo_quadro")
    private MudeDTipoQuadro mudeDTipoQuadro;

    @Column(name = "num_versione")
    private long numVersione;

    @Column(name = "flg_tipo_gestione")
    private String flgTipoGestione;

    @Type(type = "jsonb")
    @Column(name = "json_configura_quadro", columnDefinition = "jsonb")
    private String jsonConfiguraQuadro;

    @Column(name = "json_default")
    private String jsonDefault;

    @Column(name = "validation_script")
    private String validationScript;

	@ManyToOne
	@JoinColumn(name="id_modello_documentale")
	private MudeTModello modello;

    public Long getIdQuadro() {
        return idQuadro;
    }

    public void setIdQuadro(Long idQquadro) {
        this.idQuadro = idQquadro;
    }

    public MudeDTipoQuadro getMudeDTipoQuadro() {
        return mudeDTipoQuadro;
    }

    public void setMudeDTipoQuadro(MudeDTipoQuadro mudeDTipoQuadro) {
        this.mudeDTipoQuadro = mudeDTipoQuadro;
    }

    public long getNumVersione() {
        return numVersione;
    }

    public void setNumVersione(long numVersione) {
        this.numVersione = numVersione;
    }

    public String getFlgTipoGestione() {
        return flgTipoGestione;
    }

    public void setFlgTipoGestione(String flgTipoGestione) {
        this.flgTipoGestione = flgTipoGestione;
    }

    public String getJsonConfiguraQuadro() {
        return jsonConfiguraQuadro;
    }

    public void setJsonConfiguraQuadro(String jsonConfiguraQuadro) {
        this.jsonConfiguraQuadro = jsonConfiguraQuadro;
    }

    public String getJsonDefault() {
        return jsonDefault;
    }

    public void setJsonDefault(String jsonConfiguraRiepilogo) {
        this.jsonDefault = jsonConfiguraRiepilogo;
    }

    public String getValidationScript() {
        return validationScript;
    }

    public void setValidationScript(String validationScript) {
        this.validationScript = validationScript;
    }

    public MudeTModello getModello() {
		return modello;
	}

    public void setModello(MudeTModello modello) {
		this.modello = modello;
	}

    public String getTopMostCodiceTipoQuadro() {
        if(mudeDTipoQuadro != null) {
        	if(mudeDTipoQuadro.getTipoQuadroPadre() != null)
            	return mudeDTipoQuadro.getTipoQuadroPadre().getCodTipoQuadro();
        		
        	return mudeDTipoQuadro.getCodTipoQuadro();
        }

        return null;
    }
	
}