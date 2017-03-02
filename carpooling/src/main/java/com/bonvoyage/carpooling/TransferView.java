package com.bonvoyage.carpooling;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bonvoyage.domain.Transfer;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TransferView extends VerticalLayout{
	private Label mapTitleLabel = new Label("Your path");
	private Label depLabel = new Label();
	private Label arrLabel = new Label();
	private Label timeLabel = new Label();
	private GoogleMap map = null;
	
	private HorizontalSplitPanel contentPanel = new HorizontalSplitPanel();
	private VerticalLayout leftSide= new VerticalLayout();
	private VerticalLayout rightSide = new VerticalLayout();
	
	public TransferView(Transfer tran)
		{
		 this.setSizeFull();
		 this.setImmediate(true);
		 this.setResponsive(true);
		 
		 map = new GoogleMap("AIzaSyBA-NgbRwnecHN3cApbnZoaCZH0ld66fT4", null, "english");
		 map.setSizeFull();
		 map.setImmediate(true);
		 
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
		 rightSide.addComponents(depLabel,arrLabel,timeLabel);
		 depLabel.setValue(tran.getDep_addr());
		 arrLabel.setValue(tran.getArr_addr());
		 Date departure = new Date(tran.getDep_time());
		 SimpleDateFormat sdf = new SimpleDateFormat();
		 sdf.applyPattern("dd-MM-yy HH.mm");
		 String depString = sdf.format(departure);
		 timeLabel.setValue(depString);
		 contentPanel.setFirstComponent(leftSide);
		 contentPanel.setSecondComponent(rightSide);
		 this.addComponent(contentPanel);
		 
		 
		}

}
