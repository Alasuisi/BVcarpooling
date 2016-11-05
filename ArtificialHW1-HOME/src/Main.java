//test comment

import aima.core.agent.Action;
import aima.core.agent.Agent;
//import aima.core.search.framework.GraphSearch;
//import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
//import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
//import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
//import aima.core.search.uninformed.IterativeDeepeningSearch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.lang.Math;
import static java.lang.Math.pow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
//import java.util.Queue;
//import java.util.Random;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;




public class Main {
	
	protected static List<Search> searchAlgorithms = new ArrayList<Search>();
	protected static SearchAgent agent;

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception{
    	
        //Input file must be specified in args[1] "C:/Users/diego/Desktop/homeresult/map.txt"
        //BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
    	BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
        
        searchAlgorithms.add(new DepthFirstSearch(new GraphSearch()));
        searchAlgorithms.add(new AStarSearch(new GraphSearch(),new ManattanHeuristic()));
        LinkedList<Position>changes= new LinkedList<Position>();
        
        
        //In order to increment the resolution of the grid we use v as the exponent of the power of 2;
        // so if v = 0 then m = 2^0 = 1 and the grid resolution is 16x16
        // if v = 1 then m = 2^1 = 2 and the grid resolution is 32x32
        //and so on...
        
        
        int v = Integer.parseInt(args[2]); 
        //int v = 0;
        
        int m = (int) pow(2,v);
        System.out.println("M: " + m);
        
        //Read from the file and set the grid dimensions
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int maxX = Integer.parseInt(tokens.nextToken())*m;
        int maxY = Integer.parseInt(tokens.nextToken())*m;
        System.out.println("maxX: " + maxX + " maxY: " + maxY);
        Position.maxX = maxX;
        Position.maxY = maxY;
        
        //Store the matrix of integers that will be written in the .bmp file
        //and initialize it with white cells
        int[][] occupancy = new int[maxX][maxY];
        for(int i=0; i<maxX; i++)
            for(int j=0; j<maxY; j++)
                occupancy[i][j] = 16777215;
        
        
        //Read from the file the start and the goal position
        //and store them in the occupancy matrix
        tokens = new StringTokenizer(reader.readLine());
        Position robot = new Position(Integer.parseInt(tokens.nextToken())*m,Integer.parseInt(tokens.nextToken())*m);
        System.out.println("Robot");
        System.out.println("X: " + robot.x() + " Y: " + robot.y());
        occupancy[robot.y()][robot.x()] = 255;
        tokens = new StringTokenizer(reader.readLine());
        Position finish = new Position(Integer.parseInt(tokens.nextToken())*m,Integer.parseInt(tokens.nextToken())*m);
        System.out.println("Goal");
        System.out.println("X: " + finish.x() + " Y: " + finish.y());
        //occupancy[finish.y()][finish.x()] = 65280;
        occupancy[finish.y()][finish.x()] = new Color(255, 0, 0).getRGB();
        
        //Build the environment
        Environment init = new Environment(robot);
        Environment.setFinish(finish);
        int numWalls = Integer.parseInt(reader.readLine());
        for(int i=0; i < numWalls; i++) {
            tokens = new StringTokenizer(reader.readLine());
            Position temp = new Position(Integer.parseInt(tokens.nextToken())*m,Integer.parseInt(tokens.nextToken())*m);
            for(int x=0; x < m; x++)
                for(int y=0; y < m; y++) {
                    int newX = temp.x()+x;
                    int newY = temp.y()+y;
                    Environment.addWall(new Position(newX,newY));
                    occupancy[newY][newX] = 0x000000;
            }
        }

        
	//Store the occupancy matrix in a bitmap image
        String name =v+"occupancy.bmp";
        new BMP().saveBMP(args[1]+name,occupancy);
        //new BMP().saveBMP("C:/Users/diego/Desktop/diocane/img.bmp",occupancy);
        
        for(int k=0;k<searchAlgorithms.size();k++){
        //Use AIMA framework to solve the problem
        ActionFunction actFunc = new ActionFunction();
        Result resFunc = new Result();
        Goal goal = new Goal();
        Problem p = new Problem(init,actFunc,resFunc,goal);
        //TODO
        //store the path of the robot in the occupancy grid and save it in a bitmap image
        //
        
        
        Search search = searchAlgorithms.get(k);
        agent = new SearchAgent(p, search);
		init.addAgent(agent);
        
		List<Action> actions = agent.getActions();
		
		Position pos = robot;
		
		//Generating a raimbow of colors for the roboth path, for better visibility
		LinkedList<Color> colors = new LinkedList<Color>();
		int shades = (actions.size()/6)+1;
		
		for (int r=0; r<shades; r++) colors.add(new Color(r*255/shades,       255,         0));
		for (int g=shades; g>0; g--) colors.add(new Color(      255, g*255/shades,         0));
		for (int b=0; b<shades; b++) colors.add(new Color(      255,         0, b*255/shades));
		for (int r=shades; r>0; r--) colors.add(new Color(r*255/shades,         0,       255));
		for (int g=0; g<shades; g++) colors.add(new Color(        0, g*255/shades,       255));
		for (int b=shades; b>0; b--) colors.add(new Color(        0,       255, b*255/shades));
		colors.add(new Color(        0,       255,         0));
		

		
		for(int i = 0; i<actions.size() -1; i++) {
			Action a = actions.get(i);
			if(ActionFunction.UP.equals(a)) {
				pos = new Position(pos.x(),pos.y()+1);
				occupancy[pos.y()][pos.x()] = colors.pop().getRGB();
				changes.add(pos);
				
			}
			if(ActionFunction.DOWN.equals(a)) {
				pos = new Position(pos.x(),pos.y()-1);
				System.out.println(colors.size()+ "DIOMERDA");
				occupancy[pos.y()][pos.x()] = colors.pop().getRGB();
				changes.add(pos);
				
			}
			if(ActionFunction.RIGHT.equals(a)) {
				pos = new Position(pos.x()+1,pos.y());
				occupancy[pos.y()][pos.x()] = colors.pop().getRGB();
				changes.add(pos);
			
			}
			if(ActionFunction.LEFT.equals(a)) {
				pos = new Position(pos.x()-1,pos.y());
				occupancy[pos.y()][pos.x()] = colors.pop().getRGB();
				changes.add(pos);
				
			}

			System.out.println("x: "+pos.x() +" y: " +pos.y());
		}
		
		//Creating filename
		String algoName=searchAlgorithms.get(k).getClass().getSimpleName();
		System.out.println("ANNAGGIA IL PARADISO    "+args[1]+algoName+".bmp");
		
		
		new BMP().saveBMP(args[1]+algoName+".bmp",occupancy);
		//new BMP().saveBMP("C:/Users/diego/Desktop/diocane/"+algoName+".bmp",occupancy);
		
		//Resizing the image for better visibility (not very efficient approach but it worsk fine)
		BufferedImage img2 = null;
		img2=ImageIO.read(new File(args[1]+algoName+".bmp"));
		ImageIO.write(scale(img2,800,800),"bmp" , new File(args[1]+"scaled-"+algoName+".bmp"));
		//img2=ImageIO.read(new File("C:/Users/diego/Desktop/diocane/"+algoName+".bmp"));
		//ImageIO.write(scale(img2,800,800),"bmp" , new File("C:/Users/diego/Desktop/diocane/scaled-"+algoName+".bmp"));
		
		printActions(agent.getActions());
		
		//Cleaning the slate form previously colored position, so that the next iteration wont be dirty
		Iterator<Position> iter = changes.iterator();
		while(iter.hasNext())
			{
			Position temp = iter.next();
			occupancy[temp.y()][temp.x()] = 16777215;
			}
    }
}
   
    
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        
        return scaledImage;
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
