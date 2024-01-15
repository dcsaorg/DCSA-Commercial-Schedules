package org.dcsa.commercialschedules.transferobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class VesselTO {
  private String carrierServiceName;
  private String carrierServiceCode;
  private String universalServiceReference;
  private String vesselIMONumber;
  private String vesselName;
  private boolean isDummyVessel;
  private String carrierImportVoyageNumber;
  private String carrierExportVoyageNumber;
  private String universalImportVoyageReference;
  private String universalExportVoyageReference;
  private List<TimestampTO> timestamps;
}
