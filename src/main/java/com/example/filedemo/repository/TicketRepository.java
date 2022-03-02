package com.example.filedemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Ticket;
@Repository
public interface TicketRepository extends CrudRepository<Ticket,Long> {

	

}
