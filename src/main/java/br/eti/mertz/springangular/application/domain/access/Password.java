package br.eti.mertz.springangular.application.domain.access;

import java.io.Serializable;

/**
 * Classe que representa uma troca entre as senhas do usuário
 */
public class Password implements Serializable {
	
	private static final long serialVersionUID = 8824155423348403064L;
	/**
	 * Valor da senha nova do usuário
	 */
	private String value;
	/**
	 * Confirmação da senha do usuário
	 */
	private String confirm;
	/**
	 * Senha antiga do usuário
	 */
	private String older;
	/**
	 * Obtém o valor da nova senha
	 * 
	 * @return Nova senha
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Define a nova senha
	 * 
	 * @param value Senha a ser definida
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * Obtém a confirmação de senha, que deve ser igual à {@link #getValue()}
	 * 
	 * @return Confirmação de senha
	 */
	public String getConfirm() {
		return confirm;
	}
	/**
	 * Define a confirmação de senha
	 * 
	 * @param confirm Confirmação de senha a ser definida
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	/**
	 * Obtém a senha antiga
	 * 
	 * @return Senha antiga
	 */
	public String getOlder() {
		return older;
	}
	/**
	 * Define a senha antiga
	 * 
	 * @param older Senha antiga a ser definida
	 */
	public void setOlder(String older) {
		this.older = older;
	}
}