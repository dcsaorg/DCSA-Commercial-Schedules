package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.LocationEntity;
import org.dcsa.commercialschedules.persistence.entity.TransportCall;
import org.dcsa.commercialschedules.transferobjects.*;
import org.dcsa.skernel.domain.persistence.entity.Address;
import org.dcsa.skernel.domain.persistence.entity.Facility;
import org.dcsa.skernel.infrastructure.services.mapping.AddressMapper;
import org.dcsa.skernel.infrastructure.transferobject.AddressTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(
    componentModel = "spring",
    uses = {TransportEventMapper.class})
public abstract class TransportCallMapper {
  @Mappings(
      value = {
        @Mapping(target = "location", source = "transportCall"),
        @Mapping(target = "carrierImportVoyageNumber", source = "importVoyage.carrierVoyageNumber"),
        @Mapping(target = "carrierExportVoyageNumber", source = "exportVoyage.carrierVoyageNumber"),
        @Mapping(
            target = "universalImportVoyageReference",
            source = "importVoyage.universalVoyageReference"),
        @Mapping(
            target = "universalExportVoyageReference",
            source = "exportVoyage.universalVoyageReference"),
        @Mapping(target = "statusCode", source = "portCallStatusCode")
      })
  public abstract TransportCallTO toTO(TransportCall transportCall);

  // decide return data based on nullable fields
  PortTerminalLocation mapPortTerminalLocation(TransportCall transportCall) {
    // default rule : if both facility and address are present on location we return
    // PortTerminalLocation: facilitySMDGLocation
    PortTerminalLocation portTerminalLocation = null;
    LocationEntity location = transportCall.getLocation();

    // since the spring context cannot be accessed here we access the mapper via code
    AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    if (null != location) {

      if (null != location.getFacility()) {

        portTerminalLocation =
            FacilitySMDGLocationTO.builder()
                .locationName(location.getLocationName())
                .UNLocationCode(location.getUNLocationCode())
                .facilitySMDGCode(
                    Objects.requireNonNullElseGet(location.getFacility(), Facility::new)
                        .getFacilitySMDGCode())
                .build();

      } else if (null != location.getAddress()) {

        Address address = location.getAddress();
        AddressTO addressTO = addressMapper.toDTO(address);

        portTerminalLocation =
            AddressLocationTO.builder()
                .locationName(location.getLocationName())
                .address(addressTO)
                .build();

      } else {

        portTerminalLocation =
            UNLocationLocationTO.builder()
                .locationName(location.getLocationName())
                .UNLocationCode(location.getUNLocationCode())
                .build();
      }
    }

    return portTerminalLocation;
  }
  }
