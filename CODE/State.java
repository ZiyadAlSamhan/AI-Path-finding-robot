// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ZIYAD MOHAMMED ALSAMHAN
// ID: 435104871

import java.io.*;
import java.util.*;

public class State {


	private int x;	//ROBOT POSTION X-AXE 
	private int y;	//ROBOT POSTION Y-AXE

	private int m;	//COLUMNS
	private int n;	//ROWS

	private String[][] map; // THE MAP
		

	private int count_t;//TREASURE COUNTER
	private int[] txs;	//TREASURE POSTION X-AXE
	private int[] tys;	//TREASURE POSTION Y-AXE

	private int battery;//ROBOT BATTERY 
	private boolean hole;//ROBOT STATE

	// -----------------------------

	// CONSTRUCTOR 1:
	// THIS CONSTRUCTOR WILL CREATE A STATE FROM FILE.
	State(String fileName) {
		Files.openRFile(fileName);

		map = Files.readFileChar(new String[5], fileName);

		m = map.length;
		n = map[0].length;
		
		battery = m + n;// Battery initial state rows + columns
		
		count_t=0;
		
		findR();// Finding the robot and the treasure + checking if it in a hole
				// or not
		txs=new int[count_t];
		tys=new int[count_t];
		
		findT();
		
		Files.closeRFile();
	}

	// CONSTRUCTOR 2:
	// THIS CONSTRUCTOR WILL CREATE A RANDOM STATE.
	State(int n, int m, int rseed) {
		// ...
	}

	// CONSTRUCTOR 3:
	// COPY CONSTRUCTOR.
	State( State s) {
		x = s.x;
		y = s.y;
		
		battery=s.battery;
		hole=s.hole; 
		
		map=s.map;	
		
		m=s.m;
		n=s.n;
		
	
		
		count_t=s.count_t;
		txs=s.txs;
		tys=s.tys;

	}

	// METHOD THAT TELLS WHETHER THIS STATE IS EQUAL TO ANOTHER STATE.
	public boolean equals(Object ss) {
		State s=(State)ss;
		return( (x == s.x)  &&  (y == s.y) && (battery == s.battery) );
	}

	// -----------------------------

	// THE ACTIONS:

	// ACTION 1
	// RETURNS BOOLEAN:
	// TRUE MEANS ACTION WAS APPLIED,
	// FALSE MEANS ACTOIN COULD NOT AND WAS NOT APPLIED.
	public boolean move_n() {
		if (isHole() )
			return false;
		
		if(!isChraged())
			return false;

		if ((y - 1) < 0)
			return false;

		if (map[y-1][x].equals("B"))
			return false;
		
		y--;		
		battery--;
		inHole();
		
		return true;
	}

	// ACTION 2
	public boolean move_s() {
		if (isHole() )
			return false;
		
		if(!isChraged())
			return false;
		
		if ((y + 1) >= m)
			return false;
		if (map[y+1][x].equals("B"))
			return false;
		
		y++;
		battery--;
		inHole();
		
		return true;
		
	}

	// ACTION 3
	public boolean move_w() {
		if (isHole() )
			return false;
		
		if(!isChraged())
			return false;

		if ( (x - 1) < 0)
			return false;
		if (map[y][x-1].equals("B"))
			return false;
		
		x--;	
		battery--;
		inHole();
		
		return true;
	}

	// ACTION 4
	public boolean move_e() {
		if (isHole() )
			return false;
		
		if(!isChraged())
			return false;
			
		if ((x + 1) >= n)
			return false;
		
		if (map[y][x+1].equals("B"))
			return false;
		
		x++;	
		battery--;
		inHole();
		
		return true;
	}
	
	// ACTION 5
	public boolean recharge(){
		if(!map[y][x].equals("C"))
			return false;
		
		
		battery = m+n;
		
		return true;
	}


	// -----------------------------

	// GOAL TEST: THIS WILL TELL WHETHER THE TREASURE WAS FOUND.
	public boolean foundTreasure() {

			return map[ y ][ x ].equalsIgnoreCase("T") || map[ y ][ x ].equalsIgnoreCase("Z") || map[ y ][ x ].equalsIgnoreCase("Y")||map[ y ][ x ].equalsIgnoreCase("E") || map[ y ][ x ].equalsIgnoreCase("F");
			
	}

	// -----------------------------

	// DISPLAY THE STATE
	public void display() {
	
		System.out.println("***********Map***********");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (y == i && x == j)
					System.out.print("R ");
				else
					System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("**************************");
		System.out.println("M: "+m+" N "+n);
		System.out.println("Y: "+y+" X: "+x+" Battery: "+this.battery);
		
		
	}

	// THIS METHOD WILL DO the GIVEN COMMAND AND WILL RETURN THE LOG MESSAGE
	public String doCommandAndLog(String cmd) {
		String log = "ERROR";
		boolean flag = false;
		switch (cmd) {
		case "move-N":
			flag = move_n();
			break;
		case "move-S":
			flag = move_s();
			break;
		case "move-E":
			flag = move_e();
			break;
		case "move-W":
			flag = move_w();
			break;
		default:
			System.out.println("Error");
		}
		if (flag && !hole) {
			if (foundTreasure())
				log = "GOAL";
			else if (map[y][x].equals("H")) {
				log = "HOLE";
				hole = true;
			} else if (map[y][x].equals("Y")) {
				log = "GOAL";
				hole = true;
			} else
				log = "DONE";
		} else {

			log = "FAIL";
		}
		return log;
	}

	// THIS METHOD WILL WRITE THE GIVEN LOGS INTO A FILE
	public void writeLogs(String logsFilename, String logs[]) {
		Files.openWFile(logsFilename);
		Files.addRecords(logs, logs.length);
		Files.closeWFile();
	}

	// -----------------------------

	// THIS METHOD WILL RETURN THE SUCCESSOR STATES
	public State[] successors() {
		State children[] = new State[5];

		children[0] = new State(this);
		if (!children[0].move_n())
			children[0] = null;

		children[1] = new State(this);
		if (!children[1].move_s())
			children[1] = null;

		children[2] = new State(this);
		if (!children[2].move_e())
			children[2] = null;

		children[3] = new State(this);
		if (!children[3].move_w())
			children[3] = null;
		
		children[4] = new State(this);
		if (!children[4].recharge())
			children[4] = null;

		return children;

	}
	
	// -----------------------------

	// ADD EXTRAS HERE ..
	
	//THIS METHOD WILL FIND AND REMOVE THE ROBOT FROM THE MAP and IT WILL FIND HOW MANY TREASURES IN IT.
	private void findR() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				switch (map[i][j]) {
				case "R":
					y = i;
					x = j;
					map[i][j] = " ";
					continue;
				case "T":
					count_t++;
					

					continue;
				case "U":
					y = i;
					x = j;

					count_t++;
			
					map[i][j] = "T";
					continue;
				case "X":
					y = i;
					x = j;
					map[i][j] = "H";
					hole = true;
					continue;
				case "Y":
					count_t++;
				

					continue;

				case "Z":
					y = i;
					x = j;

					count_t++;
				
					map[i][j] = "Y";
					hole = true;
					continue;
				case "D":
					y = i;
					x = j;

					map[i][j] = "C";
					continue;
				case "E":
					count_t++;
				
					continue;
				case "F":
					y = i;
					x = j;
					
					count_t++;
					

					map[i][j] = "E";
					continue;

				default:

					continue;
				}
			}

	}
	
	//THIS METHOD WILL FIND TREASURS.
	private void findT() {
		int c=0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				switch (map[i][j]) {
				case "T":
					System.out.println(c+"   "+count_t);
					tys[c] = i;
					txs[c] = j;

					c++;
					continue;
				case "Y":
					tys[c] = i;
					txs[c] = j;

					c++;
					continue;
				case "E":
					tys[c] = i;
					txs[c] = j;
					
					c++;
					continue;
				default:

					continue;
				}
			}

	}
	
	//THIS METHOD WILL CALL FILE CLASS TO WRITE THE MAP WITH THE ROBOT SIMPLE.
	public void writeMap(String fileName) {
		Files.openWFile(fileName);

		String h = "R";
		switch (map[y][x]) {
		case "T":

			h = "U";
			break;

		case "Y":

			h = "Z";
			break;

		case "H":

			h = "X";
			break;
		
		case " ":

			h = "R";
			break;
			
		case "C":

			h = "D";
			break;
			
		case "E":

			h = "F";
			break;
		default:

		}
		
		Files.addMap(map, x, y, h, m, n);
		Files.closeWFile();

	}

	//THIS METHOD WILL CHECK WETHER THE ROBOT HAVE BATTERY OR NOT.
	private boolean isChraged(){
		return battery > 0;
	}
	
	//THIS METHOD WILL CHECK WETHER THE ROBOT IN A HOLE OR NOT.
	private boolean isHole() {
		return hole;
	}
	
	//THIS METHOD WILL CHECK WETHER THE ROBOT IN A HOLE OR NOT AND ASSIGN THE HOLE VARIABLE. 
	private void inHole(){
		if(map[y][x].equals("H") || map[y][x].equals(map[y][x].equals("Y") ) ){
			hole=true;
		}
	}
	

	// -----------------------------

	// STATE GETTERS AND SETTERS
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String[][] getMap() {
		return map;
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}
	
	public int getBattery() {
		return battery;
	}

	public int[] getTxs() {
		return txs;
	}

	public int[] getTys() {
		return tys;
	}

	public int getCount_t() {
		return count_t;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setM(int m) {
		this.m = m;
	}


}
