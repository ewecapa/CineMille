package lascaux.cinemille.model.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

public class ErrorResponse {
		
	/*----------------------------------------------------------------------*/
	/*General information about the HTTP Status*/
	/*----------------------------------------------------------------------*/
	
	/** The status code of the HTTP response*/
	private int statusCode;
	
	/** The reason phrase associated with the HTTP status code*/
	private String reasonPhrase;
	
	/**
     * An optional reference to the documentation that describes the HTTP status code in more detail
     *@see <a href="https://www.rfc-editor.org/rfc/rfc2616.html#section-10">RFC 2616 - Status Code Definitions</a>
     */
	private String reference;
	
	/*----------------------------------------------------------------------*/
	/*General information about the error*/
	/*----------------------------------------------------------------------*/
	
	
	/** A URI that identifies the specific occurrence of the error*/
	private String instance;
	
	/** A unique identifier used for support tracing purposes*/
	private UUID supportTraceId;
	
	/*----------------------------------------------------------------------*/
	/*Detailed dynamic & localized information about the error*/
	/*----------------------------------------------------------------------*/
	
	/** An optional localized title or summary of the error response*/
	private String responseTitle;
	
	/** The localized public message associated with the error response*/
	private String responseMessage;
	
	/*----------------------------------------------------------------------*/
	/*Detailed internal information about the error*/
	/*----------------------------------------------------------------------*/
		
	/** The internal general message describing the error*/
	private String message;
	
	/** The internal detail message providing additional information about the error*/
	private String detail;
	
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*Constructors*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	public ErrorResponse() {
		super();
	}
	
	/**
	 * Constructs an ErrorResponse object based on the given Error object.
	 *
	 * @param error The Error object containing the error information.
	 */
	public ErrorResponse(Error error) {
		this.statusCode = error.getStatusCode();
		this.reasonPhrase = error.getStatus().getReasonPhrase();
		
		//StatusCode Extension
		Map<String, String> statusCodeExtension = this.getStatusCodeExtension(error.getStatusCode());
		this.reference = statusCodeExtension.get("reference");
		this.instance = error.getInstance();
		this.supportTraceId = error.getErrorTraceId();
		this.message = error.getMessage();
				
		//Detail
		if (error.getDetail()==null || (error.getType().equals(ErrorType.SYS.name()) || error.getDetail().isBlank())) {
			this.detail = "Additional details not available";
		}
		else {
			this.detail = error.getDetail();
		}

	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*Getters & Setters*/
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
	 * @return the reasonPhrase
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}

	/**
	 * @param reasonPhrase the reasonPhrase to set
	 */
	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
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
	 * @return the responseTitle
	 */
	public String getResponseTitle() {
		return responseTitle;
	}

	/**
	 * @param responseTitle the responseTitle to set
	 */
	public void setResponseTitle(String responseTitle) {
		this.responseTitle = responseTitle;
	}

	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
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
	 * @return the supportTraceId
	 */
	public UUID getSupportTraceId() {
		return supportTraceId;
	}

	/**
	 * @param supportTraceId the supportTraceId to set
	 */
	public void setSupportTraceId(UUID supportTraceId) {
		this.supportTraceId = supportTraceId;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*ToString*/
	/*-------------------------------------------------------------------------------------------------------*/
	@Override
	public String toString() {
		Gson gson = new Gson();
	    return gson.toJson(this, ErrorResponse.class);
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*getStatusCodeExtension*/
	/*-------------------------------------------------------------------------------------------------------*/
	public Map<String, String> getStatusCodeExtension(int statusCode) {
		Map<String, String> statusCodeExtensions = new HashMap<String, String>();
		
		statusCodeExtensions.put("reference", HttpStatusCodeReference.getValue(statusCode));
		
		return statusCodeExtensions;
	}
			
}
