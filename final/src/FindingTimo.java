import java.awt.Color;

public class FindingTimo {

	static class Character {
		int xCord;
		int yCord;
		int zCord;
		int numOfMoves;
		boolean found;
	}

	static Character timo = new Character();
	static Character player = new Character();
	Character finish = new Character();

	

	final static int mLength = 15; //vertical
	final static int mWidth = 15; //horizontal
	final static int height = 3; //up down

	int move = 0; //make a universal move counter to display number of moves and use to do turtle speed if(move%4==0) or smth

	static char [][][] map = new char[mLength][mWidth][height];
	static byte [][][] ghostMap = new byte[mLength][mWidth][height];

	int previousDistance = distanceBetween();

	public byte[] moveGenerator(Character movement, char poop) {
		
		
		System.out.println("find error");
		//error lies within move generator
		
		final byte maxNumOfOptions = 5; //4 slots for moves, 1 slot at end for number of available moves
		byte []options = new byte[maxNumOfOptions];

		//checks which sides are open. adds them into a draw. //if nothing in draw, goes up and down

		//adds the directions into a raffel!

		byte numOfOption = 0;
		
		//edge error happening after changing ghostmap[timo] to ghostmap[movement]
		//update : that is not the case...
		//solution: the movement only blocks 1 pace movement, not the 0-3 jumps I added. wait but if I call the check each jump it should work...
		for(int i = 0; i < numOfOption; i++) {
			System.out.print(options[i]);
		}
		System.out.println();
		if(movement.yCord>0&&ghostMap[movement.yCord-1][movement.xCord][movement.zCord]==0) {
			System.out.println("can move forward");
			options[numOfOption] = 1;
			numOfOption++;
		}
		for(int i = 0; i < numOfOption; i++) {
			System.out.print(options[i]);
		}
		System.out.println();
		if(movement.yCord<mLength-1&&ghostMap[movement.yCord+1][movement.xCord][movement.zCord]==0) {
			System.out.println("Can move backwards");
			options[numOfOption] = 2;
			numOfOption++;
		}
		for(int i = 0; i < numOfOption; i++) {
			System.out.print(options[i]);
		}
		System.out.println();
		if(movement.xCord>0&&ghostMap[movement.yCord][movement.xCord-1][movement.zCord]==0) {
			System.out.println("Can move left");
			options[numOfOption] = 3;
			numOfOption++;
		}
		for(int i = 0; i < numOfOption; i++) {
			System.out.print(options[i]);
		}
		System.out.println();
		if(movement.xCord<mWidth-1&&ghostMap[movement.yCord][movement.xCord+1][movement.zCord]==0) {
			System.out.println("Can move right");
			options[numOfOption] = 4;
			numOfOption++;
		}
		
		for(int i = 0; i < numOfOption; i++) {
			System.out.print(options[i]);
		}
		System.out.println();
		

		options[4] = numOfOption;

		return options;

	}


	public void turtleMove(char visionCones){
		
		
		final char poop = 'o';
		
		byte direction;

		byte [] turtleDirection = moveGenerator(timo, poop); 
		
		//direction 0 = up or down
		//direction 1 = forward
		//direction 2 = backwards
		//direction 3 = left
		//direction 4 = right
		
		timo.numOfMoves++;

		if(turtleDirection[4] == 0) { //meaning all directions were skipped and none added to options list
			direction = 0;
		}
		else {
			direction = turtleDirection[(byte)(Math.random()*turtleDirection[4])];
		}
		if(map[timo.yCord][timo.xCord][timo.zCord]=='!') { //true even if on itself because it means it was visable previously
			map[timo.yCord][timo.xCord][timo.zCord] = visionCones;
		} else if (!canBeSeen(timo, visionCones)){
			map[timo.yCord][timo.xCord][timo.zCord] = ' ';
		}
		ghostMap[timo.yCord][timo.xCord][timo.zCord] = (byte)timo.numOfMoves;

		//System.out.println("Error checker : " + timo.yCord +" " +timo.xCord + " "+ timo.zCord);
		System.out.println("the direction the turtle will go in is " + direction);
		switch(direction) {

		case 0: //up down in case the turtle is trapped in its own poop
			if(timo.zCord<height-1) { //zcords should be measured and compared invididually
				timo.zCord++;
			} 
			else if(timo.zCord>0) {
				timo.zCord--;
			}
			break;
		case 1: //forward
			timo.yCord--;
			break;
		case 2: //backward
			timo.yCord++;
			break;
		case 3: //left
			timo.xCord--;
			break;
		case 4: //right
			timo.xCord++;
			break;
		}

	}



	//have to compare ghostMap everytime vision is called.
	public boolean canBeSeen(Character inQuestion, char visionCones) {
		if(map[inQuestion.yCord][inQuestion.xCord][inQuestion.zCord]==visionCones) {
			return true;
		}
		return false;
	}

	public void vision(char visionCones) {
		
		//update the try catch to remove unncessary catch
		
		try{

			map[player.yCord+1][player.xCord][player.zCord] = visionCones;

			map[player.yCord+1][player.xCord+1][player.zCord] = visionCones;
			map[player.yCord+1][player.xCord-1][player.zCord] = visionCones;
		}catch(Exception e){

		}
		try{
			map[player.yCord-1][player.xCord][player.zCord] = visionCones;

			map[player.yCord-1][player.xCord+1][player.zCord] = visionCones;
			map[player.yCord-1][player.xCord-1][player.zCord] = visionCones;

		}catch(Exception e){

		}

		try{
			map[player.yCord][player.xCord+1][player.zCord] = visionCones;
		}catch(Exception e){

		}

		try{
			map[player.yCord][player.xCord-1][player.zCord] = visionCones;
		}catch(Exception e){

		}

	}

	public int distanceBetween(){ //this method must be called after the changes like distanceBetween(smth.xCord-1) | can be called before
		int distance = Math.abs(timo.xCord-player.xCord) + Math.abs(timo.yCord-player.yCord);

		//save the print for after the method is called so i can return an int
		return distance;
	}

	public void isHot(int previousDistance, int currentDistance) {
		if(currentDistance<=previousDistance) {
			if(currentDistance<5) { //if within 5 moves, very hot
				SetUpControls.frame.getContentPane().setBackground(Color.red);
			} else {
				SetUpControls.frame.getContentPane().setBackground(Color.red.darker().darker().darker());
			}
			
		} else {
			SetUpControls.frame.getContentPane().setBackground(Color.blue.darker().darker().darker());
		}
	}
	
	public void turtleMoveTracker(char visionCones) {
		
// issue is here
		byte random = (byte)(Math.random()*3); //can move 0-2 squares every three moves
		if(move%2==0){
			for(int i = 0; i < random; i++){
				turtleMove(visionCones);	
			}
		}
		
		//error ends here (it is inside turtleMove)
		System.out.println("error in movetracker : " + timo.yCord);
		
		int currentDistance = distanceBetween();
		if(timo.zCord<player.zCord) {
			System.out.println("You hear footsteps below you");
		}
		else if(timo.zCord>player.zCord) {
			System.out.println("You hear footsteps above you");
		} else {
			System.out.println("Timo is "+currentDistance+" steps away");
		}
		
		isHot(previousDistance, currentDistance); //changes the color of controls
		
		previousDistance = currentDistance;
		
		//issue lies when I changed something here
		
		
		
		if(canBeSeen(timo, visionCones)) { //not within the other if to be ontop of vision updates
			map[timo.yCord][timo.xCord][timo.zCord]='!';
		}
	}

	public void displayGrid(int floor, String[]alphabet) {

		System.out.println("(" + alphabet[player.xCord] + ", "+(player.yCord+1) + ", " + (player.zCord+1) + ")");
		
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
		String[]alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
		final char visionCones = '.';
		Menu m = new Menu();
		
		for(int i = 0; i < 20; i++) System.out.println(); //to clear screen

		move++;
		vision(visionCones);

		if(timo.found||onTurtle()) {
			if(atBedroom()){
				m.endingScreen();
				return; //breaks the method
			}
			System.out.println("Return the turtle to coordinates (" + alphabet[finish.xCord] + ", " + (finish.yCord+1) + ", " + (finish.zCord+1) + ")");
		} else {
			
			//smth wrong here?
			
			turtleMoveTracker(visionCones);
			System.out.println("timo's current location (he's still on the run!) : " + alphabet[timo.xCord] +" "+ (timo.yCord+1) +" "+ (timo.zCord+1)); //save this for the hint
		}

		map[player.yCord][player.xCord][player.zCord] = 'x';

		displayGrid(player.zCord, alphabet);
	}

	public boolean atBedroom() {

		//make a random point the player has to reach on another floor
		if(player.yCord == finish.yCord && player.xCord == finish.xCord && player.zCord == finish.zCord){
			return true;
		}
		return false;
	}

	public boolean onTurtle() {
		if(timo.xCord==player.xCord&&timo.yCord==player.yCord&&timo.zCord==player.zCord) {
			timo.found = true;
			System.out.println("Caputred the turtle!");

			finish.xCord = (int)(Math.random()*mWidth);
			finish.yCord = (int)(Math.random()*mLength);
			finish.zCord = (int)(Math.random()*height);

			return true;
		}
		return false;
	}

	public void run() {
		SetUpControls.setControls();
		//starting cords
		
		/*
		timo.xCord = (int)(Math.random()*mWidth);
		timo.yCord = (int)(Math.random()*mLength);
		timo.zCord = (int)(Math.random()*height);
		
		*/
		
		timo.xCord = 4;
		timo.yCord = 0;
		timo.zCord = 0;
		//starting cords in the middle
		player.xCord = mWidth/2;
		player.yCord = mLength/2;
		player.zCord = 0;

		//setup the map and resets it on every run
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				for(int k = 0; k < map[0][0].length; k++) {
					map[i][j][k] = ' ';
				}
			}
		}
		update();
	}


}
