package it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CivicoVO extends Civico {
	
	@JsonIgnore
	private Via via;

	public Via getVia() {
		return via;
	}

	public void setVia(Via via) {
		this.via = via;
	}
}

