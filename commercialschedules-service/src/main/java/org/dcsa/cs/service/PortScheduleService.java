package org.dcsa.cs.service;

import lombok.AllArgsConstructor;
import org.dcsa.cs.persistence.repository.ServiceRepository;
import org.dcsa.cs.persistence.repository.specification.PortScheduleSpecification;
import org.dcsa.cs.transferobjects.PortScheduleTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PortScheduleService {
  private final ServiceRepository serviceRepository;
  private final PortScheduleMapper portScheduleMapper;

    public List<PortScheduleTO> findAll(String port, String date) {

     List<org.dcsa.cs.persistence.entity.Service> services = serviceRepository.findAll(PortScheduleSpecification.withFilters(port,date));


      List<PortScheduleTO> portScheduleTOS = portScheduleMapper.serviceToPortSchedule(services);
      return portScheduleTOS;
    }
}
