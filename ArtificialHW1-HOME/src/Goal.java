import aima.core.search.framework.problem.GoalTest;

//import aima.core.search.framework.GoalTest;

public class Goal implements GoalTest {

	Position goal = Environment.getGoalPosition();
	@Override
	public boolean isGoalState(Object state) {
		Environment currEnv = (Environment) state;
		Position robotPosition = currEnv.getRobotPosition();
		return ((goal.x() == robotPosition.x()) &&(goal.y() == robotPosition.y()));
		

	}

}
