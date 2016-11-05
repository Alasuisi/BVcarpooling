

import aima.core.agent.Action;
//import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.problem.ResultFunction;

public class Result implements ResultFunction{

	@Override
	public Object result(Object s, Action a) {
		Environment currentEnv = (Environment) s;
		Position robotPosition = currentEnv.getRobotPosition();
		Position newRobotPosition = null;
		
		if(ActionFunction.UP.equals(a)){
			newRobotPosition = new Position(robotPosition.x(), robotPosition.y() +1);
		}
		if(ActionFunction.LEFT.equals(a)){
			newRobotPosition = new Position(robotPosition.x() -1, robotPosition.y());
		}
		if(ActionFunction.RIGHT.equals(a)){
			newRobotPosition = new Position(robotPosition.x() +1, robotPosition.y());
		}
		if(ActionFunction.DOWN.equals(a)){
			newRobotPosition = new Position(robotPosition.x(), robotPosition.y() -1);
		}
		return new Environment(newRobotPosition);
	}

}
