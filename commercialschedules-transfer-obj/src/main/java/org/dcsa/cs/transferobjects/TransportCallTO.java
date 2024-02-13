package org.dcsa.cs.transferobjects;

import lombok.Data;

@Data
public class TransportCallTO {
  private String portVisitReference;
  private String transportCallReference;
  private String carrierImportVoyageNumber;
  private String carrierExportVoyageNumber;
  private String universalImportVoyageReference;
  private String universalExportVoyageReference;
  private LocationTO location;
  private TimestampTO timestamp;
}
