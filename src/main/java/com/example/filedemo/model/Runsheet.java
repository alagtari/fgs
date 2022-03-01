package com.example.filedemo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class Runsheet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String barCode;
	private LocalDateTime creationDate = LocalDateTime.now();;
	private float totalPrix;
	private RunsheetEtat etat;
	@ManyToOne
	private Personnel livreur;
	@ManyToOne
	private Personnel creator;
	@JsonIgnore
	@OneToMany(mappedBy = "runsheet")
	private List<Colis> colis;
	public String createRunsheetBarCode() {
		return "02"+this.getCreator().getHub().getId_hub() + this.getId();
	}
}
