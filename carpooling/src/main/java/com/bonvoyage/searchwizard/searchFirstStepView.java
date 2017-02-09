package com.bonvoyage.searchwizard;

import java.util.Date;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class searchFirstStepView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4198348412596061090L;
	private CarpoolingUI ui;
	private Label titleLabel = new Label();
	private Label subtitleLabel = new Label();
	private TextField departureField = new TextField();
	private TextField arrivalField = new TextField();
	private DateField departureTime = new DateField();
	
	public searchFirstStepView(CarpoolingUI ui)
		{
		this.ui=ui;
		
		
		
		this.setSizeFull();
		this.setSpacing(true);
		this.setMargin(true);
		this.addComponents(titleLabel,subtitleLabel,departureField,arrivalField,departureTime);
		this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
		this.setComponentAlignment(subtitleLabel, Alignment.TOP_CENTER);
		this.setComponentAlignment(departureField, Alignment.TOP_CENTER);
		this.setComponentAlignment(arrivalField, Alignment.TOP_CENTER);
		this.setComponentAlignment(departureTime, Alignment.TOP_CENTER);
		//titleLabel.setWidth("100%");
		//subtitleLabel.setWidth("100%");
		titleLabel.setSizeUndefined();
		titleLabel.setContentMode(ContentMode.HTML);
		titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
		titleLabel.setValue(BvStringUtils.bvColorizeWord("Please input your departure and destination addresses"));
		subtitleLabel.setValue("then your estimated departure time");
		subtitleLabel.setSizeUndefined();
		
		departureField.setWidth("80%");
		departureField.setCaptionAsHtml(true);
		departureField.setCaption(BvStringUtils.bvColorizeWord("Departure address:"));
		departureField.setRequired(true);
		departureField.setValidationVisible(false);
		arrivalField.setWidth("80%");
		arrivalField.setCaptionAsHtml(true);
		arrivalField.setCaption(BvStringUtils.bvColorizeWord("Arrival address:"));
		departureTime.setCaptionAsHtml(true);
		departureTime.setCaption(BvStringUtils.bvColorizeWord("Departure time:"));
		departureTime.setRequired(true);
		departureTime.setResolution(Resolution.MINUTE);
		departureTime.setValue(new Date(System.currentTimeMillis()));
		//this.setExpandRatio(departureTime, 100);
		this.setExpandRatio(subtitleLabel, 100);
		
		
		
		}
}
