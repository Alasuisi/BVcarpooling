

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
//import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.problem.ActionsFunction;

public class ActionFunction implements ActionsFunction{

	//List of all Action
	public static Action RIGHT = new DynamicAction("Right");
	public static Action LEFT = new DynamicAction("Left");
	public static Action UP = new DynamicAction("Up");
	public static Action DOWN = new DynamicAction("Down");
	
	
	@Override
	public Set<Action> actions(Object s) {
		
		//Set of possible Action
		Set<Action> possibleActions = new LinkedHashSet<Action>();
		//Current Environent State
		Environment currentEnv = (Environment) s;
		//Robot Position in the current Environment
		Position robotPosition = currentEnv.getRobotPosition();
		//Set of all the walls in the room
		HashSet<Position> walls = currentEnv.getWalls();
		//Iterator on walls
		Iterator<Position> it = walls.iterator();
		
		//Local Flag
		boolean noLeft = false;
		boolean noRight = false;
		boolean noUp = false;
		boolean noDown = false;
		
		
		
		while(it.hasNext()){
			Position wallPosition = it.next();
			int wallXPos = wallPosition.x();
			int wallYPos = wallPosition.y();
			
			if((robotPosition.x() + 1 == Environment.getMaxX()) ||( (robotPosition.x()+1 == wallXPos) && (robotPosition.y() == wallYPos))){
				
				noRight = true;
			}	
			if((robotPosition.x() - 1 == -1) ||( (robotPosition.x()-1 == wallXPos) && (robotPosition.y() == wallYPos))){
				
				noLeft = true;
			}
			if((robotPosition.y() + 1 == Environment.getMaxY()) ||( (robotPosition.x() == wallXPos) && (robotPosition.y()+1 == wallYPos))){
				
				noUp = true;
			}
			if((robotPosition.y() - 1 == -1) ||( (robotPosition.x() == wallXPos) && (robotPosition.y()-1 == wallYPos))){
				
				noDown = true;
			}
			
		}
		
		if(!noLeft)
			possibleActions.add(LEFT);
		if(!noRight)
			possibleActions.add(RIGHT);
		if(!noUp)
			possibleActions.add(UP);
		if(!noDown)
			possibleActions.add(DOWN);
		
		return possibleActions;
	}

}
