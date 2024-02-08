package org.dcsa.commercialschedules.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.dcsa.commercialschedules.persistence.repository.PointToPointRepository;
import org.dcsa.commercialschedules.persistence.repository.specification.PointToPointRoutingSpecifications;
import org.dcsa.commercialschedules.service.mapping.PointToPointRoutingMapper;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointToPointRoutingService {

  @Builder
  public static class PointToPointRoutingFilters {
    String placeOfReceipt;
    String placeOfDelivery;
    String departureDateTime;
    boolean isTranshipment;
    String receiptTypeAtOrigin;
    String deliveryTypeAtDestination;
    int limit;
  }

  private final PointToPointRepository pointToPointRepository;
  private final PointToPointRoutingMapper pointToPointRoutingMapper;

  public List<PointToPointRoutingTO> findAllRoutes(String placeOfReceipt, String placeOfDelivery, String departureDateTime,String arrivalDateTime, boolean isTranshipment,
                                                   String receiptTypeAtOrigin, String deliveryTypeAtDestination, int limit) {
    OffsetDateTime dateTimeDeparture =
      ObjectUtils.isNotEmpty(departureDateTime) ? OffsetDateTime.parse(departureDateTime) : null;
    OffsetDateTime dateTimearrival =
      ObjectUtils.isNotEmpty(arrivalDateTime) ? OffsetDateTime.parse(arrivalDateTime) : null;

    Specification<PointToPointRouting> specification = PointToPointRoutingSpecifications.withFilters(placeOfReceipt, placeOfDelivery, dateTimeDeparture,dateTimearrival);
    Pageable pageable = PageRequest.of(0,limit);
    Page<PointToPointRouting> result = pointToPointRepository.findAll(specification,pageable);
    List<PointToPointRouting> pointToPointRoutings = result.getContent();



    List<PointToPointRoutingTO> tos = pointToPointRoutings.stream()
      .map(pointToPointRoutingMapper::toTO)
      .collect(Collectors.toList());


    return tos;
  }
}
