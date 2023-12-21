package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.Vessel;
import org.dcsa.commercialschedules.transferobjects.VesselScheduleTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
  componentModel = "spring",
  uses = {TransportCallMapper.class})
public interface VesselScheduleMapper {
  @Mapping(target = "vesselOperatorSMDGLinerCode", source = "vesselOperatorCarrier.smdgCode")
  VesselScheduleTO toTo(Vessel vessel);

  @Mapping(target = "vesselOperatorCarrier.smdgCode", source = "vesselOperatorSMDGLinerCode")
  Vessel toEntity(VesselScheduleTO vesselScheduleTO);
}
