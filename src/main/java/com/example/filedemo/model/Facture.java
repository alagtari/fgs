package com.example.filedemo.model;

import java.util.Date;

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
public class Facture {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id_facture ;
	private Date date_creation_facture ;
	private float total_facture ;
	private String etat_facture ;
	
	@ManyToOne 
    public Colis colis ;
}
