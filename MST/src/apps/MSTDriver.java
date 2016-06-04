package apps;

import structures.*;
import java.io.*;
import java.util.ArrayList;

import apps.PartialTree.Arc;

public class MSTDriver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MST mst = new MST();
		Graph graph = new Graph("GraphTestCase1.txt");
		
		PartialTreeList ptlist = mst.initialize(graph);
		
		ArrayList<PartialTree.Arc> partialtreeArc = mst.execute(graph, ptlist);
		
		
		
		//ptlist.removeTreeContaining(ptlist.remove().getRoot());
		//to check Remove
		/* for (int i=0; i<this.size-1; i++){ //check loop
        System.out.println(Rearptr.tree.toString());
        Rearptr=Rearptr.next;
		}*/
	}

}
