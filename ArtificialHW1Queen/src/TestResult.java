
import java.util.Iterator;
import java.util.LinkedList;

public class TestResult {
	private String algoName;
	private int boardSize;
	private float execTime;
	private float nodXpand;
	private LinkedList<Boolean> goalReached=new LinkedList<Boolean>();
	
	public TestResult(String algoName, int boardSize, float execTime, float nodXpand,LinkedList<Boolean> goalReached) {
		super();
		this.algoName = algoName;
		this.boardSize = boardSize;
		this.execTime = execTime;
		this.nodXpand = nodXpand;
		this.goalReached = goalReached;
	}

	public String getAlgoName() {
		return algoName;
	}

	public void setAlgoName(String algoName) {
		this.algoName = algoName;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public float getExecTime() {
		return execTime;
	}

	public void setExecTime(float execTime) {
		this.execTime = execTime;
	}

	public float getNodXpand() {
		return nodXpand;
	}

	public void setNodXpand(float nodXpand) {
		this.nodXpand = nodXpand;
	}

	public LinkedList<Boolean> getGoalReached() {
		return goalReached;
	}

	public void setGoalReached(LinkedList<Boolean> goalReached) {
		this.goalReached = goalReached;
	}
	
	public float getSuccessRate()
	{
			float totalIterations = goalReached.size();
			float successes=0;
			Iterator<Boolean> iter = goalReached.iterator();
			while(iter.hasNext())
				{
				 boolean outcome = iter.next().booleanValue();
				 if(outcome==true) successes++;
				}
			return (successes*100)/totalIterations;
			
	}
	public int getSuccesses()
		{
		int successes=0;
		Iterator<Boolean> iter = goalReached.iterator();
		while(iter.hasNext())
			{
			if(iter.next().booleanValue()==true) successes++;
			}
		return successes;
		}

	@Override
	public String toString() {
		float totalIterations = goalReached.size();
		float successes=this.getSuccesses();
		float succRate = this.getSuccessRate();
		
		return "TestResult [algoName=" + algoName + ", boardSize=" + boardSize + ", execTime=" + execTime
				+ ", nodXpand=" + nodXpand + ", solution founded " +successes+" times over "+totalIterations+" total iterations ("+succRate+"% success rate" + "]";
	}
	
	
}