package org.dcsa.cs.transferobjects;

import lombok.Data;

import java.util.List;

@Data
public class VesselScheduleTO {
  private String vesselOperatorSMDGLinerCode;
  private String vesselIMONumber;
  private String vesselName;
  private List<TransportCallTO> transportCalls;
}
