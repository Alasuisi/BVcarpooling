package com.bonvoyage.carpooling;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.bonvoyage.domain.McsaSegment;
import com.bonvoyage.domain.TimedPoint2D;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SolutionDetailView extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 119319351706147874L;
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private VerticalLayout leftContent = new VerticalLayout();
	private VerticalLayout rightContent = new VerticalLayout();
	private GoogleMap map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
	private Label titleLabel = new Label();
	private Label subtitleLabel = new Label();
	private Label test = new Label("testLabel bla bla bla");
	
	public SolutionDetailView(LinkedList<McsaSegment> details)
		{
		 titleLabel.setContentMode(ContentMode.HTML);
		 subtitleLabel.setContentMode(ContentMode.HTML);
		 titleLabel.setValue(BvStringUtils.bvColorizeWord("Solution Details"));
		 subtitleLabel.setValue(BvStringUtils.bvColorizeString("Here you can inspect the proposed solution details"));
		 titleLabel.setSizeUndefined();
		 subtitleLabel.setSizeUndefined();
		 titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
		 this.setSizeFull();
		 this.setSpacing(true);
		 this.addComponents(titleLabel,subtitleLabel,mainLayout);
		 this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
		 this.setComponentAlignment(subtitleLabel, Alignment.TOP_CENTER);
		 this.setExpandRatio(mainLayout, 1);
		 mainLayout.setSizeFull();
		 mainLayout.addComponents(leftContent,rightContent);
		 mainLayout.setSpacing(true);
		 mainLayout.setMargin(true);
		 leftContent.setSizeFull();
		 rightContent.setSizeFull();
		 rightContent.addComponent(test);
		 leftContent.addComponent(map);
		 map.setSizeFull();
		 map.setImmediate(true);
		 drawSegments(details);
		 
		}
	private void drawSegments(LinkedList<McsaSegment> segList)
		{
		 Iterator<McsaSegment> iter = segList.iterator();
		 boolean firstSegment=true;
		 double latMax=-100;
		 double latMin=100;
		 double lngMax=-200;
		 double lngMin=200;
		 LatLon lastSegPoint=null;
		 while(iter.hasNext())
		 	{
			 McsaSegment seg = iter.next();
			 LinkedList<TimedPoint2D> pathList = seg.getSegmentPath();
			 Iterator<TimedPoint2D> pathIter = pathList.iterator();
			 ArrayList<LatLon> points = new ArrayList<LatLon>();
			 while(pathIter.hasNext())
			 	{
				 TimedPoint2D thisPoint = pathIter.next();
				 double lat =thisPoint.getLatitude();
				 double lng = thisPoint.getLongitude();
				 LatLon latlng = new LatLon(lat,lng);
				 points.add(latlng);
				 lastSegPoint=latlng;
				 if(lat>latMax)latMax=lat;
				 if(lat<latMin)latMin=lat;
				 if(lng>lngMax)lngMax=lng;
				 if(lng<lngMin)lngMin=lng;
			 	}
			 System.out.println("ma portanna? "+getRandomColor());
			 GoogleMapPolyline overlay = new GoogleMapPolyline(points, "#d31717", 0.8, 10);
			 map.addPolyline(overlay);
		 	}
		 map.fitToBounds(new LatLon(latMax,lngMax), new LatLon(latMin,lngMin));
		 System.out.println("Number of segment is: "+segList.size());
		}
	
	private static String getRandomColor()
	{
    Random random = new Random();
    int nextInt = random.nextInt(256*256*256);
    String colorCode = String.format("#%06x", nextInt);
    return colorCode;
	}
	
	

}
