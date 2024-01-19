package lascaux.cinemille.exception;

import java.util.Arrays;

import lascaux.cinemille.model.dto.Error;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	protected Error error;
	
	
	public BaseException() {
		super();
	}

	public BaseException(Error error) {
		super();
		this.error = error;
	}
	
	public BaseException(Error error, Throwable t) {
		super(t);
		this.error = error;
	}
	
	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "BaseException [error=" + error + ", getMessage()=" + getMessage() + ", getStackTrace()="
				+ Arrays.toString(getStackTrace()) + "]";
	}

}
