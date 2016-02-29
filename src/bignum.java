import java.io.File;
import com.robynsilber.bignum.ExpressionElementConstructor; /*INSTRUCTION 1: import package*/

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

public class bignum {

	public static void main(String[] args) {
		
		boolean fileFound = false;
		
		File temp = new File("tempfile.txt");
		String absolutePath = temp.getAbsolutePath();
		
		if(absolutePath.contains("tempfile.txt")){
			int charIndex = absolutePath.indexOf("tempfile.txt");
			absolutePath = absolutePath.substring(0, charIndex);
		}
		
		String inputFilePath = absolutePath + "input.txt";
		
		File inputFile = new File(inputFilePath);

		
		if(inputFile.exists()){
			
			fileFound = true;
			
		}else{
			char ch;
			if(absolutePath.startsWith("C:")){
				ch = absolutePath.charAt(2);
			}else if(absolutePath.startsWith("/Users")){
				ch = '/';
			}else{
				ch = ' ';
			}
			
			
			if(ch != ' '){
				if(absolutePath.contains("src")){
					int srcIndex = absolutePath.indexOf("src");
					absolutePath = absolutePath.substring(0, srcIndex);
					inputFilePath = absolutePath + "input.txt";
					inputFile = new File(inputFilePath);
						
				}else{
					inputFilePath = absolutePath + "src" + ch + "input.txt";
					inputFile = new File(inputFilePath);	
				}
				
				if(inputFile.exists()){
					fileFound = true;
				}else{
					System.out.println("File not found");
				}
			}else{
				System.out.println("File not found");
			}
			
			
		}
		
		
		if(fileFound){
			System.out.println("Program started!");
			ExpressionElementConstructor constructObj = new ExpressionElementConstructor(inputFile); // Constructor 2
			
			constructObj.printOutput(); // Output method
		}

	}

}
