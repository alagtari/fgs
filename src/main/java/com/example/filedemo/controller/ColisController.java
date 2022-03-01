package com.example.filedemo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.io.ByteArrayInputStream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.PDFGenerator;
import com.example.filedemo.model.PDFGenerator2;
import com.example.filedemo.model.Response;
import com.example.filedemo.payload.ColisHubsResponsePayload;
import com.example.filedemo.repository.ColisRepository.HistoStateOnly;
import com.example.filedemo.service.ColisService;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping

public class ColisController {

	@Autowired
	private final ColisService colisService;

	@Autowired
	public ColisController(ColisService colisService) {
		this.colisService = colisService;
	}

	@PostMapping(value = "/saveColis")
	public ResponseEntity saveColis(@RequestBody Colis colis) {
		Colis colisPostSave = null;
		try {
			colisPostSave = colisService.saveColis(colis);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(colisPostSave);
	}

	@GetMapping(value = "/findAllColisByFournisseur/{id}")
	public ResponseEntity findAllColisByFournisseur(@PathVariable(value = "id") Long id) {
		List<Colis> colis = null;
		try {
			colis = colisService.findAllColisByFournisseur(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping(value = "/findColisByFournisseurAndEtat/{id}/{etat}")
	public ResponseEntity findByFournisseurAndEtat(@PathVariable(value = "id") Long id,
			@PathVariable(value = "etat") ColisEtat etat) {
		List<Colis> colis = null;
		try {
			colis = colisService.findByFournisseurAndEtat(etat, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping(value = "/findColisByFournisseurAndService/{id}/{service}")
	public ResponseEntity findByFournisseurAndService(@PathVariable(value = "id") Long id,
			@PathVariable(value = "service") com.example.filedemo.model.ColisService service) {
		List<Colis> colis = null;
		try {
			colis = colisService.findByFournisseurAndService(service, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping(value = "/findColisByService/{service}")
	public ResponseEntity findByService(@PathVariable(value = "service") com.example.filedemo.model.ColisService service) {
		List<Colis> colis = null;
		try {
			colis = colisService.findByService(service);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@PutMapping("updateColis")
	public ResponseEntity updateColis(@RequestBody Colis colis) {
		Colis colisPostUpdate = null;
		try {
			colisPostUpdate = colisService.updateColis(colis);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colisPostUpdate);
	}

	@DeleteMapping("/deleteColisByReference/{reference}")
	public ResponseEntity deleteColis(@PathVariable(value = "reference") Long reference) {
		try {
			colisService.deleteColis(reference);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Colis deleted");
	}

	@GetMapping("/createBarCode/{reference}")
	public ResponseEntity generateColisBarCode(@PathVariable(value = "reference") Long reference) {
		String barCode = null;
		try {
			barCode = colisService.generateColisBarCode(reference);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(barCode);
	}

	@GetMapping("/findColisByEtat/{etat}")
	@ResponseBody
	public ResponseEntity findColisByEtat(@PathVariable(value = "etat") ColisEtat etat) {
		List<Colis> colis = null;
		try {
			colis = colisService.findColisByEtat(etat);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping("/findColisAuditByReference/{reference}")
	@ResponseBody
	public ResponseEntity getColisAud(@PathVariable(value = "reference") Long reference) {
		List<HistoStateOnly> colis = null;
		try {
			colis = colisService.getColisAud(reference);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping("/findColisByReferenceList")
	// @ResponseBody
	public ResponseEntity findByObjectList(@RequestBody List<Long> inputList) {
		List<Colis> colis = null;
		try {
			colis = colisService.findByObjectList(inputList);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping("/totalCodPerRunsheet/{id}")
	// @ResponseBody
	public ResponseEntity totalCodPerRunsheet(@PathVariable(value = "id") Long id) {
		Float totalCod = new Float(0);
		try {
			totalCod = colisService.totalCodPerRunsheet(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(totalCod==null?0:totalCod);
	}

	@GetMapping("/findColisByBarCode/{barCode}")
	@ResponseBody
	public ResponseEntity findColisByBarCode(@PathVariable(value = "barCode") String bar_code) {
		Colis colis = null;
		try {
			colis = colisService.findColisByBarCode(bar_code);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	// count by etat
	@GetMapping("countColisByFournisseurAndEtat/{id}/{etat}")
	@ResponseBody
	public ResponseEntity countByEtat(@PathVariable(value = "id") Long id, @PathVariable(value = "etat") ColisEtat etat) {
		int result = 0;
		try {
			result = colisService.countByEtat(id, etat);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/findColisByReference/{reference}")
	@ResponseBody
	public ResponseEntity findById(@PathVariable(value = "reference") Long reference) {
		Colis colis = null;
		try {
			colis = colisService.findById(reference).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@GetMapping("/findColisByRunsheetCode/{runsheet_code}")
	@ResponseBody
	public ResponseEntity findColisByRunsheet_code(@PathVariable(value = "runsheet_code") Long runsheet_code) {
		List<Colis> colis = null;
		try {
			colis = colisService.findColisByRunsheet_code(runsheet_code);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}

	@PutMapping(value = "/pdfDecharge", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity colisDechargeReport(@RequestBody List<Long> inputList) throws IOException {
		ByteArrayInputStream bis = null;
		try {
			List<Colis> coliss = colisService.findByObjectList(inputList);
			bis = PDFGenerator2.colisDechargeReport(coliss, colisService.getBarCodeColisDirectory());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=customers.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@PutMapping(value = "/pdfFactureDordereau", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity colissReport(@RequestBody List<Long> inputList1) throws IOException {
		ByteArrayInputStream bis = null;
		try {
			List<Colis> coliss = colisService.findByObjectList(inputList1);
			bis = PDFGenerator.colisPDFReport(coliss, colisService.getBarCodeColisDirectory());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=customers.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@DeleteMapping("/removeColisFromRunsheet/{reference}")
	@ResponseBody
	public ResponseEntity RemoveColisFromRunsheet(@PathVariable(value = "reference") Long reference) {
		try {
			colisService.RemoveColisFromRunsheet(reference);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Colis removed from Runsheet");
	}
	@PutMapping("/updateHubColis/{referenceColis}/{idHub}")
	public ResponseEntity updateHubColis(@PathVariable(value = "referenceColis") Long referenceColis,
			@PathVariable(value = "idHub") Long idHub) {
		Colis colis=null;
		try {
			colis = colisService.assignColisToHub(referenceColis,idHub);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}
	
	@PostMapping(value = "/massiveSaveColis")
	public ResponseEntity saveColis(@RequestBody List<Colis> colis) {
		try {
			colis.stream().forEach(c->{
				colisService.saveColis(c);
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("successful massive save");
	}
	
	@GetMapping(value = "/findAllColis")
	public ResponseEntity findAllColis() {
		List<Colis> colis = null;
		try {
			colis = colisService.findAllColis();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(colis);
	}
	
	@GetMapping(value = "/findColisHubDepartHubArrivee/{barcode}")
	public ResponseEntity findColisHubDepartHubArrivee(@PathVariable("barcode") String barcode) {
		ColisHubsResponsePayload colisHubsResponsePayload=null;
		try {
			colisHubsResponsePayload = colisService.findColisHubDepartHubArrivee(barcode);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		HttpStatus status = colisHubsResponsePayload==null?HttpStatus.NOT_FOUND:HttpStatus.OK;
		return ResponseEntity.status(status).body(colisHubsResponsePayload==null?
				"colis not found":colisHubsResponsePayload);
	}

}
