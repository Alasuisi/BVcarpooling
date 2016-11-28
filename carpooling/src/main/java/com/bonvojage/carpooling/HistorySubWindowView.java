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
			
			googleMap = new GoogleMap(null, null, null);
		    googleMap.setCenter(new LatLon(60.440963, 22.25122));
		    googleMap.setZoom(10);
		    googleMap.setSizeFull();
		    //addUserHistoryItem("casa","lavoro",new Date(System.currentTimeMillis()),googleMap,32.5);
			
		}
	public void clearSubWindow()
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
	
	}

}
