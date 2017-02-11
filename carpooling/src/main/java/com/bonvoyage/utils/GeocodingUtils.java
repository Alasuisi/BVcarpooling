package com.bonvoyage.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
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
		return path;
		}
	
	public static JsonObject toJson(List<LatLng> list)
	{
		Gson gson = new GsonBuilder().create();
		JsonArray myCustomArray = gson.toJsonTree(list).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("path", myCustomArray);
        return jsonObject;
	}
}
