package com.robynsilber.bignum;

public abstract class ExpressionElement {
	
	protected String data;
	
	protected String getData(){
		return data;
	}
	
	protected boolean isOperator(){
		if(data.contains("+") || (data.contains("*")) || (data.contains("^"))){
			return true;
		}else return false;
	}

}
