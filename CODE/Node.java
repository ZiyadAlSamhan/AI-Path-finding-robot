// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ZIYAD MOHAMMED ALSAMHAN
// ID: 435104871

import java.io.*;
import java.util.*;

class Node{
	private State state;	// the state
	private Node parent;	// the parent node
	private int action;		// the number of the action that lead to this state
	private int path_cost;	// the cost spent so far to reach this node
	private int depth;		// the depth of the node in the tree
	
	// CONSTRUCTOR : THIS CONSTRUCTOR WILL CREATE A NODE GIVEN A STATE
	Node( State st, Node pa, int a, int c, int d){
		state = st;	
		parent = pa;
		action = a;
		path_cost = c;
		depth = d;
	}
	
	
	// THIS METHOD RETURNS TRUE IS THE NODE'S STATE IS THE SAME AS THE OTHER NODE'S STATE
	public boolean hasSameState(Node n) {
		return ( state.equals(n.state) );
	}
	
 	// THIS METHOD WILL RETURN THE NEIGHBORING NODES OF COURSE, YOU CAN & SHOULD CHANGE IT
	public Node[] expand() {
		Node next_nodes[] = new Node[5];	// there are 5 actions
		State next_states[] = state.successors();
		
		for (int i=0; i<5; i++) {		// create nodes
			if (next_states[i]!=null){
				next_nodes[i]=new Node(next_states[i] , this , i , 1+this.getPath_cost() , this.getDepth()+1 );		
			}	
		
		}		
		return next_nodes;
	}
	
	// GOAL TEST: THIS WILL TELL WHETHER THE NODE'S STATE IS A GOAL.	
	public boolean isGoal() {
		return state.foundTreasure();
	}
	
	// MANHATTAN DISTANCE HEURISTIC
	public int h_md() {		
		int x0=state.getX();
		int x1=state.getTxs()[0];
		
		int y0=state.getY();
		int y1=state.getTys()[0];
		
		int min=Math.abs(x1-x0) + Math.abs(y1-y0);
		
		for(int i=1;i<state.getCount_t();i++){
			x0=state.getX();
			x1=state.getTxs()[i];
			
			y0=state.getY();
			y1=state.getTys()[i];
			
			int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
			if(distance < min){
				min=distance;
				
			}
		}
		
		
		return min;
	}
	
	//Objective function
	public int objectiveFunc(){
		int m_n=state.getM()+state.getN();
		int h=h_md();

		return (m_n*10) - (h);
	}
	
	// DISPLAY THE NODE'S INFO
	public void display() {
		state.display();
		System.out.println("Depth: "+getDepth()+" Path Cost: "+this.getPath_cost()+" Action: "+this.getAction()+" Distance: "+this.h_md());
	}

	// -----------------------------
	
	//GETTERS
	public State getState() {
		return state;
	}


	public Node getParent() {
		return parent;
	}


	public int getAction() {
		return action;
	}


	public int getPath_cost() {
		return path_cost;
	}


	public int getDepth() {
		return depth;
	}
	
}

