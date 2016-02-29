package com.robynsilber.bignum;

import java.util.ArrayList;

public class Operand extends ExpressionElement{
	
	private ArrayList<Integer> digits; 
	
	protected Operand(String s){
		this.data = s;
		this.digits = new ArrayList<Integer>();
		
		int i = 0;
		boolean nonZeroFound = false;
		while(i < data.length()){
			char l = data.charAt(i);
			int num;
			
			switch (l){
				case '1':	num = 1;	break;
				case '2':	num = 2; 	break;
				case '3':	num = 3;	break;
				case '4':	num = 4; 	break;
				case '5':	num = 5;	break;
				case '6':	num = 6; 	break;
				case '7':	num = 7;	break;
				case '8':	num = 8; 	break;
				case '9':	num = 9;	break;
				case '0':	num = 0; 	break;
				default: 	num = 0; 	break;
			}
			
			if(!nonZeroFound){
				if( (num > 0) || ((num == 0)&&(i == data.length()-1)) ){
					nonZeroFound = true;
				}
			}
			
			if(nonZeroFound){
				digits.add(0, num); //this will reverse the order of the digits
			}
			
			i++;
		}
	}
	
	protected Operand(ArrayList<Integer> digs){
		this.digits = new ArrayList<Integer>(digs);
		String string = "";
		for(int i=digits.size()-1; i>=0; i--){
			string += digits.get(i);
		}
		this.data = string;
	}
	
	protected ArrayList<Integer> getDigits(){
		return this.digits;
	}
	
	
	
	
}
