package com.example.filedemo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Ticket;
import com.example.filedemo.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private  TicketRepository fournisseurRepository ;
	
	public List<Ticket> retrieveAllTickets() {
        // TODO Auto-generated method stub
        return (List<Ticket>) fournisseurRepository.findAll();

    }



    public Ticket addTicket(Ticket f) {
        // TODO Auto-generated method stub
        return (fournisseurRepository.save(f));
    }


    public void deleteTicket(Long id) {
        // TODO Auto-generated method stub
    	fournisseurRepository.deleteById(id);
    }


    public Ticket updateTicket(Ticket f) {
        // TODO Auto-generated method stub
        return (fournisseurRepository.save(f));
    }


    public Ticket retrieveTicket(String id) {
        // TODO Auto-generated method stub
        return (fournisseurRepository.findById(Long.parseLong(id)).orElse(null));
    }
	
	
	
	
}
