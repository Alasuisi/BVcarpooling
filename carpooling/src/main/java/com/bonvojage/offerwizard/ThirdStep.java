package com.bonvojage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvojage.carpooling.CarpoolingUI;
import com.vaadin.ui.Component;

public class ThirdStep implements WizardStep{

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return "Special needs selection..";
	}

	@Override
	public Component getContent() {
		// TODO Auto-generated method stub
		return new ThirdStepView(new CarpoolingUI());
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
