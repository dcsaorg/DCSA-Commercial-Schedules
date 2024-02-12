package org.dcsa.cs.transferobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PortScheduleTO {
  private LocationTO location;
  private List<VesselTO> vesselSchedules;
}
