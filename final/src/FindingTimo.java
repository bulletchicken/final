import java.io.*;

public class FindingTimo {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static Character timo = new Character();
	static Character player = new Character();

	final static int mLength = 15; //vertical
	final static int mWidth = 15; //horizontal
	final static int height = 3; //up down

	static char [][][] map = new char[mLength][mWidth][height];



	public static void main(String[]args) {
		new FindingTimo();
		SetUpControls.setControls();
		FindingTimo fT = new FindingTimo();

		fT.run();

	}

	public void explored() {

	}

	public void displayGrid(int floor) {
		String[]alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
		System.out.println("(" + alphabet[player.xCord] + ", "+(player.yCord+1) + ", " + player.zCord + ")");
		for(int i = 0; i < map[0].length; i++) {
			System.out.print(alphabet[i] + " ");
		}
		System.out.println();

		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j][floor] + " ");
			}
			System.out.println(i+1);
		}
	}

	public void update() {
 
		char status = 'o';
		
		for(int i = 0; i < 20; i++) System.out.println();
		System.out.println(FindingTimo.map[timo.yCord][timo.xCord][timo.zCord]);
		map[timo.yCord][timo.xCord][timo.zCord] = status;
		map[player.yCord][player.xCord][player.zCord] = 'x';
		
		if(onTurtle()) {
			System.out.println("Caputred the turtle!");
		}
		displayGrid(player.zCord);
	}
	
	public boolean onTurtle() {
		if(timo.xCord==player.xCord&&timo.yCord==player.yCord&&timo.zCord==player.zCord) {
			player.hasTurtle = true;
			
			return true;
		}
		
		return false;
	}

	public void run() {

		//starting cords
		timo.xCord = (int)(Math.random()*15);
		timo.yCord = (int)(Math.random()*15);
		timo.zCord = (int)(Math.random()*height);
		//starting cords in the middle
		player.xCord = 7;
		player.yCord = 7;
		player.zCord = 0;




		//setup the map and resets it on every run
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				for(int k = 0; k < map[0][0].length; k++) {
					map[i][j][k] = ' ';
				}
			}
		}

		//use cues like you hear footsteps above or below you
		update();
	}

	public void displayIntro() {
		System.out.println("Game : type 1 to begin");
	}

	public String nameInput()throws IOException {
		return br.readLine();
	}
}