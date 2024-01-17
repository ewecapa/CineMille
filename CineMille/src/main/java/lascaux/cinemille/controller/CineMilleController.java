package lascaux.cinemille.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lascaux.cinemille.model.dto.Logging;
import lascaux.cinemille.repository.CineMilleRepository;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping("lascaux/cinemille")
public class CineMilleController {
	
	@Autowired
	private Logging log;
	
	@Autowired
	private CineMilleRepository cmRepo;
	
	@GetMapping("ciao")
	public JSONObject ciao() {
		this.log.forward("INFO", "Test", "Provona");
		return this.cmRepo.getFilmById();
	}

}
