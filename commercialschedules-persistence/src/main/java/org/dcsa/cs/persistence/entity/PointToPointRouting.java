package org.dcsa.cs.persistence.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "point_to_point_routing")
public class PointToPointRouting {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transit_time", nullable = false)
  private int transitTime;

  @ToString.Exclude
  @OneToOne
  @JoinColumn(name = "place_of_receipt_id", nullable = false)
  private Place placeOfReceipt;

  @ToString.Exclude
  @OneToOne
  @JoinColumn(name = "place_of_delivery_id", nullable = false)
  private Place placeOfDelivery;

  @OneToMany(mappedBy = "pointToPointRouting", cascade = CascadeType.ALL)
  private List<Leg> legs;


}
