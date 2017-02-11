package com.bonvoyage.searchwizard;

import com.bonvoyage.domain.Transfer;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class searchSecondStepView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4977918288479729279L;
	private Label titleLabel = new Label();
	private Label subtitleLabel = new Label();
	private Label seatsLabel = new Label();
	private Label animalLabel = new Label();
	private Label smokeLabel = new Label();
	private Label handicapLabel = new Label();
	private Label sliderLabel = new Label();
	private HorizontalLayout seatsLayout = new HorizontalLayout();
	private HorizontalLayout animalLayout = new HorizontalLayout();
	private HorizontalLayout smokeLayout = new HorizontalLayout();
	private HorizontalLayout handicapLayout = new HorizontalLayout();
	//private ComboBox seatsCombo = new ComboBox();
	private Slider seatsSlider = new Slider();
	private CheckBox animalCheck = new CheckBox();
	private CheckBox smokeCheck = new CheckBox();
	private CheckBox handicapCheck = new CheckBox();
	private CheckBox luggageCheck = new CheckBox();
	private Transfer searchTran = (Transfer) UI.getCurrent().getSession().getAttribute("search_tran");
	
	public searchSecondStepView()
		{
		 this.setSizeFull();
		 this.setMargin(true);
		 this.addComponents(titleLabel,subtitleLabel,seatsLayout,animalLayout,smokeLayout,handicapLayout);
		 titleLabel.setContentMode(ContentMode.HTML);
		 titleLabel.setValue(BvStringUtils.bvColorizeWord("Please select your special needs, if any"));
		 titleLabel.setSizeUndefined();
		 titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
		 subtitleLabel.setValue("Multiple selection allowed, seats number mandatory");
		 subtitleLabel.setSizeUndefined();
		 this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
		 this.setComponentAlignment(subtitleLabel, Alignment.TOP_CENTER);
		 this.setComponentAlignment(seatsLayout, Alignment.TOP_CENTER);
		 this.setComponentAlignment(animalLayout, Alignment.TOP_CENTER);
		 this.setComponentAlignment(smokeLayout, Alignment.TOP_CENTER);
		 this.setComponentAlignment(handicapLayout, Alignment.TOP_CENTER);
		 
		 seatsLayout.setWidth("80%");
		 seatsLayout.addComponents(seatsLabel,seatsSlider,sliderLabel);
		 seatsLayout.setSpacing(true);
		 seatsLayout.setComponentAlignment(seatsLabel, Alignment.MIDDLE_LEFT);
		 seatsLayout.setComponentAlignment(seatsSlider, Alignment.MIDDLE_RIGHT);
		 seatsLayout.setExpandRatio(seatsLabel, 1);
		 seatsLabel.setValue("How many seats do you need?");
		 seatsSlider.setMin(1);
		 seatsSlider.setMax(5);
		 //seatsSlider.setWidth("250px");
		 sliderLabel.setImmediate(true);
		 int seatsChosen =seatsSlider.getValue().intValue();
		 String sliderStringValue = String.valueOf(seatsChosen);
		 sliderLabel.setValue(sliderStringValue);
		 //seatsSlider.focus();
		// seatsSlider.setCaption(seatsSlider.getValue().toString());
		 //seatsSlider.setImmediate(true);
		 seatsSlider.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 4974897661019498434L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				int newSeatsChosen =seatsSlider.getValue().intValue();
				String newSliderStringValue = String.valueOf(newSeatsChosen);
				sliderLabel.setValue(newSliderStringValue);
				//searchTran.setOcc_seats(newSeatsChosen);
				System.out.println(newSeatsChosen);
				
			}
		});
		 
		 animalLayout.setWidth("80%");
		 animalLayout.addComponents(animalLabel,animalCheck);
		 animalLayout.setSpacing(true);
		 animalLayout.setComponentAlignment(animalLabel, Alignment.MIDDLE_LEFT);
		 animalLayout.setComponentAlignment(animalCheck, Alignment.MIDDLE_CENTER);
		 animalLabel.setValue("Do you carry animals?");
		 animalCheck.setImmediate(true);
		 animalCheck.setCaption("No");
		 animalCheck.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 4974897661019498434L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean value = animalCheck.getValue();
				if(value) 
					{
						animalCheck.setCaption("Yes");
						searchTran.setAnimal(true);
					}
				else 
					{
						animalCheck.setCaption("No");
						searchTran.setAnimal(false);
					}
				
			}
		});
		 
		smokeLayout.setWidth("80%");
		smokeLayout.addComponents(smokeLabel,smokeCheck);
		smokeLayout.setSpacing(true);
		smokeLayout.setComponentAlignment(smokeLabel, Alignment.MIDDLE_LEFT);
		smokeLayout.setComponentAlignment(smokeCheck, Alignment.MIDDLE_CENTER);
		smokeLabel.setValue("Are you a smoker?");
		smokeLabel.setDescription("You can uncheck this if you are a smoker but do not intend to, during the trip");
		
		smokeCheck.setImmediate(true);
		smokeCheck.setCaption("No");
		smokeCheck.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 3115835873592451691L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean value = smokeCheck.getValue();
				if(value) 
					{
						smokeCheck.setCaption("Yes");
						searchTran.setSmoke(true);
					}
				else 
					{
						smokeCheck.setCaption("No");
						searchTran.setSmoke(false);
					}
				
			}
		});
		
		handicapLayout.setWidth("80%");
		handicapLayout.addComponents(handicapLabel,handicapCheck);
		handicapLayout.setSpacing(true);
		handicapLayout.setComponentAlignment(handicapLabel, Alignment.MIDDLE_LEFT);
		handicapLayout.setComponentAlignment(handicapCheck, Alignment.MIDDLE_CENTER);
		handicapLabel.setValue("Do you travel with people with disabilities?");
		handicapLabel.setDescription("Select also if you have some disabilities, even temporary");
		handicapCheck.setImmediate(true);
		handicapCheck.setCaption("No");
		handicapCheck.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 4119075209966631753L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean value = handicapCheck.getValue();
				if(value) 
					{
						handicapCheck.setCaption("Yes");
						searchTran.setHandicap(true);
					}
				else 
					{
						handicapCheck.setCaption("No");
						searchTran.setHandicap(false);
					}
				
			}
		});
	}
	
	protected void updateTransfer()
	{
	 UI.getCurrent().getSession().setAttribute("search_tran", searchTran);
	}

}
