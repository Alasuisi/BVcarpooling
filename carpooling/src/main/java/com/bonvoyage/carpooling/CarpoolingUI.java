package com.bonvoyage.carpooling;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.bonvoyage.domain.User;
import com.bonvoyage.persistance.UserDAO;
import com.bonvoyage.utils.DaoException;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
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
        System.out.println(vaadinRequest.getParameter("key"));
        String user = vaadinRequest.getParameter("key");
        if(user!=null) 
        	{
        		//test.setUserName(user);
        		//UI.getCurrent().getSession().setAttribute("userid", vaadinRequest.getParameter("key"));
        		//Integer key=Integer.parseInt((String)UI.getCurrent().getSession().getAttribute("userid"));
				User loggedUser;
				try {
					loggedUser = UserDAO.load(Integer.parseInt(user));
					test.setUserName(new Integer(loggedUser.getUserID()).toString());
					UI.getCurrent().getSession().setAttribute(User.class, loggedUser);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
        	}
        setContent(layout);
        VaadinSession.getCurrent().addRequestHandler(new RequestHandler(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 5882813902895784983L;

			@Override
			public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
					throws IOException {
				if ("/rhexample".equals(request.getPathInfo())) {
		            // Generate a plain text document
		            response.setContentType("text/plain");
		            response.getWriter().append(
		               "Here's some dynamically generated content.\n");
		            response.getWriter().format(Locale.ENGLISH,
		               "Time: %Tc\n", new Date());

		            // Use shared session data
		            response.getWriter().format("Session data: %s\n",
		                session.getAttribute("mydata"));

		            return true; // We wrote a response
		        } else
		            return false; // No response was written
			}});
    }
    
    

    @WebServlet(urlPatterns = "/*", name = "CarpoolingUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CarpoolingUI.class, productionMode = false)
    public static class CarpoolingUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6850718208347968448L;
    }
}
