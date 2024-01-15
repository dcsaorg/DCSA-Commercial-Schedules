package org.dcsa.commercialschedules.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "leg")
public class Leg {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name= "mode_of_transport")
  private String modeOfTransport;
  @Column(name= "vessel_operator_smdg_liner_code")
  private String vesselOperatorSMDGLinerCode;
  @Column(name= "vessel_imo_number")
  private String vesselIMONumber;
  @Column(name= "vessel_name")
  private String vesselName;
  @Column(name= "carrier_service_name")
  private String carrierServiceName;
  @Column(name= "universal_service_reference")
  private String universalServiceReference;
  @Column(name= "carrier_service_code")
  private String carrierServiceCode;
  @Column(name= "universal_import_voyage_reference")
  private String universalImportVoyageReference;
  @Column(name= "universal_export_voyage_reference")
  private String universalExportVoyageReference;
  @Column(name= "carrier_import_voyage_number")
  private String carrierImportVoyageNumber;
  @Column(name= "carrier_export_voyage_number")
  private String carrierExportVoyageNumber;

  @ToString.Exclude
  @OneToOne
  @JoinColumn(name = "departure_id")
  private Place departure;

  @ToString.Exclude
  @OneToOne
  @JoinColumn(name = "arrival_id")
  private Place arrival;

  @ManyToOne
  @JoinColumn(name = "point_to_point_routing_id")
  private PointToPointRouting pointToPointRouting;

}
