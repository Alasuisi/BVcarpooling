package com.bonvoyage.searchwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class searchFirstStep implements WizardStep{
	private searchFirstStepView firstStep = new searchFirstStepView((CarpoolingUI)UI.getCurrent());

	@Override
	public String getCaption() {
		
		return "Departure and destination";
	}

	@Override
	public Component getContent() {
		return firstStep;
	}

	@Override
	public boolean onAdvance() {
		if(firstStep.isValid())
			{
			try {
				firstStep.updateTransfer();
				} catch (Exception e) {
					Notification error = new Notification("Geocoding error","Sorry, we were unable to identify your addresses, have you typed them correctly?",Type.ERROR_MESSAGE);
					error.setPosition(Position.TOP_RIGHT);
					error.setDelayMsec(2000);
					error.show(Page.getCurrent());
					e.printStackTrace();
					return false;
				}
			return true;
			}
			else
				{
					Notification error = new Notification("Form error","Please fill up all the required informations",Type.ERROR_MESSAGE);
					error.setPosition(Position.TOP_RIGHT);
					error.setDelayMsec(2000);
					error.show(Page.getCurrent());
					return false;
				}
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return false;
	}

}
