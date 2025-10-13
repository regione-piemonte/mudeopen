/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ping;
public class PingVO {
	
	private String label;

	private String status;
    public String getLabel() {
		return label;
	}

    public void setLabel(String label) {
		this.label = label;
	}

    public String getStatus() {
		return status;
	}

    public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PingVO other = (PingVO) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Ping {\n");
	    
	    sb.append("    label: ").append(toIndentedString(label)).append("\n");
	    sb.append("    status: ").append(toIndentedString(status)).append("\n");
	    sb.append("}");
	    return sb.toString();
		
	}
	
	  private String toIndentedString(Object o) {

	    if (o == null) {
	      return "null";
	    }

	    return o.toString().replace("\n", "\n    ");
	  }
	
}