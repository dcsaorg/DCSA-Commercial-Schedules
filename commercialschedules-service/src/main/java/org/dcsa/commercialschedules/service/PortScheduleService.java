package org.dcsa.commercialschedules.service;

import org.dcsa.commercialschedules.persistence.repository.ServiceRepository;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.dcsa.commercialschedules.transferobjects.PortScheduleTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortScheduleService {
  private ServiceRepository serviceRepository;
  private PortScheduleMapper portScheduleMapper;

    public List<PortScheduleTO> findAll(String port, String date) {

      List<org.dcsa.commercialschedules.persistence.entity.Service> services = serviceRepository.findAll(port,date);

      List<PortScheduleTO> portScheduleTOS = new ArrayList<>();

      List<PortScheduleTO> tos = services.stream()
        .map(portScheduleMapper::toTO)
        .collect(Collectors.toList());

      return tos;
    }
}
