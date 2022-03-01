package com.example.filedemo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "iduser")

public class Fournisseur  extends User{

	
//	@Id
//	@GeneratedValue (strategy = GenerationType.IDENTITY)
//		public Long   id ;
//		
		
		public String nom_societe ;
		public String nom_f ;
		public String prenom_f ;
		public String cin ;
		@Temporal(TemporalType.DATE)
		private Date date_fin_contrat ;
		public String adresse_societe;
		public String gouvernorat_societe ;
		public String  localite_societe;
		public String delegation_societe ;
	    //public int code_postal_societe ; 
		public String adresse_livraison;
	    public String gouvernorat_livraison ; 
		public String  localite_livraison;
		public String delegation_livraison ;
		//public int code_postal_livraison  ;
		public int prix_livraison ;
		public int prix_retour ;
		public String patente;
		public boolean isDeleted ;

	   // public String  password ;
		@JsonIgnore
		@OneToMany(mappedBy = "fournisseur")
		//@JsonManagedReference
		public List<Colis> colis ;
		
	
		
		
		
}
