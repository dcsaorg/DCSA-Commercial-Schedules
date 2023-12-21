package org.dcsa.commercialschedules.transferobjects;

import lombok.Builder;

public record FacilitySMDGLocationTO(
  String locationName,
  String UNLocationCode,
  String locationType,
  String facilitySMDGCode
) implements PortTerminalLocation {
  @Builder // workaround for intellij issue
  public FacilitySMDGLocationTO { }
}
