package org.dcsa.commercialschedules.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dcsa.skernel.domain.persistence.entity.Facility;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transport_call")
public class TransportCall {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transport_call_reference", length = 100, nullable = false)
  private String transportCallReference;

  @Column(name = "transport_call_sequence_number")
  private Integer transportCallSequenceNumber;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id")
  private Facility facility;

  @Column(name = "facility_type_code", length = 4, columnDefinition = "bpchar")
  private String facilityTypeCode;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "location_id")
  private LocationEntity location;

  @Column(name = "mode_of_transport_code", length = 3)
  private String modeOfTransportCode;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "vessel_id")
  private Vessel vessel;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "import_voyage_id")
  private Voyage importVoyage;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "export_voyage_id")
  private Voyage exportVoyage;


  @Column(name = "port_call_status_type_code", length = 4, columnDefinition = "bpchar")
  private String portCallStatusCode;

  @Column(name="port_visit_reference", length=50)
  private String portVisitReference;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name="transport_call_id")
  private Set<TransportEvent> timestamps = new LinkedHashSet<>();
}
