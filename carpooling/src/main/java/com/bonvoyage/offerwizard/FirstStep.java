package com.bonvoyage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class FirstStep implements WizardStep{

	@Override
	public String getCaption() {
		return "Trip selection..";
	}

	@Override
	public Component getContent() {
		return new FirstStepView(new CarpoolingUI());
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
