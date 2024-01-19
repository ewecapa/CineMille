package lascaux.cinemille.model.dto;

import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

public class Error {

	/*----------------------------------------------------------------------*/
	/* General information about the HTTP Status */
	/*----------------------------------------------------------------------*/

	/** The status code of the HTTP response */
	private int statusCode;

	/** The HTTP status indicating the nature of the error */
	private HttpStatus status;

	/*----------------------------------------------------------------------*/
	/* Error Metadata */
	/*----------------------------------------------------------------------*/

	/** Unique identifier assigned to the error trace */
	private UUID errorTraceId = UUID.randomUUID();

	/** The error type */
	private String type;

	/** The stack trace information of the error */
	private String[] stackTrace;

	/** A URI that identifies the specific occurrence of the error */
	private String instance;

	/*----------------------------------------------------------------------*/
	/* Error Message and Additional Details */
	/*----------------------------------------------------------------------*/

	/** The title or internal general message describing the error */
	private String message;

	/**
	 * The internal detail message providing additional information about the error
	 */
	private String detail;

	/*-------------------------------------------------------------------------------------------------------*/
	/* Constructors */
	/*-------------------------------------------------------------------------------------------------------*/

	public Error(HttpStatus status, ErrorType type, String message, String detail) {
		super();
		this.statusCode = status.value();
		this.status = status;
		this.type = type.name();
		this.stackTrace = ExceptionUtils.getStackFrames(new Throwable());
		this.instance = "";
		this.message = message;
		this.detail = detail;
	}

	public Error(HttpStatus status, ErrorType type,  HttpServletRequest request, String message, String detail) {
		super();
		this.statusCode = status.value();
		this.status = status;
		this.type = type.name();
		this.stackTrace = ExceptionUtils.getStackFrames(new Throwable());
		this.instance = request.getRequestURI();
		this.message = message;
		this.detail = detail;

	}

	public Error(HttpStatus status, ErrorType type, HttpServletRequest request, String message, String detail, Throwable t) {
		super();
		this.statusCode = status.value();
		this.status = status;
		this.type = type.name();
		this.stackTrace = ExceptionUtils.getStackFrames(t);
		this.instance = request.getRequestURI();
		this.message = message;
		this.detail = detail;

	}

	/*-------------------------------------------------------------------------------------------------------*/
	/* Getters & Setters */
	/*-------------------------------------------------------------------------------------------------------*/
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the stackTrace
	 */
	public String[] getStackTrace() {
		return stackTrace;
	}

	/**
	 * @param stackTrace the stackTrace to set
	 */
	public void setStackTrace(String[] stackTrace) {
		this.stackTrace = stackTrace;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the instance
	 */
	public String getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(String instance) {
		this.instance = instance;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the errorTraceId
	 */
	public UUID getErrorTraceId() {
		return errorTraceId;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/* ToString */
	/*-------------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this, Error.class);
	}
}
