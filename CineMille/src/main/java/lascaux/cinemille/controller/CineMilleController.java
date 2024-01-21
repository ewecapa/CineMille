package lascaux.cinemille.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lascaux.cinemille.model.DirectorRequest;
import lascaux.cinemille.model.GenreRequest;
import lascaux.cinemille.model.MovieCreateRequest;
import lascaux.cinemille.model.SchedulingRequest;
import lascaux.cinemille.model.dto.Error;
import lascaux.cinemille.model.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lascaux.cinemille.exception.BusinessException;
import lascaux.cinemille.exception.SystemException;
import lascaux.cinemille.model.dto.ErrorType;
import lascaux.cinemille.model.dto.Logging;
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
	 *  <p>Create movie</p>
	 * 
	 * Handler method for HTTP POST request that allows creating a movie.
	 *  
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @param payload			the body containing all data
	 * @return ResponseEntity
	 */
	@PostMapping("movies")
	public ResponseEntity createMovie(HttpServletRequest request,
			  						  @RequestHeader(value = "actor", 
			  						  				 required = true) String actor,
			  						  @RequestBody MovieCreateRequest payload) {
		try {
			this.log.forward("INFO", "Request: Insert a movie in the DB", "");
			JSONObject result = this.cmService.createMovie(request,actor,payload);
			this.log.forward("INFO", "Response: Insert a movie in the DB", result.toString());
			
			return new ResponseEntity(result, HttpStatus.OK);
			} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());
			
			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to create movie",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);
		
			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 *  <p>Create genre</p>
	 * 
	 * Handler method for HTTP POST request that allows creating a genre.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @param payload			the body containing all data
	 * @return ResponseEntity
	 */
	@PostMapping("genres")
	public ResponseEntity createGenre(HttpServletRequest request,
									  @RequestHeader(value = "actor", 
													 required = true) String actor,
									  @RequestBody GenreRequest payload) {
		try {
			this.log.forward("INFO", "Request: Insert a genre in the DB", "");
			JSONObject result = this.cmService.createGenre(request,actor,payload);
			this.log.forward("INFO", "Response: Insert a genre in the DB", result.toString());
			
			return new ResponseEntity(result, HttpStatus.OK);
			} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());
			
			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to create genre",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);
		
			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 *  <p>Create director</p>
	 * 
	 * Handler method for HTTP POST request that allows creating a director.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @param payload			the body containing all data
	 * @return ResponseEntity
	 */
	@PostMapping("directors")
	public ResponseEntity createDirector(HttpServletRequest request,
										 @RequestHeader(value = "actor", 
										 				required = true) String actor,
										 @RequestBody DirectorRequest payload) {
		try {
			this.log.forward("INFO", "Request: Insert a director in the DB", "");
			JSONObject result = this.cmService.createDirector(request,actor,payload);
			this.log.forward("INFO", "Response: Insert a director in the DB", result.toString());
			
			return new ResponseEntity(result, HttpStatus.OK);
			} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());
			
			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to create director",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);
		
			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 *  <p>Create schedule</p>
	 * 
	 * Handler method for HTTP POST request that allows creating a schedule.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @param payload			the body containing all data
	 * @return ResponseEntity
	 */
	@PostMapping("schedulings")
	public ResponseEntity createScheduling(HttpServletRequest request,
										   @RequestHeader(value = "actor", 
										   				  required = true) String actor,
										   @RequestBody SchedulingRequest payload) {
		try {
			this.log.forward("INFO", "Request: Insert a schedule in the DB", "");
			JSONObject result = this.cmService.createScheduling(request,actor,payload);
			this.log.forward("INFO", "Response: Insert a schedule in the DB", result.toString());
			
			return new ResponseEntity(result, HttpStatus.OK);
			} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());
			
			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to create schedule",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);
		
			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * <p>Get all movies</p>
	 * 
	 * Handler method for HTTP GET request that allows fetching a list of all
	 * movies. This method retrieves all the movies in the database, regardless of
	 * their scheduling.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @return {@code ResponseEntity} containing the movies list
	 */
	@GetMapping("movies")
	public ResponseEntity getMovies(HttpServletRequest request,
									@RequestHeader(value = "actor", 
												   required = true) String actor) {

		try {
			this.log.forward("INFO", "Request: Get movies", "");
			JSONObject result = this.cmService.getMovies(request, actor);
			this.log.forward("INFO", "Response: Get movies", result.toString());

			return new ResponseEntity(result, HttpStatus.OK);
		} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());

			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to get movies",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);

			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * <p>Get all projections in a specific period</p>
	 * 
	 * Handler method for HTTP GET request that allows fetching a list of all
	 * projections. This method retrieves all the projections for every movies in a 
	 * specified period.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * @param startDate 		is the starting point from which starting search projection in the week. 
	 * 
	 * @return {@code ResponseEntity} containing the movies list
	 */
	@GetMapping("movies/projections")
	public ResponseEntity getWeekProjections(HttpServletRequest request,
												@RequestHeader(value = "actor", 
															   required = true) String actor,
												@RequestParam (value = "startDate",
															   required = false,
															   defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate startDate) {

		try {
			this.log.forward("INFO", "Request: Get movies projections", "");
			JSONObject result = this.cmService.getWeekProjections(request, actor, startDate.toString());
			this.log.forward("INFO", "Response: Get movies projections", result.toString());

			return new ResponseEntity(result, HttpStatus.OK);
		} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());

			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to get movies projections",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);

			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * <p>Get all projections ever</p>
	 * 
	 * Handler method for HTTP GET request that allows fetching a list of all
	 * projections. This method retrieves all the projections fot all the movies in the database,
	 * regardless of a specific period.
	 * 
	 * Only managers can access this EndPoint, if a non-manager calls it, it will return an error.
	 * 
	 * @param request           is the HTTP Request
	 * @param actor             is the one who performed the action
	 * 
	 * @return {@code ResponseEntity} containing the projections list
	 */
	@GetMapping("movies/projections/history")
	public ResponseEntity getProjectionsHistory(HttpServletRequest request,
												@RequestHeader(value = "actor", 
															   required = true) String actor) {

		try {
			this.log.forward("INFO", "Request: Get movies projections history", "");
			JSONObject result = this.cmService.getProjectionsHistory(request, actor);
			this.log.forward("INFO", "Response: Get movies projections history", result.toString());

			return new ResponseEntity(result, HttpStatus.OK);
		} 
		catch (BusinessException | SystemException e) {
			this.log.forward("ERROR", "Response: " + e.getError().getMessage(), e.getError().toString());
			ErrorResponse errorResponse = new ErrorResponse(e.getError());

			return new ResponseEntity(errorResponse, e.getError().getStatus());
		} 
		catch (Exception e) {
			Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.SYS, request, "Unable to get movies projections history",e.getMessage(), e);
			this.log.forward("ERROR", "Response: " + error.getMessage(), error.toString());
			ErrorResponse errorResponse = new ErrorResponse(error);

			return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
