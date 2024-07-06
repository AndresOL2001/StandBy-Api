package com.standby.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.Log;

@Repository
public interface LogRepository extends JpaRepository<Log,UUID>{


    @Query(value = "select * from standby.logs l JOIN standby.acceso a ON l.id_acceso = a.id_acceso where a.id_residencial = ?1",nativeQuery = true)
    List<Log> obtenerLogsPorResidencial(UUID idResidencial);
    
    @Query("select l from Log l WHERE l.entro = false ORDER BY l.fechaCreacion DESC")
    List<Log> findLatestLogs(Pageable pageable);
    
}
