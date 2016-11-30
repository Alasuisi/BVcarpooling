package com.bonvojage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvojage.carpooling.CarpoolingUI;
import com.vaadin.ui.Component;
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return true;
	}

}
