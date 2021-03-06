package es.pongo.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response implements Serializable{
	
	private class FieldError{
		private String field;
		private List<String> codes;
		
		public FieldError(String field) {
			this.field = field;
			this.codes = new LinkedList<String>();
		}

		public String getField() {
			return field;
		}

		public List<String> getCodes() {
			return codes;
		}
	}
	
	private static final long serialVersionUID = 1098621883531475888L;
	
    private String exception;
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> errors;

    public Response(HttpStatus status, Exception exception) {
		this.status = status;
		this.exception = exception.getMessage();
		this.errors = new LinkedList<FieldError>();
	}

    public Response(HttpStatus status, Exception exception, String message) {
		this.status = status;
		this.message = message;
		this.exception = exception.getMessage();
		this.errors = new LinkedList<FieldError>();
	}

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status.value();
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List<FieldError> getErrors() {
		return errors;
	}
	
	public void addFieldError(String field, String code){
		FieldError fieldError = null;
		for(FieldError aux : errors){
			if(aux.getField().equals(field)){
				fieldError = aux;
				break;
			}
		}
		
		if(fieldError == null){
			fieldError = new FieldError(field);
			errors.add(fieldError);
		}
		
		fieldError.getCodes().add(code);
	}
}
