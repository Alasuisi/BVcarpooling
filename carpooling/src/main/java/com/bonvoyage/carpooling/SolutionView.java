package com.bonvoyage.carpooling;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import com.bonvoyage.domain.McsaSegment;
import com.bonvoyage.domain.McsaSolution;
import com.bonvoyage.utils.DaoException;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.RowReference;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SolutionView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8161596592089809122L;
	private Grid grid;
	private VerticalLayout needsLayout=new VerticalLayout();
	private Window parent;
	private WelcomeView parentView;
	public SolutionView(WelcomeView parentView,Window parent,LinkedList<McsaSolution> solList,int userid,int tranId)
		{
		 this.setSizeFull();
		 this.setImmediate(true);
		 this.setSpacing(true);
		 this.parent=parent;
		 this.parentView=parentView;
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
		 grid.addColumn("solutionID",Integer.class);
		 HashMap<Integer,LinkedList<McsaSegment>> solDetail = new HashMap<Integer,LinkedList<McsaSegment>>();
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
			 Integer solid=new Integer(thisSol.getSolutionID());
			 grid.addRow(changes,seats,arrival,waitTime,tripTime,grantString,solid);
			 solDetail.put(new Integer(thisSol.getSolutionID()), thisSol.getSolution());
		 	}
		 this.addComponent(grid);
		 grid.setColumns("Changes N°","Needed Seats","Arrival Time","Waiting Time","Trip duration","Granted Needs");
		 grid.setSizeFull();
		 grid.addItemClickListener(new ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -8385306399163517728L;

			@Override
			public void itemClick(ItemClickEvent event) {
				Item item = event.getItem();
				Integer solid =(Integer) item.getItemProperty("solutionID").getValue();
				//String test=item.getItemProperty("solutionID");
				//String test =event.getItem().toString();
				System.out.println(solid);
				Window detailWindow = new Window();
				detailWindow.center();
				detailWindow.setImmediate(true);
				detailWindow.addStyleName("animated");
				detailWindow.addStyleName("pulse");
				detailWindow.setWidth("1200px");
				detailWindow.setHeight("800px");
				detailWindow.setModal(true);
				//Label test = new Label(solDetail.get(solid).toString());
				detailWindow.setContent(new SolutionDetailView(parentView,detailWindow,solDetail.get(solid),userid,tranId,solid.intValue(),false,false));
				detailWindow.addCloseListener(new Window.CloseListener() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1179148939572272077L;

					@Override
					public void windowClose(CloseEvent e) {
						parent.close();
						try {
							parentView.updateTransfersView();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (DaoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				System.out.println("solution id "+solid);
				UI.getCurrent().addWindow(detailWindow);
				
			}
		});
		}
	
	private String timeToString(long timeMillis)
		{
		 double seconds = Math.floor((timeMillis/1000));
		 int hours = (int) (seconds/3600);
		 int minutes = (int) ((seconds-(hours*3600))/60);
		 
		 return new String(hours+":"+minutes);
		}
}
