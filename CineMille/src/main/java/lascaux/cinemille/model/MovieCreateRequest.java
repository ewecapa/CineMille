package lascaux.cinemille.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Builder
@Getter
@Setter
@Value
@JsonDeserialize()
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreateRequest {

	private String title;
	private String synopsis;
	private String releaseDate;
	private int runningTime;
	private UUID directorId;
	private List<UUID> genres;
	
	public MovieCreateRequest(@JsonProperty("title") String title,
						@JsonProperty("synopsis") String synopsis,
						@JsonProperty("releaseDate") String releaseDate,
						@JsonProperty("runningTime") int runningTime,
						@JsonProperty("directorId") UUID directorId,
						@JsonProperty("genres") List<UUID> genres) {
		this.title 		 = title;
		this.synopsis    = synopsis;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
		this.directorId  = directorId;
		this.genres 	 = genres;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
	    return gson.toJson(this, MovieCreateRequest.class);
	}
	
	
}
