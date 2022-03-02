package com.example.filedemo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "iduser")
public class Personnel extends User {
	private Long cin;
	private String nom;
	private String prenom;
	private PersonnelRole role_personnel;
	private String permis;
	private String matricule_veh;
	private String carte_grise;
	private boolean canConnect = true;
	@JsonIgnore
	@OneToMany(mappedBy = "livreur")
	private List<Dispatch> dispatchsToDeliver;
	@JsonIgnore
	@OneToMany(mappedBy = "dispatcher")
	private List<Dispatch> assignedDispatchs;
	@JsonIgnore
	@OneToMany(mappedBy = "livreur")
	private List<Console> consolesToDeliver;
	@JsonIgnore
	@OneToMany(mappedBy = "creator")
	private List<Console> assignedConsoles;
	@JsonIgnore
	@OneToMany(mappedBy = "validator")
	private List<Console> validatedConsoles;
	@ManyToOne
	public Hub hub;
	@JsonIgnore
	@OneToMany(mappedBy = "livreur")
	private List<Runsheet> runsheetsToDeliver;
	@JsonIgnore
	@OneToMany(mappedBy = "creator")
	private List<Runsheet> createdToDeliver;
	
	@JsonIgnore
	@OneToMany(mappedBy = "validator")
	private List<Debrief> validatedDebrief;
	
	@JsonIgnore
	@OneToMany(mappedBy = "livreur")
	private List<Debrief> relatedDebrief;

}
