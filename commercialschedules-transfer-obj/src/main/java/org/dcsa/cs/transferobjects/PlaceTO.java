package org.dcsa.cs.transferobjects;

import lombok.Data;

@Data
public class PlaceTO {
  private String facilityTypeCode;
  private String dateTime;
  private LocationTO location;
}
