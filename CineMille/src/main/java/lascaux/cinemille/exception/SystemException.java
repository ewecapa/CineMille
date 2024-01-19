package lascaux.cinemille.exception;

import java.util.Arrays;

import lascaux.cinemille.model.dto.Error;

@SuppressWarnings("serial")
public class SystemException extends BaseException {

	public SystemException(Error error) {
		super(error);
	}

	@Override
	public String toString() {
		return "SystemException [error=" + error + ", getStackTrace()=" + Arrays.toString(getStackTrace()) + "]";
	}

}
