package org.dcsa.commercialschedules.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.dcsa.commercialschedules.service.PointToPointRoutingService;
import org.dcsa.commercialschedules.service.PortScheduleService;
import org.dcsa.commercialschedules.service.VesselScheduleService;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.dcsa.commercialschedules.transferobjects.PortScheduleTO;
import org.dcsa.commercialschedules.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.infrastructure.validation.ValidVesselIMONumber;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class CommercialSchedulesController {

  private final VesselScheduleService vesselScheduleService;
  private final PortScheduleService portScheduleService;
  private final PointToPointRoutingService pointToPointRoutingService;

  @GetMapping(path = "/vessel-schedule")
  @ResponseStatus(HttpStatus.OK)
  public List<ServiceScheduleTO> findAll(
    @Size(max = 11) @RequestParam(required = false) String carrierServiceCode,
    @Size(max = 8) @RequestParam(required = false) String universalServiceReference,
    @ValidVesselIMONumber(allowNull = true) @RequestParam(required = false) String vesselIMONumber,
    @Size(max = 35) @RequestParam(required = false) String vesselName,
    @Size(max = 50) @RequestParam(required = false) String voyageNumber,
    @RequestParam(required = false) String universalVoyageReference,
    @Size(max = 5) @RequestParam(value = "UNLocationCode", required = false)
    String unLocationCode,
    @Size(max = 5) @RequestParam(required = false) String facilitySMDGCode,
    @RequestParam(required = false) String startDate,
    @RequestParam(required = false) String endDate,
    HttpServletRequest request, HttpServletResponse response) {

    List<ServiceScheduleTO> serviceSchedules = vesselScheduleService.fetchVesselSchedules(carrierServiceCode,universalServiceReference,vesselIMONumber,
      vesselName,voyageNumber,universalVoyageReference,unLocationCode,facilitySMDGCode,startDate,endDate);
    return serviceSchedules;

  }


  @GetMapping(path = "/point-to-point-routes")
  @ResponseStatus(HttpStatus.OK)
  public List<PointToPointRoutingTO> findAll(
    @Size(min = 5) @Size(max = 5)@RequestParam(required = true) String placeOfReceipt,
    @Size(min = 5) @Size(max = 5)@RequestParam(required = true) String placeOfDelivery,
    @RequestParam(required = false) String departureDateTime,
    @RequestParam(required = false) boolean isTranshipment,
    @RequestParam(required = false) String receiptTypeAtOrigin,
    @RequestParam(required = false) String deliveryTypeAtDestination,
    @RequestParam(required = false) int limit
  ){
    List<PointToPointRoutingTO> pointToPointRoutingTOs = pointToPointRoutingService.findAllRoutes(placeOfReceipt,placeOfDelivery,departureDateTime,isTranshipment,receiptTypeAtOrigin,deliveryTypeAtDestination,limit);
    return pointToPointRoutingTOs;
  }

  @GetMapping(path = "/port-schedule")
  public List<PortScheduleTO> findAllPortSchedules(
    @Size(min = 5) @Size(max = 5)@RequestParam(required = true) String port,
    @RequestParam(required = true) String date
  ){
    List<PortScheduleTO> portScheduleTOs = portScheduleService.findAll(port,date);
    return portScheduleTOs;

  }

}
