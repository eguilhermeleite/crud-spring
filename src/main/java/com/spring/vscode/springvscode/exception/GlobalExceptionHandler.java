package com.spring.vscode.springvscode.exception;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // classe generica para excecoes
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class  TransactionFailed{

        private Instant timestamp;
        private int status;
        private String message;
        private String path;

    }// 

    // classe para validar campos
	@Getter
    @Setter
	@NoArgsConstructor
	private static class InvalidFields {

		private int status;
		private String message;
		private List<String> errors;

	}//


    // validacao de campos
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public InvalidFields argNotValid(MethodArgumentNotValidException exc) {

		List<String> errors = 
			exc.getBindingResult().getFieldErrors().stream()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.collect(Collectors.toList());

		InvalidFields inf = new InvalidFields();

		inf.setStatus(HttpStatus.BAD_REQUEST.value());
		inf.setMessage("validation failure");
		inf.setErrors(errors);
		
		return inf;

	}// 

	// recurso nao encontrado
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public TransactionFailed notFound(NoSuchElementException exc, HttpServletRequest req ){

		TransactionFailed apiExc = new TransactionFailed(Instant.now(), HttpStatus.NOT_FOUND.value(), exc.getMessage(), req.getRequestURI());
		return apiExc;
	}//
	 
	

	// excecao na transacao
	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public TransactionFailed notCommit(TransactionSystemException exc, HttpServletRequest req ){

		TransactionFailed apiExc = new TransactionFailed(Instant.now(), HttpStatus.BAD_REQUEST.value(), exc.getMessage(), req.getRequestURI());
		return apiExc;
	}//
	

}
