package com.bonvojage.carpooling;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("bvtheme")
public class CarpoolingUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        
       // final TextField name = new TextField();
        //name.setCaption("Type your name here:");
        WelcomeView test = new WelcomeView(this);
        test.setUserName("This guy");
        test.addStyleName("set_background");
        layout.addComponent(test);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addStyleName("set_background");
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "CarpoolingUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CarpoolingUI.class, productionMode = false)
    public static class CarpoolingUIServlet extends VaadinServlet {
    }
}
