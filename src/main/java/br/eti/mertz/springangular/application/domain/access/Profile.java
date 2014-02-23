package br.eti.mertz.springangular.application.domain.access;

public enum Profile {
	ADMINISTRATOR,
	OPERATOR;
	
	public String getBundleName() {
		return "profile.role." + this;
	}
}