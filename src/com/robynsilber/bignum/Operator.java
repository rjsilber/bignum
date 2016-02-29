package com.robynsilber.bignum;

public class Operator extends ExpressionElement {
	
	private char c;
	
	protected Operator(String s){
		this.data = s;
		this.c = s.charAt(0);
	}
	
	protected char getC(){
		return this.c;
	}

}
