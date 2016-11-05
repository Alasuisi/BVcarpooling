import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;



public class EightPuzzleBenchmark {
	
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });
	
	
	static SixteenPuzzleBoard hard = new SixteenPuzzleBoard(new int[] {6,8,3,0,1,9,2,13,15,14,12,11,4,7,5,10});
	static SixteenPuzzleBoard easy = new SixteenPuzzleBoard(new int[] {1,2,7,6,0,11,5,3,4,12,13,15,10,8,9,14});
	public static void main(String[] args )
		{
		 aStarMH(easy);
		 aStarMIS(easy);
		 
		 aStarMH(hard);
		//System.out.println(0%4);
		 //randomState(15000);
		
		}
	
		private static void aStarMH(SixteenPuzzleBoard initState) {
		System.out.println("\nEightPuzzleDemo AStar Search (ManhattanDistance)-->");
		try {
			Problem problem = new Problem(initState, SixteenFunctionFactory
					.getActionsFunction(), SixteenFunctionFactory
					.getResultFunction(), new SixteenPuzzleGoalTest());
			Search search = new AStarSearch(new GraphSearch(),
					new Manhattan16Function());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
		private static void aStarMIS(SixteenPuzzleBoard initState) {
			System.out.println("\nEightPuzzleDemo AStar Search (Misplaced Tiles)-->");
			try {
				Problem problem = new Problem(initState, SixteenFunctionFactory
						.getActionsFunction(), SixteenFunctionFactory
						.getResultFunction(), new SixteenPuzzleGoalTest());
				Search search = new AStarSearch(new GraphSearch(),
						new MisplacedTiles16());
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	
	private static void eightPuzzleAStarManhattanDemo() {
		System.out
				.println("\nEightPuzzleDemo AStar Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(random1, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = new AStarSearch(new GraphSearch(),
					new ManhattanHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
	
	private static SixteenPuzzleBoard randomState(long time)
		{
		SixteenPuzzleBoard board = new SixteenPuzzleBoard(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
		Random r = new Random(System.currentTimeMillis());
		
		long now= System.currentTimeMillis();
		long end = now+time;
		System.out.println("Altering the state for "+time+" milliseconds");
		while(System.currentTimeMillis() < end) {
			switch (r.nextInt(4)) {
			case 0: board.moveGapUp(); break;
			case 1: board.moveGapDown(); break;
			case 2: board.moveGapLeft(); break;
			case 3: board.moveGapRight(); break;
			}
		}
		System.out.println(board);
		
		return board;

	}
}
