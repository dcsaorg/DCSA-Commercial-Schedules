package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.PointToPointRouting;
import org.dcsa.commercialschedules.persistence.repository.PointToPointRepository;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
  componentModel = "spring",
  uses = {LegsMapper.class})
public interface  PointToPointRoutingMapper {

  @Mapping(source = "legs", target ="legs")
  PointToPointRoutingTO toTO(PointToPointRouting pointToPointRouting);

  default String map(OffsetDateTime value){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    String formattedString = value.format(formatter);
    return formattedString;

  }


}
