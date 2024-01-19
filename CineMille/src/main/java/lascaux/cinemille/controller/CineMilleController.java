package lascaux.cinemille.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lascaux.cinemille.model.dto.Error;
import lascaux.cinemille.model.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lascaux.cinemille.exception.BusinessException;
import lascaux.cinemille.exception.SystemException;
import lascaux.cinemille.model.dto.ErrorType;
import lascaux.cinemille.model.dto.Logging;
import lascaux.cinemille.repository.CineMilleRepository;
import lascaux.cinemille.service.CineMilleService;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("lascaux/cinemille/")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CineMilleController {
	
	/*-----------------------------------------*/
	/* Dependency Injection */
	/*-----------------------------------------*/
	
	/**
	 * Entity used for logging
	 */
	@Autowired
	private Logging log;
	
	/**
	 * The service class, used to perform the most complicated operations
	 */
	@Autowired
	private CineMilleService cmService;
	
	
	
	/*-----------------------------------------*/
	/* CRUD Methods */
	/*-----------------------------------------*/
	
	/**
	 * <p> Get all movies </p>
	 * 
	 * Handler method for HTTP GET request that allows fetching a list of all movies.
	 * This method retrieves all the movies in the database, regardless of their scheduling.
	 * 
	 * @return {@code ResponseEntity} containing the movies list
	 */
	@GetMapping("movies")
	public ResponseEntity getMovies(HttpServletRequest request, @RequestHeader(value="actor", required=true) String actor) {
		
		try {
    		this.log.forward("INFO", "Request: Get movies", "");	
  			List<JSONObject> result = this.cmService.getMovies(request,actor);
			this.log.forward("INFO", "Response: Get movies", result.toString());
						
			return new ResponseEntity(result, HttpStatus.OK);	
      	}
    	catch(BusinessException | SystemException e) {
    		this.log.forward("ERROR", "Response: " + e.getError().getMessage() , e.getError().toString());
    		ErrorResponse errorResponse = new ErrorResponse(e.getError());
    		
      		return new ResponseEntity(errorResponse, e.getError().getStatus());
      	}
      	catch(Exception e) { 
      		Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to get movies", e.getMessage(), e);
      		this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
      		ErrorResponse errorResponse = new ErrorResponse(error);
      		
      		return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);	
  		}
	}

}
