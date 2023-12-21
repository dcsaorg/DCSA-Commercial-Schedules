package org.dcsa.commercialschedules.service;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.dcsa.commercialschedules.persistence.repository.PointToPointRepository;
import org.dcsa.commercialschedules.service.mapping.PointToPointRoutingMapper;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointToPointRoutingService {

  private PointToPointRepository pointToPointRepository;
  private PointToPointRoutingMapper pointToPointRoutingMapper;

  public List<PointToPointRoutingTO> findAllRoutes(String placeOfReceipt, String placeOfDelivery, String departureDateTime, boolean isTranshipment,
                                                   String receiptTypeAtOrigin, String deliveryTypeAtDestination, int limit) {

    List<PointToPointRouting> pointToPointRoutings = pointToPointRepository.findAll();

    List<PointToPointRoutingTO> tos = pointToPointRoutings.stream()
      .map(pointToPointRoutingMapper::toTO)
      .collect(Collectors.toList());


    return null;
  }
}
