import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGenAlgoUtil;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.environment.nqueens.NQueensBoard.Config;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;


public class NQueensBenchmark {
	private static LinkedList<TestResult> results = new LinkedList<TestResult>();
	


	public static void main(String[] args) {

		testAll(1,10);		
			
	}
	
	private static void testAll(int runs, int iterations)
		{
		/*Depth first search
		 * Parameters are: testSIA(boardSize,runs,iterationsPerRun)
		 * */
		 testDFS(8,runs,iterations);
		 testDFS(10,runs,iterations);
		 testDFS(12,runs,iterations);
		
		/*Depth limited search
		 * Parameters are: testSIA(boardSize,runs,iterationsPerRun)
		 * */
		 testDLS(8,runs,iterations);
		 testDLS(10,runs,iterations);
		 testDLS(12,runs,iterations);
		
		/*breath first search
		 * Parameters are: testBFS(boardSize,runs,iterationsPerRun)
		 * */
		 testBFS(8,runs,iterations);
		 testBFS(10,runs,iterations);
		 testBFS(12,runs,iterations);
		
		/*Iterative deepening search
		 * Parameters are: testIDS(boardSize,runs,iterationsPerRun)
		 * */
		 testIDS(8,runs,iterations);
		 testIDS(10,runs,iterations);
		 testIDS(12,runs,iterations);
		
		/*Simualted annealing
		 * Parameters are: testSIA(boardSize,runs,iterationsPerRun,nodeLimit)
		 * */
		 testSIA(8,runs,iterations,200);
		 testSIA(10,runs,iterations,200);
		 testSIA(12,runs,iterations,200);
		
		
		/*Hill Climbing
		 * Parameters are: testHCS(boardSize,runs,iterationsPerRun)
		 * */
		 testHCS(8,runs,iterations);
		 testHCS(10,runs,iterations);
		 testHCS(12,runs,iterations);
		
		/*Genetic Algorithm
		 * Parameters are: testGAS(boardSize,runs,iterationsPerRun,timeLimit,PoulationDimension)
		 * */
		testGAS(8,runs,iterations,200,50);
		testGAS(10,runs,iterations,200,50);
		testGAS(12,runs,iterations,200,50);
		
		System.out.println(results.size()+" mannaggia dio serpente");
		Iterator<TestResult> iter = results.iterator();
		while(iter.hasNext())
			{
			System.out.println(iter.next().toString());
			}
		}
	
	private static void testDFS(int boardSize,int runs,int iterations)
		{
		LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
		float totalRunTime=0;
		float totalRunNodes=0;
		for(int i=0;i<runs;i++){
		float totalTime=0;
		float totalNodes=0;
		for(int j=0;j<iterations;j++)
			{
			long startTime = System.currentTimeMillis();
			
			//System.out.println("\nNQueensDemo DFS -->");
			try {
				Problem problem = new Problem(new NQueensBoard(boardSize), NQueensFunctionFactory.getIActionsFunction(),
						NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				Search search = new DepthFirstSearch(new GraphSearch());
				SearchAgent agent = new SearchAgent(problem, search);
				//printActions(agent.getActions());
				Properties prop =agent.getInstrumentation();
				float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
				totalNodes=totalNodes+nodes;
				outcomeList.add(new Boolean(true));
				//System.out.println("portanna "+prop.getProperty("nodesExpanded"));
				//printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis();
			float duration = (endTime - startTime);
			totalTime=totalTime+duration;
			//System.out.println("Completed iteration: "+i);
			}
		
		float mean = totalTime/iterations;
		float meanNodes= totalNodes/iterations;
		System.out.println("DFS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
		totalRunTime=totalRunTime+mean;
		totalRunNodes=totalRunNodes+meanNodes;
		}
		float execMeanTime=totalRunTime/runs;
		float execMeanNodes=totalRunNodes/runs;
		String message = "\nThis is a test for DFS of Nqueens problem with\n"
					   + "board size of "+boardSize+". The results are evaluated\n"
					   + "over "+iterations+" iterations per runs, over "+runs+""
					   + " runs.\n\n";
		System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
				+ "mean expanded nodes: "+execMeanNodes);
		TestResult result = new TestResult("DFS", boardSize, execMeanTime, execMeanNodes,outcomeList);
		results.add(result);
		
		
	}
	
	private static void testDLS(int boardSize,int runs,int iterations)
		{
		LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
		float totalRunTime=0;
		float totalRunNodes=0;
		for(int i=0;i<runs;i++){
		float totalTime=0;
		float totalNodes=0;
		for(int j=0;j<iterations;j++)
			{
			long startTime = System.currentTimeMillis();
			
			try {
				Problem problem = new Problem(new NQueensBoard(boardSize), NQueensFunctionFactory.getIActionsFunction(),
						NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				Search search = new DepthLimitedSearch(boardSize);
				SearchAgent agent = new SearchAgent(problem, search);
				//printActions(agent.getActions());
				Properties prop =agent.getInstrumentation();
				float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
				totalNodes=totalNodes+nodes;
				outcomeList.add(new Boolean(true));
				//printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis();
			float duration = (endTime - startTime);
			totalTime=totalTime+duration;
			//System.out.println("Completed iteration: "+i);
			}
		
		float mean = totalTime/iterations;
		float meanNodes= totalNodes/iterations;
		System.out.println("DLS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
		totalRunTime=totalRunTime+mean;
		totalRunNodes=totalRunNodes+meanNodes;
		}
		float execMeanTime=totalRunTime/runs;
		float execMeanNodes=totalRunNodes/runs;
		String message = "\nThis is a test for DLS of Nqueens problem with\n"
					   + "board size of "+boardSize+". The results are evaluated\n"
					   + "over "+iterations+" iterations per runs, over "+runs+""
					   + " runs.\n\n";
		System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
				+ "mean expanded nodes: "+execMeanNodes);
		TestResult result = new TestResult("DLS", boardSize, execMeanTime, execMeanNodes,outcomeList);
		results.add(result);
		}
	
	private static void testBFS(int boardSize,int runs,int iterations)
	{
	float totalRunTime=0;
	float totalRunNodes=0;
	LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
	for(int i=0;i<runs;i++){
	float totalTime=0;
	float totalNodes=0;
	for(int j=0;j<iterations;j++)
		{
		long startTime = System.currentTimeMillis();
		
		try {
			Problem problem = new Problem(new NQueensBoard(boardSize), NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
			Search search = new BreadthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			//printActions(agent.getActions());
			Properties prop =agent.getInstrumentation();
			float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
			totalNodes=totalNodes+nodes;
			outcomeList.add(new Boolean(true));
			//printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		float duration = (endTime - startTime);
		totalTime=totalTime+duration;
		System.out.println("BFS Completed iteration: "+j+" of run number "+i);
		}
	
	float mean = totalTime/iterations;
	float meanNodes= totalNodes/iterations;
	System.out.println("BFS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
	totalRunTime=totalRunTime+mean;
	totalRunNodes=totalRunNodes+meanNodes;
	}
	float execMeanTime=totalRunTime/runs;
	float execMeanNodes=totalRunNodes/runs;
	String message = "\nThis is a test for BFS of Nqueens problem with\n"
				   + "board size of "+boardSize+". The results are evaluated\n"
				   + "over "+iterations+" iterations per runs, over "+runs+""
				   + " runs.\n\n";
	System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
			+ "mean expanded nodes: "+execMeanNodes);
	TestResult result = new TestResult("BFS", boardSize, execMeanTime, execMeanNodes,outcomeList);
	results.add(result);
	}
	
	private static void testIDS(int boardSize,int runs,int iterations)
		{
		LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
		float totalRunTime=0;
		float totalRunNodes=0;
		for(int i=0;i<runs;i++){
		float totalTime=0;
		float totalNodes=0;
		for(int j=0;j<iterations;j++)
			{
			long startTime = System.currentTimeMillis();
			
			try {
				Problem problem = new Problem(new NQueensBoard(boardSize), NQueensFunctionFactory.getIActionsFunction(),
						NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				Search search = new IterativeDeepeningSearch();
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				Properties prop =agent.getInstrumentation();
				float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
				totalNodes=totalNodes+nodes;
				outcomeList.add(new Boolean(true));
				printInstrumentation(agent.getInstrumentation());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis();
			float duration = (endTime - startTime);
			totalTime=totalTime+duration;
			System.out.println("IDS Completed iteration: "+j+" of run number "+i);
			}
		
		float mean = totalTime/iterations;
		float meanNodes= totalNodes/iterations;
		System.out.println("IDS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
		totalRunTime=totalRunTime+mean;
		totalRunNodes=totalRunNodes+meanNodes;
		}
		float execMeanTime=totalRunTime/runs;
		float execMeanNodes=totalRunNodes/runs;
		String message = "\nThis is a test for IDS of Nqueens problem with\n"
					   + "board size of "+boardSize+". The results are evaluated\n"
					   + "over "+iterations+" iterations per runs, over "+runs+""
					   + " runs.\n\n";
		System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
				+ "mean expanded nodes: "+execMeanNodes);
		TestResult result = new TestResult("IDS", boardSize, execMeanTime, execMeanNodes,outcomeList);
		results.add(result);
		
		}

	private static void testSIA(int boardSize,int runs,int iterations, int nodeLimit)
		{
		LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
		float totalRunTime=0;
		float totalRunNodes=0;
		for(int i=0;i<runs;i++){
		float totalTime=0;
		float totalNodes=0;
		for(int j=0;j<iterations;j++)
			{
			long startTime = System.currentTimeMillis();
			
			try {
				Problem problem = new Problem(new NQueensBoard(boardSize, Config.QUEENS_IN_FIRST_ROW),
						NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(new AttackingPairsHeuristic(),
						new Scheduler(20, 0.045, nodeLimit));
				SearchAgent agent = new SearchAgent(problem, search);
				//printActions(agent.getActions());
				Properties prop =agent.getInstrumentation();
				float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
				totalNodes=totalNodes+nodes;
				//printInstrumentation(agent.getInstrumentation());
				System.out.println("Search Outcome=" + search.getOutcome());
				String outcome = search.getOutcome().toString();
				if(outcome.equals("FAILURE"))
					{
					outcomeList.add(new Boolean(false));
					}
				else outcomeList.add(new Boolean(true));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			long endTime = System.currentTimeMillis();
			float duration = (endTime - startTime);
			totalTime=totalTime+duration;
			System.out.println("SIA Completed iteration: "+j+" of run number "+i);
			}
		
		float mean = totalTime/iterations;
		float meanNodes= totalNodes/iterations;
		System.out.println("SIA required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
		totalRunTime=totalRunTime+mean;
		totalRunNodes=totalRunNodes+meanNodes;
		}
		float execMeanTime=totalRunTime/runs;
		float execMeanNodes=totalRunNodes/runs;
		String message = "\nThis is a test for SIA of Nqueens problem with\n"
					   + "board size of "+boardSize+". The results are evaluated\n"
					   + "over "+iterations+" iterations per runs, over "+runs+""
					   + " runs.\n\n";
		System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
				+ "mean expanded nodes: "+execMeanNodes);
		TestResult result = new TestResult("SIA", boardSize, execMeanTime, execMeanNodes,outcomeList);
		results.add(result);
		
		}
	
	private static void testHCS(int boardSize,int runs,int iterations)
	{
	LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
	float totalRunTime=0;
	float totalRunNodes=0;
	for(int i=0;i<runs;i++){
	float totalTime=0;
	float totalNodes=0;
	for(int j=0;j<iterations;j++)
		{
		long startTime = System.currentTimeMillis();
		
		try {
			Problem problem = new Problem(new NQueensBoard(boardSize, Config.QUEENS_IN_FIRST_ROW),
					NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			HillClimbingSearch search = new HillClimbingSearch(new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);
			//printActions(agent.getActions());
			Properties prop =agent.getInstrumentation();
			float nodes = Long.parseLong(prop.getProperty("nodesExpanded"));
			totalNodes=totalNodes+nodes;
			//printInstrumentation(agent.getInstrumentation());
			System.out.println("Search Outcome=" + search.getOutcome());
			String outcome = search.getOutcome().toString();
			System.out.println("Final State=\n" + search.getLastSearchState());
			if(outcome.equals("FAILURE"))
				{
				outcomeList.add(new Boolean(false));
				}
			else outcomeList.add(new Boolean(true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		float duration = (endTime - startTime);
		totalTime=totalTime+duration;
		System.out.println("HCS Completed iteration: "+j+" of run number "+i);
		}
	
	float mean = totalTime/iterations;
	float meanNodes= totalNodes/iterations;
	System.out.println("HCS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
	totalRunTime=totalRunTime+mean;
	totalRunNodes=totalRunNodes+meanNodes;
	}
	float execMeanTime=totalRunTime/runs;
	float execMeanNodes=totalRunNodes/runs;
	String message = "\nThis is a test for HCS of Nqueens problem with\n"
				   + "board size of "+boardSize+". The results are evaluated\n"
				   + "over "+iterations+" iterations per runs, over "+runs+""
				   + " runs.\n\n";
	System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
			+ "mean expanded nodes: "+execMeanNodes);
	TestResult result = new TestResult("HCS", boardSize, execMeanTime, execMeanNodes,outcomeList);
	results.add(result);
	
	}
	
	private static void testGAS(int boardSize,int runs,int iterations, long timeLimit, int popSize)
	{
	LinkedList<Boolean> outcomeList = new LinkedList<Boolean>();
	float totalRunTime=0;
	float totalRunNodes=0;
	for(int i=0;i<runs;i++){
	float totalTime=0;
	float totalNodes=0;
	for(int j=0;j<iterations;j++)
		{
		long startTime = System.currentTimeMillis();
		
		try {
			FitnessFunction<Integer> fitnessFunction = NQueensGenAlgoUtil.getFitnessFunction();
			GoalTest goalTest = NQueensGenAlgoUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int k = 0; k < popSize; k++) {
				population.add(NQueensGenAlgoUtil.generateRandomIndividual(boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(boardSize,
					NQueensGenAlgoUtil.getFiniteAlphabetForBoardOfSize(boardSize), 0.15);
			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, timeLimit);

			System.out.println("Max Time"+timeLimit+" milliseconds Best Individual=\n"
					+ NQueensGenAlgoUtil.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + boardSize);
			System.out.println("# Board Layouts = " + (new BigDecimal(boardSize)).pow(boardSize));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
			float nodes = new Long(ga.getPopulationSize());
			totalNodes=totalNodes+nodes;
			//printInstrumentation(agent.getInstrumentation());
			//System.out.println("Search Outcome=" + search.getOutcome());
			//String outcome = search.getOutcome().toString();
			//System.out.println("Final State=\n" + search.getLastSearchState());
			/*if(outcome.equals("FAILURE"))
				{
				outcomeList.add(new Boolean(false));
				}
			else outcomeList.add(new Boolean(true));*/
			outcomeList.add(goalTest.isGoalState(bestIndividual));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		float duration = (endTime - startTime);
		totalTime=totalTime+duration;
		System.out.println("GAS Completed iteration: "+j+" of run number "+i);
		}
	
	float mean = totalTime/iterations;
	float meanNodes= totalNodes/iterations;
	System.out.println("GAS required: "+mean+" milliseconds, and expanded "+meanNodes+" nodes");
	totalRunTime=totalRunTime+mean;
	totalRunNodes=totalRunNodes+meanNodes;
	}
	float execMeanTime=totalRunTime/runs;
	float execMeanNodes=totalRunNodes/runs;
	String message = "\nThis is a test for HCS of Nqueens problem with\n"
				   + "board size of "+boardSize+". The results are evaluated\n"
				   + "over "+iterations+" iterations per runs, over "+runs+""
				   + " runs.\n\n";
	System.out.println(message+"mean execution time: "+execMeanTime+" milliseconds.\n"
			+ "mean expanded nodes: "+execMeanNodes);
	TestResult result = new TestResult("GAS", boardSize, execMeanTime, execMeanNodes,outcomeList);
	results.add(result);
	
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
}
