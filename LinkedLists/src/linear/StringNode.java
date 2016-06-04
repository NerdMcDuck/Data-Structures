package linear;

public class StringNode {
	
	public String data; //data field
	
	public StringNode next; //next field 
	
	public StringNode(String data, StringNode next){ //constructor
		this.data = data;
		this.next = next;
	}
	
	public String toString(){
		return data;
	}
}
