package com.bonvojage.carpooling;

import java.util.Date;

import com.bonvojage.designs.Landpage;
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

import utils.BvStringUtils;

public class WelcomeView extends Landpage {
private CarpoolingUI ui;
private HistorySubWindowView subwin =new HistorySubWindowView(ui);



public WelcomeView(CarpoolingUI ui)
	{
	this.ui=ui;
	setTitle("Carpooling service interface");
	offerRideBtn.setHtmlContentAllowed(true);
	offerRideBtn.setCaption(BvStringUtils.bvColorizeString("Offer Ride"));
	offerRideBtn.setIcon(FontAwesome.AUTOMOBILE);
	searchRideBtn.setHtmlContentAllowed(true);
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
			Window subWindow = new Window("History sub window example");
			subWindow.center();
			//subWindow.setContent();
			subWindow.setWidth("640px");
			subWindow.setHeight("480px");
			UI.getCurrent().addWindow(subWindow);
			
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
