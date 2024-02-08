package org.dcsa.commercialschedules.persistence.repository;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PointToPointRepository extends JpaRepository<PointToPointRouting, UUID>, JpaSpecificationExecutor<PointToPointRouting> {

    Page<PointToPointRouting> findAll(Specification<PointToPointRouting> specification, Pageable pageable);


  }


