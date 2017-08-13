package com.package.scala.exo1

import org.junit._
import org.junit.Assert._
import java.io._

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

object ApplyTestSuite {
  
}

@Test
class ApplyTestSuite { 
	
	@Test
	def testMovieList = {	
		writeToOutputFile("Getting full list")
		writeToOutputFile(MoviesFacade.getMoviesList(Map[String,String]()))
	}
	
	def writeToOutputFile(output:String):Unit = {
	
		val writer = new FileWriter(new File("c:/temp/applytesting/output.txt" ),true)
		writer.append("\n***************New Test***************\n")
		writer.append(output)
		writer.close()
	}

}

@RunWith(classOf[JUnitRunner])
class FunApplyTestSuite extends FunSuite with ShouldMatchers{
	
	//Test query 
	test("Movie Query : Success Path") {
		val inputQuery = """{"year":1981}"""
		val retString = MoviesFacade.getMoviesList(inputQuery)
		//Find no. of movies returned
		val noRecords = "actor".r.findAllMatchIn(retString).length
		println(noRecords)
		noRecords should be >= 2
  	}
	
	//Test insert error
	test ("Movie Insert : Already exists") {
		val inputInsert = """{"actor":"Ford, Harrison","popularity":8,"subject":"Action","year":1981,"length":116,"title":"Raiders of the Lost Ark"}"""
		val retCode = MoviesFacade.addMovie(inputInsert)
		val existsPos = "exists".r.findAllMatchIn(retCode).length
		
		existsPos should be > 0
	}
	
	//Test Successful update
	test ("Movie Update : Successful update") {
		val inputUpdate = """{"popularity":12, "year":1981, "title":"Raiders of the Lost Ark"}"""
		val retCode = MoviesFacade.updatePopularity(inputUpdate)
		val successPos = "successfully".r.findAllMatchIn(retCode).length
		
		successPos should be > 0
	}

}