package com.example.filedemo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Debrief;

@Repository
public interface DebriefRepository extends CrudRepository<Debrief, Long> {
   @Query("SELECT d from Debrief d WHERE d.livreur.iduser= :idLivreur")
   public List<Debrief> getDebriefByIdLivreur(@Param("idLivreur") Long idLivreur);
   
   @Query("SELECT d from Debrief d WHERE d.validator.iduser= :idValidator")
   public List<Debrief> getDebriefByIdValidator(@Param("idValidator") Long idValidator);
   
   @Query("SELECT d from Debrief d WHERE d.livreur.iduser= :idLivreur AND d.creationDate= :creationDate")
   public Optional<Debrief> checkDebriefEligibility(@Param("idLivreur") Long idLivreur, @Param("creationDate")@Temporal(TemporalType.DATE) Date creationDate);
}
