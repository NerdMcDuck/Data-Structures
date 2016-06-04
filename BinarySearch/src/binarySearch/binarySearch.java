package binarySearch;

import java.util.ArrayList;

public class binarySearch {

	public static void main(String[] args) {
		
		int[] arr = new int[4];
		System.out.println(arr[1]);
		// TODO Auto-generated method stub
		ArrayList<Integer> occs = new ArrayList<Integer>(); 
		occs.add(12);
		occs.add(8);
		occs.add(7);
		occs.add(5);
		occs.add(3);
		occs.add(2);
		occs.add(6);
	
		
		System.out.println("ArrayList: " + occs);
		ArrayList<Integer> occsAscend = ascendingOrder(occs);
		System.out.println("Ascending: " + occsAscend);
		ArrayList<Integer> midPoints = new ArrayList<Integer>(); //the midpoints
		
		int high = occsAscend.size()-1; //index of last element
		int low = 1; //index of first element
		int target = occsAscend.get(0); //the target's frequency, which can be found at the last index of the Occurrence ArrayList
		System.out.println("high: " +  occsAscend.get(high) + " low: " + occsAscend.get(low) + " target: " + target);
		while(low <= high){ //start of Binary Search, 
			
			int mid = (high+low)/2; //midpoint
			midPoints.add(mid);
			
			System.out.println("mid: " + mid);
			
			if(target == occsAscend.get(mid)){ //if targets' frequency equals the midpoint
				System.out.println("entered1");
				midPoints.add(mid); //add the target
				occsAscend.add(mid, target);
				occsAscend.remove(0);
				System.out.println("occsAscendAdd: " + occsAscend);
				break; //exit loop
				
			} else if(target >occsAscend.get(mid)) { //if the target is greater than the mid
				System.out.println("entered2");
				low = mid + 1;
				
			} else {
				System.out.println("entered3");
				high = mid - 1;
			}
				
		}//endWhile
		
		
		
		System.out.println("MidPoints: " + midPoints);

		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	}
	
	private static ArrayList<Integer> ascendingOrder(ArrayList<Integer> arr){
		ArrayList<Integer> asc = new ArrayList<Integer>();
		for(int i = arr.size()-1; i >= 0; i--){
			asc.add(arr.get(i));
		}
		return asc;
	}
	

}
