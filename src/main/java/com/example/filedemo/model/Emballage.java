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
public class Emballage {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id ;
	private int taille ;
	private int stock ;
	
	@ManyToOne
    public Fournisseur fournisseur ;
}
