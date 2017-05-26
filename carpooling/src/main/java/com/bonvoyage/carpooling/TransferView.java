package com.bonvoyage.carpooling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.bonvoyage.domain.TimedPoint2D;
import com.bonvoyage.domain.Transfer;
import com.google.maps.model.LatLng;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TransferView extends VerticalLayout{
	private Label mapTitleLabel = new Label("Your path");
	private Label depLabel = new Label();
	private Label arrLabel = new Label();
	private Label timeLabel = new Label();
	private GoogleMap map = null;
	
	private HorizontalSplitPanel contentPanel = new HorizontalSplitPanel();
	private VerticalLayout leftSide= new VerticalLayout();
	private VerticalLayout rightSide = new VerticalLayout();
	
	public TransferView(Transfer tran)
		{
		 this.setWidth("100%");
		 this.setHeight("500px");
		 this.setImmediate(true);
		 this.setResponsive(true);
		 contentPanel.setSplitPosition(65, Unit.PERCENTAGE);
		 
		 map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
		 map.setSizeFull();
		 map.setImmediate(true);
		 Iterator<TimedPoint2D> pathIter = tran.getPath().iterator();
		 ArrayList<LatLon> points = new ArrayList<LatLon>();
		 
		 double latMax=-100;
			double latMin=100;
			double lngMax=-200;
			double lngMin=200;
			while(pathIter.hasNext())
				{
				 TimedPoint2D current = pathIter.next();
				 System.out.println("current point "+current);
				 double lat = current.getLatitude();
				 double lng= current.getLongitude();
				 points.add(new LatLon(lat,lng));
				 if(lat>latMax)latMax=lat;
				 if(lat<latMin)latMin=lat;
				 if(lng>lngMax)lngMax=lng;
				 if(lng<lngMin)lngMin=lng;
				 //lastPoint++;
				}
			GoogleMapPolyline overlay = new GoogleMapPolyline(points, "#d31717", 0.8, 10);
			 map.addPolyline(overlay);
		 
		double latCenter = (latMax+latMin)/2;
		double lngCenter = (lngMax+lngMin)/2;
		LatLon center = new LatLon(latCenter,lngCenter);
		 map.setCenter(center);
		 map.fitToBounds(new LatLon(latMax,lngMax), new LatLon(latMin,lngMin));
		
		GoogleMapMarker start = new GoogleMapMarker();
		start.setPosition(points.get(0));
		start.setDraggable(true);
		start.setAnimationEnabled(true);
		start.setCaption("Departure here");
		map.addMarker(start);
		GoogleMapMarker end = new GoogleMapMarker();
		end.setPosition(points.get(points.size()-1));
		end.setDraggable(true);
		end.setAnimationEnabled(true);
		end.setCaption("Destination here");
		map.addMarker(end);
			
		 
		
		 
		 contentPanel.setLocked(true);
		 contentPanel.setResponsive(true);
		 contentPanel.setSizeFull();
		 
		 leftSide.setSizeFull();
		 leftSide.setImmediate(true);;
		 leftSide.setResponsive(true);
		 
		 rightSide.setSizeFull();
		 rightSide.setResponsive(true);
		 rightSide.setImmediate(true);
		 leftSide.addComponents(mapTitleLabel,map);
		 mapTitleLabel.setHeightUndefined();
		 leftSide.setComponentAlignment(mapTitleLabel, Alignment.TOP_LEFT);
		 leftSide.setComponentAlignment(map, Alignment.TOP_LEFT);
		 leftSide.setExpandRatio(map, 1);
		 rightSide.addComponents(depLabel,arrLabel,timeLabel);
		 depLabel.setValue(tran.getDep_addr());
		 arrLabel.setValue(tran.getArr_addr());
		 Date departure = new Date(tran.getDep_time());
		 SimpleDateFormat sdf = new SimpleDateFormat();
		 sdf.applyPattern("dd-MM-yy HH.mm");
		 String depString = sdf.format(departure);
		 timeLabel.setValue(depString);
		 contentPanel.setFirstComponent(leftSide);
		 contentPanel.setSecondComponent(rightSide);
		 this.addComponent(contentPanel);
		 
		 
		}

}
