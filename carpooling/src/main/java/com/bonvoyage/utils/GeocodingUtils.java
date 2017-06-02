package com.bonvoyage.utils;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

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
	public static double evaluateLenght(LinkedList<TimedPoint2D> path)
		{
		 TimedPoint2D previous=null;
		 double lenght=0;
		 Iterator<TimedPoint2D> iter = path.iterator();
		 while(iter.hasNext())
		 	{
			 TimedPoint2D thisPoint = iter.next();
			 if(previous==null)
			 	{
				 previous=thisPoint;
			 	}else
			 		{
			 		 lenght=lenght+evaluateDistance(previous,thisPoint);
			 		 previous=thisPoint;
			 		}
		 	}
		 return lenght;
		}
	public static double evaluateDistance(TimedPoint2D pPoint,TimedPoint2D dPoint)
		{
		Point2D.Double secondPoint = new Point2D.Double(dPoint.getLatitude(), dPoint.getLongitude());
		return evaluateDistance(pPoint,secondPoint);
		}
	
	public static double evaluateDistance(TimedPoint2D pPoint,Point2D.Double dPoint)
		{
		/*double dlon = pPoint.getLongitude()-dPoint.getLongitude();
		double dlat = pPoint.getLatitude()-dPoint.getLatitude();
		double a = Math.pow((Math.sin(dlat/2)),2) + Math.cos(dPoint.getLatitude());
		
				dlon = lon2 - lon1 
				dlat = lat2 - lat1 
				a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2 
				c = 2 * atan2( sqrt(a), sqrt(1-a) ) 
				d = R * c (where R is the radius of the Earth)*/
		GeodeticCalculator geoCalc = new GeodeticCalculator();
	
		Ellipsoid reference = Ellipsoid.WGS84;  
	
		GlobalPosition pointA = new GlobalPosition(dPoint.getX(), dPoint.getY(), 0.0); // Point A
	
		GlobalPosition userPos = new GlobalPosition(pPoint.getLatitude(), pPoint.getLongitude(), 0.0); // Point B
	
		double distance = geoCalc.calculateGeodeticCurve(reference, userPos, pointA).getEllipsoidalDistance(); // Distance between Point A and Point B
		return distance;
		}
}
