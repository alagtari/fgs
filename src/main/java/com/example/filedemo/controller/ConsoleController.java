package com.example.filedemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.filedemo.model.Console;
import com.example.filedemo.model.Hub;
import com.example.filedemo.payload.ConsolePayload;
import com.example.filedemo.service.ConsoleService;

@RestController
public class ConsoleController {

	@Autowired
	ConsoleService consoleService;

	@GetMapping("/retrieve-all-consoles")
	@ResponseBody
	public ResponseEntity getConsole() {
		List<Console> consoles = new ArrayList<>();
		try {
			consoles = consoleService.retrieveAllConsoles();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consoles);
	}

	@GetMapping("/retrieve-console/{Console-id}")
	@ResponseBody
	public ResponseEntity retrieveConsole(@PathVariable("Console-id") String id) {
		Console console = null;
		try {
			console = consoleService.retrieveConsole(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(console);
	}

	@PostMapping("/addConsole")
	@ResponseBody
	public ResponseEntity addConsole(@RequestBody ConsolePayload consolePayload) {
		Console console =null;
		try {
			console = consoleService.addConsole(consolePayload);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(console);
	}

	@DeleteMapping("/remove-console/{id}")
	@ResponseBody
	public ResponseEntity removeConsole(@PathVariable("id") Long id) {
		try {
			consoleService.deleteConsole(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("console deleted");
	}

	/*@PutMapping("/modify-console")
	@ResponseBody
	public ResponseEntity modifyConsole(@RequestBody Console console) {
		Console consolePostUpdate = null;
		try {
			consolePostUpdate = consoleService.updateConsole(console);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consolePostUpdate);
	}*/
	@GetMapping("/getArriveeConsole/{barCode}")
	public ResponseEntity getArriveeonsole(@PathVariable("barCode") String barCode) {		
		Hub hub = null;
		try {
			hub = consoleService.getArriveeByBarCode(barCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(hub);
	}
	
	@DeleteMapping("/removeColisFromConsole/{barCode}/{idConsole}")
	public ResponseEntity removeColisFromConsole(@PathVariable("barCode") String barCode,
			@PathVariable("idConsole") Long idConsole) {		
		Console console = null;
		try {
			console = consoleService.removeColisFromConsole(barCode, idConsole);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(console);
	}
	@GetMapping("/findConsoleByBarCode/{barCode}")
	@ResponseBody
	public ResponseEntity findConsoleByBarCode(@PathVariable String barCode) {
		Console console = null;
		try {
			console = consoleService.findConsoleByBarCode(barCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(console);
	}
	
	@PutMapping("/approveConsole/{barCode}/{idValidator}")
	@ResponseBody
	public ResponseEntity approveConsole(@PathVariable("barCode") String barCode, 
			@PathVariable("idValidator") long idValidator) {
		Console console = null;
		try {
			console = consoleService.approveConsole(barCode, idValidator);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(console);
	}
	
	@GetMapping("/findConsolesByValidator/{idValidator}")
	@ResponseBody
	public ResponseEntity findConsoleByBarCode(@PathVariable long idValidator) {
		List<Console> consoles=new ArrayList<>();
		try {
			consoles = consoleService.findConsoleByIdValidor(idValidator);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consoles);
	}
	
	@GetMapping("/findConsolesByCreator/{idCreator}")
	@ResponseBody
	public ResponseEntity findConsolesByIdCreator(@PathVariable long idCreator) {
		List<Console> consoles=new ArrayList<>();
		try {
			consoles = consoleService.findConsoleByIdCreator(idCreator);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consoles);
	}
	
	@GetMapping("/findConsolesEntrant/{idPersonnel}")
	@ResponseBody
	public ResponseEntity findConsolesEntrant(@PathVariable long idPersonnel) {
		List<Console> consoles=new ArrayList<>();
		try {
			consoles = consoleService.findConsolesEntrant(idPersonnel);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consoles);
	}
	
	@GetMapping("/findConsolesSortant/{idPersonnel}")
	@ResponseBody
	public ResponseEntity findConsolesSortant(@PathVariable long idPersonnel) {
		List<Console> consoles=new ArrayList<>();
		try {
			consoles = consoleService.findConsolesSortant(idPersonnel);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(consoles);
	}
}
