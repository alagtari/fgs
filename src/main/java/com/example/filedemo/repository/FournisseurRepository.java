package com.example.filedemo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.User;

import javax.transaction.Transactional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

	public List<Fournisseur> findByIsDeletedFalse();

	@Query(value = "SELECT * FROM fournisseur f WHERE f.is_deleted=0", nativeQuery = true)
	public List<Fournisseur> getFournisseurIsDeletedFalse();

	@Modifying
	@Transactional
	@Query("UPDATE Fournisseur e SET e.nom_societe=:nom_societe ,e.nom_f=:nom_f ,e.prenom_f=:prenom_f ,e.cin=:cin, e.date_fin_contrat=:date_fin_contrat , e.adresse_societe=:adresse_societe , e.gouvernorat_societe=:gouvernera_societe , e.localite_societe=:localite_societe, e.delegation_societe=:delegation_societe, e.adresse_livraison=:adresse_livraison, e.gouvernorat_livraison=:gouvernera_livraison , e.localite_livraison=:localite_livraison , e.delegation_livraison=:delegation_livraison, e.prix_livraison=:prix_livraison, e.prix_retour=:prix_retour ,e.password=:password where e.iduser=:id")
	public Object updateFournisseur(@Param("nom_societe") String nom_societe, @Param("nom_f") String nom_f,
			@Param("prenom_f") String prenom_f, @Param("cin") CharSequence charSequence,
			@Param("date_fin_contrat") Date date_fin_contrat,
			@Param("adresse_societe") String adresse_societe, @Param("gouvernera_societe") String gouvernera_societe,
			@Param("localite_societe") String localite_societe, @Param("delegation_societe") String delegation_societe,
			@Param("adresse_livraison") String adresse_livraison,
			@Param("gouvernera_livraison") String gouvernera_livraison,
			@Param("localite_livraison") String localite_livraison,
			@Param("delegation_livraison") String delegation_livraison, @Param("prix_livraison") int prix_livraison,
			@Param("prix_retour") int prix_retour, @Param("password") String password,
			@Param("id") long id);
}