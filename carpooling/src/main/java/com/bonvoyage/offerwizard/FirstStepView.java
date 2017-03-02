package com.bonvoyage.offerwizard;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.designs.OfferFirstStep;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.domain.UserProfile;
import com.bonvoyage.utils.BvStringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.vaadin.ui.UI;

public class FirstStepView extends OfferFirstStep{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5091980142862302684L;
	private CarpoolingUI ui;
	
public FirstStepView(CarpoolingUI ui) throws JsonProcessingException{
	this.ui=ui;
	UserProfile loggedUser = UI.getCurrent().getSession().getAttribute(UserProfile.class);
	Transfer tran = new Transfer();
	//JsonObject user_role = new JsonObject();
	//user_role.addProperty("role", "driver");
	tran.setUser_role("driver");
	tran.setUser_id(loggedUser.getUserID());
	tran.setProf_id(loggedUser.getProfileID());
	UI.getCurrent().getSession().setAttribute(Transfer.class, tran);
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
