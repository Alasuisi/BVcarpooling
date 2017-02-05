package com.bonvojage.offerwizard;

import com.bonvojage.carpooling.CarpoolingUI;
import com.bonvojage.designs.OfferThirdStepDesign;
import com.bonvojage.utils.BvStringUtils;
import com.bonvoyaje.domain.Transfer;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;

public class ThirdStepView extends OfferThirdStepDesign{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6413035607816601665L;
	private CarpoolingUI ui;
	private Transfer tran = (Transfer) UI.getCurrent().getSession().getAttribute(Transfer.class);
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
		
		super.smokerSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -4012570066199683803L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(smokerSelection.getValue().toString().equals("Yes"))tran.setSmoke(true);
				else tran.setSmoke(false);
				updateTransfer();
				
			}
		});
		
		super.handicapSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 2978774416304991962L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(handicapSelection.getValue().toString().equals("Yes")) tran.setHandicap(true);
				else tran.setHandicap(false);
				updateTransfer();
			}
		});
		
		super.animalSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -560525369645236935L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(animalSelection.getValue().toString().equals("Yes")) tran.setAnimal(true);
				else tran.setAnimal(false);
				updateTransfer();
				
			}
		});
		
		super.luggageSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8235662468998390322L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(luggageSelection.getValue().toString().equals("Yes")) tran.setLuggage(true);
				else tran.setLuggage(false);
				updateTransfer();
				
			}
		});
	
		}
	private void updateTransfer()
		{
			if(isSelectionDone())
			{
			UI.getCurrent().getSession().setAttribute(Transfer.class, tran);
			}
		}
	
	public boolean isSelectionDone()
		{
			boolean smoker=!super.smokerSelection.isEmpty();
			boolean luggage = !super.luggageSelection.isEmpty();
			boolean handicap = !super.handicapSelection.isEmpty();
			boolean animal = !super.animalSelection.isEmpty();
			if(smoker && luggage && handicap && animal) 
				{
				System.out.println(smoker);
				System.out.println(luggage);
				System.out.println(handicap);
				System.out.println(animal);
				return true;
				}
			else return false;
		}
}
