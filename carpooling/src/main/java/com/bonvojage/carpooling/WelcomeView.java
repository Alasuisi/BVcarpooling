package com.bonvojage.carpooling;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.vaadin.teemu.wizards.Wizard;

import com.bonvojage.designs.Landpage;
import com.bonvojage.offerwizard.FirstStep;
import com.bonvojage.offerwizard.FourthStep;
import com.bonvojage.offerwizard.SecondStep;
import com.bonvojage.offerwizard.ThirdStep;
import com.bonvojage.utils.BvStringUtils;
import com.bonvojage.utils.DaoException;
import com.bonvojage.utils.DbConnector;
import com.bonvoyage.persistance.TransferDAO;
import com.bonvoyage.persistance.UserDAO;
import com.bonvoyaje.domain.Transfer;
import com.bonvoyaje.domain.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;



import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class WelcomeView extends Landpage {
/**
	 * 
	 */
	private static final long serialVersionUID = 4387692572453769971L;
private CarpoolingUI ui;
private HistorySubWindowView subwin =new HistorySubWindowView(ui);
private User loggedUser = null;


public WelcomeView(CarpoolingUI ui)
	{
	this.ui=ui;
	
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
			subWindow.addStyleName("zoomInLeft");
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
		
		@Override
		public void buttonClick(ClickEvent event) {
			try {
				if(UI.getCurrent().getSession().getAttribute("userid")!=null)
					{
					Integer key=Integer.parseInt((String)UI.getCurrent().getSession().getAttribute("userid"));
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
			/*
			DbConnector conn = new DbConnector();
			Connection c=conn.connect();
			Statement stmt = null;
			
			try {
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT * FROM user_query;" );
				 while ( rs.next() ) {
			            int id = rs.getInt("user_id");
			            String  address = rs.getString("source_location_address");
			            int queryid  = rs.getInt("query_id");
			            String  destaddress = rs.getString("destination_location_address");
			            System.out.println( "ID = " + id );
			            System.out.println( "SOURCE = " + address );
			            System.out.println( "AGE = " + queryid );
			            System.out.println( "DESTINATION = " + destaddress );
			            System.out.println();
			         }
			         rs.close();
			         stmt.close();
			         conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}
	});
	
	Button testbutton = new Button("test transfer insert");
	super.buttonsLayout.addComponent(testbutton);
	testbutton.addClickListener(new Button.ClickListener() {
		
		/**
		 * 
		 */
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
}
