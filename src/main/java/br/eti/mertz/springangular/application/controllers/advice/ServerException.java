package br.eti.mertz.springangular.application.controllers.advice;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonPropertyOrder({"message", "key", "cause"})
public class ServerException extends Exception {

	private String key;
	
	public ServerException(String message) {
		super(message);
	}
	
	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerException(String message, String key, Throwable cause) {
		super(message, cause);
		this.key = key;
	}

	public ServerException(Throwable cause) {
		super(cause);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
