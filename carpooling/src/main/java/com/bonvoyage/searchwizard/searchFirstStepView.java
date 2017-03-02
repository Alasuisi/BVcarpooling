package com.bonvoyage.searchwizard;

import java.awt.geom.Point2D;
import java.util.Date;
import java.util.List;

import com.bonvoyage.carpooling.CarpoolingUI;
import com.bonvoyage.domain.Transfer;
import com.bonvoyage.domain.UserProfile;
import com.bonvoyage.utils.BvStringUtils;
import com.bonvoyage.utils.GeocodingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
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
	private UserProfile loggedUser = (UserProfile) UI.getCurrent().getSession().getAttribute(UserProfile.class);
	private Transfer searchTran = new Transfer();
	
	public searchFirstStepView(CarpoolingUI ui)
		{
		///View properties///
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
		//this.addStyleName("search_background");
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
		arrivalField.setRequired(true);
		departureTime.setCaptionAsHtml(true);
		departureTime.setCaption(BvStringUtils.bvColorizeWord("Departure time:"));
		departureTime.setRequired(true);
		departureTime.setResolution(Resolution.MINUTE);
		DateRangeValidator depTimeVal = new DateRangeValidator("You cannot turn back time!", new Date(System.currentTimeMillis()), null, Resolution.MINUTE);
		departureTime.addValidator(depTimeVal);
		departureTime.setValue(new Date(System.currentTimeMillis()));
		
		//this.setExpandRatio(departureTime, 100);
		this.setExpandRatio(departureTime, 100);
		
		
		///Data Properties///
		
		
		try {
			searchTran.setUser_role("passenger");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchTran.setUser_id(loggedUser.getUserID());
		searchTran.setProf_id(loggedUser.getProfileID());
		UI.getCurrent().getSession().setAttribute("search_tran", searchTran);
		
		
		
		}
	protected boolean isValid()
		{
		return departureField.isValid()&&arrivalField.isValid()&&departureTime.isValid();
		}
	
	protected void updateTransfer() throws Exception
		{
		 if(isValid())
		 	{
			 List<LatLng> path = GeocodingUtils.toCoordinates(departureField.getValue(), arrivalField.getValue());
			 
			 searchTran.setPath(GeocodingUtils.toTimedPath(path));
			 LatLng source =path.get(0);
			 LatLng destination = path.get(path.size()-1);
			 searchTran.setDep_gps(new Point2D.Double(source.lat,source.lng));
			 searchTran.setArr_gps(new Point2D.Double(destination.lat, destination.lng));
			 searchTran.setDep_time(departureTime.getValue().getTime());
			 searchTran.setDep_addr(departureField.getValue());
			 searchTran.setArr_addr(arrivalField.getValue());
			 UI.getCurrent().getSession().setAttribute("search_tran",searchTran);
		 	}
		}
}
