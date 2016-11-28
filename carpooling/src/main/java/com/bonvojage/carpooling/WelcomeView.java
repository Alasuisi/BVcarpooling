package com.bonvojage.carpooling;

import com.bonvojage.designs.Landpage;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class WelcomeView extends Landpage {
private CarpoolingUI ui;
private HistorySubWindowView subwin =new HistorySubWindowView(ui);



public WelcomeView(CarpoolingUI ui)
	{
	this.ui=ui;
	userHistory.addItemClickListener(new ItemClickEvent.ItemClickListener() {
		
		@Override
		public void itemClick(ItemClickEvent event) {
			Window subWindow = new Window("Sub-window");
			subWindow.center();
			subWindow.setContent(subwin);
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
public void addUserHistoryItem(String trip,String date)
	{
	Object newItemId = userHistory.addItem();
	Item row1 = userHistory.getItem(newItemId);
	row1.getItemProperty("Trip").setValue(trip);
	row1.getItemProperty("Date").setValue(date);
	
	}
public void setTitle(String title)
	{
	titleLable.setValue(title);
	}
public HistorySubWindowView getHistoryItemSubWindow()
	{
	return subwin;
	}
}
