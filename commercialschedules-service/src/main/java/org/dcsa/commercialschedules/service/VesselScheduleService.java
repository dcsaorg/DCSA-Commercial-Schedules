package org.dcsa.commercialschedules.service;

import lombok.RequiredArgsConstructor;
import org.dcsa.commercialschedules.persistence.repository.ServiceRepository;
import org.dcsa.commercialschedules.transferobjects.ServiceScheduleTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private ServiceRepository serviceRepository;

  public List<ServiceScheduleTO> fetchVesselSchedules(String carrierServiceCode, String universalServiceReference, String vesselIMONumber,
                                                      String vesselName, String voyageNumber, String universalVoyageReference,
                                                      String unLocationCode, String facilitySMDGCode, String startDate, String endDate) {

    List<org.dcsa.commercialschedules.persistence.entity.Service> services = serviceRepository.findDataByFilters(carrierServiceCode,universalServiceReference,vesselIMONumber,vesselName,voyageNumber,
      universalVoyageReference, unLocationCode,startDate,endDate);
    return null;
  }
}
