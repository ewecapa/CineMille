package lascaux.cinemille.exception;

import lascaux.cinemille.model.dto.Error;

@SuppressWarnings("serial")
public class BusinessException extends BaseException {

	public BusinessException(Error error) {
		super(error);
	}

}
