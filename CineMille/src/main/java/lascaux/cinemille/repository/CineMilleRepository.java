package lascaux.cinemille.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lascaux.cinemille.model.Film;
import net.minidev.json.JSONObject;

@Repository
public interface CineMilleRepository extends JpaRepository<Film,UUID>{

	@Query(value = "SELECT CAST(f.film_id AS VARCHAR) AS \"filmId\", f.title, f.release_date AS \"releaseDate\", "
			+ "f.running_time AS \"runningTime\", d.director_id AS \"directorId\", d.name || ' ' || d.surname AS director "
			+ "FROM film f JOIN director d ON f.director_id = d.director_id "
			, nativeQuery = true)
	public JSONObject getFilmById();
}
