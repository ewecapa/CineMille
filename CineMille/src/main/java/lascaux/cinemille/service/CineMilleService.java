package lascaux.cinemille.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.servlet.http.HttpServletRequest;
import lascaux.cinemille.exception.BusinessException;
import lascaux.cinemille.exception.SystemException;
import lascaux.cinemille.model.dto.Error;
import lascaux.cinemille.model.dto.ErrorResponse;
import lascaux.cinemille.model.dto.ErrorType;
import lascaux.cinemille.model.dto.Logging;
import lascaux.cinemille.repository.CineMilleRepository;
import net.minidev.json.JSONObject;

@Service
public class CineMilleService {


	/*-----------------------------------------*/
	/* Dependency Injection */
	/*-----------------------------------------*/
	
	/**
	 * Entity used for logging
	 */
	@Autowired
	private Logging log;
	
	/**
	 * The repository class, used to store all the queries to DB and execute them
	 */
	@Autowired
	private CineMilleRepository cmRepository;
	
	
	/*-----------------------------------------*/
	/* CRUD Methods */
	/*-----------------------------------------*/
	
	public List<JSONObject> getMovies(HttpServletRequest request, String actor){
		
		try {
			
			this.log.forward("DEBUG", "Start: Check if user is a manager", actor);
			if(!this.cmRepository.isManager(actor)) {
				this.log.forward("ERROR", "End: User isn't a manager", actor);
				Error error = new Error(HttpStatus.FORBIDDEN, ErrorType.BSN, request, "User is not allowed to perform this operation", "User " + actor + " isn't a manager");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: User is a manager", actor);
			
			this.log.forward("DEBUG", "Start: SQL query for getting movies", "");
			List<JSONObject> movies = this.cmRepository.getMovies();
			this.log.forward("DEBUG", "End: SQL query for getting movies", movies.toString());
			
			return movies;
		}
		catch(BusinessException | SystemException e) {
    		throw e;
      	}
      	catch(Exception e) { 
      		throw e;
  		}
		
	}
}
