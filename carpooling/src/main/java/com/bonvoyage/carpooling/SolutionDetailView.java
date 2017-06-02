package com.bonvoyage.carpooling;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.bonvoyage.domain.McsaSegment;
import com.bonvoyage.domain.TimedPoint2D;
import com.bonvoyage.utils.BvStringUtils;
import com.bonvoyage.utils.GeocodingUtils;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
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
	private Label rightTitle = new Label();
	private Label rightSub = new Label();
	private Label fooLabel = new Label();
	private Grid rideDetails = new Grid();
	private Button bookButton = new Button();
	
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
		 Column waitCol =rideDetails.addColumn("Wait time",String.class);
		 Column hopInCol=rideDetails.addColumn("Hop in at",String.class);
		 Column hopOutCol =rideDetails.addColumn("Hop of at",String.class);
		 Column segDurCol =rideDetails.addColumn("Segment duration",String.class);
		 Column segLenCol = rideDetails.addColumn("Segment lenght",String.class);
		 hopInCol.setRenderer(new HtmlRenderer());
		 hopOutCol.setRenderer(new HtmlRenderer());
		 segLenCol.setRenderer(new HtmlRenderer());
		 mainLayout.setSizeFull();
		 mainLayout.addComponents(leftContent,rightContent);
		 mainLayout.setSpacing(true);
		 mainLayout.setMargin(true);
		 leftContent.setSizeFull();
		 rightContent.setSizeFull();
		 rightContent.addComponents(rightTitle,rightSub,rideDetails,fooLabel,bookButton);
		 rightTitle.setSizeUndefined();
		 rightSub.setSizeUndefined();
		 fooLabel.setSizeFull();
		 rightContent.setComponentAlignment(rightTitle, Alignment.TOP_CENTER);
		 rightContent.setComponentAlignment(rightSub, Alignment.TOP_CENTER);
		 rightContent.setComponentAlignment(rideDetails, Alignment.TOP_CENTER);
		 rightContent.setComponentAlignment(fooLabel, Alignment.MIDDLE_LEFT);
		 rightContent.setComponentAlignment(bookButton, Alignment.TOP_CENTER);
		 bookButton.setCaptionAsHtml(true);
		 bookButton.setCaption(BvStringUtils.bvColorizeString("Book this ride!!"));
		 fooLabel.setValue("Notice: black segments real path will be evaluated when the solution will be booked");
		 rightTitle.setContentMode(ContentMode.HTML);
		 rightTitle.setValue(BvStringUtils.bvColorizeWord("You depart on:"));
		 rightTitle.addStyleName(ValoTheme.LABEL_HUGE);
		 rightSub.setContentMode(ContentMode.HTML);
		 rightContent.setExpandRatio(rideDetails, 1);
		 rideDetails.setSizeFull();
		 leftContent.addComponent(map);
		 map.setSizeFull();
		 map.setImmediate(true);
		 setDataForView(details);
		 
		}
	private void setDataForView(LinkedList<McsaSegment> segList)
		{
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		 SimpleDateFormat daySdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
		 Iterator<McsaSegment> iter = segList.iterator();
		 boolean firstTime=true;
		 double latMax=-100;
		 double latMin=100;
		 double lngMax=-200;
		 double lngMin=200;
		 LatLon lastSegPoint=null;
		 Date departure=null;
		 while(iter.hasNext())
		 	{
			 McsaSegment seg = iter.next();
			 double segLenght = GeocodingUtils.evaluateLenght(seg.getSegmentPath());
			 if(departure==null) departure = new Date(seg.getSegmentDeparture());
			 String ddayString = daySdf.format(departure);
			 rightSub.setValue(BvStringUtils.bvColorizeWord(ddayString));
			 LinkedList<TimedPoint2D> pathList = seg.getSegmentPath();
			 Iterator<TimedPoint2D> pathIter = pathList.iterator();
			 ArrayList<LatLon> points = new ArrayList<LatLon>();
			 while(pathIter.hasNext())
			 	{
				 TimedPoint2D thisPoint = pathIter.next();
				 double lat =thisPoint.getLatitude();
				 double lng = thisPoint.getLongitude();
				 LatLon latlng = new LatLon(lat,lng);
				 if(lastSegPoint!=null&&firstTime) 
				 	{
					 points.add(lastSegPoint);
					 firstTime=false;
				 	}
				 points.add(latlng);
				 lastSegPoint=latlng;
				 if(lat>latMax)latMax=lat;
				 if(lat<latMin)latMin=lat;
				 if(lng>lngMax)lngMax=lng;
				 if(lng<lngMin)lngMin=lng;
			 	}
			 firstTime=true;
			 String color = getRandomColor();
			 GoogleMapPolyline overlay;
			 if(seg.getFromTransferID()==seg.getToTransferID())
			 	{
				 overlay = new GoogleMapPolyline(points,color, 0.8, 10);
				 String labelValue = "This path in this color are with a driver";
				 Label toAdd=colorLabel(labelValue,color);
				 //rightContent.addComponent(toAdd);
			 	}else
			 		{
			 		color="#000000";
			 		overlay = new GoogleMapPolyline(points,color, 0.8, 10);
					 String labelValue = "This path in this color is on foot";
					 Label toAdd=colorLabel(labelValue,color);
					// rightContent.addComponent(toAdd);
			 		}
			 map.addPolyline(overlay);
			 String waitString = timeToString(seg.getDepartureWaitTime());
			 String depString =colorString(sdf.format(new Date(seg.getSegmentDeparture())),color);
			 String arrString = colorString(sdf.format(new Date(seg.getSegmentArrival())),color);
			 String segDuration = timeToString(seg.getSegmentDuration());
			 String segLenString = colorString(""+segLenght,color);
			 System.out.println("segment duration "+seg.getSegmentDuration());
			 rideDetails.addRow(waitString,depString,arrString,segDuration,segLenString);
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
	
	private Label colorLabel(String value,String hexColor)
		{
		String colorTag="<font color="+hexColor+">";
		String closeTag = "</font>";
		String labelValue = colorTag+value+closeTag;
		Label result = new Label();
		result.setContentMode(ContentMode.HTML);
		result.setValue(labelValue);
		return result;
		}
	private String colorString(String value,String hexColor)
		{
		String colorTag="<font color="+hexColor+">";
		String closeTag = "</font>";
		String labelValue = colorTag+value+closeTag;
		return labelValue;
		}
	private String timeToString(long timeMillis)
	{
	 double seconds = Math.floor((timeMillis/1000));
	 int hours = (int) (seconds/3600);
	 int minutes = (int) ((seconds-(hours*3600))/60);
	 if(hours==0&&minutes==0) minutes=1;
	 return new String(hours+":"+minutes);
	}
	

}
