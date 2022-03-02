package com.example.filedemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.filedemo.model.Anomalie;
import com.example.filedemo.payload.AnomaliePayload;
import com.example.filedemo.service.AnomalieService;

@Controller
public class AnomalieController {
	@Autowired
	AnomalieService anomalieService;

	@PostMapping(value = "/createAnomalie")
	public ResponseEntity createAnomalie(@RequestBody AnomaliePayload anomaliePayload) {
		Anomalie anomaliePostSave = null;
		try {
			anomaliePostSave = anomalieService.createAnomalie(anomaliePayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(anomaliePostSave);
	}

	@PutMapping(value = "/updateAnomalie")
	public ResponseEntity updateAnomalie(@RequestBody AnomaliePayload anomaliePayload) {
		Anomalie anomaliePostUpdated = null;
		try {
			anomaliePostUpdated = anomalieService.updateAnomalie(anomaliePayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(anomaliePostUpdated);
	}

	@GetMapping(value = "/findAnomalie/{idAnomalie}")
	public ResponseEntity updateAnomalie(@PathVariable Long idAnomalie) {
		Anomalie anomalie = null;
		try {
			anomalie = anomalieService.findAnomalie(idAnomalie);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(anomalie);
	}

	@DeleteMapping(value = "/deleteAnomalie/{idAnomalie}")
	public ResponseEntity deleteAnomalie(@PathVariable Long idAnomalie) {
		try {
			anomalieService.deleteAnomalie(idAnomalie);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Anomalie deleted");
	}

	@GetMapping("/findAllAnomalie")
	@ResponseBody
	public ResponseEntity findAllAnomalie() {
		List<Anomalie> anomalies = new ArrayList<>();
		try {
			anomalies = anomalieService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(anomalies);
	}

}
