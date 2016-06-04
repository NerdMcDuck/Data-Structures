package generics;

public class LinkedList {
	
	public void addToFront(T data){
		
		Node<T> n = new Node(data,null);
		n.next = head;
		head = n;
		}
	
	public static void main(String[] args){
		
		LinkedList<String> list= new LinkedList();
		list.addToFront("hello");
		
		LinkedList<Integer> list2 = new LinkedList();
		list2.addToFront(new Integer(4));
	}

}

