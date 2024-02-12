package org.dcsa.cs.persistence.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.dcsa.cs.persistence.entity.LocationEntity;
import org.dcsa.cs.persistence.entity.Place;
import org.dcsa.cs.persistence.entity.PointToPointRouting;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class PointToPointRoutingSpecifications {

  public static Specification<PointToPointRouting> withFilters(
    String placeOfReceipt,
    String placeOfDelivery,
    OffsetDateTime departureDateTime,
    OffsetDateTime arrivalDateTime
  ) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      Join<PointToPointRouting, Place> placeOfReceiptJoin = root.join("placeOfReceipt");
      Join<Place, LocationEntity> locationOfReceiptJoin = placeOfReceiptJoin.join("location");

      Join<PointToPointRouting, Place> placeOfDeliveryJoin = root.join("placeOfDelivery");
      Join<Place, LocationEntity> locationOfDeliveryJoin = placeOfDeliveryJoin.join("location");

      if (placeOfReceipt != null) {
        predicates.add(criteriaBuilder.equal(locationOfReceiptJoin.get("UNLocationCode"), placeOfReceipt));
      }

      if (placeOfDelivery != null) {
        predicates.add(criteriaBuilder.equal(locationOfDeliveryJoin.get("UNLocationCode"), placeOfDelivery));
      }

      if (departureDateTime != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(placeOfReceiptJoin.get("dateTime"), departureDateTime));
      }
      if (arrivalDateTime != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(placeOfDeliveryJoin.get("dateTime"), arrivalDateTime));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
