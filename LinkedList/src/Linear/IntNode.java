package Linear;

public class IntNode {

	public int data;
    public IntNode next;
    public IntNode(int data, IntNode next) {
        this.data = data; this.next = next;
    }
    public String toString() {
        return data + "";

    }
    public class StringNode {
        public String data;
        public StringNode next;
        public StringNode(String data, StringNode next) {
            this.data = data; this.next = next;
        }
        public String toString() {
            return data;
        }
    }
      

public static void traverse(IntNode front) {
	if (front == null) {
		System.out.println("Empty list");
		return;
	}
	System.out.print(front.data);  // first item
	IntNode ptr=front.next;    // prepare to loop starting with second item
	while (ptr != null) {
		System.out.print("->" + ptr.data);
		ptr = ptr.next;
	}
	System.out.println();
}

public static IntNode addToFront(IntNode front, int item) {
	/*
	IntNode ptr = new IntNode(item, null);
	ptr.next = front;
	front = ptr;
	return front;
	*/
	return new IntNode(item, front);
}

//Problem Set 2 - Problem 1
public static IntNode addBefore(IntNode front, int target, int newItem) {
  /* COMPLETE THIS METHOD */
		 
		IntNode newNode = new IntNode(newItem, null);
		IntNode ptr = front; //current node
		IntNode bptr = null; //previous node
		while(ptr!=null && ptr.data != target){
			
			bptr = ptr; //one before current pointer
			ptr = ptr.next; //current pointer
			if(ptr.data == target){
				bptr.next = newNode; //assigns previous pointer to the newly created node
				newNode.next = ptr; //change newNode pointer to point to current position
				newNode = bptr;		
		}
	}
	
	return front; //returns the front
	
	}

//Adds to the end method
public static IntNode addToRear(int data, IntNode head){
	IntNode newNode = new IntNode(data, null);
	if(head == null){
		return head;
	}
	IntNode curr = head; //will always be the current node
	IntNode prev = null; //will be a node behind current node
	
	while(curr != null){ //run as long as current node isn't null
		prev = curr; //make it equal to the current node
		curr = curr.next; //go to the next node
		
		if (curr == null){ //when curr is equal to null
			prev.next = newNode; //
			newNode.next = curr;
			newNode = prev;
		}
	}
	return head;
				
}

//delete Method
public static IntNode delete(int target, IntNode head){
	if (head==null){
		return null;
	}
	IntNode curr = head;
	IntNode prev = null;
	
	while(curr != null && curr.data != target){
		prev = curr;
		curr = curr.next;
		
		if (curr.data == target){
			prev.next = curr.next;	
			prev = curr;
		}
	}
	return head;
}

public static IntNode deleteFromFront(IntNode head){
	return head.next;
}
	

public static void main(String[] args){
	IntNode front = null;
	traverse(front);  // test traverse on empty list
	front = addToFront(front,4);
	traverse(front);
	front = addToFront(front,6);
	traverse(front);
	front = addToFront(front,8);
	traverse(front);
	front = addToFront(front,9);
	traverse(front);
	front = addToFront(front,89);
	traverse(front);
	front = addBefore(front,8,12);
	traverse(front);
	front = addBefore(front,6,19);
	traverse(front);
	front = addBefore(front,4,69);
	traverse(front);
	front = addToRear(20,front);
	traverse(front);
	front = addToRear(30,front);
	traverse(front);
	front = delete(69,front);
	traverse(front);
	System.out.println("Delete the front Node");
	front = deleteFromFront(front);
	traverse(front);
	
}

}