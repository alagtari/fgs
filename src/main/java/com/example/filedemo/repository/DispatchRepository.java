package com.example.filedemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.filedemo.model.Dispatch;
@Repository
public interface DispatchRepository extends CrudRepository<Dispatch, Long>{
   @Query(value = "select * from Dispatch d where d.date_creation= :date", nativeQuery = true)
   public List<Dispatch> findByDateCreation(@Param("date") String date);
   
   @Query("select d from Dispatch d where d.dispatcher.iduser= :idDispatcher")
   List<Dispatch> findByDispatcher(@Param("idDispatcher")long idDispatcher);
}
