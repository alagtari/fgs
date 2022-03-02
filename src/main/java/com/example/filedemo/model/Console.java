package com.example.filedemo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Console {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idConsole ;
	LocalDateTime dateCreation = LocalDateTime.now();
	private Long idHubDepart;
	private Long idHubArrivee;
	private String titreHubDepart;
	private String titreHubArrivee;
	private ConsoleEtat etat ;
	@ManyToOne 
    private Personnel livreur;
	@ManyToOne 
    private Personnel creator;
	@ManyToOne 
    private Personnel validator;
	@OneToMany(mappedBy = "console")
	private List<Colis> colis;
	private String barCode;
	
	public String createBarCode() {
		return this.barCode = "03"+this.idHubDepart+this.idHubArrivee+this.idConsole;
	}
}
