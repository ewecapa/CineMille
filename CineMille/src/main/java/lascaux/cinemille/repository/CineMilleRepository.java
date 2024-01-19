package lascaux.cinemille.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lascaux.cinemille.model.Movie;
import net.minidev.json.JSONObject;

@Repository
public interface CineMilleRepository extends JpaRepository<Movie,UUID>{

	
	/*------------------------------------------------------------------------*/
	/*    						INSERT - Void                                 */
	/*------------------------------------------------------------------------*/
	
	
	
	
	
	/*------------------------------------------------------------------------*/
	/*    					   SELECT - Payload                               */
	/*------------------------------------------------------------------------*/
	
	
	@Query(value = "SELECT CAST(m.movie_id AS VARCHAR) AS \"movieId\", m.title, m.release_date AS \"releaseDate\", "
			+ "m.running_time AS \"runningTime\", d.director_id AS \"directorId\", d.name || ' ' || d.surname AS director "
			+ "FROM movie m JOIN director d ON m.director_id = d.director_id "
			, nativeQuery = true)
	public List<JSONObject> getMovies();
	
	
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
}
