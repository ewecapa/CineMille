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
@Table(name = "film")
public class Film {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id", updatable = false, nullable = false)
    private UUID filmId;
	
	@Column(name = "title", updatable = true, nullable = false)
    private String title;
	
	@Column(name = "synopsis", updatable = true, nullable = true)
    private String synopsis;
	
	@Column(name = "releaseDate", updatable = true, nullable = false)
    private LocalDate releaseDate;
	
	@Column(name = "runningTime", updatable = true, nullable = false)
    private int runningTime;
	
	@Column(name = "director_id", updatable = true, nullable = false)
    private UUID director_id;
    
   
}
