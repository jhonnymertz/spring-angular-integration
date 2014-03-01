package br.eti.mertz.springangular.application.domain.access;

import java.io.Serializable;


public class Password implements Serializable {
	
	private static final long serialVersionUID = 8824155423348403064L;

	private String value;

	private String confirm;

	private String older;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getOlder() {
		return older;
	}

	public void setOlder(String older) {
		this.older = older;
	}
}