package com.example.filedemo.controller;


import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.filedemo.model.Dispatch;
import com.example.filedemo.payload.DispatchPayload;
import com.example.filedemo.service.ColisService;
import com.example.filedemo.service.DispatchService;
import com.example.filedemo.service.PersonnelService;


@Controller
@RestController
@CrossOrigin("*")
@RequestMapping

public class DispatchController {
	@Autowired
    private DispatchService dispatchService;
	@Autowired
    private PersonnelService personnelService;
	
	@Autowired
    private ColisService colisService;
	
    @PostMapping("/addDispatch")
    @ResponseBody
    public ResponseEntity saveDispatch(@RequestBody DispatchPayload dispatchPayload ) {
       Dispatch dispatch= new Dispatch();
       try {
    	   dispatch.setDispatcher(personnelService.retrievePersonnel(dispatchPayload.getIdDispatcher()));
    	   dispatch.setLivreur(personnelService.retrievePersonnel(dispatchPayload.getIdLivreur()));
    	   dispatch =dispatchService.addDispatch(dispatch); 
    	   dispatch.setColis(colisService.assignColisToDispatch(dispatch, dispatchPayload.getColisReferences()));
    	   dispatch =dispatchService.synchronize(dispatch); 
       }
       catch(Exception e) {
    	   e.printStackTrace();
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(dispatch);
    }
    
    @GetMapping("/findByCreationDate/{creationDate}")
    @ResponseBody
    public ResponseEntity findByCreationDate(@PathVariable String creationDate) {
       ArrayList<Dispatch> dispatchs=null;
       try {
    	   dispatchs=(ArrayList<Dispatch>) dispatchService.findByCreationDate(creationDate);
       }
       catch(Exception e) {
			e.printStackTrace();
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.OK).body(dispatchs);
    }
    
    @GetMapping("/findAllDispatchs")
    @ResponseBody
    public ResponseEntity findAllDispatch() {
       ArrayList<Dispatch> dispatchs=null;
       try {
    	   dispatchs=(ArrayList<Dispatch>) dispatchService.findAllDispatchs();
       }
       catch(Exception e) {
			e.printStackTrace();
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.OK).body(dispatchs);
    }
    
    @GetMapping("/findDispatchsByDisptacher/{idDispatcher}")
    @ResponseBody
    public ResponseEntity findDispatchsByDisptacher(@PathVariable("idDispatcher") long idDispatcher) {
       ArrayList<Dispatch> dispatchs=null;
       try {
    	   dispatchs=(ArrayList<Dispatch>) dispatchService.findDispatchsByDisptacher(idDispatcher);
       }
       catch(Exception e) {
			e.printStackTrace();
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
       return ResponseEntity.status(HttpStatus.OK).body(dispatchs);
    }
}
