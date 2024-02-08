package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.TransportEvent;
import org.dcsa.commercialschedules.transferobjects.TimestampTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TransportEventMapper {
  @Mappings(
    value = {
      @Mapping(target = "eventTypeCode", source = "transportEventTypeCode"),
      @Mapping(target = "eventClassifierCode", source = "eventClassifierCode"),
    })
  TimestampTO toTO(TransportEvent transportEvent);

  default String map(OffsetDateTime value){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
    return value.format(formatter);
  }
}
