package org.dcsa.commercialschedules.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dcsa.skernel.domain.persistence.entity.Address;
import org.dcsa.skernel.domain.persistence.entity.Facility;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "location")
public class LocationEntity {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "location_name", length = 100)
  private String locationName;

  @Column(name = "latitude", length = 10)
  private String latitude;

  @Column(name = "longitude", length = 11)
  private String longitude;

  @Column(name = "un_location_code", length = 5, columnDefinition = "bpchar")
  private String UNLocationCode;

  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "address_id")
  private Address address;

  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "facility_id")
  private Facility facility;
}
