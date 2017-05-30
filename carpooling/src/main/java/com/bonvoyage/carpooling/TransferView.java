package com.bonvoyage.carpooling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.bonvoyage.domain.McsaSolution;
import com.bonvoyage.domain.TimedPoint2D;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.persistance.McsaSolutionDAO;
import com.bonvoyage.utils.BvStringUtils;
import com.google.maps.model.LatLng;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;

public class TransferView extends VerticalLayout{
	private Label mapTitleLabel = new Label("Your path");
	private Label depLabel = new Label();
	private Label arrLabel = new Label();
	private Label timeLabel = new Label();
	private Button fixMap = new Button();
	private Button searchSolutionBtn = new Button();
	private GoogleMap map = null;
	
	private HorizontalSplitPanel contentPanel = new HorizontalSplitPanel();
	private VerticalLayout leftSide= new VerticalLayout();
	private VerticalLayout rightSide = new VerticalLayout();
	private double rndlatMax;
	private double rndlngMax;
	private double rndlatMin;
	private double rndlngMin;
	
	public TransferView(Transfer tran)
		{
		 this.setWidth("100%");
		 this.setHeight("500px");
		 this.setImmediate(true);
		 this.setResponsive(true);
		 contentPanel.setSplitPosition(65, Unit.PERCENTAGE);
		 
		 map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
		 map.setSizeFull();
		 map.setImmediate(true);
		 Iterator<TimedPoint2D> pathIter = tran.getPath().iterator();
		 ArrayList<LatLon> points = new ArrayList<LatLon>();
		 
		 double latMax=-100;
			double latMin=100;
			double lngMax=-200;
			double lngMin=200;
			while(pathIter.hasNext())
				{
				 TimedPoint2D current = pathIter.next();
				 //System.out.println("current point "+current);
				 double lat = current.getLatitude();
				 double lng= current.getLongitude();
				 points.add(new LatLon(lat,lng));
				 if(lat>latMax)latMax=lat;
				 if(lat<latMin)latMin=lat;
				 if(lng>lngMax)lngMax=lng;
				 if(lng<lngMin)lngMin=lng;
				 //lastPoint++;
				}
			GoogleMapPolyline overlay = new GoogleMapPolyline(points, "#d31717", 0.8, 10);
			 map.addPolyline(overlay);
		 
		
		 
		
		GoogleMapMarker start = new GoogleMapMarker();
		start.setPosition(points.get(0));
		start.setDraggable(true);
		start.setAnimationEnabled(true);
		start.setCaption("Departure here");
		map.addMarker(start);
		GoogleMapMarker end = new GoogleMapMarker();
		end.setPosition(points.get(points.size()-1));
		end.setDraggable(true);
		end.setAnimationEnabled(true);
		end.setCaption("Destination here");
		map.addMarker(end);
			
		 
		
		 
		 contentPanel.setLocked(true);
		 contentPanel.setResponsive(true);
		 contentPanel.setSizeFull();
		 
		 leftSide.setSizeFull();
		 leftSide.setImmediate(true);;
		 leftSide.setResponsive(true);
		 
		 rightSide.setSizeFull();
		 rightSide.setResponsive(true);
		 rightSide.setImmediate(true);
		 leftSide.addComponents(mapTitleLabel,map);
		 
		 double latCenter = (latMax+latMin)/2;
			double lngCenter = (lngMax+lngMin)/2;
			LatLon center = new LatLon(latCenter,lngCenter);
			 map.setCenter(center);
			 System.out.println("boundaries 1: "+latMax+","+lngMax);
			 System.out.println("boundaries 2: "+latMin+","+lngMin);
			 rndlatMax = (double)Math.round(latMax * 100000d) / 100000d;
			 rndlngMax = (double)Math.round(lngMax * 100000d) / 100000d;
			 rndlatMin = (double)Math.round(latMin * 100000d) / 100000d;
			 rndlngMin = (double)Math.round(lngMin * 100000d) / 100000d;
			 //map.fitToBounds(new LatLon(rndlatMax,rndlngMax), new LatLon(rndlatMin,rndlngMin));
			 map.setZoom(6);
		 
		 mapTitleLabel.setHeightUndefined();
		 leftSide.setComponentAlignment(mapTitleLabel, Alignment.TOP_LEFT);
		 leftSide.setComponentAlignment(map, Alignment.TOP_LEFT);
		 leftSide.setExpandRatio(map, 1);
		 rightSide.addComponents(depLabel,arrLabel,timeLabel,fixMap,searchSolutionBtn);
		 rightSide.setMargin(true);
		 rightSide.setExpandRatio(timeLabel, 1);
		 rightSide.setComponentAlignment(fixMap, Alignment.TOP_CENTER);
		 rightSide.setComponentAlignment(searchSolutionBtn, Alignment.TOP_CENTER);
		 rightSide.setSpacing(true);
		 
		 searchSolutionBtn.setCaptionAsHtml(true);
		 searchSolutionBtn.setCaption(BvStringUtils.bvColorizeString("Look for solutions"));
		 searchSolutionBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -9024891871815325038L;

			@Override
			public void buttonClick(ClickEvent event) {
				LinkedList<McsaSolution> results= new LinkedList<McsaSolution>();
				System.out.println(tran.toString());
				try {
					results = McsaSolutionDAO.readComputedSolutions(tran.getUser_id(), tran.getTran_id());
				} catch (IOException e) {
					System.out.println("result list size: "+results.size());
					e.printStackTrace();
				}
				if(results.size()==0)
					{
					try {
						results = McsaSolutionDAO.searchSolutions(tran, 1800000, 5);
						Iterator<McsaSolution> iter = results.iterator();
						while(iter.hasNext())
							{
							System.out.println(iter.next().toString());
							}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				
			}
		});
		 fixMap.setCaptionAsHtml(true);
		 fixMap.setCaption(BvStringUtils.bvColorizeString("Center Map"));
		 fixMap.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -9024891871815325038L;

			@Override
			public void buttonClick(ClickEvent event) {
				fitMap();
				
			}
		});
		 depLabel.setValue(tran.getDep_addr());
		 depLabel.setCaptionAsHtml(true);
		 depLabel.setCaption(BvStringUtils.bvColorizeWord("Your departure address:"));
		 arrLabel.setValue(tran.getArr_addr());
		 arrLabel.setCaptionAsHtml(true);
		 arrLabel.setCaption(BvStringUtils.bvColorizeWord("Your arrival address:"));
		 Date departure = new Date(tran.getDep_time());
		 SimpleDateFormat sdf = new SimpleDateFormat();
		 sdf.applyPattern("dd-MM-yy HH.mm");
		 String depString = sdf.format(departure);
		 timeLabel.setValue(depString);
		 timeLabel.setCaptionAsHtml(true);
		 timeLabel.setCaption(BvStringUtils.bvColorizeWord("Your departure time:"));
		 contentPanel.setFirstComponent(leftSide);
		 contentPanel.setSecondComponent(rightSide);
		 this.addComponent(contentPanel);
		 
		 
		}
	public void fitMap()
		{
		map.fitToBounds(new LatLon(rndlatMax,rndlngMax), new LatLon(rndlatMin,rndlngMin));
		}

}
