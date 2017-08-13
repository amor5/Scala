package com.package.scala.exo2

object Exo2 {

	/*--------------------------------------------------------
	Join a list to a map
	---------------------------------------------------------*/
	//Problem: Find the corresponding salary for an employee
	
	val employeeList: List[Int] = List(1001,1002,1003)
	val employeeSalary = Map(1001 -> 10000,
		1002 -> 15000, 1003 -> 12000)

	for (employee <- employeeList) {
		println("Salary for employee " + employee +
				" is " + employeeSalary(employee))
	}

	/*--------------------------------------------------------
	Do a word count of all the words in movietweets.csv
	---------------------------------------------------------*/
	//Problem: Find out how many times each word repeats in movietweets.csv
	//The file is available under resources directory
	
		val dataFile="movietweets.csv"
				

  //Source created from file
  import scala.io.Source
  
	val fileData = Source.fromFile(dataFile ).getLines()
	
	//Split each line into words and add the words to a Buffer
	var wordList = scala.collection.mutable.Buffer[String]()
	
	//Split first by comma and then by space
	for ( line <- fileData) {
		var firstSplit = line.split(",")
		for ( words1 <- firstSplit) {
			var words2 = words1.split(" ")
			for ( words3 <- words2) {
				wordList += words3
			}
		}
	}
	
	println(wordList.size)
	
	//Now create a map that holds the words and their counts
	var wordCounts = scala.collection.mutable.Map[String, Int]()
	
	for ( word <- wordList.toArray ) {
		if ( ! wordCounts.contains(word) ) {
			wordCounts += ( word -> 0 )
		}
		
		wordCounts(word) = wordCounts(word) + 1 
	}
	
	println("total positive : " + wordCounts("positive"))
	println(wordCounts)
	

}