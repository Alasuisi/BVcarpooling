package com.bonvojage.carpooling;

import java.util.Date;

import com.bonvojage.designs.HistorySubWindowDesign;
import com.vaadin.data.Item;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;

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
			googleMap.addMarker("DRAGGABLE: Paavo Nurmi Stadion", new LatLon(
			        60.442423, 22.26044), true, "VAADIN/1377279006_stadium.png");
			googleMap.addMarker("NOT DRAGGABLE: Iso-Heikkilä", new LatLon(
			        60.450403, 22.230399), false, null);
			googleMap.setMinZoom(4);
			googleMap.setMaxZoom(16);
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
