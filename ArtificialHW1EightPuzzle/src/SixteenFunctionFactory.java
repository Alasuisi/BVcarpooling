
import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
//import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory.EPActionsFunction;
//import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory.EPResultFunction;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;

public class SixteenFunctionFactory {

	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			SixteenPuzzleBoard board = (SixteenPuzzleBoard) state;
			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(SixteenPuzzleBoard.UP)) {
				actions.add(SixteenPuzzleBoard.UP);
			}
			if (board.canMoveGap(SixteenPuzzleBoard.DOWN)) {
				actions.add(SixteenPuzzleBoard.DOWN);
			}
			if (board.canMoveGap(SixteenPuzzleBoard.LEFT)) {
				actions.add(SixteenPuzzleBoard.LEFT);
			}
			if (board.canMoveGap(SixteenPuzzleBoard.RIGHT)) {
				actions.add(SixteenPuzzleBoard.RIGHT);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			SixteenPuzzleBoard board = (SixteenPuzzleBoard) s;

			if (SixteenPuzzleBoard.UP.equals(a)
					&& board.canMoveGap(SixteenPuzzleBoard.UP)) {
				SixteenPuzzleBoard newBoard = new SixteenPuzzleBoard(board);
				newBoard.moveGapUp();
				return newBoard;
			} else if (SixteenPuzzleBoard.DOWN.equals(a)
					&& board.canMoveGap(SixteenPuzzleBoard.DOWN)) {
				SixteenPuzzleBoard newBoard = new SixteenPuzzleBoard(board);
				newBoard.moveGapDown();
				return newBoard;
			} else if (SixteenPuzzleBoard.LEFT.equals(a)
					&& board.canMoveGap(SixteenPuzzleBoard.LEFT)) {
				SixteenPuzzleBoard newBoard = new SixteenPuzzleBoard(board);
				newBoard.moveGapLeft();
				return newBoard;
			} else if (SixteenPuzzleBoard.RIGHT.equals(a)
					&& board.canMoveGap(SixteenPuzzleBoard.RIGHT)) {
				SixteenPuzzleBoard newBoard = new SixteenPuzzleBoard(board);
				newBoard.moveGapRight();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}
