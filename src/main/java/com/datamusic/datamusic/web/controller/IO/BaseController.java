package com.datamusic.datamusic.web.controller.IO;

import java.util.Map;
import java.util.HashMap;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.ServletException;

@RestControllerAdvice
public class BaseController {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handleValidateExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			errors.put(fieldName, message);
		});
        ApiResponse response = new ApiResponse(false, "Han ocurrido errores",null,errors);
        return response;
		
	}
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResponse handleException(Exception e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", e.getMessage());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido errores", null,errors);
        return respuesta;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseBody
    public ApiResponse handleExceptionNoResourceFoundException(NoResourceFoundException e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", "No se ha encontrado el recurso "+e.getResourcePath()+" para el metodo HTTP "+e.getHttpMethod());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido errores", null,errors);
        return respuesta;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServletException.class})
    @ResponseBody
    public ApiResponse handleExceptionRequestMethodNotSupportedException(ServletException e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", e.getMessage());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido errores", null,errors);
        return respuesta;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ApiResponse handleExceptionConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", e.getMessage());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido errores", null,errors);
        return respuesta;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLGrammarException.class})
    @ResponseBody
    public ApiResponse handleExceptionSQLGrammarException(SQLGrammarException e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", e.getMessage());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido errores", null,errors);
        return respuesta;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public ApiResponse handleExceptionDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", e.getLocalizedMessage());
        ApiResponse respuesta = new ApiResponse(false, "Han ocurrido un error de violacion de integridad", null,errors);
        return respuesta;
    }
    
 
}
