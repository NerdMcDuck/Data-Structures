package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
	/**
	 * Positions of opening brackets
	 */
	ArrayList<Integer> openingBracketIndex; 
    
	/**
	 * Positions of closing brackets
	 */
	ArrayList<Integer> closingBracketIndex; 

    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
        scalars = null;
        arrays = null;
        openingBracketIndex = null;
        closingBracketIndex = null;
    }

    /**
     * Matches parentheses and square brackets. Populates the openingBracketIndex and
     * closingBracketIndex array lists in such a way that closingBracketIndex[i] is
     * the position of the bracket in the expression that closes an opening bracket
     * at position openingBracketIndex[i]. For example, if the expression is:
     * <pre>
     *    (a+(b-c))*(d+A[4])
     * </pre>
     * then the method would return true, and the array lists would be set to:
     * <pre>
     *    openingBracketIndex: [0 3 10 14]
     *    closingBracketIndex: [8 7 17 16]
     * </pe>
     * 
     * See the FAQ in project description for more details.
     * 
     * @return True if brackets are matched correctly, false if not
     */
    public boolean isLegallyMatched() {
    	// COMPLETE THIS METHOD
    	//Initializing ArrayLists 
    	openingBracketIndex = new ArrayList<Integer>();
    	closingBracketIndex = new ArrayList<Integer>();
    	
    	Stack<Character> stk = new Stack<Character>();
		// scan expression
		for (int i=0; i < expr.length();i++) {
			char ch = expr.charAt(i);
			if (ch == '(' || ch == '[') {
				stk.push(ch);
				openingBracketIndex.add(i); //adds position of opening paren/brackets to arraylist
			} else if  (ch == ')' || ch == ']') {
				closingBracketIndex.add(i); //adds position of closing paren/brackets to arraylist 
				try {
					char ch2 = stk.pop();
					if (ch2 == '(' && ch == ')') {
						continue;
					}
					if (ch2 == '[' && ch == ']') {
						continue;
					}
					return false;
				} catch (NoSuchElementException e) {
					return false;
				}
			}
		}
    	return true;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, the constructors for ScalarSymbol and ArraySymbol
     * will initialize values to zero and null, respectively.
     * The actual values will be loaded from a file in the loadSymbolValues method.
     */
    
    public void buildSymbols() {
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
    	StringTokenizer st = new StringTokenizer(expr,delims); //tokenizes the string expression based on the predefined delimiters 
    	int numTokens = st.countTokens();
    	ArrayList<String> tokens = new ArrayList<String>(); //initializes an array with the size of the number of tokens in the string
    	
    	//Adds tokens into an array
    	while(st.hasMoreTokens()){ 
    		String sym = st.nextToken(); //new string symbols
    		tokens.add(sym); //adds symbols into an array
    	} //end of while
    	
    	for(int i = 0; i < tokens.size()-1; i++){ //goes through the tokens array
    		int tokenindex = expr.indexOf(tokens.get(i)); //returns element at index i, then returns the index of that element in expr 
    		if(i > 0){
    			char ch = expr.charAt(tokenindex - 1);
    			if(Character.isLetter(ch)){
    				continue;
    			}//continue
    			
    			if(expr.charAt(tokenindex + tokens.get(i).length()) == '['){
    				ArraySymbol array = new ArraySymbol(tokens.get(i));
    				if(arrays.equals(delims) || arrays.contains(array)){
    					continue;
    				} else {
    					arrays.add(array);
    				}//end of else
    			}
    			}// end if expr.charAt 
    		for(int j = 0; j < tokens.size() -1; j++){ //goes through tokens array again
    			ScalarSymbol scalar = new ScalarSymbol(tokens.get(j)); 
    			if(scalars.indexOf(scalar) == -1){ //if scalar variable is not found, then it adds it, else leaves it alone
    				scalars.add(scalar); 
    				
    			}//end if scalars
    		}//end for (int j)
    				
    			
    			
    		}// end if(i > 0)
    		
    	
    	// COMPLETE THIS METHOD
    	
    }//method ends
    
    
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions. (Note: you can use one or more private helper methods
     * to implement the recursion.)
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    	// COMPLETE THIS METHOD
    	//System.out.println("Tokens: " + tokens); //Test to see if adding correct tokens
    	System.out.println("arrays: " + arrays);
    	System.out.println("scalars: " + scalars);
    	Stack<String> operands = new Stack<String>();
    	Stack<String> operators = new Stack<String>();
    	StringTokenizer st = new StringTokenizer(expr, delims, true);
	    float Fresult = 0;
        /*while(st.hasMoreTokens()){
            String tokens = st.nextToken();
            
            if(tokens.equals("+")|| tokens.equals("-")|| tokens.matches("(")||tokens.equals("/") || tokens.equals("[")){
                
                operators.push(tokens);
            } else if (tokens.matches("[0-9]+")){
            	operands.push(tokens);
            } else if(tokens.equals(")") || tokens.equals("]")){
                    float ch2 = Integer.parseInt(operands.pop());
                    String operator = operators.pop();
                    float ch1 = Integer.parseInt(operands.pop());
                    
             if(!operators.isEmpty()){
                        operators.pop();
                    }
                    float result = 0;
                    if(operators.equals("*")){
                        result = ch1*ch2;
                    } else if(operators.equals("/")){
                        result = ch1/ch2;
                    } else if(operators.equals("+")){
                        result = ch1+ch2;
                    } else if(operators.equals("-")){
                        result = ch1-ch2;
                    }
                    
                    operands.push(result+"");
                }
        }
        // FOLLOWING LINE ADDED TO MAKE COMPILER HAPPY
        Fresult = Float.parseFloat(operands.pop());*/
    	return Fresult;
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    	for (ArraySymbol as: arrays) {
    		System.out.println(as);
    	}
    }

}
