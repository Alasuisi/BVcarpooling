package com.bonvojage.offerwizard;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import utils.BvStringUtils;

public class FourthStepView extends VerticalLayout{
	//private VerticalLayout mainLayout;
	private HorizontalLayout midLayout = new HorizontalLayout();
	private VerticalLayout midLeftLayout = new VerticalLayout();
	private VerticalLayout midRightLayout = new VerticalLayout();
	private Label titleLabel= new Label();
	private TextField addressField = new TextField();
	private TextField destinationField = new TextField();
	private Button searchButton = new Button();
	private GoogleMap map;
	
	public FourthStepView()
		{
			this.setSizeFull();
			titleLabel.setContentMode(ContentMode.HTML);
			titleLabel.setValue(BvStringUtils.bvColorizeString("Inser your trip infos here.."));
			titleLabel.setStyleName("huge");
			addressField.setCaption("Departure");
			addressField.setDescription("Please enter your departure address here");
			addressField.setWidth("100%");
			destinationField.setCaption("Destination");
			destinationField.setDescription("Please enter your destination address here");
			destinationField.setWidth("100%");
			midLeftLayout.addComponents(addressField,destinationField);
			midLeftLayout.setWidth("100%");
			searchButton.setCaption("Search");
			midRightLayout.addComponent(searchButton);
			midRightLayout.setWidth("-1");
			midRightLayout.setHeight("100%");
			midRightLayout.setComponentAlignment(searchButton, Alignment.BOTTOM_RIGHT);
			midLayout.addComponents(midLeftLayout,midRightLayout);
			midLayout.setExpandRatio(midLeftLayout, 1);
			midLayout.setWidth("100%");
			midLayout.setSpacing(true);
			setMapCoordinates();
			this.addComponents(titleLabel,midLayout,map);
			this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
			this.setExpandRatio(map, 1);
			
			searchButton.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					testAPI();
					
				}
			});
		}
	private void setMapCoordinates()
		{
		map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
		map.setSizeFull();
		ArrayList<LatLon> points = new ArrayList<LatLon>();
		points.add(new LatLon(60.448118, 22.253738));
		points.add(new LatLon(60.455144, 22.24198));
		points.add(new LatLon(60.460222, 22.211939));
		points.add(new LatLon(60.488224, 22.174602));
		points.add(new LatLon(60.486025, 22.169195));

		GoogleMapPolyline overlay = new GoogleMapPolyline(
		        points, "#d31717", 0.8, 10);
		map.addPolyline(overlay);
		map.setCenter(new LatLon(60.440963, 22.25122));
		map.setMinZoom(4);
		map.setMaxZoom(16);
		map.setZoom(10);
		map.setSizeFull();
		}
	
	private void testAPI()
		{
		
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4");
		DirectionsResult results=null;
		
		try {
			results = DirectionsApi.getDirections(context, "ferentino", "roma").await();
			DirectionsRoute[] routes = results.routes;
			for(int i =0;i<routes.length;i++)
				{
				EncodedPolyline lines=routes[0].overviewPolyline;
				List<LatLng> list = lines.decodePath();
				ArrayList<LatLon> points = new ArrayList<LatLon>();
				Iterator<LatLng> iter = list.iterator();
				while(iter.hasNext())
					{
					 LatLng current = iter.next();
					 System.out.println(current.toString());
					 points.add(new LatLon(current.lat,current.lng));
					}
				GoogleMapPolyline overlay = new GoogleMapPolyline(points, "#d31717", 0.8, 10);
				map.addPolyline(overlay);
				/*DirectionsLeg[] legs=routes[i].legs;
				for(int j=0;j<legs.length;j++)
					{
					System.out.println("inner portanna");
					 System.out.println(legs[i].startAddress);
					 System.out.println(legs[i].endAddress);
					}*/
				}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		}

}
