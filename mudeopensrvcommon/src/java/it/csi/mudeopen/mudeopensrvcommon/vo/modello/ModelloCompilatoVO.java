/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.modello;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ModelloCompilatoVO implements Serializable {

	private static final long serialVersionUID = 7682288869524486457L;

	private String filename;

    byte[] fileContent;

    public String getFilename() {
		return filename;
	}

    public void setFilename(String filename) {
		this.filename = filename;
	}

    public byte[] getFileContent() {
		return fileContent;
	}

    public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModelloCompilatoVO [filename=");
		builder.append(filename);
		builder.append(", fileContent=");
		builder.append(Arrays.toString(fileContent));
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fileContent);
		result = prime * result + Objects.hash(filename);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ModelloCompilatoVO other = (ModelloCompilatoVO) obj;
		return Arrays.equals(fileContent, other.fileContent) && Objects.equals(filename, other.filename);
	}
	
	

}