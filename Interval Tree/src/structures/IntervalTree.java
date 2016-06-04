package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		// COMPLETE THIS METHOD
		Queue<IntervalTreeNode> Q = new Queue<IntervalTreeNode>(); //create a new Queue Q
		IntervalTreeNode T = null; //creates an IntervalTreeNode T
		float splitValue = 0; //create splitValue and temporarily initialize it to 0
		
		for(int p = 0; p < endPoints.size(); p++){//Go through the endPoint array
			splitValue = endPoints.get(p); //set the splitValue to the current index element
			
			T = new IntervalTreeNode(splitValue, splitValue,splitValue); //Create nodes for each point "p" in endPoints -(splitValue,minSplitValue, maxSplitValue)
			Q.enqueue(T); //enqueue T in queue Q

		}//endfor
		int s = Q.size(); //Let s be the size of Q 
		
		while(s > 0){
		
		 s = Q.size(); 
		if(s == 1){
			T = Q.dequeue(); //T is the root of the interval tree
			//System.out.println("SplitValue: " + T.splitValue); //prints out the final splitvalue
			return T;
		}//endif
		
		int temps = s;
		//IntervalTreeNode T1, T2, N, newT;
		//float v1, v2, x;
		
		while(temps > 1){
			
			IntervalTreeNode T1 = Q.dequeue();
			IntervalTreeNode T2 = Q.dequeue();
			float v1 = T1.maxSplitValue; //MAXIMUM split value of leaf nodes in T1
			float v2 = T2.minSplitValue; //MINIMUM split value of leaf nodes in T2
			float x = (v1+v2)/2; //split value x
			
			IntervalTreeNode N = new IntervalTreeNode(x, T1.minSplitValue,T2.maxSplitValue); //(splitValue, minSplitValue, maxSplitValue)
			IntervalTreeNode newT = N; //N is root of newT
			N.leftIntervals = new ArrayList<Interval>();
			N.rightIntervals = new ArrayList<Interval>();
			N.leftChild = T1; //T1 as left child of N
			N.rightChild = T2; //T2 as right child of N
			Q.enqueue(newT);
			temps = temps -2;
			
		}//endwhile
		
		if(temps == 1){
			Q.enqueue(Q.dequeue());
			
			}//endif
		}
		
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		return T;
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {

		// COMPLETE THIS METHOD
		char c = ' ';
		for(Interval xy : leftSortedIntervals){//iterate through the entire interval list 
			c = 'l';
			 mapTree(root, xy, c); //recursive call
			
		}
		
		for(Interval xy : rightSortedIntervals){//iterate through the entire interval list 
			c = 'r';
			 mapTree(root, xy, c);
			 //System.out.println("root: " + root.toString());
			
		}
		
	}
	
	private void mapTree(IntervalTreeNode root, Interval lrIntervals, char lr){
		if(root == null){ //if root is null just return
			return;
			
		}
		if(lr == 'l'){ //if we're sorting leftSortedIntervals
			if(lrIntervals.contains(root.splitValue)){//if the splitValue is within the interval
				
				if(root.leftIntervals == null){ //if leftIntervals is null 
					
					root.leftIntervals = new ArrayList<Interval>(); //create an arrayList of Interval
					
				} else {//if leftIntervals not null
					
					root.leftIntervals.add(lrIntervals); //add to it
					
					//System.out.println("Lroot: " + root.leftIntervals.toString());
					return; //go back to mapIntervalsToTree
					
				}//first inner endif statement
				
			} 
			if(root.splitValue > lrIntervals.rightEndPoint ) {//if splitValue is greater than the rightEndPoint
		
				mapTree(root.leftChild, lrIntervals,lr); //go left
				
			} else {
				
				mapTree(root.rightChild, lrIntervals, lr); //go right
				
			}//end second inner if statement
			
		}else {//lr == 'r'
			
			if(lrIntervals.contains(root.splitValue)){//if the splitValue is within the interval
				
				if(root.rightIntervals == null){ //if rightIntervals is null 
					
					root.rightIntervals = new ArrayList<Interval>(); //create an arrayList of Interval
					
				} else {//if rightIntervals not null
					
					root.rightIntervals.add(lrIntervals); //add to it
					
					//System.out.println("Rroot: " + root.rightIntervals.toString());
					return; //go back to mapIntervalsToTree
					
				}//first inner endif statement
				
			} 
			if(root.splitValue > lrIntervals.rightEndPoint ) {//if splitValue is greater than the rightEndPoint
				
				mapTree(root.leftChild, lrIntervals,lr); //go left
				
			} else {
				
				mapTree(root.rightChild, lrIntervals, lr); //go right
				
			}//end second inner if statement
			
		}//endif
	}
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		// COMPLETE THIS METHOD
		IntervalTreeNode r = getRoot();//Let R be the root node of T
		//System.out.println("root: " + r.toString());
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		return query_tree(r, q);//Input: Interval tree T, query interval Iq
	}
	
	private ArrayList<Interval> query_tree(IntervalTreeNode T, Interval q){
		
		ArrayList<Interval> resultList = new ArrayList<Interval>(); //Output: ResultList, a list of intervals from T that intersect Iq, Let ResultList be empty.
		float splitValue = T.splitValue; //Let SplitVal be the split value stored in R
		ArrayList<Interval> lList = T.leftIntervals; // Let Llist be the list of intervals stored in R that is sorted by left endpoint
		ArrayList<Interval> rList = T.rightIntervals; // Let Rlist be the list of intervals stored in R that is sorted by right endpoint
		IntervalTreeNode lSub = T.leftChild;  //Let Lsub be the left subtree of R
		IntervalTreeNode rSub = T.rightChild;   //Let Rsub be the right subtree of R
		
		
		if(lSub == null && rSub == null){//If R is a leaf, return empty list.
			
			return resultList;
		} 
		
		if(q.contains(splitValue)){
			 //System.out.println("lList: " + lList);
			
				for(int i = 0; i < lList.size(); i++){
					resultList.add(lList.get(i)); //Add all intervals in Llist to ResultList
				}//endfor
				
			resultList.addAll(query_tree(rSub,q)); //Query Rsub and add the results to ResultList
			resultList.addAll(query_tree(lSub,q)); //Query Lsub and add the results to ResultList
			
		} else if(splitValue < q.leftEndPoint){ //if SplitVal falls to the left of Iq then
			
			int i = rList.size() - 1;
			while(i >= 0){ //while (i >= 0 and the i-th interval in Rlist intersects Iq)
				if(rList.get(i).intersects(q)){ //i-th interval in Rlist intersects Iq
					resultList.add(rList.get(i)); //Add the i-th interval to ResultList
					i = i -1;
				} else 
					
					return resultList; //if it's outofBounds
			}//endwhile
			resultList.addAll(query_tree(rSub,q));
		} else if(splitValue > q.rightEndPoint){
		
			int i = 0; //Let i be 0
			while(i < lList.size() && lList != null){ //SplitVal falls to the right of Iq then
				if(lList.get(i).intersects(q)){ //i-th interval in llist intersects Iq
					resultList.add(lList.get(i)); //Add the i-th interval to ResultList
					i = i +1;
				} else
				return resultList; //if it's outofBounds
			}//endwhile
			resultList.addAll(query_tree(lSub,q)); // Query Lsub and add the results to ResultList
		} 
		
		return resultList; //Return ResultList
	}
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}

