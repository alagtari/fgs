package com.example.filedemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.ColisEtat;
import com.example.filedemo.model.ColisService;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Long> {

	@Query("SELECT s FROM Colis s WHERE s.etat = :etat ")
	public List<Colis> findColisByEtat(@Param("etat") ColisEtat etat);

	@Query(value = "SELECT * FROM Colis s WHERE s.fournisseur_iduser  = :id ", nativeQuery = true)
	public List<Colis> findByFournisseur_id(@Param("id") Long id);

	@Query("SELECT s FROM Colis s WHERE s.etat = :etat AND fournisseur_iduser = :fournisseur_id ")
	public List<Colis> findByFournisseurAndEtat(@Param("etat") ColisEtat etat,
			@Param("fournisseur_id") Long fournisseur_id);

	@Query("SELECT s FROM Colis s WHERE s.service = :service AND fournisseur_iduser = :fournisseur_id ")
	public List<Colis> findByFournisseurAndService(@Param("service") ColisService service,
			@Param("fournisseur_id") Long fournisseur_id);

	@Query("SELECT s FROM Colis s WHERE s.service = :service")
	public List<Colis> findAllColisByService(@Param("service") com.example.filedemo.model.ColisService service);

	@Query(value = "SELECT c.etat , r.revtstmp FROM revinfo r , Colis_aud c WHERE c.reference = :reference and c.rev = r.rev", nativeQuery = true)
	public List<HistoStateOnly> getColisAud(@Param("reference") Long reference);

	public static interface HistoStateOnly {

		String getEtat();

		Long getRevtstmp();

	}

	@Query("SELECT count(*) FROM Colis s WHERE s.etat=:etat AND fournisseur_iduser = :fournisseur_id  ")

	public int countByEtat(@Param("fournisseur_id") Long fournisseur_id, @Param("etat") ColisEtat etat);

	@Query(value = "SELECT * FROM Colis s WHERE s.reference IN :inputList ", nativeQuery = true)
	List<Colis> findByObjectList(@Param("inputList") List<Long> inputList);

	@Query(value = "SELECT * FROM Colis c WHERE c.bar_code  LIKE :bar_code ", nativeQuery = true)
	Colis findColisByBarCode(@Param("bar_code") String bar_code);

	@Query(value = "SELECT * FROM Colis c WHERE c.runsheet_id = :runsheet_code ", nativeQuery = true)
	List<Colis> findColisByRunsheet_code(@Param("runsheet_code") Long runsheet_code);

	@Query(value = "SELECT SUM(c.cod) FROM colis c WHERE c.runsheet_code_runsheet= :id ", nativeQuery = true)
	public Float totalCodPerRunsheet(@Param("id") Long id);

	@Query("SELECT c FROM Colis c WHERE c.bar_code IN ( :barCodesList) ")
	public List<Colis> findColisByBarCodesList(@Param("barCodesList") List<String> barCodesList);

	@Query(value = "SELECT * FROM colis c LEFT JOIN dispatch d on c.dispatch_id_dispatch"
			+ "= d.id_dispatch WHERE "
			//+ "c.etat= 1 AND"
			+ " d.livreur_iduser= :idLivreur And d.id_dispatch = ("
			+ "	SELECT id_dispatch FROM dispatch d where d.livreur_iduser= :idLivreur ORDER BY date_creation DESC,id_dispatch DESC LIMIT 1)", nativeQuery = true)
	public List<Colis> findColisDispatchByIdLivreur(@Param("idLivreur") Long idLivreur);

	@Query(value = "SELECT * FROM colis c LEFT JOIN runsheet r on c.runsheet_id=r.id"
			+ " WHERE "
			//+ "c.etat= 4 AND "
			+ "r.livreur_iduser= :idLivreur And r.etat= 0", nativeQuery = true)
	public List<Colis> findColisRunsheetByIdLivreur(@Param("idLivreur") Long idLivreur);

	@Query(value = "SELECT * from colis c where c.bar_code = :barCode"
			+ " and (select count(*) from colis c1 where c1.bar_code_ancien_colis= :barCode AND c1.service= 1)>0", nativeQuery = true)
	public Optional<Colis> findColisEchange(@Param("barCode") String barCode);
	
	@Query("SELECT c FROM Colis c WHERE c.barCodeAncienColis= :barCode AND c.service= 1")
	public Optional<Colis> findNewColisEchange(@Param("barCode") String barCode);
}
