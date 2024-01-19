package lascaux.cinemille.model;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id", updatable = false, nullable = false)
    private UUID movieId;
	
	@Column(name = "title", updatable = true, nullable = false)
    private String title;
	
	@Column(name = "synopsis", updatable = true, nullable = true)
    private String synopsis;
	
	@Column(name = "release_date", updatable = true, nullable = false)
    private LocalDate releaseDate;
	
	@Column(name = "running_time", updatable = true, nullable = false)
    private int runningTime;
	
	@Column(name = "director_id", updatable = true, nullable = false)
    private UUID director_id;
    
   
}
