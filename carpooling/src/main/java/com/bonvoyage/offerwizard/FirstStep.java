package com.bonvoyage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.utils.BvStringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FirstStep implements WizardStep{

	@Override
	public String getCaption() {
		return "Trip selection..";
	}

	@Override
	public Component getContent() {
		try {
			return new FirstStepView(new CarpoolingUI());
		} catch (JsonProcessingException e) {
			Notification error = new Notification("BAD","Unable to create wizardStep",Type.ERROR_MESSAGE);
			error.show(Page.getCurrent());
			e.printStackTrace();
		}
		return new VerticalLayout();
	}

	@Override
	public boolean onAdvance() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return true;
	}

}
