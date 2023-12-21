package org.dcsa.commercialschedules.service;

import org.dcsa.commercialschedules.persistence.entity.Service;
import org.dcsa.commercialschedules.transferobjects.PortScheduleTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
  componentModel = "spring"
)
public interface PortScheduleMapper {
  @Mapping(source = "", target ="")
  PortScheduleTO toTO(Service entity);
}
