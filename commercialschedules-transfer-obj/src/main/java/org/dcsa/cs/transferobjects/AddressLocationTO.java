package org.dcsa.cs.transferobjects;

import lombok.Builder;
import org.dcsa.skernel.infrastructure.transferobject.AddressTO;

public record AddressLocationTO(
  String locationName,
  String locationType,
  AddressTO address
) implements PortTerminalLocation {
  @Builder // workaround for intellij issue
  public AddressLocationTO { }
}
