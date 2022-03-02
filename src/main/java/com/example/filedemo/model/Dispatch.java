package com.example.filedemo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dispatch {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idDispatch ;
    @Temporal(TemporalType.DATE)
	private Date dateCreation;
	@OneToMany(mappedBy = "dispatch")
	private List<Colis> colis;
	@ManyToOne
	private Personnel livreur;
	@ManyToOne
	private Personnel dispatcher;
	
	
}
