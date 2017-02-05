package com.bonvojage.offerwizard;

import org.vaadin.teemu.wizards.WizardStep;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvoyaje.domain.Transfer;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class ThirdStep implements WizardStep{
	private ThirdStepView thirdView = new ThirdStepView(new CarpoolingUI()); 

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return "Special needs selection..";
	}

	@Override
	public Component getContent() {
		// TODO Auto-generated method stub
		return thirdView;
	}

	@Override
	public boolean onAdvance() {
		if (thirdView.isSelectionDone()) 
			{
				System.out.println("stato della transfer "+UI.getCurrent().getSession().getAttribute(Transfer.class).toString());
				return true;
			}
		else
			{
			Notification error = new Notification("Form error","Please fill up all the required informations",Type.ERROR_MESSAGE);
			error.setPosition(Position.TOP_RIGHT);
			error.setDelayMsec(2000);
			error.show(Page.getCurrent());
			
			System.out.println("stato della transfer "+UI.getCurrent().getSession().getAttribute(Transfer.class).toString());
			return false;
			}
	}

	@Override
	public boolean onBack() {
		// TODO Auto-generated method stub
		return true;
	}

}
