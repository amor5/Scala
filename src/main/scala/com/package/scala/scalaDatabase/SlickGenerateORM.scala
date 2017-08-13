package com.package.scala.scalaDataBase

object GenerateORM extends App {

	import slick._
	
	slick.codegen.SourceCodeGenerator.main(
  			Array("slick.driver.MySQLDriver", 
  				"com.mysql.jdbc.Driver", 
  				"jdbc:mysql://localhost:3306/employees", 
  				"src/main/scala", 
  				"com.package.scala.scalaDataBase", 
  				"root", 
  				""))

}