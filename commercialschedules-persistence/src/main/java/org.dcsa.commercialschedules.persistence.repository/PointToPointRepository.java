package org.dcsa.commercialschedules.persistence.repository;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PointToPointRepository extends JpaRepository<PointToPointRouting, UUID> {

  List<PointToPointRouting> findAll();

}
