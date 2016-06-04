package linear;

public class IntNode {
	
	public int data; //data field
	
	public IntNode next; //next field 
	
	public IntNode(int data, IntNode next){ //constructor
		this.data = data;
		this.next = next;
	}
	
	public String toString(){
		return data + "";
	}
}
