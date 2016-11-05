

import aima.core.search.framework.HeuristicFunction;

public class ManattanHeuristic implements HeuristicFunction{

	@Override
	public double h(Object state) {
		Environment currEnv = (Environment) state;
		Position robotPosition = currEnv.getRobotPosition();
		Position goalPosition = currEnv.getGoalPosition();
		Position startPosition = currEnv.getStartPosition();
		//return Math.abs(robotPosition.x() - goalPosition.x()) +Math.abs(robotPosition.y() - goalPosition.y());
		
		//Prefer Straight lines heuristics
		int dx1=robotPosition.x()-goalPosition.x();
		int dy1=robotPosition.y()-goalPosition.y();
		int dx2=startPosition.x()-goalPosition.x();
		int dy2=startPosition.y()-goalPosition.y();
		double cross =Math.abs(dx1*dy2-dx2*dy1);
		return cross*0.001;
	}

}
