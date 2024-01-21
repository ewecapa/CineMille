package lascaux.cinemille.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import lascaux.cinemille.model.Movie;
import net.minidev.json.JSONObject;

@Repository
public interface CineMilleRepository extends JpaRepository<Movie,UUID>{

	
	/*------------------------------------------------------------------------*/
	/*    						INSERT - Void                                 */
	/*------------------------------------------------------------------------*/
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO movie (movie_id, title, synopsis, release_date, running_time, director_id) "
			+ "VALUES(:movieId, :title, :synopsis, Cast(:releaseDate AS DATE), :runningTime, :directorId)", nativeQuery = true)
	public void createMovie(@Param("movieId") UUID movieId, 
							@Param("title") String title, 
							@Param("synopsis") String synopsis, 
							@Param("releaseDate") String releaseDate,
							@Param("runningTime") int runningTime ,
							@Param("directorId") UUID directorId);
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO movie_genre (movie_id, genre_id) VALUES(:movieId, :genreId)", nativeQuery = true)
	public void boundMovieAndGenre(@Param("movieId") UUID movieId, @Param("genreId") UUID genreId);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO genre (genre_id, name) VALUES(:genreId, :name)", nativeQuery = true)
	public void createGenre(@Param("genreId") UUID genreId, @Param("name") String name);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO director (director_id, name,surname,birthday) VALUES(:directorId, :name, :surname, Cast(:birthday AS DATE))", nativeQuery = true)
	public void createDirector(@Param("directorId") UUID directorId, @Param("name") String name, @Param("surname") String surname, @Param("birthday") String birthday);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO scheduling (scheduling_id, movie_id, start_date, end_date) "
			+ "VALUES(:scheduleId, :movieId, Cast(:startDate AS DATE), Cast(:endDate AS date))", nativeQuery = true)
	public void createScheduling(@Param("scheduleId") UUID scheduleId, @Param("movieId") UUID movieId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	
	/*------------------------------------------------------------------------*/
	/*    					   SELECT - Payload                               */
	/*------------------------------------------------------------------------*/
	
	
	@Query(value = "SELECT "
			+ "    CAST(m.movie_id AS varchar) AS \"movieId\", "
			+ "    m.title, "
			+ "    m.synopsis, "
			+ "    m.release_date AS \"releaseDate\", "
			+ "    m.running_time || ' min' AS \"runningtime\", "
			+ "    array_agg(g.\"name\") AS \"genre\", "
			+ "    d.\"name\" || ' ' || d.surname AS \"director\" "
			+ "FROM "
			+ "    movie m "
			+ "    JOIN movie_genre mg ON m.movie_id = mg.movie_id "
			+ "    JOIN genre g ON mg.genre_id = g.genre_id "
			+ "    JOIN director d ON m.director_id = d.director_id "
			+ "GROUP BY "
			+ "    m.movie_id, m.title, m.synopsis, m.release_date, m.running_time, d.\"name\", d.surname; "
			+ ""
			, nativeQuery = true)
	public List<JSONObject> getMovies();
		
	@Query(value = "SELECT   CAST(m.movie_id AS varchar) AS \"movieId\", "
			+ "         m.title, "
			+ "         m.synopsis, "
			+ "         m.running_time AS \"runningTime\", "
			+ "			h.hall_id as \"hallId\", "
			+ "			h.capacity, "
			+ "         h.\"name\", "
			+ "         h.is_imax AS \"HallIsImax\", "
			+ "         p.\"day\", "
			+ "         array_agg(p.\"hour\") AS \"projectionHours\" "
			+ "FROM    movie m "
			+ "   JOIN     scheduling s "
			+ "	ON       m.movie_id = s.movie_id "
			+ "   JOIN     projection p "
			+ "	ON       m.movie_id = p.movie_id "
			+ "   JOIN     hall h "
			+ "	ON       p.hall_id = h.hall_id "
			+ "WHERE   CAST(:startDate AS DATE) BETWEEN s.start_date AND      s.end_date "
			+ "	AND      p.\"day\" < CAST(:startDate AS  DATE) + interval '1 week' "
			+ "	AND      p.\"day\" >= CAST(:startDate AS DATE) "
			+ "GROUP BY m.movie_id, m.title, m.synopsis, m.running_time, h.\"name\", h.is_imax, p.\"day\", h.hall_id, h.capacity "
			+ "ORDER BY p.\"day\", h.\"name\"", nativeQuery = true)
	public List<JSONObject> getWeekProjections(@Param("startDate") String startDate);
	
	@Query(value = "SELECT   CAST(m.movie_id AS varchar) AS \"movieId\", "
			+ "         m.title, "
			+ "         m.synopsis, "
			+ "         m.running_time AS \"runningTime\", "
			+ "			h.hall_id as \"hallId\", "
			+ "			h.capacity, "
			+ "         h.\"name\", "
			+ "         h.is_imax AS \"HallIsImax\", "
			+ "         p.\"day\", "
			+ "         array_agg(p.\"hour\") AS \"projectionHours\" "
			+ "FROM    movie m "
			+ "   JOIN     scheduling s "
			+ "	ON       m.movie_id = s.movie_id "
			+ "   JOIN     projection p "
			+ "	ON       m.movie_id = p.movie_id "
			+ "   JOIN     hall h "
			+ "	ON       p.hall_id = h.hall_id "
			+ "WHERE p.\"day\" < current_date "
			+ "GROUP BY m.movie_id, m.title, m.synopsis, m.running_time, h.\"name\", h.is_imax, p.\"day\", h.hall_id, h.capacity "
			+ "ORDER BY p.\"day\", h.\"name\"", nativeQuery = true)
	public List<JSONObject> getProjectionsHistory();
	
	@Query(value = "SELECT Cast(m.release_date as varchar) FROM movie m WHERE m.movie_id = :movieId", nativeQuery = true)
	public String getReleaseDateByMovieId(@Param("movieId") UUID movieId);
	
	/*------------------------------------------------------------------------*/
	/*    						UPDATE - Void                                 */
	/*------------------------------------------------------------------------*/
	
	
	
	
	
	/*------------------------------------------------------------------------*/
	/*    						DELETE - Void                                 */
	/*------------------------------------------------------------------------*/
	
	
	
	
	
	/*------------------------------------------------------------------------*/
	/*    					IS/EXISTS - true/false                            */
	/*------------------------------------------------------------------------*/
	
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM manager m WHERE m.email = :email)",nativeQuery = true)
	public boolean isManager(@Param("email") String email);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM movie m WHERE lower(m.title) = lower(:title))",nativeQuery = true)
	public boolean existsMovieByTitle(@Param("title") String title);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM movie m WHERE m.movie_id = :movieId)",nativeQuery = true)
	public boolean existsMovieById(@Param("movieId") UUID movieId);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM director d WHERE d.director_id = :directorId)",nativeQuery = true)
	public boolean existsDirectorById(@Param("directorId") UUID directorId);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM genre g WHERE g.genre_id = :genreId)",nativeQuery = true)
	public boolean existsGenreById(@Param("genreId") UUID genreId);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM genre g WHERE Lower(g.name) = Lower(:name))",nativeQuery = true)
	public boolean existsGenreByName(@Param("name") String name);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM director d WHERE Lower(d.name) = Lower(:name) AND Lower(d.surname) = Lower(:surname) AND d.birthday= Cast(:birthday AS DATE))",nativeQuery = true)
	public boolean existsDirectorByInfo(@Param("name") String name, @Param("surname") String surname, @Param("birthday") String birthday);
	
	@Query(value = "SELECT EXISTS (SELECT TRUE FROM scheduling s WHERE s.movie_id = :movieId)",nativeQuery = true)
	public boolean existsScheduleByMovieId(@Param("movieId") UUID movieId);
	
	@Query(value = "SELECT CASE WHEN m.release_date = s.start_date THEN TRUE ELSE FALSE END FROM movie m LEFT JOIN scheduling s ON m.movie_id = s.movie_id WHERE m.movie_id = :movieId",nativeQuery = true)
	public boolean isStartDateEqualToReleaseDate(@Param("movieId") UUID movieId);
	
}
