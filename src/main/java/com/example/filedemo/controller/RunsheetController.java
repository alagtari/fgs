package com.example.filedemo.controller;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.filedemo.model.PDFGenerator3;
import com.example.filedemo.model.Runsheet;
import com.example.filedemo.payload.RunsheetPayload;
import com.example.filedemo.service.RunsheetService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping

public class RunsheetController {

	@Autowired
	RunsheetService runsheetService;

	@GetMapping("/retrieveAllRunsheets")
	@ResponseBody
	public ResponseEntity getRunsheet() {
		List<Runsheet> runsheets = new ArrayList<>();
		try {
			runsheets = runsheetService.retrieveAllRunsheets();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheets);
	}

	@GetMapping("/findRunsheetById/{RunsheetId}")
	@ResponseBody
	public ResponseEntity findRunsheetById(@PathVariable("RunsheetId") Long id) {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.findById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheet);
	}

	@GetMapping("/findRunsheetByBarCode/{barCode}")
	@ResponseBody
	public ResponseEntity findRunsheetByBarCode(@PathVariable("barCode") String barCode) {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.findByBarCode(barCode);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheet);
	}

	@PostMapping("/addRunsheet")
	@ResponseBody
	public ResponseEntity addRunsheet(@RequestBody RunsheetPayload runsheetPayload) {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.addRunsheet(runsheetPayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(runsheet);
	}

	@DeleteMapping("/removeRunsheet/{id}")
	@ResponseBody
	public ResponseEntity removeRunsheet(@PathVariable("id") Long id) {
		try {
			runsheetService.deleteRunsheet(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("runsheet deleted");
	}

	@PutMapping("/modifyRunsheet")
	@ResponseBody
	public ResponseEntity modifyRunsheet(@RequestBody RunsheetPayload runsheetPayload) {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.updateRunsheet(runsheetPayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheet);
	}

	@GetMapping(value = "/runsheet/{code_runsheet}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity colissReport(@PathVariable(value = "code_runsheet") Long code_runsheet) throws IOException {
		Runsheet r = runsheetService.findById(code_runsheet);
		ByteArrayInputStream bis = PDFGenerator3.runsheetPDFReport(r);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=colis.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping("/createBarCodeRunsheet/{id}")
	public ResponseEntity createBarCodeRunsheet(@PathVariable(value = "id") Long id) {
		String barCode = null;
		try {
			barCode = runsheetService.generateRunsheetBarCode(runsheetService.findById(id));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(barCode);
	}

	@PutMapping(value = "/addColisToRunsheet/{id}")
	public ResponseEntity addColisToRunsheet(@RequestBody List<String> barCodesList,
			@PathVariable(value = "id") Long id) throws IOException {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.addColisToRunsheet(barCodesList, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheet);
	}
	
	@PutMapping(value = "/encloseRunsheet/{id}")
	public ResponseEntity encloseRunsheet(@PathVariable(value = "id") Long id) throws IOException {
		Runsheet runsheet = null;
		try {
			runsheet = runsheetService.encloseRunsheet(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(runsheet);
	}

}
