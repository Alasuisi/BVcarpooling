


import java.util.HashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;

public class Environment extends AbstractEnvironment {
    private static int maxX;
    private static int maxY;
    private final Position robot;
    private static Position goal;
    private static Position start;
    private static final Set<Position> walls = new HashSet<>();

    public Environment(Position robot) {
        this.robot = robot;
        this.start=robot;
    }

	public static void setFinish(Position finish) {
		goal = finish;
		
	}

	public static void addWall(Position position) {
		walls.add(position);
		
	}
	
	public final Position getRobotPosition(){
		
		return this.robot;
		
	}
	public final Position getStartPosition()
		{
		return this.start;
		}
	
	public static Position getGoalPosition(){
		
		return goal;
		
	}
	
	public static final HashSet getWalls(){
		
		return (HashSet) walls;
	}
	
	public static int getMaxX(){
		
		return maxX;
	}
	
	public static int getMaxY(){
		
		return maxY;
	}



	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode(){
		
		return this.robot.x()*this.robot.y();
	}
	
	@Override
	public boolean equals(Object o){
		
		Environment newEnv = (Environment) o;
		return this.getRobotPosition().x() == newEnv.getRobotPosition().x() && this.getRobotPosition().y() == newEnv.getRobotPosition().y();
		
	}

	@Override
	public void executeAction(Agent agent, Action action) {
		// TODO Auto-generated method stub
		
	}
	
	
}
