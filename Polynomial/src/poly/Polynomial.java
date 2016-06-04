package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		Node poly1 = this.poly; //poly1
		Polynomial sum = new Polynomial(); 
		sum.poly = new Node(0,0,null);
		sum.poly.next = new Node(0,0,null);
		Node tmpNode = null;
		Node end = sum.poly.next;
		
		if(p.poly == null){ //p.poly is the polynomial being added = poly2
			return this; //if empty, returns first polynomial
		}
		if(poly1 == null){
			return p;
		}
	

		while(poly1 != null && p.poly != null){ //while not null
			
			if(p.poly.term.degree == poly1.term.degree){ //same degree
			
				if(p.poly.term.coeff + poly1.term.coeff == 0){ //if coefficients add up to 0
					p.poly = p.poly.next; poly1 = poly1.next; //just move to the next node
					continue; //continue on
				}
			
				tmpNode = new Node(p.poly.term.coeff + poly1.term.coeff,p.poly.term.degree,null); //creates new node with (sum of coeffs,degree(any since same degree),next=null)
				end.next = tmpNode; //good does what it needs to do, (about time)
				end = tmpNode; //just set sum.poly.next to our new tmpNode
				
				p.poly = p.poly.next; poly1 = poly1.next;//both pointers move to next node
				
			} else if(p.poly.term.degree > poly1.term.degree){
				
				if(p.poly.term.coeff + poly1.term.coeff == 0){
					poly1 = poly1.next;
				}
				
				tmpNode = new Node(poly1.term.coeff,poly1.term.degree,null);
				end.next = tmpNode;
				end = tmpNode;
				poly1 = poly1.next;//this.poly pointer jumps to next node
				
			} else if(p.poly.term.degree < poly1.term.degree) {
				
				if(p.poly.term.coeff + poly1.term.coeff == 0){
					p.poly = p.poly.next; 
				}
				tmpNode = new Node(p.poly.term.coeff,p.poly.term.degree,null);
				end.next = tmpNode;
				end = tmpNode;
				p.poly = p.poly.next; //p pointer goes to next node
			}
			
		}
		
		while(p.poly != null){ //if poly1 is null
			
			tmpNode = new Node(p.poly.term.coeff,p.poly.term.degree,null);
			end.next = tmpNode;
			end = tmpNode;
			p.poly = p.poly.next;
		} 
		
		while(poly1 != null){//if p.poly is null
			
			tmpNode = new Node(poly1.term.coeff,poly1.term.degree,null);
			end.next = tmpNode;
			end = tmpNode;
			poly1 = poly1.next;
		}
		
	    sum.poly = sum.poly.next.next; //gets rid of the trailing zeros
		return sum;
		}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		/** COMPLETE THIS METHOD **/
		
		Node poly1 = this.poly; //poly1
		Node poly2 = p.poly;
		Polynomial product = new Polynomial(); 
		product.poly = new Node(0,0,null);
		Polynomial tmppoly = new Polynomial();
		 
		
		if(poly1 == null){ //p.poly is the polynomial being multiplied = poly1
			return tmppoly; //if empty, returns 0 since anything time 0 is 0
		}
		if(poly2 == null){ //same thing
			return tmppoly;
		} 
		
		while(poly1 != null){ //goes through this.poly or the first polynomial inputed
			
			if(poly1.term.coeff == 0){
				poly1 = poly1.next;
				continue;
			}
			
			while(poly2 != null){ //goes through p.poly
				
				Polynomial tmpPolynomial = new Polynomial();
				tmpPolynomial.poly = new Node(0,0,null);
				tmpPolynomial.poly.next = new Node(0,0,null);
				
		
			Node tmpNode = new Node(poly1.term.coeff * poly2.term.coeff,poly1.term.degree + poly2.term.degree,null);
			product.poly = tmpNode;
			
			 //tmppoly= tmppoly.add(product);
			
			poly2 = poly2.next;
			
			}
			
			poly1 = poly1.next;
			tmppoly= tmppoly.add(product);
			poly2 = p.poly;
		}
		product = tmppoly;
		return product;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		/** COMPLETE THIS METHOD **/
		float result = 0; 
		
		for(Node poly = this.poly; poly != null; poly = poly.next ){
			
			result = result + poly.term.coeff * (float)Math.pow(x, poly.term.degree);
			
		}
		return result;
	}
		

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
