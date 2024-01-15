package org.dcsa.commercialschedules.transferobjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationTO {
  private String locationName;
  private String locationType;
  private String UNLocationCode;
}
