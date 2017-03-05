package com.bonvoyage.utils;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.bonvoyage.domain.TimedPoint2D;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

public class GeocodingUtils {
	private static String apiKey="AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4";
	
	private  GeocodingUtils(){};
	
	public static List<LatLng> toCoordinates(String from,String to) throws Exception
		{
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		DirectionsResult results=null;
		try {
			results = DirectionsApi.getDirections(context, from, to).await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DirectionsRoute[] routes = results.routes;
		EncodedPolyline lines=routes[0].overviewPolyline;
		List<LatLng> path = lines.decodePath();
		DistanceMatrixApiRequest test = new DistanceMatrixApiRequest(context);
		return path;
		}
	
	/*public static JsonObject toJson(List<LatLng> list)
	{
		Gson gson = new GsonBuilder().create();
		JsonArray myCustomArray = gson.toJsonTree(list).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("path", myCustomArray);
        return jsonObject;
	}*/
	
	public static LinkedList<TimedPoint2D> toTimedPath(List<LatLng> list)
		{
		LinkedList<TimedPoint2D> timedPath = new LinkedList<TimedPoint2D>();
		Iterator<LatLng> iterlatlng = list.iterator();
		while(iterlatlng.hasNext())
			{
			 LatLng temp =iterlatlng.next();
			 TimedPoint2D toAdd = new TimedPoint2D(temp.lat,temp.lng,System.currentTimeMillis());
			 //toAdd.setLocation(temp.lat, temp.lng);
			 timedPath.add(toAdd);
			 
			}
		return timedPath;
		}
	
	public static void getDistanceAndDurationForDirection(LatLng origin,LatLng destination,GeoApiContext geoApiContext){
		 // LatLng origin=new LatLng(startLocation.getLat(),startLocation.getLng());
		 // LatLng destination=new LatLng(destinationLocation.getLat(),destinationLocation.getLng());
		  try {
		    DistanceMatrix distanceMatrix=DistanceMatrixApi.newRequest(geoApiContext).origins(origin).destinations(destination).await();
		    if (distanceMatrix.rows.length == 0 || distanceMatrix.rows[0].elements.length == 0)     throw new RuntimeException("No distance and duration found.");
		    //return new RouteDistanceDuration(distanceMatrix.rows[0].elements[0].distance.inMeters,distanceMatrix.rows[0].elements[0].duration.inSeconds);
		    System.out.println("Portanna 1 "+distanceMatrix.rows[0].elements[0].distance.inMeters);
		    System.out.println("Portanna 2 "+distanceMatrix.rows[0].elements[0].duration.inSeconds);
		  }
		 catch (  Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		  }
		}
	
	/*public RouteDistanceDurvoidation getDistanceAndDurationForDirection(RouteLocation startLocation,RouteLocation destinationLocation){
		  LatLng origin=new LatLng(startLocation.getLat(),startLocation.getLng());
		  LatLng destination=new LatLng(destinationLocation.getLat(),destinationLocation.getLng());
		  try {
		    DistanceMatrix distanceMatrix=DistanceMatrixApi.newRequest(geoApiContext).origins(origin).destinations(destination).await();
		    if (distanceMatrix.rows.length == 0 || distanceMatrix.rows[0].elements.length == 0)     throw new RuntimeException("No distance and duration found.");
		    return new RouteDistanceDuration(distanceMatrix.rows[0].elements[0].distance.inMeters,distanceMatrix.rows[0].elements[0].duration.inSeconds);
		  }
		 catch (  Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		  }
		}*/
}
