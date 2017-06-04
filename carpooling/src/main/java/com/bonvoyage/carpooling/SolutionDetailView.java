package com.bonvoyage.carpooling;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.bonvoyage.domain.McsaSegment;
import com.bonvoyage.domain.McsaSolution;
import com.bonvoyage.domain.SolDetGridRow;
import com.bonvoyage.domain.TimedPoint2D;
import com.bonvoyage.persistance.McsaSolutionDAO;
import com.bonvoyage.utils.BvStringUtils;
import com.bonvoyage.utils.DaoException;
import com.bonvoyage.utils.GeocodingUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class SolutionDetailView extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 119319351706147874L;
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private VerticalLayout mainLayoutAlt = new VerticalLayout();
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
	private LinkedList<GoogleMapPolyline> polyList = new LinkedList<GoogleMapPolyline>();
	private LinkedList<SolDetGridRow> rows=new LinkedList<SolDetGridRow>();
	private BeanItemContainer<SolDetGridRow> container= new BeanItemContainer<SolDetGridRow>(SolDetGridRow.class,rows);
	private int userid;
	private int tranid;
	private int solid;
	private Window parent;
	private WelcomeView parentView;
	private boolean booked;
	
	public SolutionDetailView(WelcomeView parentView,Window parent,LinkedList<McsaSegment> details,int userid,int tranid,int solid,boolean booked,boolean vertical)
		{
		this.userid=userid;
		this.tranid=tranid;
		this.solid=solid;
		this.parent=parent;
		this.booked=booked;
		this.parentView=parentView;
		 titleLabel.setContentMode(ContentMode.HTML);
		 subtitleLabel.setContentMode(ContentMode.HTML);
		 titleLabel.setValue(BvStringUtils.bvColorizeWord("Solution Details"));
		 subtitleLabel.setValue(BvStringUtils.bvColorizeString("Here you can inspect the proposed solution details"));
		 titleLabel.setSizeUndefined();
		 subtitleLabel.setSizeUndefined();
		 titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
		 this.setSizeFull();
		 this.setSpacing(true);
		 if(!vertical)this.addComponents(titleLabel,subtitleLabel,mainLayout,fooLabel);   //////
		 else this.addComponents(titleLabel,subtitleLabel,mainLayoutAlt,fooLabel);
		 this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
		 this.setComponentAlignment(subtitleLabel, Alignment.TOP_CENTER);
		 if(!vertical)this.setExpandRatio(mainLayout, 1);    //////
		 else this.setExpandRatio(mainLayoutAlt, 1);
		 rideDetails.setImmediate(true);
		// instantiateGrid();
		 if(!vertical)
		 {
			 mainLayout.setSizeFull();
			 mainLayout.addComponents(leftContent,rightContent);
			 mainLayout.setSpacing(true);
			 mainLayout.setMargin(true);
		 }else
		 	{
			 mainLayoutAlt.setSizeFull();
			 mainLayoutAlt.addComponents(leftContent,rightContent);
			 mainLayoutAlt.setSpacing(true);
			 mainLayoutAlt.setMargin(true);
		 	}
		 leftContent.setSizeFull();
		 rightContent.setSizeFull();
		 rideDetails= new Grid("Segments",container);
		 rightContent.addComponents(rightTitle,rightSub,rideDetails,bookButton);
		 rightTitle.setSizeUndefined();
		 rightSub.setSizeUndefined();
		 fooLabel.setValue("Notice: black segments real path will be evaluated when the solution will be booked");
		 fooLabel.setSizeUndefined();
		 rightContent.setComponentAlignment(rightTitle, Alignment.TOP_CENTER);
		 rightContent.setComponentAlignment(rightSub, Alignment.TOP_CENTER);
		 rightContent.setComponentAlignment(rideDetails, Alignment.TOP_CENTER);
		 this.setComponentAlignment(fooLabel, Alignment.MIDDLE_CENTER);
		 rightContent.setComponentAlignment(bookButton, Alignment.TOP_CENTER);
		 if(!booked)
		 	{
			 bookButton.setCaptionAsHtml(true);
			 bookButton.setCaption(BvStringUtils.bvColorizeString("Book this ride!!"));
			 bookButton.setImmediate(true);
		 	}else
		 		{
		 		bookButton.setCaption("Cancel reservation");
				bookButton.addStyleName(ValoTheme.BUTTON_DANGER);
				bookButton.markAsDirty();
		 		}
		 rightTitle.setContentMode(ContentMode.HTML);
		 rightTitle.setValue(BvStringUtils.bvColorizeWord("You depart on:"));
		 rightTitle.addStyleName(ValoTheme.LABEL_HUGE);
		 rightSub.setContentMode(ContentMode.HTML);
		 if(!vertical)rightContent.setExpandRatio(rideDetails, 1);
		 else mainLayoutAlt.setExpandRatio(leftContent, 1);
		 rightContent.setSpacing(true);
		 rideDetails.setSizeFull();
		 leftContent.addComponent(map);
		 map.setSizeFull();
		 map.setImmediate(true);
		 setDataForView(details);
		 setBookRideBtn(details);
		 
		}
	private void instantiateGrid()
		{
		 Column waitCol =rideDetails.addColumn("Wait time",String.class);
		 Column hopInCol=rideDetails.addColumn("Hop in at",String.class);
		 Column hopOutCol =rideDetails.addColumn("Hop of at",String.class);
		 Column segDurCol =rideDetails.addColumn("Segment duration",String.class);
		 Column segLenCol = rideDetails.addColumn("Segment lenght",String.class);
		 hopInCol.setRenderer(new HtmlRenderer());
		 hopOutCol.setRenderer(new HtmlRenderer());
		 segLenCol.setRenderer(new HtmlRenderer());
		}
	private void setBookRideBtn(LinkedList<McsaSegment> details)
		{
		bookButton.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 4115737125365447340L;

			@Override
			public void buttonClick(ClickEvent event) {
				if(!booked)
				{
					//bookButton.removeStyleName(ValoTheme.BUTTON_DANGER);
					//bookButton.setCaption(BvStringUtils.bvColorizeString("Book this ride!!"));
					//bookButton.markAsDirty();
					McsaSolution updated=null;
					try {
						updated =McsaSolutionDAO.bookRide(userid, tranid, solid);
					} catch (ClientHandlerException | UniformInterfaceException | DaoException | IOException e) {
						Notification error = new Notification("Error", e.getMessage(), Type.ERROR_MESSAGE);
						error.show(UI.getCurrent().getPage());
						e.printStackTrace();
					}
					LinkedList<McsaSegment> updatedSol = updated.getSolution();
					setDataForView(updatedSol);
					booked=true;
					bookButton.setCaption("Cancel reservation");
					bookButton.addStyleName(ValoTheme.BUTTON_DANGER);
					bookButton.markAsDirty();
				}else
					{
					String serverMessage=null;
					 try {
						  serverMessage=McsaSolutionDAO.deleteReservation(userid, tranid);
						 // bookButton.removeStyleName(ValoTheme.BUTTON_DANGER);
						 // bookButton.setCaption(BvStringUtils.bvColorizeString("Book this ride!!"));
						 // bookButton.markAsDirty();
						 // booked=false;
						  Notification success = new Notification("Success",serverMessage,Type.ASSISTIVE_NOTIFICATION);
						  success.setDelayMsec(3500);
						  success.setPosition(Position.TOP_RIGHT);
						  success.show(UI.getCurrent().getPage());
						  if(parent!=null)parent.close();
						  System.out.println("is parentview null "+parentView==null);
						  if(parentView!=null)parentView.updateTransfersView();
						 // setDataForView(details);
						 } catch (ClientHandlerException | UniformInterfaceException | DaoException e) {
								Notification error = new Notification("Error",serverMessage,Type.ERROR_MESSAGE);
								error.show(UI.getCurrent().getPage());
							} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
		});
		}
	private void setDataForView(LinkedList<McsaSegment> segList)
		{

		 if(polyList.size()!=0)
		 	{
			 Iterator<GoogleMapPolyline> politer = polyList.iterator();
			 while(politer.hasNext())
			 	{
				 map.removePolyline(politer.next());
			 	}
		 	}
		 if(rows.size()!=0) rows = new LinkedList<SolDetGridRow>();
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
			 double segLenght = Math.ceil(GeocodingUtils.evaluateLenght(seg.getSegmentPath()));
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
				 //String labelValue = "This path in this color are with a driver";
				 //Label toAdd=colorLabel(labelValue,color);
				 //rightContent.addComponent(toAdd);
			 	}else
			 		{
			 		color="#000000";
			 		overlay = new GoogleMapPolyline(points,color, 0.8, 10);
					 String labelValue = "This path in this color is on foot";
					 //Label toAdd=colorLabel(labelValue,color);
					// rightContent.addComponent(toAdd);
			 		}
			 map.addPolyline(overlay);
			 polyList.add(overlay);
			 String waitString = timeToString(seg.getDepartureWaitTime());
			 String depString =colorString(sdf.format(new Date(seg.getSegmentDeparture())),color);
			 String arrString = colorString(sdf.format(new Date(seg.getSegmentArrival())),color);
			 String segDuration = timeToString(seg.getSegmentDuration());
			 String segLenString = colorString(""+segLenght,color);
			 SolDetGridRow toAdd = new SolDetGridRow(waitString,depString,arrString,segDuration,segLenString);
			 rows.add(toAdd);
			 System.out.println("segment duration "+seg.getSegmentDuration());
			 //rideDetails.addRow(waitString,depString,arrString,segDuration,segLenString);
		 	}
		 System.out.println("how many rows? "+rows.size());
		 setGrid();
		 map.fitToBounds(new LatLon(latMax,lngMax), new LatLon(latMin,lngMin));
		 System.out.println("Number of segment is: "+segList.size());
		}
	
	private  void setGrid()
		{
		 rightContent.removeComponent(rideDetails);
		 container= new BeanItemContainer<SolDetGridRow>(SolDetGridRow.class,rows);
		 rideDetails = new Grid(container);
		 rideDetails.getColumn("waitString").setHeaderCaption("Wait Time");
		 rideDetails.getColumn("depString").setHeaderCaption("Hop in at");
		 rideDetails.getColumn("arrString").setHeaderCaption("Hop off at");
		 rideDetails.getColumn("segDuration").setHeaderCaption("Segment duration");
		 rideDetails.getColumn("segLenString").setHeaderCaption("Segment length");
		 rideDetails.getColumn("waitString").setRenderer(new HtmlRenderer());
		 rideDetails.getColumn("depString").setRenderer(new HtmlRenderer());
		 rideDetails.getColumn("arrString").setRenderer(new HtmlRenderer());
		 rideDetails.getColumn("segDuration").setRenderer(new HtmlRenderer());
		 rideDetails.getColumn("segLenString").setRenderer(new HtmlRenderer());
		 rideDetails.setColumnOrder("waitString","depString","arrString","segDuration","segLenString");
		 rideDetails.markAsDirty();
		 rightContent.addComponent(rideDetails, 2);
		 rightContent.setComponentAlignment(rideDetails, Alignment.TOP_CENTER);
		 rightContent.setExpandRatio(rideDetails, 1);
		 rideDetails.setSizeFull();
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
