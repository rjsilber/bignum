package com.robynsilber.bignum;

import java.util.ArrayList;
import java.util.Stack;

/* Expression is a class whose constructor takes the entire line resulting from performing
 * the function Scanner.nextLine() after first verifying that the next line in the text file
 * is not null {through use of the function Scanner.hasNextLine()} */

public class Expression {
	
	private int totalOperands;
	private int totalOperators;
	private String line;
	protected ArrayList<ExpressionElement> allElements;
	protected Stack<Operand> stack;
	
	protected Expression (String line){
		this.line = line;
		totalOperands = 0;
		totalOperators = 0;
		this.allElements = new ArrayList<ExpressionElement>();
		stack = new Stack<Operand>();
	}

	protected int getTotalOperands() {
		return totalOperands;
	}
	
	protected void incrementTotalOperands(){
		totalOperands++;
	}

	protected int getTotalOperators() {
		return totalOperators;
	}
	
	protected void incrementTotalOperators(){
		totalOperators++;
	}

	protected String getLine() {
		return line;
	}
	
	protected ArrayList<ExpressionElement> getAllElements(){
		return allElements;
	}
	
	protected void push(Operand oper){
		stack.push(oper);
	}
	
	protected Operand pop(){
		return stack.pop();
	}
	
	protected Operand peek(){
		return stack.peek();
	}
	
	protected int sizeOfStack(){
		return stack.size();
	}
	
	protected Stack<Operand> getStack(){
		return stack;
	}
	
	protected boolean isEmpty(){
		return stack.isEmpty();
	}
	
	protected void setFieldsToNull(){
		totalOperands = 0;
		totalOperators = 0;
		line = "";
		stack = null;
		this.allElements = null;
	}

}
