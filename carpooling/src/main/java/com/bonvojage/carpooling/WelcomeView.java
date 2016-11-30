package com.bonvojage.carpooling;

import com.bonvojage.designs.Landpage;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

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
	searchRideBtn.setHtmlContentAllowed(true);
	searchRideBtn.setCaption(BvStringUtils.bvColorizeString("Search Ride"));
	//searchRideBtn.setIcon(FontAwesome.SMILE_O);
	clearUserHistory();
	addUserHistoryItem("work-home","at a point in time");
	
	userHistory.addItemClickListener(new ItemClickEvent.ItemClickListener() {
		
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
	}
public void setUserName(String userString)
	{
	userLabel.setValue(userString);
	}
public void clearUserHistory()
	{
	userHistory.getContainerDataSource().removeAllItems();
	}
public void addUserHistoryItem(String trip,String date)
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
}
