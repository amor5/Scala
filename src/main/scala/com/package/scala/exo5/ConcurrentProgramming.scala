package com.package.scala.exo5


object ConcurrentProgramming extends App {

	import scala.concurrent._
	import ExecutionContext.Implicits.global

	val filePromise = Promise[List[String]]()
	val fileFuture = filePromise.future

	val fileLines = Future {

		println("Reading the file")
		import scala.io.Source

		val dataFile ="auto-data.csv"
			

		//Source created from file.
		val fileData = Source.fromFile(dataFile).getLines()

		var fileBuffer = scala.collection.mutable.Buffer[String]()

		for (line <- fileData) {
			fileBuffer += line
		}

		filePromise.success(fileBuffer.toList)
		println("Completed Reading file")
	}

	val useFile = Future {

		println("Waiting for file to complete ")

		fileFuture.onSuccess {
			case fileList => println("Lines in List : " + fileList.length)
							 
		}
		
	}
	
  	import scala.concurrent.duration._
	Await.result(useFile, 10 seconds)

}