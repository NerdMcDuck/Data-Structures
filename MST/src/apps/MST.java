package apps;

import structures.*;
import java.util.ArrayList;

import apps.PartialTree.Arc;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		/* COMPLETE THIS METHOD */
		PartialTreeList L = new PartialTreeList(); //creates an empty list L of partial trees
		
		int index = 0;
		
		for(Vertex v : graph.vertices){ //Goes through loop, assigns each vertex of the graph to v

			PartialTree T = new PartialTree(v); //Create a partial tree T containing only v

			v.parent = v; //Mark v as belonging to T
			MinHeap<PartialTree.Arc> P = new MinHeap<PartialTree.Arc>();
			
			for (Vertex.Neighbor nbr = graph.vertices[index].neighbors; nbr != null; nbr = nbr.next) { //go through the edges
				
				P = T.getArcs();
				P.insert(new PartialTree.Arc(graph.vertices[index],  nbr.vertex, nbr.weight));

			}
			
			//System.out.println(T.toString()); //Print out the PartialTree
			L.append(T); //Add the partial tree T to the list L
			index++;	
			
		}
	
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param graph Graph for which MST is to be found
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(Graph graph, PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> L = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){ //there's more an 1 item in the tree
			
			PartialTree PTX = ptlist.remove(); //remove the front vertex
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs(); //the arcs of the removed tree
			
			Arc a = PQX.deleteMin(); //get the highest priority item in the tree
			
			Vertex v1 = a.v1; //v1
			Vertex v2 = a.v2; //v2
			
			//PQX.deleteMin(); //delete the highest priority item in the tree
			
			
			if(v1.parent.equals(v2.parent)){
				//if they belong to the same tree
				
				a = PQX.deleteMin();
				
				v1 = a.v1;
				v2 = a.v2;
				
				//PQX.deleteMin();
				
				PartialTree PTY = ptlist.removeTreeContaining(v2);
				//MinHeap<PartialTree.Arc> PQY = PTY.getArcs();
				PTX.merge(PTY);
				PTY.getRoot().parent = PTX.getRoot();
				ptlist.append(PTX);
				
			} else { //if they don't belong to the same tree
				PartialTree PTY = ptlist.removeTreeContaining(v2); //removes the tree rooted at v2
				//MinHeap<PartialTree.Arc> PQY = PTY.getArcs(); //get the arcs of this tree
				
				PTX.merge(PTY); //combining vertices PTX and PTY
				PTY.getRoot().parent = PTX.getRoot(); //set all of PTYs' roots to point to PTXs' roots. 
				//PQX.merge(PQY); //merging the arc priority queues into one queue
				//PTX.getRoot().name = PTX.getRoot().name.concat(" " + PTY.getRoot().name); //Rename the vertices to reflect the merged tree
				ptlist.append(PTX);
				// Vertices: A C  PQ: (C A 1), (C E 2), (A D 3), (A B 4), (C D 4), (C B 5)
				// Vertices: B D  PQ: (D E 1), (D A 3), (D B 3), (B A 4), (D C 4), (B C 5)
				//Vertices: E B D  PQ: (D E 1), (E C 2), (D A 3), (D B 3), (B A 4), (D C 4), (B C 5)
				//System.out.println(PTX.toString()); 
				
			} 
			
		} 
		
		/* A conditional that expresses my dissatisfaction with
		* this assignment
		* 
		
		if(!isWorking){
			me.cry();
		} else{
			System.out.println("WORKS! I is WINRAR!! ");
			System.out.println(":)");
		}*/
		//To add to L, take out each ARC in ptlist and add it to L
		PartialTree tmp = ptlist.remove();
		MinHeap<PartialTree.Arc> arcs = tmp.getArcs();
		
		while(!arcs.isEmpty()){
				Arc a = arcs.getMin();
				L.add(a);
				arcs.deleteMin();
			
		}
		//(D E 1), (E C 2), (A D 3), (D A 3), (D B 3), (A B 4), (C D 4), (B A 4), (D C 4), (C B 5), (B C 5)
		System.out.println(L.toString());
		return L;
		
	}
}
