package com.example.filedemo.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colis {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
		public Long   reference ; 
		public String bar_code ; 
		public String nom_c ;
		public String prenom_c ;
		public int tel_c_1;
		public int tel_c_2;
		LocalDateTime date_creation = LocalDateTime.now();
		public String adresse ; 
		public String gouvernorat ; 
		public String delegation ;
		public String localite;
		public float  cod ;
		public float latitude ;
		public float longitude ;
		public ColisModePaiement  mode_paiement;
		public ColisService  service  ;
		public String designation  ;
		public String remarque ;
		private int nbrt;
		@Audited
		public ColisEtat  etat ;
		@ManyToOne
		public Anomalie anomalie ;
		public Integer  nb_p=0 ;
		public Integer longeur=0 ;
		public Integer  largeur =0;
		public Integer  hauteur=0 ;
		public Integer  poids =0;
		private String barCodeAncienColis;
		@Transient
        private Long ancienColisId;
	    @Temporal(TemporalType.DATE)
		private Date dateLivraison;
		@ManyToOne
	    public Fournisseur fournisseur ;
		
		@ManyToOne
	    public Hub hub ;
		
		@ManyToOne
	    public Runsheet runsheet ;
		@JsonIgnore
		@ManyToOne
		public Console console;
		@JsonIgnore
		@ManyToOne
		public Debrief debrief;
		@JsonIgnore
		@ManyToOne
		private Dispatch dispatch;
		private ColisTaille taille;
		private boolean isFragile;
		public String toColisBarCode ()
		{
			int serviceCode =0 ; 
			serviceCode = this.service.equals(ColisService.livraison)? 1 : 2;
			return "010"+ serviceCode + (this.hub!=null? this.hub.getId_hub().toString():"") + 
					(this.fournisseur!=null? this.fournisseur.getIduser()+"":"") +(this.ancienColisId!=null?this.ancienColisId.toString():"")+this.reference ;
			
		}
}
