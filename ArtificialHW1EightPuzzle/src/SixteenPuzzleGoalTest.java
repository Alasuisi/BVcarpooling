
import aima.core.search.framework.problem.GoalTest;

public class SixteenPuzzleGoalTest implements GoalTest {
	SixteenPuzzleBoard goal = new SixteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5,
			6, 7, 8 ,9,10,11,12,13,14,15});

	public boolean isGoalState(Object state) {
		SixteenPuzzleBoard board = (SixteenPuzzleBoard) state;
		return board.equals(goal);
	}
}
