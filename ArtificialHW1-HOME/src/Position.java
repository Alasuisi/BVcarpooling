

public class Position {

	
	public static int maxX;
	public static int maxY;
	
	public int x;
	public int y;
	
	public Position(int i, int j) {
		
		this.x = i;
		this.y = j;
	}

	public int x() {
		// TODO Auto-generated method stub
		return this.x;
	}
	
	public int y() {
		// TODO Auto-generated method stub
		return this.y;
	}
	
	@Override
	public boolean equals(Object o){
		System.out.println("ciao");
		return true;
	}

}
