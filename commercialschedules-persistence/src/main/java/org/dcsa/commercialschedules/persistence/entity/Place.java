package org.dcsa.commercialschedules.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "place")
public class Place {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "facility_type_code")
  private String facilityTypeCode;

  @ToString.Exclude
  @OneToOne
  @Column(name = "location_id")
  private LocationEntity location;

  @Column(name = "date_time", nullable = false)
  private OffsetDateTime dateTime;

}
