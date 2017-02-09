package com.bonvoyage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;

public class FourthStep implements WizardStep {

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return "Trip route";
	}

	@Override
	public Component getContent() {
		// TODO Auto-generated method stub
		return new FourthStepView();
	}

	@Override
	public boolean onAdvance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return false;
	}

}
