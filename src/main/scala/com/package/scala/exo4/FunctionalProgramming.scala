package com.package.scala.exo4


object FunctionalProgramming extends App {
  
	//Input class - impure
	def convertToInt(inStr:String) : Int = {
		
		if ( inStr.forall(x => Character.isDigit(x)) ) {
			inStr.toInt
		}
		else {
			println("Input String is not a numeric value " + inStr)
			-1
		}
	}
	
	println(convertToInt("445532"))
	println(convertToInt("54B43"))
	
	
	//Solution class - pure
	def convertToInt2(inStr:String) : Int = {
		
		if ( isAllDigits(inStr) ) {
			inStr.toInt
		}
		else {
			printError(inStr)
			-1
		}

	}
	
	//Function to check if a string is all digits
	def isAllDigits(x: String) = x forall Character.isDigit
	
	//Function to print an error
	def printError(inStr:String) : Unit = {
		println("Input String is not a numeric value " + inStr)
	}
	
	println(convertToInt2("445532"))
	println(convertToInt2("54B43"))
}