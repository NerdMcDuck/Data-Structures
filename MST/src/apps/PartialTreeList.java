package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.MinHeap;
import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
   
    	/* COMPLETE THIS METHOD */

    	if(this.rear == null){//if list is empty 
    		throw new NoSuchElementException();
    	}
    	//else 

    	Node Rearptr = this.rear; //points to the rear of the CLL
    	Node ptx = Rearptr.next; //The first arc in L
    	this.rear.next = ptx.next; //Remove PTX from L
    	this.size--; //decrement the size
    	/*
    	
    	//check the removed vertices
    	System.out.println("removed: " + ptx.tree.toString());
    	System.out.println();
    	
    	//Test the tree output
    	Iterator<PartialTree> tmp= this.iterator();
    	while(tmp.hasNext()){
    		
    		System.out.println(tmp.next() );
    	}
    	 */
    	PartialTree PTX = ptx.tree; 
    	return PTX; 
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    		/* COMPLETE THIS METHOD */

    	if(this.rear == null){ //if tree is empty 
    		throw new NoSuchElementException();
    	}
    	
    	
    	Iterator<PartialTree> ptx = this.iterator(); //iterates over the list

    	PartialTree Removedtree = null; //the tree to be returned 
    	PartialTree prev = null; //pointer that keeps track of the previous vertex 
    	boolean isMatchingtree = false; //A boolean value that throws NoSuchElementException if no matching tree is found 
    	
    /*	if(this.size == 2){ //only two trees
    		while(ptx.hasNext()){
    			PartialTree tmp = ptx.next();
    			
    			if(vertex.name.equals(tmp.getRoot().name) || (vertex.name.equals(tmp.getArcs().getMin().v1.name))){
    				isMatchingtree = true;
    				if(tmp.getRoot().equals(this.rear.tree.getRoot())){
    					Node PreviousNode = new Node(prev);
    					PreviousNode.next = this.rear.next;
    					this.rear = PreviousNode;
    					Removedtree = tmp;
    					this.size--;
    					break;
    				
    				} else {
    					
        				this.rear.next = this.rear.next.next; 
        				
        				Removedtree = tmp;
        				this.size--; //decrement the size
        				break;
    				}
    				
    			}//end2ndif
    			prev = tmp;
    			
    		}//endwhile
    	}//endif
    	
    if(this.size > 2){	*/
    	
    	while(ptx.hasNext()) {

    		PartialTree tmp = ptx.next();
    		
    		if(vertex.name.equals(tmp.getRoot().name) || (vertex.name.equals(tmp.getArcs().getMin().v1.name)) ||(vertex.name.equals(tmp.getArcs().getMin().v2.name)) ){
    			isMatchingtree = true; //there is a matching tree
    			if(tmp.getRoot().equals(this.rear.tree.getRoot())){ //if the tree to be removed is the rear
    				
    				//create the node pointers and remove tree
    				Node PreviousNode = new Node(prev); //previousNode
    				Node CurrentNode = new Node(tmp); //CurrentNode which is the rear
    				PreviousNode.next = CurrentNode;
    				CurrentNode.next = this.rear.next;
    				this.rear = PreviousNode;
    				this.rear.next = CurrentNode.next;
    				
    				Removedtree = tmp;
    				this.size--;
    				break;
    			}
	    			if(prev != null){ //Not the first vertex that is returned from next()
	    				//create pointers
	    				Node CurrentNode = new Node(tmp);//current vertex
	    				Node previousNode = new Node(prev); //previous vertex
	    				Node nextNode = new Node(ptx.next()); //next vertex
	
	    				//Since all the pointers are null, assign each pointer to the next vertex since pointers are created with null as their next
	    				previousNode.next = CurrentNode; //
	    				CurrentNode.next = nextNode;
	    				nextNode.next = this.rear;
	
	    				//Remove the current vertex
	    				previousNode.next = CurrentNode.next;
	    				CurrentNode.next = nextNode;
	    				nextNode = this.rear;
	    				this.rear.next = previousNode;
	    				
	    				Removedtree = tmp;
	    				this.size--; //decrement the size of the tree  
	    				break;
	    			} else {
	    				//prev is null meaning vertex was found to be the front 
	
	    				Node Rearptr = this.rear; //points to the rear of the CLL
	    				Node head = Rearptr.next; //The first arc (vertex) in ptx
	    				this.rear.next = head.next; //Remove head from ptx
	    				
	    				Removedtree = tmp;
	    				this.size--; //decrement the size
	    				break;
	    			} //endInnerif


    		}  //endOuterif

    		prev = tmp; //previous pointer 

    	}//endwhile
   // }//endif
    	
    	if(isMatchingtree == false){ //if no matching tree is found
    		
    		throw new NoSuchElementException(); // gone through the whole tree and failed
    		
    	}
    	
    	//print out the tree for debugging
    	/*Iterator<PartialTree> debug = this.iterator();
    	while(debug.hasNext()){
    		PartialTree tmp = debug.next();
    		System.out.println(tmp.toString());
    	}*/
    	System.out.println("Removed Tree: " + Removedtree); //Tests the output - should print out the removed tree
    	
    	return Removedtree; 
    }
    
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


