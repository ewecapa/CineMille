package lascaux.cinemille.model;
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
public class GenreRequest {
	
	private String name;
	
	public GenreRequest(@JsonProperty("name") String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
	    return gson.toJson(this, GenreRequest.class);
	}

}
