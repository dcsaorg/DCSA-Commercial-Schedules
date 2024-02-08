package org.dcsa.commercialschedules.service.mapping;

import org.dcsa.commercialschedules.persistence.entity.TransportEvent;
import org.dcsa.commercialschedules.transferobjects.TimestampTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransportEventMapper {
  @Mappings(
    value = {
      @Mapping(target = "eventTypeCode", source = "transportEventTypeCode"),
      @Mapping(target = "eventClassifierCode", source = "eventClassifierCode"),
    })
  TimestampTO toTO(TransportEvent transportEvent);

  @Mappings(
    value = {
      @Mapping(target = "transportEventTypeCode", source = "eventTypeCode"),
      @Mapping(target = "eventClassifierCode", source = "eventClassifierCode"),
      @Mapping(target = "eventCreatedDateTime", source = "eventCreatedDateTime"),
    })
  TransportEvent toEntity(TimestampTO timestampTO);
}
