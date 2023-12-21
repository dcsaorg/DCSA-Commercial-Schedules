package org.dcsa.commercialschedules.persistence.repository;

import lombok.NonNull;
import org.dcsa.commercialschedules.persistence.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID>, JpaSpecificationExecutor<Service> {
    @Override
    @EntityGraph(value = "graph.vessels")
    Page<Service> findAll(Specification<Service> spec, @NonNull Pageable pageable);

  @Query("SELECT cs FROM Service cs " +
    "JOIN cs.vessels vs " +
    "JOIN vs.transportCalls tc " +
    "JOIN tc.location loc " +
    "JOIN tc.timestamps ts " +  // Corrected alias to match the field in TransportEvent
    "WHERE (:carrierServiceCode IS NULL OR cs.carrierServiceCode = :carrierServiceCode) " +
    "AND (:universalServiceReference IS NULL OR cs.universalServiceReference = :universalServiceReference) " +
    "AND (:vesselIMONumber IS NULL OR vs.vesselIMONumber = :vesselIMONumber) " +
    "AND (:vesselName IS NULL OR vs.vesselName = :vesselName) " +
    "AND (:voyageNumber IS NULL OR tc.importVoyage.carrierVoyageNumber = :voyageNumber OR tc.exportVoyage.carrierVoyageNumber = :voyageNumber) " +
    "AND (:universalVoyageReference IS NULL OR tc.importVoyage.universalVoyageReference = :universalVoyageReference OR tc.exportVoyage.universalVoyageReference = :universalVoyageReference) " +
    "AND (:unLocationCode IS NULL OR loc.UNLocationCode = :unLocationCode) " +
    "AND (:startDate IS NULL OR ts.eventDateTime >= :startDate) " +
    "AND (:endDate IS NULL OR ts.eventDateTime <= :endDate) " +
    "ORDER BY cs.carrierServiceCode, vs.vesselIMONumber, tc.portVisitReference")
  List<Service> findDataByFilters(@Param("carrierServiceCode") String carrierServiceCode,
                                  @Param("universalServiceReference") String universalServiceReference,
                                  @Param("vesselIMONumber") String vesselIMONumber,
                                  @Param("vesselName") String vesselName,
                                  @Param("voyageNumber") String voyageNumber,
                                  @Param("universalVoyageReference") String universalVoyageReference,
                                  @Param("unLocationCode") String unLocationCode,
                                  @Param("startDate") String startDate,
                                  @Param("endDate") String endDate);

  List<Service> findAll(String port, String date);
}
