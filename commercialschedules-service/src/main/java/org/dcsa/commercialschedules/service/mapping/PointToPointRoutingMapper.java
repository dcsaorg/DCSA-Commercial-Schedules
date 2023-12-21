package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.dcsa.commercialschedules.persistence.repository.PointToPointRepository;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
  componentModel = "spring",
  uses = {LegsMapper.class})
public interface  PointToPointRoutingMapper {

  @Mapping(source = "legs", target ="legs")
  PointToPointRoutingTO toTO(PointToPointRouting pointToPointRouting);


}
