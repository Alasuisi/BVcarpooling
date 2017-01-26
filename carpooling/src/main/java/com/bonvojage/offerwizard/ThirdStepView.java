package com.bonvojage.offerwizard;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvojage.designs.OfferThirdStepDesign;
import com.bonvojage.utils.BvStringUtils;

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
		super.smokerLabel.setValue("Would you accept a smoker passenger?");
		super.smokerSelection.removeAllItems();
		super.smokerSelection.addItems("Yes","No");
		super.handicapLabel.setValue("Would you/can you accept passenger with handicaps?");
		super.handicapSelection.removeAllItems();
		super.handicapSelection.addItems("Yes","No");
		super.animalLabel.setValue("Would you / can you, accept animals?");
		super.animalSelection.removeAllItems();
		super.animalSelection.addItems("Yes","No");
		super.luggageLabel.setValue("Would you accept passenger with luggages?");
		super.luggageSelection.removeAllItems();
		super.luggageSelection.addItems("Yes","No");
		}
}
