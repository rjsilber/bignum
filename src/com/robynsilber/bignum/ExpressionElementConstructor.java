package com.robynsilber.bignum;

/* PACKAGE com.robynsilber.bignum INSTRUCTIONS:
 * 
 * 1) Import the package com.robynsilber.bignum
 * 
 * 2) Instantiate ExpressionElementConstructor using one of the two provided constructor methods:
 * 		
 * 		-constructor1:  ExpressionElementConstructor constructObj = new ExpressionElementConstructor("input.txt");
 * 			 -OR-
 * 		-constructor2:	java.io.File file = new java.io.File("input.txt");
 * 						ExpressionElementConstructor constructObj = new ExpressionElementConstructor(file);	
 *
 * 3) Print the output to the console by invoking the ExpressionElementConstructor method printOutput():
 * 
 * 		-method call:	constructObj.printOutput();
 * */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ExpressionElementConstructor {
	
	protected java.io.File file;
	protected static Scanner input;
	protected ArrayList<String> textFileStrings;
	protected ArrayList<String> output;
	
	/*Constructor can either accept a String that is the pathname
	 * of the file to be input, or a java.io.File object*/
	
	//Constructor 1
	public ExpressionElementConstructor(String filePathName){
		file = new java.io.File(filePathName);
		constructExpressionElementConstructor();
	}
	
	//Constructor 2
	public ExpressionElementConstructor(java.io.File newFile){
		this.file = newFile;
		constructExpressionElementConstructor();
	}
	
	
	private void constructExpressionElementConstructor(){
		output = new ArrayList<String>();
//		String programStarted = "Program started!\n";
		String errorMsg = "\nError: wrong expression!\n";
		
//		output.add(programStarted);
		Scanner input = null;
		try{
			input = new Scanner(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		textFileStrings = hasNextLineArrList(input);
		input.close();
		
		
		for(int i=0; i<textFileStrings.size(); i++){
			String lineOfText = "\n" + textFileStrings.get(i);
			
			Expression expression = null;
			
			StringTokenizer strToken = new StringTokenizer(lineOfText);
			
			if(strToken.hasMoreElements()){
				expression = new Expression(lineOfText);
			}
			
			while(strToken.hasMoreElements()){
				String token = strToken.nextToken();
				Operator operator = constructExpressionElement(expression, token); //returns null if input token was an operand
				
				if(operator != null){ //Expression has located an Operator in the text line
					
					if(expression.sizeOfStack() >= 2){ //The stack must have at least two Operand objects in it
						performMathOperation(expression, operator);
					}else{ // Incorrect format of expression and/or insufficient number of operands
						output.add(lineOfText);
						output.add(errorMsg);
						expression.setFieldsToNull();
						expression = null;
						break; //breaks out of the while loop
					}
				}
			}
			
			if(expression != null){
				
				if(expression.getTotalOperands() > (expression.getTotalOperators() + 1)){
					//Invalid number of operators per operands
					output.add(lineOfText);
					output.add(errorMsg);
				}else if(expression.getTotalOperands() > (expression.getTotalOperators() + 1)){
					//Invalid number of operators per operands
					output.add(lineOfText);
					output.add(errorMsg);
				}else{
					if(!expression.stack.isEmpty()){
						
						if(expression.stack.size() == 1){
							ArrayList<Integer> digitsToPrint = expression.pop().getDigits();
							lineOfText += "  =";
							output.add(lineOfText);
							String computationDigits = "\n";
							for(int j=digitsToPrint.size()-1; j>=0; j--){
								computationDigits += digitsToPrint.get(j);
							}
							computationDigits += "\n";
							output.add(computationDigits);
						}else{ //if stack.size() > 1
							output.add(lineOfText);
							output.add(errorMsg);
						}

					}else{ //if stack is empty
						output.add(lineOfText);
						output.add(errorMsg);
					}
					
				}
				
				expression.setFieldsToNull();
				expression = null;
			
			}
			strToken = null;
		}

	}
	
	
	
	
	private ArrayList<String> hasNextLineArrList(Scanner input){
		ArrayList<String> arrl = new ArrayList<String>();
		
		while(input.hasNextLine()){
			String line = input.nextLine();
			arrl.add(line);
		}
		return arrl;
	}
	
	
	
	
	private Operator constructExpressionElement(Expression expression, String s){
		
		if(s.length() > 0){
			if((s.length()==1) && ((s.contains("+")) || (s.contains("*")) || (s.contains("^")))){
				Operator operator = new Operator(s);
				expression.allElements.add(operator);
				expression.incrementTotalOperators();
				return operator;
			}else{ //Note: else statement relies on the assumption that no erroneous characters will exist in the text file
				Operand operand = new Operand(s);
				expression.allElements.add(operand);
				expression.push(operand);
				expression.incrementTotalOperands();
				return null;
			}
		}
		
		return null;
	}
	
	
	
	
	private void performMathOperation(Expression expression, Operator operator){

		if((operator.getC() == '+') || (operator.getC() == '*') || (operator.getC() == '^')){
			ArrayList<Integer> dig2 = expression.pop().getDigits();
			ArrayList<Integer> dig1 = expression.pop().getDigits();
			
			ArrayList<Integer> resultReturned = new ArrayList<Integer>();
			
			Operand operand = null;
			
			if(operator.getC() == '+'){
				resultReturned = performAddition(dig1, dig2);
				operand = new Operand(removeLeadingZeros(resultReturned));
				expression.push(operand);
				
			}else if(operator.getC() == '*'){
				resultReturned = performMultiplication(dig1, dig2);
				operand = new Operand(removeLeadingZeros(resultReturned));
				expression.push(operand);
				
			}else if(operator.getC() == '^'){
				//Exponent value is guaranteed to be an integer small enough to be stored in memory
				int t = 1;
				int powerRaised = 0;
				for(int i=0; i<dig2.size(); i++){
					powerRaised += (t * dig2.get(i));
					t *= 10;
				}
				
				resultReturned = performExponentiation(dig1, powerRaised);
				operand = new Operand(removeLeadingZeros(resultReturned));
				expression.push(operand);
			}
		}
	}
	
	
	
	
	private ArrayList<Integer> performAddition(ArrayList<Integer> dig1, ArrayList<Integer> dig2){
		
		int i, j;
		int size1 = dig1.size(); //index: i
		int size2 = dig2.size(); //index: j
		
		int a = 0;
		int r = 0; //remainder
		int c = 0; //carry value
		
		ArrayList<Integer> digitsAdded = new ArrayList<Integer>();
		
		if(size1 == size2){
			for(i=0; i<size1; i++){
				a = dig1.get(i) + dig2.get(i) + c;
				c = 0;
				if(a < 10){
					digitsAdded.add(a);
				}else{
					r = a % 10;
					digitsAdded.add(r);
					c = a / 10; // integer division
				}
			}
			
			while(c > 0){
				r = c % 10;
				digitsAdded.add(r);
				c = c / 10;
			}
			
		}else if(size1 > size2){
			i=0;
			for(j=0; j<size2; j++){
				a = dig1.get(j) + dig2.get(j) + c;
				c = 0;
				if(a < 10){
					digitsAdded.add(a);
				}else{
					r = a % 10;
					digitsAdded.add(r);
					c = a / 10;
				}
			}for(i=j; i<size1; i++){
				a = dig1.get(i) + c;
				c = 0;
				if(a < 10){
					digitsAdded.add(a);
				}else{
					r = a % 10;
					digitsAdded.add(r);
					c = a / 10;
				}
			}while(c > 0){
				r = c % 10;
				digitsAdded.add(r);
				c = c / 10;
			}
			
		}else{ //size1 < size2
			for(i=0; i<size1; i++){
				a = dig1.get(i) + dig2.get(i) + c;
				c = 0;
				if(a < 10){
					digitsAdded.add(a);
				}else{
					r = a % 10;
					digitsAdded.add(r);
					c = a / 10;
				}
			}for(j=i; j<size2; j++){
				a = dig2.get(j) + c;
				c = 0;
				if(a < 10){
					digitsAdded.add(a);
				}else{
					r = a % 10;
					digitsAdded.add(r);
					c = a / 10;
				}
			}while(c > 0){
				r = c % 10;
				digitsAdded.add(r);
				c = c / 10;
			}
		}
		
		ArrayList<Integer> additionArrList = removeLeadingZeros(digitsAdded);
		
		return additionArrList;
	}
	
	
	private ArrayList<Integer> performMultiplication(ArrayList<Integer> digs1, ArrayList<Integer> digs2){
		
		ArrayList<Integer> digits1 = new ArrayList<Integer>();
		ArrayList<Integer> digits2 = new ArrayList<Integer>();
		
		if(digits1.size() != digits2.size()){
			if(digits1.size() > digits2.size()){
				digits1 = digs1;
				digits2 = digs2;
			}else{
				digits1 = digs2;
				digits2 = digs1;
			}
		}else{
			digits1 = digs1;
			digits2 = digs2;
		}
		
		int i, j;
		
		int size1 = digits1.size(); //index: i
		int size2 = digits2.size(); //index: j
		
		int m; // stores the result of multiplying two digits
		int c = 0; // the value carried over
		int d1, d2; //digits
		int r; //remainder
		
		ArrayList<ArrayList<Integer>> valuesToSum = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> vals;
		
		for(j=0; j<size2; j++){
			d2 = digits2.get(j);
			vals = new ArrayList<Integer>();
			int temp = j;
			while(temp > 0){
				vals.add(0);
				temp--;
			}
			for(i=0; i<size1; i++){
				d1 = digits1.get(i);
				m = (d1*d2) + c;
				if(m < 10){
					vals.add(m);
					c = 0;
				}else{
					r = m % 10;
					vals.add(r);
					c = m / 10;
				}
			}
			if(c > 0){
				if(c < 10){
					vals.add(c);
				}else{
					while(c > 0){
						r = c % 10;
						vals.add(r);
						c = c / 10;
					}
				}
			}
			valuesToSum.add(vals);
			vals = null;
			c = 0;
			m = 0;
			r = 0;
		}
		
		ArrayList<Integer> sum0 = new ArrayList<Integer>();
		ArrayList<Integer> sum1 = new ArrayList<Integer>();
		
		while(valuesToSum.size() > 1){
			sum1 = valuesToSum.remove(1);
			sum0 = valuesToSum.remove(0);
			ArrayList<Integer> resultOfSum = performAddition(sum0, sum1);

			valuesToSum.add(resultOfSum);
			
			sum0 = null;
			sum1 = null;
		}
		
		ArrayList<Integer> finishedResult = valuesToSum.get(0);
		
		return finishedResult;
	}
	
	
	private ArrayList<Integer> removeLeadingZeros(ArrayList<Integer> al){
		
		ArrayList<Integer> arrLst = new ArrayList<Integer>();
		for(int i=0; i<al.size(); i++){
			arrLst.add(al.get(i));
		}
		
		if((arrLst.size() <= 1) || (arrLst.get(arrLst.size()-1) > 0)){
			return arrLst;
		}
		
		int size = arrLst.size();
		int indexLast = size - 1;
		int elementLast = arrLst.get(indexLast);
		
		while((size > 1) && (elementLast == 0)){
			arrLst.remove(indexLast);
			size--;
			indexLast--;
			elementLast = arrLst.get(indexLast);
		}
		
		return arrLst;
	}

	
	
	private ArrayList<Integer> performExponentiation(ArrayList<Integer> digitsArr, int power){
		
		ArrayList<Integer> valuesToMultiply = new ArrayList<Integer>();
		valuesToMultiply = digitsArr;
		
		if(power == 0){  //base case 0
			/*This if-case is only accessed if the method call argument for the int power = 0*/
			ArrayList<Integer> newArrList = new ArrayList<Integer>();
			newArrList.add(1);
			return newArrList;
		}
		if(power == 1){ //base case 1
			return digitsArr;
		}
		
		//else: call on performExponentiation recursively, by decrementing power by 1
		valuesToMultiply = performMultiplication(digitsArr, (performExponentiation(valuesToMultiply, power-1)));
		
		return valuesToMultiply;
	}
	
	
	
	public void printOutput(){
		for(int i=0; i<output.size(); i++){
			System.out.print(output.get(i));
		}
	}
	
	
	
}
