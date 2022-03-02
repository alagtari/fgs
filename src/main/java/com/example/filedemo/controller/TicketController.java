package com.example.filedemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.filedemo.model.Ticket;
import com.example.filedemo.service.TicketService;

@Controller
@RestController
public class TicketController {

	@Autowired 
	private  TicketService fournisseurService ;

	//@Autowired 
	//private final ColisService colisService ;

	//http://localhost:8080/retrieve-all-Tickets
    @GetMapping("/retrieve-all-Tickets")
    @ResponseBody
    public List<Ticket> getTickets() {
        List<Ticket> list = fournisseurService.retrieveAllTickets();
        return list;
    }

    // http://localhost:8080/retrieve-Ticket/{Ticket-id}
    @GetMapping("/retrieve-Ticket/{Ticket-id}")
    @ResponseBody
    public Ticket retrieveTicket(@PathVariable("Ticket-id") String id) {
        return fournisseurService.retrieveTicket(id);
    }

    // Ajouter  : http://localhost:8080/add-Ticket
    @PostMapping("/add-Ticket")
    @ResponseBody
    public Ticket addTicket(@RequestBody Ticket p) {
    	Ticket p1 = fournisseurService.addTicket(p);
        return p1;
    }



    // http://localhost:8080/remove-Ticket/{id}
    @DeleteMapping("/remove-Ticket/{id}")
    @ResponseBody
    public void removeTicket (@PathVariable("id") Long id) {
    	fournisseurService.deleteTicket(id);
    }

    // http://localhost:8080/modify-Ticket
    @PutMapping("/modify-Ticket")
    @ResponseBody
    public Ticket modifyTicket(@RequestBody Ticket p) {
        return fournisseurService.updateTicket(p);
    }



}
