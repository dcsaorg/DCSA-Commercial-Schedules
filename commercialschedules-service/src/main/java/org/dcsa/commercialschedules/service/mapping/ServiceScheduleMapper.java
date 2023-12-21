package org.dcsa.commercialschedules.service.mapping;


import org.dcsa.commercialschedules.persistence.entity.Service;
import org.dcsa.commercialschedules.transferobjects.ServiceScheduleTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
  componentModel = "spring",
  uses = {VesselScheduleMapper.class})
public interface ServiceScheduleMapper {
  @Mapping(target = "vesselSchedules", source = "vessels")
  ServiceScheduleTO toTO(Service service);

  @Mapping(target = "vessels", source = "vesselSchedules")
  Service toEntity(ServiceScheduleTO serviceScheduleTO);
}

