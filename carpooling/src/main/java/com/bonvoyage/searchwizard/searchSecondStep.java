package com.bonvoyage.searchwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.vaadin.ui.Component;

public class searchSecondStep implements WizardStep{
	//private searchSecondStepView secondStep = new searchSecondStepView();
	private searchSecondStepView secondStep = new searchSecondStepView();

	@Override
	public String getCaption() {
		
		return "Special needs..";
	}

	@Override
	public Component getContent() {

		return secondStep;
	}

	@Override
	public boolean onAdvance() {
		secondStep.updateTransfer();
		return true;
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return true;
	}

}
