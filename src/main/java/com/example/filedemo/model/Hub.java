package com.example.filedemo.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hub {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id_hub ;
	private String gouvernorat ;
	private String titre ;
	private String adresse ;
	@ElementCollection(targetClass=	String.class)
	public List<String> gouvernorat_lie = new ArrayList<String>();

	
	@JsonIgnore
	@OneToMany(mappedBy = "hub")
	public List<Colis> colis ;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hub")
	public List<Personnel> personnel ;
	
}
