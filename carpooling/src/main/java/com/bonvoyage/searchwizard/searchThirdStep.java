package com.bonvoyage.searchwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;

public class searchThirdStep implements WizardStep{

	@Override
	public String getCaption() {
		
		return "Confirmation";
	}

	@Override
	public Component getContent() {
		
		return new searchThirdStepView();
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
