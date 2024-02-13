package org.dcsa.cs.transferobjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimestampTO {
  private String eventTypeCode;
  private String eventClassifierCode;
  private String eventDateTime;
}
