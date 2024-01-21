package lascaux.cinemille.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class DirectorRequest {
	
	private String name;
	private String surname;
	private String birthday;
	
	public DirectorRequest(@JsonProperty("name") String name,
						   @JsonProperty("surname") String surname,
						   @JsonProperty("birthday") String birthday) {
		this.name 	  = name;
		this.surname  = surname;
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
	    return gson.toJson(this, DirectorRequest.class);
	}

}
