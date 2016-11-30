package com.bonvojage.carpooling;

import java.util.ArrayList;
import java.util.Date;

import com.bonvojage.designs.HistorySubWindowDesign;
import com.vaadin.data.Item;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

public class HistorySubWindowView extends HistorySubWindowDesign {
	private CarpoolingUI ui;
	private GoogleMap googleMap;
    
	
	public HistorySubWindowView(CarpoolingUI UI)
		{
			this.ui=ui;
			dateLabel.setValue("Trip date:");
			fromLabel.setValue("Departure:");
			toLabel.setValue("Arrival:");
			scoreLabel.setValue("Obtained score:");
			
			GoogleMap googleMap = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
			googleMap.setSizeFull();
			ArrayList<LatLon> points = new ArrayList<LatLon>();
			points.add(new LatLon(60.448118, 22.253738));
			points.add(new LatLon(60.455144, 22.24198));
			points.add(new LatLon(60.460222, 22.211939));
			points.add(new LatLon(60.488224, 22.174602));
			points.add(new LatLon(60.486025, 22.169195));

			GoogleMapPolyline overlay = new GoogleMapPolyline(
			        points, "#d31717", 0.8, 10);
			googleMap.addPolyline(overlay);
			googleMap.setCenter(new LatLon(60.440963, 22.25122));
			googleMap.setMinZoom(4);
			googleMap.setMaxZoom(16);
			googleMap.setZoom(10);
			googleMap.setSizeFull();
			rightmostLayout.addComponent(googleMap);
		    //addUserHistoryItem("casa","lavoro",new Date(System.currentTimeMillis()),googleMap,32.5);
			
		}
	/*public void clearSubWindow()
	{
	historyTable.getContainerDataSource().removeAllItems();
	}
public void addUserHistoryItem(String departure,String destination,Date date,GoogleMap map,double score)
	{
	Object newItemId = historyTable.addItem();
	Item row1 = historyTable.getItem(newItemId);
	row1.getItemProperty("Departure").setValue(departure);
	row1.getItemProperty("Destination").setValue(destination);
	row1.getItemProperty("Path").setValue(map);
	row1.getItemProperty("Date").setValue(date);
	row1.getItemProperty("Score").setValue(date);
	
	}*/

}
