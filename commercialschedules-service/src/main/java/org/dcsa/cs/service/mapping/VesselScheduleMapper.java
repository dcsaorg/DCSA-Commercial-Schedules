package org.dcsa.cs.service.mapping;

import org.dcsa.cs.persistence.entity.Vessel;
import org.dcsa.cs.transferobjects.VesselScheduleTO;
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
