package org.dcsa.commercialschedules.service;

import lombok.AllArgsConstructor;
import org.dcsa.commercialschedules.persistence.repository.ServiceRepository;
import org.dcsa.commercialschedules.persistence.repository.specification.PortScheduleSpecification;
import org.dcsa.commercialschedules.transferobjects.PortScheduleTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortScheduleService {
  private final ServiceRepository serviceRepository;
  private final PortScheduleMapper portScheduleMapper;

    public List<PortScheduleTO> findAll(String port, String date) {

     List<org.dcsa.commercialschedules.persistence.entity.Service> services = serviceRepository.findAll(PortScheduleSpecification.withFilters(port,date));


      List<PortScheduleTO> portScheduleTOS = portScheduleMapper.serviceToPortSchedule(services);
      return portScheduleTOS;
    }
}
