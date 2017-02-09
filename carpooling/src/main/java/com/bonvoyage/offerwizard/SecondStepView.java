package com.bonvoyage.offerwizard;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.designs.OfferSecondStepDesign;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.data.Validator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;

public class SecondStepView extends OfferSecondStepDesign{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7818376208374089884L;
	private CarpoolingUI ui;
	private Transfer tran = (Transfer) UI.getCurrent().getSession().getAttribute(Transfer.class);
	Boolean secondStepCompleted = false;
	public SecondStepView(CarpoolingUI ui)
		{
		this.ui=ui;
		UI.getCurrent().getSession().setAttribute("secondStepCompleted", secondStepCompleted);
		titleLabel.setValue(BvStringUtils.bvColorizeString("Please select seats"));
		subtitleLabel.setValue("You can chose availability and age of your passengers");
		seatsNumberLabel.setValue("Please chose number of available seats:");
		seatsNumberSelection.removeAllItems();
		seatsNumberSelection.addItems("one","two","three","four","five");
		seatsNumberSelection.setRequired(true);
		seatsNumberSelection.setImmediate(true);
		babyNumberLabel.setValue("Would you accept babies? (age 0-2)");
		babyNumberSelection.removeAllItems();
		babyNumberSelection.addItems("Yes","No");
		babyNumberSelection.setRequired(true);
		babyNumberSelection.setImmediate(true);
		childrenNumberLabel.setValue("Would you accept children? (age 2-8)");
		childrenNumberSelection.removeAllItems();
		childrenNumberSelection.addItems("Yes","No");
		childrenNumberSelection.setRequired(true);
		childrenNumberSelection.setImmediate(true);
		
		seatsNumberSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 4151541568180301911L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if(seatsNumberSelection.getValue().toString().equals("one")) tran.setAva_seats(1);
				if(seatsNumberSelection.getValue().toString().equals("two")) tran.setAva_seats(2);
				if(seatsNumberSelection.getValue().toString().equals("three")) tran.setAva_seats(3);
				if(seatsNumberSelection.getValue().toString().equals("four")) tran.setAva_seats(4);
				if(seatsNumberSelection.getValue().toString().equals("five")) tran.setAva_seats(5);
				if(!babyNumberSelection.isEmpty()&&!childrenNumberSelection.isEmpty())
					{
					UI.getCurrent().getSession().setAttribute(Transfer.class, tran);
					secondStepCompleted=true;
					UI.getCurrent().getSession().setAttribute("secondStepCompleted", secondStepCompleted);
					}
				
			}
		});
		
		babyNumberSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 9039081086135438860L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification toImplement = new Notification("Dev message","This function is not implemented yet",Type.WARNING_MESSAGE);
				toImplement.show(Page.getCurrent());
				if(!seatsNumberSelection.isEmpty()&&!childrenNumberSelection.isEmpty())
					{
					secondStepCompleted=true;
					UI.getCurrent().getSession().setAttribute("secondStepCompleted", secondStepCompleted);
					}
				
			}
		});
		
		childrenNumberSelection.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -1121766195391632988L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification toImplement = new Notification("Dev message","This function is not implemented yet",Type.WARNING_MESSAGE);
				toImplement.show(Page.getCurrent());
				if(!seatsNumberSelection.isEmpty()&&!babyNumberSelection.isEmpty())
					{
					secondStepCompleted=true;
					UI.getCurrent().getSession().setAttribute("secondStepCompleted", secondStepCompleted);
					
					}
				
			}
		});
		

		
		}

}
