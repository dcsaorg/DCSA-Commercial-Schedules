package org.dcsa.commercialschedules.transferobjects;

import lombok.Data;

@Data
public class TimestampTO {
  private String eventTypeCode;
  private String eventClassifierCode;
  private String eventDateTime;
}
