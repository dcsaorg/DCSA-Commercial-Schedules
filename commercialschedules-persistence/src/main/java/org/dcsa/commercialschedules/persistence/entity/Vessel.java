package org.dcsa.commercialschedules.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dcsa.skernel.domain.persistence.entity.Carrier;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vessel")
public class Vessel {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "vessel_imo_number", length = 7, unique = true)
  private String vesselIMONumber;

  @Column(name = "vessel_name", length = 35)
  private String vesselName;

  @Column(name = "vessel_flag", length = 2, columnDefinition = "bpchar")
  private String vesselFlag;

  @Column(name = "vessel_call_sign", length = 18)
  private String vesselCallSign;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_operator_carrier_id")
  private Carrier vesselOperatorCarrier;

  @Column(name = "is_dummy")
  private Boolean isDummyVessel;

  @Column(name = "length_overall", columnDefinition = "numeric")
  private Float vesselLength;

  @Column(name = "width", columnDefinition = "numeric")
  private Float vesselWidth;

  @Column(name = "dimension_unit", length = 3)
  private String dimensionUnit;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "vessel",cascade = CascadeType.ALL)
  private Set<TransportCall> transportCalls = new LinkedHashSet<>();
}
