package com.bonvoyage.carpooling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import com.bonvoyage.domain.McsaSolution;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SolutionView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8161596592089809122L;
	private Grid grid;
	private VerticalLayout needsLayout=new VerticalLayout();

	public SolutionView(LinkedList<McsaSolution> solList)
		{
		 this.setSizeFull();
		 this.setImmediate(true);
		 this.setSpacing(true);
		 //BeanItemContainer<McsaSolution> itemContainer =  new BeanItemContainer<McsaSolution>(McsaSolution.class, solList);
		 //itemContainer.addNestedContainerBean("solution");
		 //solutionGrid = new Grid("Computed solutions",itemContainer);
		 grid = new Grid();
		 grid.addColumn("Changes N°", Integer.class);
		 grid.addColumn("Needed Seats", Integer.class);
		 grid.addColumn("Arrival Time",Date.class);
		 grid.addColumn("Waiting Time", String.class);
		 grid.addColumn("Trip duration", String.class);
		 grid.addColumn("Granted Needs", String.class);
		 Iterator<McsaSolution> soliter = solList.iterator();
		 while(soliter.hasNext())
		 	{
			 McsaSolution thisSol=soliter.next();
			 Integer changes = new Integer(thisSol.getChanges());
			 Integer seats = new Integer(thisSol.getNeededSeats());
			 Date arrival = new Date(thisSol.getArrivalTime());
			 //String waitTime = String.format("%1$tH:%1$tM:%1$tS", thisSol.getTotalWaitTime());
			 //String tripTime = String.format("%1$tH:%1$tM:%1$tS", thisSol.getTotalTripTime());
			 String waitTime = timeToString(thisSol.getTotalWaitTime());
			 String tripTime = timeToString(thisSol.getTotalTripTime());
			 String grantString = new String();
			 if(thisSol.isAnimal()) grantString=grantString+"Animal ";
			 if(thisSol.isHandicap()) grantString=grantString+"Handicap ";
			 if(thisSol.isLuggage()) grantString=grantString+"Luggage ";
			 if(thisSol.isSmoke()) grantString=grantString+"Smoke";
			 grid.addRow(changes,seats,arrival,waitTime,tripTime,grantString);
		 	}
		 this.addComponent(grid);
		 grid.setSizeFull();
		}
	private String timeToString(long timeMillis)
		{
		 double seconds = Math.floor((timeMillis/1000));
		 int hours = (int) (seconds/3600);
		 int minutes = (int) ((seconds-(hours*3600))/60);
		 
		 return new String(hours+":"+minutes);
		}
}
