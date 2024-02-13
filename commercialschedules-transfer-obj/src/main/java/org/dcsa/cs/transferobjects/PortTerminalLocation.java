package org.dcsa.cs.transferobjects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// marker interface
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "locationType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = AddressLocationTO.class, name = "ADDR"),
  @JsonSubTypes.Type(value = FacilitySMDGLocationTO.class, name = "FACS"),
  @JsonSubTypes.Type(value = UNLocationLocationTO.class, name = "UNLO")
})
public interface PortTerminalLocation {}
