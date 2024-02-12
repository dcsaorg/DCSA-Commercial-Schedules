package org.dcsa.cs.persistence.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.dcsa.cs.persistence.entity.*;
import org.dcsa.skernel.domain.persistence.entity.Location;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PortScheduleSpecification {

  public static Specification<Service> withFilters(String port, String date) {

    return (root, query, builder) -> {
      Join<Service, Vessel> serviceVesselJoin = root.join(Service_.VESSELS, JoinType.LEFT);
      Join<Vessel, TransportCall> vesselTransportCallJoin =
        serviceVesselJoin.join(Vessel_.TRANSPORT_CALLS, JoinType.LEFT);
      Join<TransportCall, Location> transportCallLocationJoin =
        vesselTransportCallJoin.join(TransportCall_.LOCATION, JoinType.LEFT);

      List<Predicate> predicates = new ArrayList<>();

      if (null != port) {
        Predicate transportCallLocationJoinPredicate =
          builder.equal(transportCallLocationJoin.get(LocationEntity_.U_NLOCATION_CODE), port);
        predicates.add(transportCallLocationJoinPredicate);
      }

      if (null != date) {

        final DateTimeFormatter DATE_FORMAT =
          new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
            .toFormatter();

        Join<TransportCall, TransportEvent> transportCallTransportEventJoin =
          vesselTransportCallJoin.join("timestamps", JoinType.LEFT);

        Predicate dateRangePredicate = null;


         if (null != date) {
          dateRangePredicate =
            builder.greaterThanOrEqualTo(
              transportCallTransportEventJoin.get(TransportEvent_.EVENT_DATE_TIME),
              LocalDateTime.parse(date, DATE_FORMAT).atOffset(ZoneOffset.UTC));
        }

        predicates.add(dateRangePredicate);
      }

      return builder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
