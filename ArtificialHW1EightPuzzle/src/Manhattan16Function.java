
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class Manhattan16Function implements HeuristicFunction {

	public double h(Object state) {
		SixteenPuzzleBoard board = (SixteenPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 16; i++) {
			XYLocation loc = board.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(i, loc);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(int i, XYLocation loc) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		switch (i) {

		case 1:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 1);
			break;
		case 2:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 2);
			break;
		case 3:
			retVal = Math.abs(xpos - 0) + Math.abs(ypos - 3);
			break;
		case 4:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 0);
			break;
		case 5:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 1);
			break;
		case 6:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 2);
			break;
		case 7:
			retVal = Math.abs(xpos - 1) + Math.abs(ypos - 3);
			break;
		case 8:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 0);
			break;
		case 9:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 1);
			break;
		case 10:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 2);
			break;
		case 11:
			retVal = Math.abs(xpos - 2) + Math.abs(ypos - 3);
			break;
		case 12:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 0);
			break;
		case 13:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 1);
			break;
		case 14:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 2);
			break;
		case 15:
			retVal = Math.abs(xpos - 3) + Math.abs(ypos - 3);
			break;

		}
		return retVal;
	}
	}
