package org.dcsa.commercialschedules.transferobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationTO {
  private String locationName;
  private String locationType;
  @JsonProperty("UNLocationCode")
  private String UNLocationCode;
}
