package lascaux.cinemille.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logging {

	Logger logger;
	
	public Logging() {
		super();
		this.logger = LoggerFactory.getLogger(Logging.class);
	}
	
	public void forward(String level, String msg, String payload) {
	
	String log = "[cinemille] [" + msg + "] [" + payload + "]";
	
	switch(level) {
		case "INFO": {
						this.logger.info( log );
					} break;
					
		case "ERROR": {
						this.logger.error( log );
					} break;
		
		case "DEBUG": {
						this.logger.debug( log );
					} break;
					
		case "TRACE": {
						this.logger.trace( log );
					} break;
					
		case "WARN": {
						this.logger.warn( log );
					} break;
	
		}
	
	}
		
}
