package org.dcsa.commercialschedules.persistence.repository;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface PointToPointRepository extends JpaRepository<PointToPointRouting, UUID>, JpaSpecificationExecutor<PointToPointRouting> {

    List<PointToPointRouting> findAll(Specification<PointToPointRouting> specification);

    List<PointToPointRouting> findAll();
  }


