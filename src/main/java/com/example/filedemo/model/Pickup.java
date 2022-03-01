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
public class Pickup {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id ;
	private Date date_creation ;
	
	@ManyToOne 
    public Personnel personnel ;
	
	@ManyToOne 
    public Colis colis ;
}
