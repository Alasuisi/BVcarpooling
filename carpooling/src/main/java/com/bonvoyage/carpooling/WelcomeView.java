package com.bonvoyage.carpooling;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.vaadin.teemu.wizards.Wizard;

import com.bonvoyage.designs.Landpage;
import com.bonvoyage.domain.McsaSolution;
import com.bonvoyage.domain.Pool;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.domain.UserProfile;
import com.bonvoyage.offerwizard.FirstStep;
import com.bonvoyage.offerwizard.FourthStep;
import com.bonvoyage.offerwizard.SecondStep;
import com.bonvoyage.offerwizard.ThirdStep;
import com.bonvoyage.persistance.McsaSolutionDAO;
import com.bonvoyage.persistance.PoolDAO;
import com.bonvoyage.persistance.TransferDAO;
import com.bonvoyage.persistance.UserDAO;
import com.bonvoyage.searchwizard.searchFirstStep;
import com.bonvoyage.searchwizard.searchSecondStep;
import com.bonvoyage.searchwizard.searchThirdStep;
import com.bonvoyage.utils.BvStringUtils;
import com.bonvoyage.utils.DaoException;
import com.bonvoyage.utils.DbConnector;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.vaadin.data.Item;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.ContextClickEvent.ContextClickListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window.CloseEvent;

public class WelcomeView extends Landpage {
/**
	 * 
	 */
	private static final long serialVersionUID = 4387692572453769971L;
private CarpoolingUI ui;
private HistorySubWindowView subwin =new HistorySubWindowView(ui);
private UserProfile loggedUser = null;
private Accordion driverContent = new Accordion();
private Accordion passengerContent = new Accordion();
//private VerticalLayout testLay = new VerticalLayout();
//private Label testLab = new Label("BLA BLA BLA");
//private VerticalLayout testLay2 = new VerticalLayout();
//private Label testLab2 = new Label("BLA BLA BLA");
private TabSheet serviceSelectionContent = new TabSheet();


public WelcomeView(CarpoolingUI ui) throws JsonParseException, JsonMappingException, IOException
	{
	this.ui=ui;
	super.rightSplitVertical.setSizeFull();
	super.leftSplitVertical.setSizeFull();
	super.buttonsLayout.setHeight("130px");
	super.mainLogo.addStyleName("animated");
	super.mainLogo.addStyleName("tada");
	super.leftSplitVertical.addStyleName("animated");
	super.leftSplitVertical.addStyleName("bounceInLeft");
	super.leftSplitVertical.addStyleName("delay15");
	
	setTitle("Carpooling service interface");
	offerRideBtn.setHtmlContentAllowed(true);
	offerRideBtn.setCaption(BvStringUtils.bvColorizeString("Offer Ride"));
	offerRideBtn.setImmediate(true);
	offerRideBtn.addStyleName("animated");
	offerRideBtn.addStyleName("delay05");
	offerRideBtn.addStyleName("rollIn");
	offerRideBtn.setIcon(FontAwesome.AUTOMOBILE);
	searchRideBtn.setHtmlContentAllowed(true);
	searchRideBtn.setImmediate(true);
	searchRideBtn.addStyleName("animated");
	searchRideBtn.addStyleName("delay1");
	searchRideBtn.addStyleName("rollIn");
	searchRideBtn.setCaption(BvStringUtils.bvColorizeString("Search Ride"));
	searchRideBtn.setIcon(FontAwesome.USERS);
	clearUserHistory();
	userHistory.removeContainerProperty("Trip");
	userHistory.removeContainerProperty("Date");
	userHistory.addContainerProperty("Trip",            Label.class,     null);
    userHistory.addContainerProperty("Date", Date.class, null);
    userHistory.setColumnExpandRatio("Trip", 1);
	Label demoTrip = new Label("work "+FontAwesome.ARROW_RIGHT.getHtml()+" home");
    demoTrip.setContentMode(ContentMode.HTML);
    Date demoWhen = new Date(System.currentTimeMillis());
	addUserHistoryItem(demoTrip,demoWhen);
	
	
	/////test content portion fo the welcomepage
	super.rightSplitVertical.addComponent(serviceSelectionContent);
	serviceSelectionContent.setSizeFull();
	serviceSelectionContent.addTab(driverContent, "My offerings", FontAwesome.CAB);
	serviceSelectionContent.addTab(passengerContent, "My ride requests",FontAwesome.USERS);
	//testLay.setSizeFull();
	//testLay.addComponent(testLab);
	//testLay2.addComponent(testLab2);
	//driverContent.addTab(testLay,"test1");
	//driverContent.addTab(testLay2, "test2");
	passengerContent.setImmediate(true);
	driverContent.setImmediate(true);
	driverContent.addStyleName("animated");
	driverContent.addStyleName("fadeInUpBig");
	driverContent.addStyleName("delay15");
	driverContent.addSelectedTabChangeListener(new SelectedTabChangeListener(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 5004943874436714662L;

		@Override
		public void selectedTabChange(SelectedTabChangeEvent event) {
			driverContent.removeStyleName("delay15");
			driverContent.removeStyleName("fadeInUpBig");
			driverContent.removeStyleName("animated");
			driverContent.markAsDirty();
			
		}});

	try {
		updateTransfersView();
	} catch (DaoException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	///////////////////////////////////////////////
	
	
	userHistory.addItemClickListener(new ItemClickEvent.ItemClickListener() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3770220223265414635L;

		@Override
		public void itemClick(ItemClickEvent event) {
			Window subWindow = new Window("History sub window example");
			subWindow.center();
			subWindow.setContent(subwin);
			subWindow.setWidth("640px");
			subWindow.setHeight("480px");
			UI.getCurrent().addWindow(subWindow);
			
			//titleLable.setValue("Carpooling inteface");
			
			
		}
	});
	
	offerRideBtn.addClickListener(new Button.ClickListener() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6593187804263271206L;

		@Override
		public void buttonClick(ClickEvent event) {
			Window subWindow = new Window("New trip Wizard");
			subWindow.center();
			subWindow.setImmediate(true);
			subWindow.addStyleName("animated");
			//subWindow.addStyleName("delay05");
			subWindow.addStyleName("pulse");
			Wizard myWizard = new Wizard();
			myWizard.addStep(new FirstStep());
			myWizard.addStep(new SecondStep());
			myWizard.addStep(new ThirdStep());
			myWizard.addStep(new FourthStep());
			myWizard.getFinishButton().addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -9007586123936892338L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						TransferDAO.insert(UI.getCurrent().getSession().getAttribute(Transfer.class));
						updateTransfersView();
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					subWindow.close();
					
				}
			});
			myWizard.getCancelButton().addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -8280582140236466557L;

				@Override
				public void buttonClick(ClickEvent event) {
					
					subWindow.close();
						
				}
			});
			myWizard.setSizeFull();
			subWindow.setContent(myWizard);
			subWindow.setWidth("800px");
			subWindow.setHeight("600px");
			UI.getCurrent().addWindow(subWindow);
			
		}
	});
	
	searchRideBtn.addClickListener(new Button.ClickListener() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2489390320156663464L;

		@Override
		public void buttonClick(ClickEvent event) {
			Window subWindow = new Window("Search trip Wizard");
			subWindow.center();
			subWindow.setImmediate(true);
			subWindow.addStyleName("animated");
			//subWindow.addStyleName("delay05");
			subWindow.addStyleName("zoomInLeft");
			Wizard myWizard = new Wizard();
			myWizard.addStep(new searchFirstStep());
			myWizard.addStep(new searchSecondStep());
			myWizard.addStep(new searchThirdStep());
			
			myWizard.getFinishButton().addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -7503617608417852323L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						TransferDAO.insert((Transfer) UI.getCurrent().getSession().getAttribute("search_tran"));
						updateTransfersView();
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DaoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					subWindow.close();
					
				}
			});
			myWizard.getCancelButton().addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -3305823179945029197L;

				@Override
				public void buttonClick(ClickEvent event) {
					subWindow.close();
					UI.getCurrent().getSession().setAttribute("search_tran", null);
					
				}
			});

			myWizard.setSizeFull();
			subWindow.setContent(myWizard);
			subWindow.setWidth("800px");
			subWindow.setHeight("600px");
			UI.getCurrent().addWindow(subWindow);
			
		}
	});
	
	/*searchRideBtn.addClickListener(new Button.ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			try {
				if(UI.getCurrent().getSession().getAttribute(User.class)!=null)
					{
					Integer key=UI.getCurrent().getSession().getAttribute(User.class).getUserID();
					User loggedUser = UserDAO.load(key.intValue());
					UI.getCurrent().getSession().setAttribute(User.class, loggedUser);
					System.out.println("key in session is:"+key.intValue());
					//System.out.println(loggedUser.getProfileID()+" "+loggedUser.getUserID()+" "+loggedUser.getNtransit().toString());
					Label userdata = new Label("user:"+loggedUser.getUserID()+" profileid:"+loggedUser.getProfileID()+
											   "confort:"+loggedUser.getConfortlevel()+" emission:"+loggedUser.getEmissionsens()+" etc etc");
					rightSplitVertical.addComponent(userdata);
					
					}else
						{
						Label error = new Label("Cannot use that function, carpooling module has been loaded without an user!");
								rightSplitVertical.addComponent(error);		
						}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});*/
	
	/*
	Button testbutton = new Button("test transfer insert");
	super.buttonsLayout.addComponent(testbutton);
	testbutton.addClickListener(new Button.ClickListener() {
		
		
		private static final long serialVersionUID = -8978462989992476953L;

		@Override
		public void buttonClick(ClickEvent event) {
			Transfer tran = new Transfer();
			tran.setAnimal(true);
			tran.setArr_addr("prova destinazione");
			Point2D point = new Point2D.Float();
			point.setLocation(4.5, 6.6);
			tran.setArr_gps(point);
			tran.setAva_seats(4);
			tran.setClass_id(666);
			tran.setDep_addr("prova partenza");
			Point2D deppoint = new Point2D.Float();
			deppoint.setLocation(8.8, 15.5);
			tran.setDep_gps(deppoint);
			tran.setDep_time(System.currentTimeMillis());
			tran.setHandicap(false);
			tran.setLuggage(true);
			tran.setOcc_seats(2);
			JsonObject path = new JsonObject();
			path.addProperty("test property", "aint nobody got time fo dat");
			path.addProperty("test property2", "test test test");
			tran.setPath(path);
			tran.setPool_id(333);
			tran.setPrice(55.0);
			tran.setProf_id(1);
			tran.setReser_id(33);
			tran.setSmoke(true);
			tran.setStatus("status string test");
			//tran.setTran_id(1);
			tran.setType("type string test");
			tran.setUser_id(80);
			JsonObject user_role = new JsonObject();
			user_role.addProperty("role", "driver");
			tran.setUser_role(user_role);
			try {
				TransferDAO.insert(tran);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	});
	*/
	}

		

	
public void setUserName(String userString)
	{
	userLabel.setValue(userString);
	}
public void clearUserHistory()
	{
	userHistory.getContainerDataSource().removeAllItems();
	}
public void addUserHistoryItem(Label trip,Date date)
	{
	Object newItemId = userHistory.addItem();
	Item row1 = userHistory.getItem(newItemId);
	row1.getItemProperty("Trip").setValue(trip);
	row1.getItemProperty("Date").setValue(date);
	
	}
public void setTitle(String title)
	{
	titleLable.setValue(BvStringUtils.bvColorizeWord(title));
	titleLable.addStyleName("animated");
	titleLable.addStyleName("bounceInRight");
	titleLable.addStyleName("delay025");
	//titleLable.setValue("<font color=#ff0000>p</font><font color=#df5d1f>o</font><font color=#bfad3f>r</font><font color=#9fe65f>t</font><font color=#7ffe7f>a</font><font color=#5ff39f>n</font><font color=#3fc6bf>n</font><font color=#1f7ddf>a</font></a>");
	}
public HistorySubWindowView getHistoryItemSubWindow()
	{
	return subwin;
	}
public Table getHistoryTable()
	{
	return userHistory;
	}
public void setHistoryTable(Table tab)
	{
	 super.userHistory=tab;
	}

public void updateTransfersView() throws JsonParseException, JsonMappingException, IOException, DaoException
	{
	driverContent.removeAllComponents();
	passengerContent.removeAllComponents();
	loggedUser = (UserProfile) UI.getCurrent().getSession().getAttribute(UserProfile.class);
	
	UserProfile thisUser =UI.getCurrent().getSession().getAttribute(UserProfile.class);
	LinkedList<Transfer> test = TransferDAO.readMyOfferings(thisUser);
	HashMap<Integer,McsaSolution> bookedSet = new HashMap<Integer,McsaSolution>();
	LinkedList<McsaSolution> booked=null;
	try {
		booked = McsaSolutionDAO.getAllBookedReservations(thisUser.getUserID());
		Iterator<McsaSolution> bookIter = booked.iterator();
		System.out.println("Size of booked solution "+booked.size());
		while(bookIter.hasNext())
			{
			McsaSolution sol = bookIter.next();
			bookedSet.put(new Integer(sol.getTransferID()), sol);
			}
	} catch (ClientHandlerException | UniformInterfaceException | DaoException e1) {
		Notification notice = new Notification("Notice","You have no reservations booked",Type.HUMANIZED_MESSAGE);
		notice.setPosition(Position.TOP_RIGHT);
		notice.setDelayMsec(3500);
		notice.show(UI.getCurrent().getPage());
	}
	
	Iterator<Transfer> iter = test.iterator();
	while(iter.hasNext())
		{
		 Transfer tran = iter.next();
		 System.out.println("reading users trnasfer: "+tran.toString());
		 if(tran.getUser_role().equals("driver"))
		 	{
			 String tabTitle ="TRIP FROM: "+tran.getDep_addr()+" TO: "+tran.getArr_addr()+" AVAILABLE SEATS:"+tran.getOcc_seats()+"/"+tran.getAva_seats();
			 Pool driverPool = PoolDAO.getPool(tran.getUser_id(), tran.getTran_id());
			 TransferView toAdd = new TransferView(this,tran,driverPool);
			 toAdd.setImmediate(true);
			 driverContent.addTab(toAdd, tabTitle);
			 //toAdd.fitMap();
			 //System.out.println(toPrint.toString());
		 	}else
		 		{
		 		if(bookedSet.containsKey(new Integer(tran.getTran_id())))
		 			{
		 			String tabTitle ="Booked Trip from: "+tran.getDep_addr()+" to: "+tran.getArr_addr()+". Confirmed seats:"+tran.getOcc_seats();
		 			McsaSolution sol = bookedSet.get(new Integer(tran.getTran_id()));
		 			SolutionDetailView bookedView = new SolutionDetailView(this,null,sol.getSolution(),tran.getUser_id(),tran.getTran_id(),sol.getSolutionID(),true,true);
		 			passengerContent.addTab(bookedView,tabTitle);
		 			}else
		 				{
				 		String tabTitle ="TRIP FROM: "+tran.getDep_addr()+" TO: "+tran.getArr_addr()+" REQUESTED SEATS:"+tran.getOcc_seats();
				 		TransferView toAdd = new TransferView(this,tran);
						 toAdd.setImmediate(true);
				 		passengerContent.addTab(toAdd,tabTitle);
				 		//toAdd.fitMap();
		 				}
		 		}
		}
	driverContent.markAsDirtyRecursive();
	passengerContent.markAsDirtyRecursive();
	}
/*private void updateTransfersView() throws JsonParseException, JsonMappingException, IOException
	{
	driverContent.removeAllComponents();
	passengerContent.removeAllComponents();
	UserProfile thisUser =UI.getCurrent().getSession().getAttribute(UserProfile.class);
	LinkedList<Transfer> test = TransferDAO.readMyOfferings(thisUser);
	Iterator<Transfer> iter = test.iterator();
	while(iter.hasNext())
		{
		 Transfer tran = iter.next();
		 if(tran.getUser_role().equals("driver"))
		 	{
			 String tabTitle ="TRIP FROM: "+tran.getDep_addr()+" TO: "+tran.getArr_addr()+" AVAILABLE SEATS:"+tran.getOcc_seats()+"/"+tran.getAva_seats();
			 driverContent.addTab(new TransferView(tran), tabTitle);
			 //System.out.println(toPrint.toString());
		 	}else
		 		{
		 		String tabTitle ="TRIP FROM: "+tran.getDep_addr()+" TO: "+tran.getArr_addr()+" REQUESTED SEATS:"+tran.getOcc_seats();
		 		passengerContent.addTab(new TransferView(tran),tabTitle);
		 		}
		}
	}*/
}
