package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		// COMPLETE THIS METHOD 
		
		 int jIndex; //Initialize inner index for inner loop
		
		 for(int index = 1; index < intervals.size(); index++){ //go through the list
			 Interval varAt = intervals.get(index); //get the element in the list
			 jIndex = index; //initialize inner index to the current position
			 if(lr == 'l'){//check which endpoint to sort
				 
				 while(jIndex != 0 && intervals.get(jIndex -1).leftEndPoint > varAt.leftEndPoint){ //compares the previous element with the current one
					 intervals.set(jIndex, intervals.get(jIndex - 1)); //does the swapping, replaces current element with previous element
					 jIndex-=1;
				 }
				 intervals.set(jIndex, varAt); //swaps the element in "index" with the current element in "jindex" and returns the elements that was already there
				 
			 }else { //if lr == 'r'
				 while(jIndex != 0 && intervals.get(jIndex -1).rightEndPoint > varAt.rightEndPoint){
					 intervals.set(jIndex, intervals.get(jIndex - 1));
					 jIndex-=1;
				 }
				 intervals.set(jIndex, varAt);
			 }
			 
		 }
		 //Test out if sorted correctly
		 //System.out.println("Sort: " + "\n" + intervals.toString() + "\n"); 
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
		//Create Arraylist for storage
		ArrayList<Integer> sortedEndPoints = new ArrayList<Integer>();
		ArrayList<Integer> sortedEndPointNoDups = new ArrayList<Integer>();
		
		//Go through the leftSortedIntervals and add to sortedEndPoints
		for(int index = 0; index < leftSortedIntervals.size(); index++){
			sortedEndPoints.add(leftSortedIntervals.get(index).leftEndPoint);
			
		}
		
		for(int jIndex = 0; jIndex < rightSortedIntervals.size(); jIndex++){
				sortedEndPoints.add(rightSortedIntervals.get(jIndex).rightEndPoint);	
			
		}
		
		sorting(sortedEndPoints);//Private sorting method
		//Goes through the new ArrayList and fills it with all the items and no duplicate
		int count = 0;//count to keep track of the size
		while(sortedEndPoints.iterator().hasNext() && count < sortedEndPoints.size()){//while there are still elements in the array and the count is less than the size
			for(int i = 0; i<sortedEndPoints.size(); i++){//go through the array
				count++;//increase the count
				if(!sortedEndPointNoDups.contains(sortedEndPoints.get(i))){//if it's not in the array, add it
					sortedEndPointNoDups.add(sortedEndPoints.get(i));
			
				}//endif
			}//endfor
		}//endwhile
		
		//System.out.println("SortedEndPoints: " + "\n" + sortedEndPoints.toString() + "\n");
		//System.out.println("SortedEndPointsNoDups: " + "\n" + sortedEndPointNoDups.toString() + "\n");
		
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		return sortedEndPointNoDups;
	}
	private static ArrayList<Integer> sorting(ArrayList<Integer> sort){
		int jIndex;
		for(int index = 1; index < sort.size(); index++){
			Integer varAt = sort.get(index); 
			 jIndex = index; 
			 while(jIndex != 0 && sort.get(jIndex -1) > varAt){
				 sort.set(jIndex, sort.get(jIndex - 1));
				 jIndex-=1;
			 }
			 sort.set(jIndex, varAt);
		}
		return sort;
	}
}
