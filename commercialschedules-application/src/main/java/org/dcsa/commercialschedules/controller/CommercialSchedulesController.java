package org.dcsa.commercialschedules.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.dcsa.commercialschedules.persistence.entity.Service_;
import org.dcsa.commercialschedules.service.PointToPointRoutingService;
import org.dcsa.commercialschedules.service.PortScheduleService;
import org.dcsa.commercialschedules.service.VesselScheduleService;
import org.dcsa.commercialschedules.transferobjects.PointToPointRoutingTO;
import org.dcsa.commercialschedules.transferobjects.PortScheduleTO;
import org.dcsa.commercialschedules.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.CursorDefaults;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;
import org.dcsa.skernel.infrastructure.pagination.Paginator;
import org.dcsa.skernel.infrastructure.validation.ValidVesselIMONumber;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("${spring.application.context-path}")
public class CommercialSchedulesController {

  private final VesselScheduleService vesselScheduleService;
  private final PortScheduleService portScheduleService;
  private final PointToPointRoutingService pointToPointRoutingService;
  private final Paginator paginator;

  @GetMapping(path = "/vessel-schedules")
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
    @RequestParam(required = false, defaultValue = "100") Integer limit,
    @RequestParam(value = "API-Version", required = false) String apiVersion,
    HttpServletRequest request, HttpServletResponse response) {

    Cursor cursor = paginator.parseRequest(
      request,
      new CursorDefaults(limit, Sort.Direction.DESC, Service_.CARRIER_SERVICE_CODE));

    PagedResult<ServiceScheduleTO> result = vesselScheduleService.findAll(
      cursor,
      VesselScheduleService.ServiceSchedulesFilters.builder()
        .carrierServiceCode(carrierServiceCode)
        .universalServiceReference(universalServiceReference)
        .vesselIMONumber(vesselIMONumber)
        .vesselName(vesselName)
        .voyageNumber(voyageNumber)
        .universalVoyageReference(universalVoyageReference)
        .unLocationCode(unLocationCode)
        .facilitySMDGCode(facilitySMDGCode)
        .startDate(startDate)
        .endDate(endDate)
        .limit(limit)
        .build());

    paginator.setPageHeaders(request, response, cursor, result);
    return result.content();
  }


  @GetMapping(path = "/point-to-point-routes")
  @ResponseStatus(HttpStatus.OK)
  public List<PointToPointRoutingTO> findAll(
    @Size(min = 5) @Size(max = 5)@RequestParam(required = true) String placeOfReceipt,
    @Size(min = 5) @Size(max = 5)@RequestParam(required = true) String placeOfDelivery,
    @RequestParam(required = false) String departureDateTime,
    @RequestParam(required = false) String arrivalDateTime,
    @RequestParam(required = false) boolean isTranshipment,
    @RequestParam(required = false) String receiptTypeAtOrigin,
    @RequestParam(required = false) String deliveryTypeAtDestination,
    @RequestParam(required = false, defaultValue = "100") Integer limit,
    @RequestParam(value = "API-Version", required = false) String apiVersion,
    HttpServletRequest request, HttpServletResponse response
  ){
    Cursor cursor = paginator.parseRequest(
      request,
      new CursorDefaults(limit, Sort.Direction.DESC));

    PagedResult<PointToPointRoutingTO> result = pointToPointRoutingService.findAllRoutes(cursor,placeOfReceipt,placeOfDelivery,departureDateTime,arrivalDateTime,isTranshipment,receiptTypeAtOrigin,deliveryTypeAtDestination);

    paginator.setPageHeaders(request, response, cursor, result);
    return result.content();
  }

  @GetMapping(path = "/port-schedules")
  public List<PortScheduleTO> findAllPortSchedules(
      @Size(min = 5) @Size(max = 5) @RequestParam(required = true) String port,
      @RequestParam(required = true) String date,
      @RequestParam(value = "API-Version", required = false) String apiVersion,
      HttpServletRequest request, HttpServletResponse response) {

    List<PortScheduleTO> portScheduleTOs = portScheduleService.findAll(port, date);
    return portScheduleTOs;
  }
}
