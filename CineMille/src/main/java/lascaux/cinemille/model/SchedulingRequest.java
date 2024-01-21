package lascaux.cinemille.model;

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
public class SchedulingRequest {
	private UUID movieId;
	private String startDate;
	private String endDate;
	
	public SchedulingRequest(@JsonProperty("movieId") UUID movieId,
							 @JsonProperty("startDate") String startDate,
							 @JsonProperty("endDate") String endDate) {
		this.movieId = movieId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
	    return gson.toJson(this, SchedulingRequest.class);
	}

}
