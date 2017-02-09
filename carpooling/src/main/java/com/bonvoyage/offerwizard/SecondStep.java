package com.bonvoyage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.domain.Transfer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class SecondStep implements WizardStep{

	@Override
	public String getCaption() {
		
		return "Passenger characteristics..";
	}

	@Override
	public Component getContent() {
		return new SecondStepView(new CarpoolingUI());
	}

	@Override
	public boolean onAdvance() {
		Boolean secondStepCompleted=(Boolean) UI.getCurrent().getSession().getAttribute("secondStepCompleted");
		Transfer tran =(Transfer) UI.getCurrent().getSession().getAttribute(Transfer.class);
		if(secondStepCompleted.booleanValue()==true) 
			{
			System.out.println("stato della transfer "+tran.toString());
			return true;
			}
		else
			{
			Notification error = new Notification("Form error","Please fill up all the required informations",Type.ERROR_MESSAGE);
			error.setPosition(Position.TOP_RIGHT);
			error.setDelayMsec(2000);
			error.show(Page.getCurrent());
			
			System.out.println("stato della transfer "+tran.toString());
			return false;
			}
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return true;
	}

}
