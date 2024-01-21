package lascaux.cinemille.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lascaux.cinemille.exception.BusinessException;
import lascaux.cinemille.exception.SystemException;
import lascaux.cinemille.model.DirectorRequest;
import lascaux.cinemille.model.GenreRequest;
import lascaux.cinemille.model.MovieCreateRequest;
import lascaux.cinemille.model.SchedulingRequest;
import lascaux.cinemille.model.dto.Error;
import lascaux.cinemille.model.dto.ErrorType;
import lascaux.cinemille.model.dto.Logging;
import lascaux.cinemille.repository.CineMilleRepository;
import net.minidev.json.JSONObject;

@Service
@SuppressWarnings({"unchecked"})
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
	
	@Transactional
	public JSONObject createMovie(HttpServletRequest request, String actor, MovieCreateRequest payload) {
		try {
			// Check if the actor is authorized
			this.isManager(request, actor);	
			
			// Check if already exists a movie with same title
			this.log.forward("DEBUG", "Start: Check if movie already exists", "");
			if(payload.getTitle() != null && payload.getTitle().isBlank() && this.cmRepository.existsMovieByTitle(payload.getTitle())) {
				this.log.forward("ERROR", "End: Movie with this title already exists", "");
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Movie already exists", payload.getTitle() + " already exists");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: No movie with same title", "");
			
			// Check if director exists
			this.log.forward("DEBUG", "Start: Check if director exists", "");
			if(payload.getDirectorId() != null && !this.cmRepository.existsDirectorById(payload.getDirectorId())) {
				this.log.forward("ERROR", "End: Director doesn't exists", "");
				Error error = new Error(HttpStatus.NOT_FOUND, ErrorType.BSN, request, "Not Found", "Director not found");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: Director exists", "");
			
			// Insert Movie
			UUID movieId = UUID.randomUUID();
			this.log.forward("DEBUG", "Start: Insert Movie", "");
			this.cmRepository.createMovie(movieId,
										  payload.getTitle(),
										  payload.getSynopsis(),
										  payload.getReleaseDate(),
										  payload.getRunningTime(),
										  payload.getDirectorId());
			this.log.forward("DEBUG", "End: Movie created", payload.toString());
			
			// Check that every genre exists
			this.log.forward("DEBUG", "Start: Check if every specified genre exists", "");
			if(payload.getGenres() != null && !payload.getGenres().isEmpty()) {
				for(UUID genreId : payload.getGenres()) {
					this.log.forward("DEBUG", "Start: Bound movie and genre " + genreId, "");
					if(!this.cmRepository.existsGenreById(genreId)) {
						this.log.forward("ERROR", "End: genre " + genreId + " does not exists", "");
						Error error = new Error(HttpStatus.NOT_FOUND, ErrorType.BSN, request, "Not Found", "Genre not found");
						throw new BusinessException(error);
					}
					// Bound Movie and Genre
					this.cmRepository.boundMovieAndGenre(movieId,genreId);	
				}
			}
			this.log.forward("DEBUG", "End: Every specified genre exists", "");	
			
			return new JSONObject(Map.of("message","Movie correctly created","movieId",movieId));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public JSONObject createGenre(HttpServletRequest request, String actor, GenreRequest payload) {
		try {
			// Check if the actor is authorized
			this.isManager(request, actor);	
			
			// Check if already exists a genre with same name
			this.log.forward("DEBUG", "Start: Check if genre already exists", "");
			if(payload.getName() != null && !payload.getName().isBlank() && this.cmRepository.existsGenreByName(payload.getName())) {
				this.log.forward("ERROR", "End: Genre already exists", "");
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Genre already exists", payload.getName() + " already exists");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: No Genre with same title", "");
			

			// Insert genre
			UUID genreId = UUID.randomUUID();
			this.log.forward("DEBUG", "Start: Insert genre", "");
			this.cmRepository.createGenre(genreId, payload.getName());
			this.log.forward("DEBUG", "End: Genre created", payload.toString());
			
			return new JSONObject(Map.of("message","Genre correctly created","genreId",genreId));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	@Transactional
	public JSONObject createDirector(HttpServletRequest request, String actor, DirectorRequest payload) {
		try {
			// Check if the actor is authorized
			this.isManager(request, actor);	
			
			// Check if already exists a director with same name
			this.log.forward("DEBUG", "Start: Check if director already exists", "");
			if(payload.getName() != null && payload.getSurname() != null && payload.getBirthday() != null) {
				if(!payload.getName().isBlank() && !payload.getSurname().isBlank()) {
					if(this.cmRepository.existsDirectorByInfo(payload.getName(),payload.getSurname(),payload.getBirthday())){
						this.log.forward("ERROR", "End: Director already exists", "");
						Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Director already exists", payload.getName() + " already exists");
						throw new BusinessException(error);
					}
				}
			}
			this.log.forward("DEBUG", "End: No director with same name", "");

			
			// Insert director
			UUID directorId = UUID.randomUUID();
			this.log.forward("DEBUG", "Start: Insert director", "");
			this.cmRepository.createDirector(directorId, payload.getName(),payload.getSurname(),payload.getBirthday());
			this.log.forward("DEBUG", "End: Director created", payload.toString());
			
			return new JSONObject(Map.of("message","Director correctly created","directorId",directorId));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public JSONObject createScheduling(HttpServletRequest request, String actor, SchedulingRequest payload) {
		try {
			// Check if the actor is authorized
			this.isManager(request, actor);	
			
			//Check if the movie exists and it's not null
			this.log.forward("DEBUG", "Start: Check if movie exists", "");
			if(payload.getMovieId() != null && !this.cmRepository.existsMovieById(payload.getMovieId())) {
				this.log.forward("ERROR", "End: Movie not found", "");
				Error error = new Error(HttpStatus.NOT_EXTENDED, ErrorType.BSN, request, "Movie not found", payload.getMovieId().toString());
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: Movie exists", "");
			
			//Check if there's already a schedule for that movie
			this.log.forward("DEBUG", "Start: Check if schedule exists for this movie", "");
			if(this.cmRepository.existsScheduleByMovieId(payload.getMovieId())) {
				this.log.forward("ERROR", "End: Schedule for that movie already exists", "");
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Schedule already exists","");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "Start: No schedule for this movie", "");
			
			//Check if startDate is equals to releaseDate of movie
			this.log.forward("DEBUG", "Start: Check if startDate is equal to releaseDate", "");
			System.out.println(this.cmRepository.getReleaseDateByMovieId(payload.getMovieId()));
			if(!this.cmRepository.getReleaseDateByMovieId(payload.getMovieId()).equals(payload.getStartDate())) {
				this.log.forward("ERROR", "End: releaseDate and startDate not equals", "");
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "startDate and ReleaseDate not equals","");
				throw new BusinessException(error);
			}
			
			//Check that endDate is not more than 21 day after the startDate
			this.log.forward("DEBUG", "Start: Check if endDate is 21 or less days after startDate", "");
			LocalDate startDateFormatted = LocalDate.parse(payload.getStartDate());
			LocalDate endDateFormatted = LocalDate.parse(payload.getEndDate());
			if(endDateFormatted.isAfter(startDateFormatted.plusDays(21))) {
				this.log.forward("ERROR", "End: Wrong endDate", "");
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Wrong endDate","");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: Correct endDate", "");
			
			//Insert schedule
			this.log.forward("DEBUG", "Start: Create schedule", "");
			UUID scheduleId = UUID.randomUUID();
			this.cmRepository.createScheduling(scheduleId, payload.getMovieId(), payload.getStartDate(), payload.getEndDate());
			this.log.forward("DEBUG", "End: Schedule created", "");
			
			return new JSONObject(Map.of("message","Schedule correctly created","directorId",scheduleId));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}

	public JSONObject getMovies(HttpServletRequest request, String actor) {
		try {

			// Check if the actor is authorized
			this.isManager(request, actor);

			// Get all movies
			this.log.forward("DEBUG", "Start: SQL query for getting movies", "");
			List<JSONObject> movies = this.cmRepository.getMovies();
			this.log.forward("DEBUG", "End: SQL query for getting movies", movies.toString());

			return new JSONObject(Map.of("message","Movies correctly retrieved", "movies",movies));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	public JSONObject getWeekProjections(HttpServletRequest request, String actor, String startDate){
		try {

			LocalDate startDateFormatted = LocalDate.parse(startDate);
			//If the startDate is before today, it means that we're trying to get a sort of history of projections.
			// Only managers are allowed
			if(startDateFormatted.isBefore(LocalDate.now())) {
				
				// Check if the actor is authorized
				this.isManager(request, actor);
			}

			// Get all projection in that period and transform the payload to a structured one
			this.log.forward("DEBUG", "Start: SQL query for getting movies projection", "");
			List<JSONObject> projections = transformPayload(this.cmRepository.getWeekProjections(startDate));
			this.log.forward("DEBUG", "End: SQL query for getting movies projection", projections.toString());
			
			//If there's no movies projected in that week, return 200 and inform that there's no projection
			//Inform that everything is ok otherwise
			String message = projections.isEmpty()? "There's no projections in this week" : "Projection correctly retrieved";
			
			return new JSONObject(Map.of("message",message, "projections",projections));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	public JSONObject getProjectionsHistory(HttpServletRequest request, String actor) {
		
		try {
			// Check if the actor is authorized
			this.isManager(request, actor);	

			// Get all projections for every movies, regardless the period
			this.log.forward("DEBUG", "Start: SQL query for getting movies projection history", "");
			List<JSONObject> projections = transformPayload(this.cmRepository.getProjectionsHistory());
			this.log.forward("DEBUG", "End: SQL query for getting movies projection history", projections.toString());
			
			//If there's no movies projected in that week, return 200 and inform that there's no projection
			//Inform that everything is ok otherwise
			String message = projections.isEmpty()? "There's no projections in the history of CineMille, so strange..." : "Projections history correctly retrieved";
			
			return new JSONObject(Map.of("message",message, "projections",projections));
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	

	/*-----------------------------------------*/
	/* SUPPORT Methods */
	/*-----------------------------------------*/

	/**
	 * <p>Check if the actor is authorized</p>
	 * 
	 * This method access the manager table in the DB and checks if the actor is a
	 * manager. If its not, throws an error.
	 * 
	 * @param actor the one who performed the action
	 */
	private void isManager(HttpServletRequest request, String actor) {

		try {
			this.log.forward("DEBUG", "Start: Check if the header 'actor is a well formed email", actor);
			if(!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(actor).matches()) {
				this.log.forward("ERROR", "End: The header 'actor' has to be a well formed email", actor);
				Error error = new Error(HttpStatus.BAD_REQUEST, ErrorType.BSN, request, "Bad 'actor' header", actor + " isn't a well formed email");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: The header 'actor' is a well formed email", actor);
			
			this.log.forward("DEBUG", "Start: Check if user is a manager", actor);

			// Check if the actor is authorized
			if (!this.cmRepository.isManager(actor)) {
				this.log.forward("ERROR", "End: User isn't a manager", actor);
				Error error = new Error(HttpStatus.FORBIDDEN, ErrorType.BSN, request, "User is not allowed to perform this operation", "User " + actor + " isn't a manager");
				throw new BusinessException(error);
			}
			this.log.forward("DEBUG", "End: User is a manager", actor);
		} 
		catch (BusinessException | SystemException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * <p>Transform payload to a structured one</p>
	 * 
	 * This method transforms the input list of movies with projections into a structured payload
	 * 
	 * @param originalPaylod is the payload to be transformed, directly retrieved by SQL query
	 */
	private List<JSONObject> transformPayload(List<JSONObject> originalPayload) {
        // Resulting list to store the transformed payload
        List<JSONObject> transformedPayload = new ArrayList<>();

        // Map to store movieId to details mapping for efficient processing
        Map<String, JSONObject> movieIdToDetailsMap = new HashMap<>();

        // Iterate over each movie in the original payload
        for (JSONObject movieObject : originalPayload) {
            // Extract the movieId from the current movie object
            String movieId = (String) movieObject.get("movieId");

            // Retrieve or create a details object for the current movieId
            JSONObject details = movieIdToDetailsMap.computeIfAbsent(movieId, k -> {
                JSONObject movieDetails = new JSONObject();
                movieDetails.put("movieId", movieId);
                movieDetails.put("projections", new ArrayList<>());
                movieDetails.put("synopsis", movieObject.get("synopsis"));
                movieDetails.put("title", movieObject.get("title"));
                movieDetails.put("runningTime", movieObject.get("runningTime"));
                return movieDetails;
            });

            // Create a projection object for the current movie details
            JSONObject projection = new JSONObject();
            projection.put("haIlId", movieObject.get("hallId"));
            projection.put("hallName", movieObject.get("name"));
            projection.put("hallIsImax", movieObject.get("HallIsImax"));
            projection.put("capacity", movieObject.get("capacity"));
            projection.put("projectionHours", movieObject.get("projectionHours"));

            // Create a projectionInfo object with day and details for the current movie
            JSONObject projectionInfo = new JSONObject();
            projectionInfo.put("day", movieObject.get("day"));
            projectionInfo.put("details", List.of(projection));

            // Retrieve the list of projections for the current movie details
            List<JSONObject> projections = (List<JSONObject>) details.get("projections");

            // Flag to check if a projection with the same day already exists
            boolean found = false;

            // Iterate over existing projections for the current movie details
            for (JSONObject existingProjection : projections) {
                // Check if the existing projection has the same day as the current movie
                if (existingProjection.get("day").equals(movieObject.get("day"))) {
                    // Retrieve the existing details and add the new details
                    List<JSONObject> existingDetails = new ArrayList<>((List<JSONObject>) existingProjection.get("details"));
                    existingDetails.addAll((List<JSONObject>) projectionInfo.get("details"));

                    // Update the existing projection with the modified details
                    existingProjection.put("details", existingDetails);

                    // Set the flag to indicate that the projection was found
                    found = true;

                    // Exit the loop
                    break;
                }
            }

            // If no existing projection with the same day was found, add the new projectionInfo
            if (!found) {
                projections.add(projectionInfo);
            }
        }

        // Add all movie details to the resulting transformed payload list
        transformedPayload.addAll(movieIdToDetailsMap.values());

        // Return the final transformed payload
        return transformedPayload;
    }
}
