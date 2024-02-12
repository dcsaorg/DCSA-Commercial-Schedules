package org.dcsa.cs.transferobjects;

import lombok.Data;

import java.util.List;

@Data
public class PointToPointRoutingTO {
  private PlaceTO placeOfReceipt;
  private PlaceTO placeOfDelivery;
  private int transitTime;
  private List<LegTO> legs;


}
