package org.dcsa.cs.service;

import org.dcsa.cs.persistence.entity.*;
import org.dcsa.cs.transferobjects.LocationTO;
import org.dcsa.cs.transferobjects.PortScheduleTO;
import org.dcsa.cs.transferobjects.TimestampTO;
import org.dcsa.cs.transferobjects.VesselTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PortScheduleMapper {

  VesselTO vesselToVesselTO(Vessel vessel, Service service){
    String carrierExportVoyageNumber = null;
    String carrierImportVoyageNumber = null;
    String universalImportVoyageReference = null;
    String universalExportVoyageReference = null;
    Optional<TransportCall> transportCall = vessel.getTransportCalls().stream().findFirst();
    if(transportCall.isPresent()){
      carrierExportVoyageNumber = transportCall.get().getExportVoyage().getCarrierVoyageNumber();
      carrierImportVoyageNumber = transportCall.get().getImportVoyage().getCarrierVoyageNumber();
      universalImportVoyageReference = transportCall.get().getImportVoyage().getUniversalVoyageReference();
      universalExportVoyageReference = transportCall.get().getExportVoyage().getUniversalVoyageReference();
    }

    Set<TransportEvent> timestamps = transportCall.get().getTimestamps();
    List<TimestampTO> timestampTOS = new ArrayList<>();
    for(TransportEvent timetstamp: timestamps){
      timestampTOS.add(TransportEventToTO(timetstamp));

    }
   return VesselTO.builder().carrierServiceCode(service.getCarrierServiceCode()).carrierServiceName(service.getCarrierServiceName()).universalServiceReference(service.getUniversalServiceReference()).
      carrierExportVoyageNumber(carrierExportVoyageNumber).carrierImportVoyageNumber(carrierImportVoyageNumber).universalImportVoyageReference(universalImportVoyageReference).universalExportVoyageReference(universalExportVoyageReference)
      .vesselIMONumber(vessel.getVesselIMONumber()).vesselName(vessel.getVesselName()).isDummyVessel(vessel.getIsDummyVessel()).timestamps(timestampTOS).build();

  }

  private TimestampTO TransportEventToTO(TransportEvent timetstamp) {
    return TimestampTO.builder().eventClassifierCode(timetstamp.getEventClassifierCode()).eventTypeCode(timetstamp.getTransportEventTypeCode()).eventDateTime(String.valueOf(timetstamp.getEventDateTime())).build();
  }


  List<PortScheduleTO> serviceToPortSchedule(List<Service> services){
  List<VesselTO> vesselTOS = new ArrayList<>();
    List<PortScheduleTO> PortScheduleTOs = new ArrayList<>();
    for(Service service : services){
      PortScheduleTO to = new PortScheduleTO();
      Set<Vessel> vessels = service.getVessels();
      for(Vessel vessel : vessels){
        VesselTO vesselTO =  vesselToVesselTO(vessel,service);
        vesselTOS.add(vesselTO);
      }
      to.setVesselSchedules(vesselTOS);
      LocationTO location = buildLocationTO(vessels.stream().findFirst());
      to.setLocation(location);
      PortScheduleTOs.add(to);
    }

    return PortScheduleTOs;
    }

    LocationTO buildLocationTO(Optional<Vessel> vessel){

    if (vessel.isPresent()) {
      LocationEntity location =
          vessel.get().getTransportCalls().stream().findFirst().get().getLocation();
      return LocationTO.builder()
          .locationName(location.getLocationName())
          .UNLocationCode(location.getUNLocationCode())
          .build();
    }
    return null;
  }
}
