package com.bonvojage.offerwizard;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvojage.designs.OfferThirdStepDesign;
import com.bonvojage.utils.BvStringUtils;
import com.vaadin.ui.Alignment;

public class ThirdStepView extends OfferThirdStepDesign{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6413035607816601665L;
	private CarpoolingUI ui;
	
	public ThirdStepView(CarpoolingUI ui)
		{
		this.ui=ui;
		super.titleLabel.setValue(BvStringUtils.bvColorizeString("Please choose your preferences"));
		super.subtitleLabel.setValue("Select one option per choice");
		super.setSpacing(false);
		super.smokerLayout.setWidth("100%");
		//super.smokerLayout.setMargin(true);
		super.smokerLayout.setComponentAlignment(super.smokerSelection, Alignment.MIDDLE_RIGHT);
		super.smokerLayout.setComponentAlignment(super.smokerLabel, Alignment.MIDDLE_LEFT);
		super.smokerLabel.setValue("Would you accept a smoker passenger?");
		super.smokerSelection.setWidth("150px");
		super.smokerSelection.removeAllItems();
		super.smokerSelection.addItems("Yes","No");
		super.handicapLayout.setWidth("100%");
		super.handicapLayout.setComponentAlignment(super.handicapSelection, Alignment.MIDDLE_RIGHT);
		super.handicapLayout.setComponentAlignment(super.handicapLabel, Alignment.MIDDLE_LEFT);
		super.handicapLabel.setValue("Would you/can you accept passenger with handicaps?");
		super.handicapSelection.setWidth("150px");
		super.handicapSelection.removeAllItems();
		super.handicapSelection.addItems("Yes","No");
		super.animalLayout.setWidth("100%");
		super.animalLayout.setComponentAlignment(super.animalSelection, Alignment.MIDDLE_RIGHT);
		super.animalLayout.setComponentAlignment(super.animalLabel, Alignment.MIDDLE_LEFT);
		super.animalLabel.setValue("Would you / can you, accept animals?");
		super.animalSelection.setWidth("150px");
		super.animalSelection.removeAllItems();
		super.animalSelection.addItems("Yes","No");
		super.luggageLayout.setWidth("100%");
		super.luggageLayout.setComponentAlignment(super.luggageSelection, Alignment.MIDDLE_RIGHT);
		super.luggageLayout.setComponentAlignment(super.luggageLabel, Alignment.MIDDLE_LEFT);
		super.luggageLabel.setValue("Would you accept passenger with luggages?");
		super.luggageSelection.setWidth("150px");
		super.luggageSelection.removeAllItems();
		super.luggageSelection.addItems("Yes","No");
		}
}
