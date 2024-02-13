package org.dcsa.cs.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "voyage")
public class Voyage {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "carrier_voyage_number", length = 50, nullable = false)
  private String carrierVoyageNumber;

  @Column(name = "universal_voyage_reference", length = 5)
  private String universalVoyageReference;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_id")
  private Service service;
}
