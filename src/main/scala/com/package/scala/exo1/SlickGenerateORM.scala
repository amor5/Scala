package com.package.scala.exo1

object GenerateMoviesORM extends App {
  
/*	slick.codegen.SourceCodeGenerator.main(
  Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, password)
)*/
	import slick._
	
	slick.codegen.SourceCodeGenerator.main(
  			Array("slick.driver.MySQLDriver", 
  				"com.mysql.jdbc.Driver", 
  				"jdbc:mysql://localhost:3306/movies", 
  				"src/main/scala", 
  				"com.package.scala.exo1", 
  				"root", 
  				""))

}