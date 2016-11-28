package com.bonvojage.carpooling;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
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
        test.setUserName("antani");
        test.clearUserHistory();
        test.addUserHistoryItem("antani","perdue");
        //test.setTitle("titolo titolo");
        /*Button button = new Button("Click Me");
        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });*/
        
        //layout.addComponents(name, button,test);
        layout.addComponent(test);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "CarpoolingUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CarpoolingUI.class, productionMode = false)
    public static class CarpoolingUIServlet extends VaadinServlet {
    }
}
