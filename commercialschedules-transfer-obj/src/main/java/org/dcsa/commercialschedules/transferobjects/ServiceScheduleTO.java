package org.dcsa.commercialschedules.transferobjects;

import lombok.Data;

import java.util.List;

@Data
public class ServiceScheduleTO {
  private String carrierServiceName;
  private String carrierServiceCode;
  private String universalServiceReference;
  private List<VesselScheduleTO> vesselSchedules;
}
