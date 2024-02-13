package org.dcsa.cs.transferobjects;

import lombok.Data;

@Data
public class LegTO {
  private int sequenceNumber;
  private String modeOfTransport;
  private String vesselOperatorSMDGLinerCode;
  private String vesselIMONumber;
  private String vesselName;
  private String carrierServiceName;
  private String universalServiceReference;
  private String carrierServiceCode;
  private String universalImportVoyageReference;
  private String universalExportVoyageReference;
  private String carrierImportVoyageNumber;
  private String carrierExportVoyageNumber;
  private PlaceTO departure;
  private PlaceTO arrival;

}
