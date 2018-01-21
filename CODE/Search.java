// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ZIYAD MOHAMMED ALSAMHAN
// ID: 435104871

import java.io.*;
import java.util.*;

public class Search {


	// CONSTANTS:
	private static final int FRINGE_MAX_SIZE = 100000;
	
	// ATTRIBUTES:
	private Node root;	// the root node
	private Node goal;	// the goal node

	private int numNodesExpanded;		// number of nodes expanded
	private LinkedList<Node> fringe;	//BFS FRINGE
	private PriorityQueue<Node> fringe2;//A* FRINGE

	
	private int n_closed; // closed list size
	private LinkedList<State> closed;// the closed list containing visited nodes

	
	
	//private LinkedList<NodeB> nodesB;
	//private int b_num;
	
	// CONSTRUCTOR 1: THIS CONSTRUCTOR WILL CREATE A SEARCH OBJECT.
	Search( State init_state ) {
		root = new Node(init_state, null, -1, 0, 0); // make the root node
		fringe = new LinkedList<Node>();	// initialize Queue
		MyComparator x=new MyComparator();
		fringe2=new PriorityQueue<Node>(x);
		closed = null;
		n_closed = 0;
		goal = null;
		numNodesExpanded = 0;
		
		// ...
		
	}
	

	// THIS METHOD INITIALIZES THE CLOSED LIST
	private void initialize_closed() {
		if (closed==null)
			closed = new LinkedList<State>();
		n_closed=0;
	}
	
	// THIS METHOD TESTS WHETHER THE NODE WAS
	// VISITED OR NOT USING A SIMPLE FOR LOOP.
	// YOU CAN CHANGE IT.
	private boolean visited(Node n) {
		
		if ( closed.contains(n.getState()))  // change this
			return true;
		
		return false;
	}
	

	// THIS METHOD ADDS A NODE TO THE CLOSED LIST.
	// IT SIMPLY ADDS IT TO THE ND OF THE LIST. YOU
	// CAN CHANGE IT TO A MORE SOPHISTICATED METHOD.
	private void mark_as_visited(Node n) {
		// if the list is  full do a left shift:
		n_closed++;
		closed.add(n.getState());
	}
	
	
	// THIS METHOD WILL DO CHOOSE THE SEARCH TYPE AND CAN RETURN THE GOAL NODE. YOU CAN EXTRACT THE SOLUTION BY FOLLOWING THE PARENT NODES	
	public Node doSearch(int type){
		Node res=null;
		double b_time=System.currentTimeMillis();
		
		
		if(type == 1)
			res=this.doSearch_BFS();
		if(type == 2)
			res=this.doSearch_A();
		if(type == 3)
			res=this.doSearch_HC();
		
		double e_time=System.currentTimeMillis();
		double total=e_time-b_time;
		
		System.out.println("TOTAL TIME: "+total);
		
		return res;
	}
	
	//SEARCHING BY BFS ALGORTHIM 
	public Node doSearch_BFS() {
		initialize_closed();
		
		numNodesExpanded = 0;
		Node nodesList[];
		Node current = root;
	
		fringe.add(current);	
		while (!fringe.isEmpty()) {

			current = fringe.remove();
			mark_as_visited(current);
			
			if (current.isGoal()) {
				System.out.println("\nDO_SEARCH Expanded nodes: "+numNodesExpanded);
				current.display();
				return current;	
			}
			
			nodesList = current.expand();
			numNodesExpanded++;		
			
		

			for (int i=0; i<5; i++) {	// we have 5 actions
				if (nodesList[i]!=null){
					if(!visited(nodesList[i]))
						fringe.add(nodesList[i]);
					
				}
					
			}			
		
		}
		
		System.out.println("BFS Goal not Found");
		return null;	// goal not found
		
	}
	
	//SEARCHING BY A* ALGORTHIM
	public Node doSearch_A() {
		initialize_closed();
		
		numNodesExpanded = 0;
		Node nodesList[];
		Node current = root;
		
		System.out.println("A*");
		fringe2.add(current);	
		
		while (!fringe2.isEmpty()) {

			current = fringe2.remove();
			mark_as_visited(current);
	
			if (current.isGoal()) {
				System.out.println("DO_SEARCH Expanded nodes: "+numNodesExpanded);
				current.display();
				return current;	
			}
			
			nodesList = current.expand();
			numNodesExpanded++;		
			
			
			
			for (int i = 0 ; i < 5 ; i++) {	
				if (nodesList[i] != null)					
					if(!this.visited(nodesList[i]))
						fringe2.add(nodesList[i]);
					
					
					
			}			
		}
		
		System.out.println("A*: Goal not Found");
		System.out.println("DO_SEARCH Expanded nodes: "+numNodesExpanded);
		current.display();
		return null;	// goal not found
		
	}
	
	//SEARCHING BY HC ALGORTHIM
	public Node doSearch_HC() {
		numNodesExpanded = 0;
		Node nodesList[];
		Node current = root;
		
		boolean flag=true;
			
		while (flag) {
			
			if (current.isGoal()) {
				System.out.println("\n\nDO_SEARCH Expanded nodes: "+numNodesExpanded);
				current.display();
				return current;	
			}
			
			nodesList = current.expand();
			numNodesExpanded++;		
			
			int max=current.objectiveFunc();
			int index=-1;
			
			for (int i=0; i<5; i++) {	// we have 5 actions
				if (nodesList[i]!=null){
					if( (  nodesList[i].objectiveFunc()) > max){
						max= nodesList[i].objectiveFunc();
						index=i;
					}					
				}
					
			}		
			if(index == -1)
				flag=false;
			else
				current=nodesList[index];
		
		}
		
		
		System.out.println("HC: Goal not Found");
		System.out.println("DO_SEARCH Expanded nodes: "+numNodesExpanded);
		current.display();
		
		return null;	// goal not found
		
	}
	
	
	
	// GIVEN THE GOAL NODE, THIS METHOD WILL EXTRACT
	// THE SOLUTION, WHICH IS A SEQUENCE OF ACTIONS.	
	public String[] extractSolution( Node goalNode ) {
		if(goalNode == null){
			String[] noop={"NOOP"};
			return noop;
		}
		// first find solution length;
		int len=0;	
		Node n = goalNode;
		while (n!=null) {
			n = n.getParent();
			len++;
		}
		//declare an array of strings
		String sol[] = new String[len-1];

		n = goalNode;
		for (int i=len-2; i>=0; i--) {
			switch (n.getAction()) {
				case 0: sol[i] = new String("move-N");
					break;
				case 1: sol[i] = new String("move-S");
					break;
				case 2: sol[i] = new String("move-E");
					break;
				case 3: sol[i] = new String("move-W");
					break;
				case 4: sol[i] = new String("recharge");
			}
			n = n.getParent();
		}
		return sol;		
	}
	
	// THIS METHOD WILL DISPLAY THE SOLUTION
	public void displaySolution(Node goalNode) {
		String sol[] = extractSolution(goalNode);
		for (int i=0; i<sol.length; i++) 
			System.out.println(sol[i]);
	}

}
	/*
	public void makeNodeB(){
		if (nodesB==null)
			nodesB = new LinkedList<NodeB>();
		this.b_num=0;
	}
	public boolean isIn(Node n){
		return nodesB.contains(new NodeB(n));
	}
	public boolean insertB(Node n){
		for(int i=0;i<nodesB.size();i++){
			if(nodesB.get(i).ff(new NodeB(n))){
				if(nodesB.get(i).limit())
					return false;
				else{
					nodesB.get(i).increament();
					return true;
				}
			}
				
		}
		
		nodesB.add(new NodeB(n));
		b_num++;
		
		
		return true;
	}
	
	
	
	
}
class NodeB{
	private Node node;
	private int count;
	
	public boolean ff(Object o){
		NodeB n=(NodeB)o;
		if( isSame(n.node))
			if(limit()){
				System.out.println("LIMIT");
				return true;
			}
		return false;
	}
	
	public boolean equals(Object o){
		NodeB n=(NodeB)o;
		if( isSame(n.node))
			if(limit()){
				System.out.println("LIMIT");
				return true;
			}
		return false;
	}
	public boolean isSame(Node n){
		State s1=node.getState();
		State s2=n.getState();
		
		if(s1.getX() == s2.getX() && s1.getY() == s2.getY())
			return true;
		
		return false;
	}
	public boolean limit(){
		return count > 2;
	}
	public void increament(){
		count++;
	}
	public NodeB(Node node) {
		this.node = node;
		count=0;
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}


*/

//For the priority queue (A* FRINGE)
class MyComparator implements Comparator<Node> {
	
	 public int compare(Node a,Node b){    
		 	int total1=a.h_md() + a.getPath_cost();
		 	int total2=b.h_md() + b.getPath_cost();

		 	
		 	if(total1 > total2)
		 		return 1;
		 	
		 	if(total1 < total2)
		 		return -1;
		 	
	       return  0;
	  }
}

