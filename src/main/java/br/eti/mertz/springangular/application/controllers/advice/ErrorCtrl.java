package br.eti.mertz.springangular.application.controllers.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorCtrl {
	
	static final Logger logger = LoggerFactory.getLogger(ErrorCtrl.class);
	
	@ExceptionHandler(ServerException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	@ResponseBody
	public ServerException serverError(ServerException ex) {
		logger.error("Internal server error: {}, cause: {}", ex.getMessage(), ex.getCause());
		return ex;
	}
	
	@ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ServerException usernameError(Exception ex) {
		logger.error("Internal server error: {}, cause: {}", ex.getMessage(), ex.getCause());
		return new ServerException(ex);
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String unknownError(Exception e) throws ServerException {
		
		String error = "Ops! Unknown error occurred.";
		
		if(e != null) {
			if(StringUtils.hasText(e.getMessage())) {
				error = e.getMessage();				
			} else if (e.getCause() != null && 
					StringUtils.hasText(e.getCause().getMessage())) {
				error = e.getCause().getMessage();
			}
		}
		
		logger.error("Unknown error: {}", error);
		
		return error;
	}

}
