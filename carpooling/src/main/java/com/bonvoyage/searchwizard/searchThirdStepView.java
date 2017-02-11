package com.bonvoyage.searchwizard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bonvoyage.domain.Transfer;
import com.bonvoyage.utils.BvStringUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class searchThirdStepView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2536812286473661703L;
	private VerticalLayout contentLayout = new VerticalLayout();
	private Label titleLabel = new Label();
	private Label subtitleLabel = new Label();
	private Label departureLabel = new Label();
	private Label arrivalLabel = new Label();
	private Label timeLabel = new Label();
	private Label needsLabel = new Label();
	private Label conclusionLabel = new Label();
	private Transfer searchTransfer = (Transfer) UI.getCurrent().getSession().getAttribute("search_tran");
	
	
	public searchThirdStepView()
		{
			
			this.setSizeFull();
			this.addComponents(titleLabel,subtitleLabel,contentLayout);
			this.setExpandRatio(contentLayout, 1);
			this.setMargin(true);
			contentLayout.setWidth("80%");
			contentLayout.setHeight("100%");
			contentLayout.addComponents(departureLabel,arrivalLabel,timeLabel,needsLabel,conclusionLabel);
			titleLabel.setSizeUndefined();
			titleLabel.setContentMode(ContentMode.HTML);
			titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
			subtitleLabel.setSizeUndefined();
			departureLabel.setSizeUndefined();
			arrivalLabel.setSizeUndefined();
			timeLabel.setSizeUndefined();
			needsLabel.setSizeUndefined();
			conclusionLabel.setSizeUndefined();
			this.setComponentAlignment(titleLabel, Alignment.TOP_CENTER);
			this.setComponentAlignment(subtitleLabel, Alignment.TOP_CENTER);
			contentLayout.setComponentAlignment(departureLabel, Alignment.TOP_LEFT);
			contentLayout.setComponentAlignment(arrivalLabel, Alignment.TOP_LEFT);
			contentLayout.setComponentAlignment(timeLabel, Alignment.TOP_LEFT);
			contentLayout.setComponentAlignment(needsLabel, Alignment.TOP_LEFT);
			contentLayout.setComponentAlignment(conclusionLabel, Alignment.TOP_CENTER);
			//this.setExpandRatio(subtitleLabel, 1);
			
			
			titleLabel.setValue(BvStringUtils.bvColorizeWord("Your trip parameters recap"));
			subtitleLabel.setValue("If it's ok, click on finish, eitherway go back or cancel");
			departureLabel.setCaptionAsHtml(true);
			departureLabel.setCaption(BvStringUtils.bvColorizeWord("Chosen departure address"));
			departureLabel.setValue(searchTransfer.getDep_addr());
			arrivalLabel.setCaptionAsHtml(true);
			arrivalLabel.setCaption(BvStringUtils.bvColorizeWord("Chosen arrival address"));
			arrivalLabel.setValue(searchTransfer.getArr_addr());
			timeLabel.setCaptionAsHtml(true);
			timeLabel.setCaption(BvStringUtils.bvColorizeWord("Chosen departure time"));
			Date depTime = new Date(searchTransfer.getDep_time());
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("dd-MM-yy HH.mm");
			String depString = sdf.format(depTime);
			timeLabel.setValue(depString);
			needsLabel.setCaptionAsHtml(true);
			needsLabel.setCaption(BvStringUtils.bvColorizeWord("Your selected special needs"));
			needsLabel.setContentMode(ContentMode.HTML);
			needsLabel.addStyleName(ValoTheme.LABEL_HUGE);
			String specialNeeds = new String();
			if(searchTransfer.isAnimal()) specialNeeds=specialNeeds+"Animals, ";
			if(searchTransfer.isHandicap()) specialNeeds=specialNeeds+"Disabilities, ";
			if(searchTransfer.isLuggage()) specialNeeds=specialNeeds+"Luggage, ";
			if(searchTransfer.isSmoke()) specialNeeds = specialNeeds+"Smoker";
			needsLabel.setValue(specialNeeds);
			conclusionLabel.addStyleName(ValoTheme.LABEL_BOLD);
			conclusionLabel.addStyleName("wrapLine");
			conclusionLabel.setValue("Notice: until a suitable pool is found, a full travel solution with private car may be shown in the main page");
			
			
		}
}
