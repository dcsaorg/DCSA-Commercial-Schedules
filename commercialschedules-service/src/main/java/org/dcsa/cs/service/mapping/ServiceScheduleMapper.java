package org.dcsa.cs.service.mapping;


import org.dcsa.cs.persistence.entity.Service;
import org.dcsa.cs.transferobjects.ServiceScheduleTO;
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

