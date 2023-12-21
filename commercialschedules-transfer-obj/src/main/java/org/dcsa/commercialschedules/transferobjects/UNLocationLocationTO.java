package org.dcsa.commercialschedules.transferobjects;

import lombok.Builder;

public record UNLocationLocationTO(
  String locationName,
  String locationType,
  String UNLocationCode
) implements PortTerminalLocation {
  @Builder // workaround for intellij issue
  public UNLocationLocationTO { }
}
