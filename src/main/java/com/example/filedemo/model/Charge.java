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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Charge {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id ;
	private String designation ;
	private float montant ;
	private String type ;
	private Date date_charge ;

}
