package com.example.filedemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.ColisService;

@Repository
public interface ColisRepository extends JpaRepository < Colis , Long >{

	@Query("SELECT s FROM Colis s WHERE s.etat = :etat ")
	  public List<Colis> findColisByEtat(@Param("etat") ColisEtat etat);
	
	@Query(value="SELECT * FROM Colis s WHERE s.fournisseur_iduser  = :id ", nativeQuery = true)
	  public List<Colis> findByFournisseur_id(@Param("id") Long id);
	
	@Query("SELECT s FROM Colis s WHERE s.etat = :etat AND fournisseur_iduser = :fournisseur_id ")
	  public List<Colis> findByFournisseurAndEtat(@Param("etat") ColisEtat etat, @Param("fournisseur_id") Long fournisseur_id ); 
	
	@Query("SELECT s FROM Colis s WHERE s.service = :service AND fournisseur_iduser = :fournisseur_id ")
	  public List<Colis> findByFournisseurAndService(@Param("service") ColisService service, @Param("fournisseur_id") Long fournisseur_id ); 
	@Query("SELECT s FROM Colis s WHERE s.service = :service")
	  public List<Colis> findAllColisByService(@Param("service") com.example.filedemo.model.ColisService service); 
	
	
	@Query(value="SELECT c.etat , r.revtstmp FROM revinfo r , Colis_aud c WHERE c.reference = :reference and c.rev = r.rev", nativeQuery = true)
	 public List<HistoStateOnly> getColisAud(@Param("reference") Long reference);
	
	 public static interface HistoStateOnly {

	     String getEtat();

	     Long getRevtstmp();

	  }
	 @Query("SELECT count(*) FROM Colis s WHERE s.etat=:etat AND fournisseur_iduser = :fournisseur_id  ")

	 public int  countByEtat( @Param("fournisseur_id") Long fournisseur_id, @Param("etat") ColisEtat etat);
	 
	 @Query(value = "SELECT * FROM Colis s WHERE s.reference IN :inputList " , 
		     nativeQuery = true)
		List<Colis> findByObjectList( @Param("inputList")List<Long> inputList);
	 
	 @Query(value = "SELECT * FROM Colis c WHERE c.bar_code  LIKE :bar_code " , nativeQuery = true)
		Colis findColisByBarCode(@Param("bar_code") String bar_code);

	 @Query(value = "SELECT * FROM Colis c WHERE c.runsheet_id = :runsheet_code ", nativeQuery = true)
	 List<Colis> findColisByRunsheet_code(@Param("runsheet_code") Long runsheet_code);

	@Query(value="SELECT SUM(c.cod) FROM colis c WHERE c.runsheet_code_runsheet= :id ", nativeQuery = true)
	public Float totalCodPerRunsheet(@Param("id") Long id);
    
	@Query("SELECT c FROM Colis c WHERE c.bar_code IN ( :barCodesList) ")
	public List<Colis> findColisByBarCodesList( @Param("barCodesList") List<String> barCodesList);
}
