// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ZIYAD MOHAMMED ALSAMHAN
// ID: 435104871

import java.io.*;
import java.util.*;

public class Agent {

	public static void main(String[] args) {

		int n_args = args.length;
		if (n_args!=5) {
			System.out.println("ERROR: ILLEGAL NUMBER OF ARGUMENTS:");
			System.out.println("Number of arguments must be 5.");
			return;
		}
		String mode = args[0];
		if (!mode.equals("s") && !mode.equals("c")) {
			System.out.println("ERROR: ILLEGAL MODE:");
			System.out.println("Mode must be 's' or 'c'.");
			return;
		}
		if (mode.equals("c")) {//Phase1
			String MapFile = args[1];
			String CommandFile = args[2];
			String finalMapFile = args[3];
			String logFile = args[4];

			State s=new State(MapFile);
						
			String[] cmd=Agent.readCmd(CommandFile);
			String[] logs=new String[cmd.length];
			
			for(int i=0;i<cmd.length;i++){
				logs[i]=s.doCommandAndLog(cmd[i]);
			}
			s.writeLogs(logFile, logs);

			s.writeMap(finalMapFile);
		
		}
		else
		if (mode.equals("s")) {//Phase2
			//n1(ALGORTHIM TYPE) -> 1-BFS/2-A*/3-HC
			int na = Integer.valueOf(args[1]);
			
			String MapFile = args[2];
			String actionsFile = args[3];
			String finalMapFile = args[4];
			
			State s=new State(MapFile);
			Search search=new Search(s);

			Node goalNode=search.doSearch(na);
			search.displaySolution(goalNode);
			String[] actions=search.extractSolution(goalNode);

			if(goalNode != null){
				goalNode.getState().writeLogs(actionsFile, actions);
				goalNode.getState().writeMap(finalMapFile);
			}
			else{
				s.writeLogs(actionsFile, actions);
				s.writeMap(finalMapFile);
			}
		}

	}
	
	public static String[] readCmd(String s){
		Files.openRFile(s);
		String res[]=Files.readFileString(s);
		Files.closeRFile();
	
		return res;
	}

	
}

class Files{
	
	public static  Scanner scan;
	public static  Formatter F;

    //Opening a writing file
	public static void openWFile(String s){
 		try{
 		F=new Formatter(s);
 		}
 		catch(Exception e){
 			System.out.println("Formater Error"+e.getMessage());
 		}
 		
 	}
 	//Writing on a file
 	public static void addRecords(String[] arr,int c){
 		for(int i=0;i<c;i++){
 			F.format("%s\n", arr[i]);
 		}
 		
 	}
	//Writing on a file
 	public static void addMap(String[][] map,int x,int y,String h,int m,int n){
 		F.format("%s\n%s\n",m,n);
 		for(int i=0;i<map.length;i++){
 			for(int j=0;j<map[0].length;j++){
 				if(y==i&&x==j)
 					F.format(h);
 				else
 					F.format("%s", map[i][j]);
 			}
 			F.format("\n");
 		}
 		
 	}
 	//Closing a writing file
 	public static void closeWFile(){
 		F.close();
 	}
 	
 	//Opening the reading file
 	public static void openRFile(String s){
 		try{
 			scan=new Scanner(new File(s));
 		}
 		catch(Exception e){
 			System.out.println("Scanner Error: "+e.getMessage());
 		}
 	}
 	//Reading a file
 	public static String[] readFileString(String s){
 		
 		int count=0;//How many word in the file?
 		for(int i=0;scan.hasNext();i++){
 			count++;
 			scan.next();	
 		}
 		
 		String res[]=new String[count];
 		
 		closeRFile();
 		openRFile(s);
 		
 		for(int i=0;scan.hasNext();i++){
 			String z=String.format("%s", scan.next());	
 			res[i]=z;
 		}
 		
 		
 		
 		return res;

 	}
 	public static String[][] readFileChar(String[] arr,String file){
 	
 		int m=scan.nextInt();
 		int n=scan.nextInt();
 		closeRFile();
 		openRFile(file);
 		
 		String res[][]=new String[m][n];
 		scan.nextLine();
 		scan.nextLine();
 		
 		for(int i=0;scan.hasNextLine();i++){
 			String tmp=scan.nextLine();
 			for(int j=0;j<n;j++){
 				res[i][j]=tmp.charAt(j)+"";
 			}
 		}	
 		
 		return res;

 	}
 	//Closing the reading file
 	public static void closeRFile(){
 		scan.close();
 	}
 	
	
	
}