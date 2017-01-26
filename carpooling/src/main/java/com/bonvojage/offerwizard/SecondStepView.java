package com.bonvojage.offerwizard;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvojage.designs.OfferSecondStepDesign;
import com.bonvojage.utils.BvStringUtils;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;

public class SecondStepView extends OfferSecondStepDesign{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7818376208374089884L;
	private CarpoolingUI ui;
	
	public SecondStepView(CarpoolingUI ui)
		{
		this.ui=ui;
		
		titleLabel.setValue(BvStringUtils.bvColorizeString("Please select seats"));
		subtitleLabel.setValue("You can chose availability and age of your passengers");
		seatsNumberLabel.setValue("Please chose number of available seats:");
		seatsNumberSelection.removeAllItems();
		seatsNumberSelection.addItems("one","two","three","four","five");
		babyNumberLabel.setValue("Would you accept babies? (age 0-2)");
		babyNumberSelection.removeAllItems();
		babyNumberSelection.addItems("Yes","No");
		childrenNumberLabel.setValue("Would you accept children? (age 2-8)");
		childrenNumberSelection.removeAllItems();
		childrenNumberSelection.addItems("Yes","No");
		
		}

}
