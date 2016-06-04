package generics;
import java.util.NoSuchElementException;

public class Stack<T> {
	
	Node<T> head;
	
	//add to stack
	public void push(T data){
		
		head = new (data,head);

	}
	
	//remove from front of stack
	public T pop(){
		
		if (head == null)
			throw new NoSuchElementException();
		
		T tmp = head.data;
		head = head.next;
		return tmp;
		
	}
}
/* Loop start i=0 --> s.length()
 * if(s[i] is opening)
 * 		push[i]
 * else{ 
 * 	if(stack is empty)
 * 		return false
 * ch = stack.pop()l
 * 	if(s[i] !matches ch)
 * 		return false;
 * if(stack is empty)
 * 	return true;
 * else return false;
 */
