package com.package.scala.exo6

object SlickExercice extends App{
  
	import slick.driver.MySQLDriver.api._
	import scala.concurrent.ExecutionContext.Implicits.global
	import scala.concurrent._
	import scala.concurrent.duration._

	import com.package.scala.scalaDatabase.Tables

	val db = Database.forURL(
		driver = "com.mysql.jdbc.Driver",
		user = "root", password = "",
		url = "jdbc:mysql://localhost:3306/employees")

	implicit val session = db.createSession()
	
	//Print department ID and manager name
	val innerJoin = for ( (m,e) <- 
				Tables.DeptManager
				join 
				Tables.Employees 
				on( _.empNo === _.empNo))
				yield ( m.deptNo, e.lastName )
				
	val joinResult = innerJoin.result
	val joinFuture: Future[Seq[(String,String)]] 
				= db.run(joinResult)
	
	println("\nDepartment Manager Name : ")
	joinFuture.onSuccess {
		case res => res.map{
				resRow => println(resRow._1 + " " + resRow._2)
		}
	}
	while ( ! joinFuture.isCompleted) {
		Thread.sleep(1000)
	}
	
				
}