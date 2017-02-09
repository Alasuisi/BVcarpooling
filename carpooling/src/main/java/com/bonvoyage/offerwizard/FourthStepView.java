package com.bonvoyage.offerwizard;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.bonvoyage.domain.Transfer;
import com.bonvoyage.utils.BvStringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.tapio.googlemaps.client.rpcs.MarkerDraggedRpc;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FourthStepView extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8437169755108605020L;
	//private VerticalLayout mainLayout;
	private HorizontalLayout midLayout = new HorizontalLayout();
	private VerticalLayout midLeftLayout = new VerticalLayout();
	private VerticalLayout midRightLayout = new VerticalLayout();
	private Label titleLabel= new Label();
	private TextField addressField = new TextField();
	private TextField destinationField = new TextField();
	private Button searchButton = new Button();
	private GoogleMap map;
	private GoogleMapPolyline overlay=null;
	private int lastPoint=-1;
	MarkerDragListener dragListener=null;
	private Transfer tran = UI.getCurrent().getSession().getAttribute(Transfer.class);
	private boolean transferComplete = false;
	
	public FourthStepView()
		{
			this.setSizeFull();
			titleLabel.setContentMode(ContentMode.HTML);
			titleLabel.setValue(BvStringUtils.bvColorizeString("Inser your trip infos here.."));
			titleLabel.setStyleName("huge");
			addressField.setCaption("Departure");
			addressField.setDescription("Please enter your departure address here");
			addressField.setWidth("100%");
			addressField.setImmediate(true);
			addressField.setRequired(true);
			addressField.setValidationVisible(false);
			destinationField.setCaption("Destination");
			destinationField.setDescription("Please enter your destination address here");
			destinationField.setWidth("100%");
			destinationField.setImmediate(true);
			destinationField.setRequired(true);
			destinationField.setValidationVisible(false);
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
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -1730053778767639074L;

				@Override
				public void buttonClick(ClickEvent event) {
					if(addressField.isEmpty()|| destinationField.isEmpty())
						{
						 if(addressField.isEmpty()) addressField.setValidationVisible(true);
						 if(destinationField.isEmpty()) destinationField.setValidationVisible(true);
						 Notification error = new Notification("Incomplete Form","Please fill up all the required informations",Type.ERROR_MESSAGE);
						 error.setDelayMsec(2000);
						 error.setPosition(Position.TOP_RIGHT);
						 error.show(Page.getCurrent());
						}
					else{
						addressField.setValidationVisible(false);
						destinationField.setValidationVisible(false);
						String from=addressField.getValue();
						String to = destinationField.getValue();
						System.out.println("from: "+from+" to: "+to);
						Collection<GoogleMapMarker> markerColl =map.getMarkers();
						/*Iterator<GoogleMapMarker> markerIter = markerColl.iterator();
						while(markerIter.hasNext())
							{
							map.removeMarker(markerIter.next());
							}*/
						tran.setDep_addr(from);
						tran.setArr_addr(to);
						testAPI(from,to);
					}
					
				}
			});
		}
	private void setMapCoordinates()
		{
		map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
		map.setSizeFull();
		map.setImmediate(true);
		}
	
	private void testAPI(String from, String to)
		{
		transferComplete=false;
		lastPoint=-1;
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4");
		DirectionsResult results=null;
		
		try {
			if(overlay!=null)
				{
				map.removePolyline(overlay);
				overlay=null;
				}
			results = DirectionsApi.getDirections(context, from, to).await();
			DirectionsRoute[] routes = results.routes;
			
			for(int i =0;i<routes.length;i++)
				{
				EncodedPolyline lines=routes[0].overviewPolyline;
				List<LatLng> list = lines.decodePath();
				/////////////////TEST GEOJSON/////////////////
				
				
				System.out.println(testJson(list));
				tran.setPath(testJson(list));
				LatLng source =list.get(0);
				LatLng destination = list.get(list.size()-1);
				tran.setDep_gps(new Point2D.Double(source.lat,source.lng));
				tran.setArr_gps(new Point2D.Double(destination.lat, destination.lng));
				tran.setDep_time(System.currentTimeMillis()+3600000);
				UI.getCurrent().getSession().setAttribute(Transfer.class, tran);
				transferComplete=true;
				/////////////////////////////////////////////
				ArrayList<LatLon> points = new ArrayList<LatLon>();
				
				Iterator<LatLng> iter = list.iterator();
				//boundaries inverted
				double latMax=-100;
				double latMin=100;
				double lngMax=-200;
				double lngMin=200;
				while(iter.hasNext())
					{
					 LatLng current = iter.next();
					 //System.out.println(current.toString());
					 points.add(new LatLon(current.lat,current.lng));
					 if(current.lat>latMax)latMax=current.lat;
					 if(current.lat<latMin)latMin=current.lat;
					 if(current.lng>lngMax)lngMax=current.lng;
					 if(current.lng<lngMin)lngMin=current.lng;
					 lastPoint++;
					}
				overlay = new GoogleMapPolyline(points, "#d31717", 0.8, 10);
				map.addPolyline(overlay);
				double latCenter = (latMax+latMin)/2;
				double lngCenter = (lngMax+lngMin)/2;
				LatLon center = new LatLon(latCenter,lngCenter);
				//LatLon center = new LatLon(latMax,lngMax);
				System.out.println("latMax: "+latMax+" latMin: "+latMin+" lngMax: "+lngMax+" lngMin: "+lngMin+" latCenter: "+latCenter+" lngCenter: "+lngCenter );
				map.setCenter(center);
				//map.setVisibleAreaBoundLimits(new LatLon(latMax,lngMax), new LatLon(latMin,lngMin));
				//map.setVisibleAreaBoundLimitsEnabled(true);
				map.fitToBounds(new LatLon(latMax,lngMax), new LatLon(latMin,lngMin));
				Collection<GoogleMapMarker> markerColl=map.getMarkers();
				if(markerColl.isEmpty())
				{
					GoogleMapMarker start = new GoogleMapMarker();
					start.setPosition(points.get(0));
					start.setDraggable(true);
					start.setAnimationEnabled(true);
					start.setCaption("Departure here");
					map.addMarker(start);
					GoogleMapMarker end = new GoogleMapMarker();
					end.setPosition(points.get(lastPoint));
					end.setDraggable(true);
					end.setAnimationEnabled(true);
					end.setCaption("Destination here");
					map.addMarker(end);
				}else
					{
					Iterator<GoogleMapMarker> markerIter = markerColl.iterator();
					while(markerIter.hasNext())
						{
						 GoogleMapMarker thisMarker = markerIter.next();
						 if(thisMarker.getCaption().equals("Destination here")) thisMarker.setPosition(points.get(lastPoint));
						 else thisMarker.setPosition(points.get(0));
						}
					}
				dragListener = new MarkerDragListener(){

					/**
					 * 
					 */
					private static final long serialVersionUID = -5310998097794555252L;

					@Override
					public void markerDragged(GoogleMapMarker draggedMarker, LatLon oldPosition) {
						LatLon oldDeparture = points.get(0);
						//LatLon oldArrival = points.get(lastPoint);
						boolean isDeparture=false;
						if(oldPosition.equals(oldDeparture)) isDeparture=true;
						LatLng newPosition = new LatLng(draggedMarker.getPosition().getLat(),draggedMarker.getPosition().getLon());
						try {
							GeocodingResult[] results = GeocodingApi.newRequest(context).latlng(newPosition).await();
							if(isDeparture)
								{
								System.out.println("new departure is: "+results[0].formattedAddress);
								/*map.removeMarker(start);
								map.removeMarker(end);*/
								map.removeMarkerDragListener(dragListener);
								//map.removeMarker(start);
								testAPI(results[0].formattedAddress,to);
								
								}else
									{
									System.out.println("new arrival is: "+results[0].formattedAddress);
									map.removeMarkerDragListener(dragListener);
									//map.removeMarker(end);
									testAPI(from,results[0].formattedAddress);
									}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						/*DirectionsApi.
						map.removePolyline(overlay);*/
						
						
					}};
				map.addMarkerDragListener(dragListener);
				}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		}
	
	public JsonObject testJson(List<LatLng> list)
		{
			Gson gson = new GsonBuilder().create();
			JsonArray myCustomArray = gson.toJsonTree(list).getAsJsonArray();
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.add("path", myCustomArray);
	        return jsonObject;
		}
	public boolean isFormCompleted()
		{
		 return transferComplete;
		}
	
	public String toJson(List<LatLng> list){
		  JsonObject featureCollection = new JsonObject();
		  featureCollection.addProperty("type", "FeatureCollection");
		  JsonObject properties = new JsonObject();
		  properties.addProperty("name", "ESPG:4326");
		  JsonObject crs = new JsonObject();
		  crs.addProperty("type", "name");
		  crs.add("properties", properties);
		  featureCollection.add("crs", crs);

		  JsonArray features = new JsonArray();
		  JsonObject feature = new JsonObject();
		  feature.addProperty("type", "Feature");
		  //JSONObject geometry = new JSONObject();

		  //JSONAray JSONArrayCoord = new JSONArray();
		  Iterator<LatLng> iter = list.iterator();
		  while(iter.hasNext())
		  	{
			  LatLng eachElement = iter.next();
			    //if(eachElement.getLongtitude()!=null){
			      JsonObject geometry = new JsonObject();
			      JsonArray JSONArrayCoord = new JsonArray();
			      JsonObject newFeature = new JsonObject();
			     // JsonObject jsonCoord = new JsonObject();
			     // jsonCoord.addProperty("0", eachElement.lng);
			     // jsonCoord.addProperty("1", eachElement.lat);
			      String lng =new Double(eachElement.lng).toString();
			      String lat = new Double(eachElement.lat).toString();
			      JsonPrimitive lngPrim = new JsonPrimitive("portanna");

			      JSONArrayCoord.add(new JsonPrimitive(lng));
			      JSONArrayCoord.add(new JsonPrimitive(lat));
			      
			      geometry.addProperty("type", "Point");
			      geometry.add("coordinates", JSONArrayCoord);
			      feature.add("geometry", geometry);
			      features.add(newFeature);
			      featureCollection.add("features", features);
			    //}
		  	}

		  return featureCollection.toString();
		}

}
