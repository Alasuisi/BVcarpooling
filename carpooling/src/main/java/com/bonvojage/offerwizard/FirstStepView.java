package com.bonvojage.offerwizard;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvojage.designs.OfferFirstStep;

import utils.BvStringUtils;

public class FirstStepView extends OfferFirstStep{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5091980142862302684L;
	private CarpoolingUI ui;
	
public FirstStepView(CarpoolingUI ui){
	this.ui=ui;
	titleLable.setValue(BvStringUtils.bvColorizeString("Please choose a trip to share"));
	subtitleLabel.setValue("You can choose only one trip to share, from the list below");
	tripList.removeAllItems();
	String ex1="There wil be information about 1st selectable trip.";
	String ex2="Here information about 2nd selectable trip.";
	String ex3="Here anoter trip that the user can share.";
	String ex4="Only 1 trip can be shared per time.";
	tripList.addItems(ex1,ex2,ex3,ex4);
	}

}