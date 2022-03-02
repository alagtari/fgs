package com.example.filedemo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id_ticket ;
	private String description ;
	private String sujet ;
	
	@ManyToOne 
    public Colis colis ;
}