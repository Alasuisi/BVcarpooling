package com.bonvoyage.searchwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class searchFirstStep implements WizardStep{

	@Override
	public String getCaption() {
		
		return "Departure and destination";
	}

	@Override
	public Component getContent() {
		CarpoolingUI ui =(CarpoolingUI) UI.getCurrent();
		return new searchFirstStepView(ui);
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
