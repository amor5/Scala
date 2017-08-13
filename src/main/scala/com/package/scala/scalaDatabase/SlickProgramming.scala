package com.package.scala.scalaDataBase

object SlickProgramming extends App {

	import slick.driver.MySQLDriver.api._
	import scala.concurrent.ExecutionContext.Implicits.global

	import scala.concurrent._
	import scala.concurrent.duration._
	import java.text.{ DateFormat, SimpleDateFormat }
	import com.package.scala.scalaDataBase.Tables
	
	//Create a connection and a Session	
	val db = Database.forURL(
		driver = "com.mysql.jdbc.Driver",
		user = "root", password = "",
		url = "jdbc:mysql://localhost:3306/employees")

	implicit val session = db.createSession()

	//Regular querying using SQL
	println("\nDoing regular SQL queries")
	val stmt = session.prepareStatement("SELECT * FROM employees limit 10")
	val rs = stmt.executeQuery()
	while (rs.next()) {
		println(rs.getString("last_name"))
	}
	
	//Doing Simple Queries asynchronously
	println("\nDoing async simple Department Query :")
	val deptQuery = for (dept 
							<- Tables.Departments
								.sortBy { x => x.deptNo }) 
						yield dept
						
	val deptResults = deptQuery.result
	val queryFuture: Future[Seq[Tables.DepartmentsRow]] 
					= db.run(deptResults)

	queryFuture.onSuccess {
		case dept => dept.map {
			dRow => println(dRow.deptNo + 
						" - " + dRow.deptName)
		}
	}

	Await.result(queryFuture, 10 seconds)

	//Doing Queries with filtering
	import slick.backend._

	println("\nDoing employee query with row and column filters :")
	val empQuery = for ( 
			emp <- Tables.Employees 
				if (emp.empNo === 10001)
					&& ( emp.firstName like "%") )
		 yield emp.lastName
		
	val empResult = empQuery.result

	val queryFuture2: Future[Seq[String]] = db.run(empResult)

	queryFuture2.onSuccess {
		case emp => emp.map {
			dRow => println(emp(0))
		}
	}
	queryFuture2.onFailure {
		case error => println("Error found " 
				+ error.getMessage)
	}

	while (! queryFuture2.isCompleted ) {
		println("waiting")
		Thread.sleep(1000)
	}


	println("\nDoing inserts")
	//Doing Inserts using Futures.
	val df = new SimpleDateFormat("yyyy/MM/dd");
	
	val insert = DBIO.seq(
		Tables.Departments += Tables.DepartmentsRow("1001", "Testing"),
		Tables.Employees += Tables.EmployeesRow(
			1001,
			new java.sql.Date(df.parse("1970/12/12").getTime),
			"Steve", "Smith", "M",
			new java.sql.Date(df.parse("1995/01/01").getTime)))

	val insFuture = db.run(insert)

	Await.result(insFuture, 10 seconds)



	println("\nDoing updates :")
	//Doing updates
	val updateQuery = Tables.Employees.
		filter { emp => emp.firstName === "Domenick" }.
			map { emp => emp.firstName }.
				update("Domenick1")
				
	val updateFuture = db.run(updateQuery)
	updateFuture.onSuccess {
				case comp => println("Update completed :" + comp)			
	}
	
	//Doing deletes
	println("\nDoing deletes :")
	val deleteQuery = Tables.Employees.
		filter { emp => emp.empNo === 1001 }.
			delete
			
	val deleteFuture = db.run(deleteQuery)
	deleteFuture.onSuccess {
			case comp => println("Delete completed :" + comp)
		}
		
	Await.result(deleteFuture, 10 seconds)
	
	//Doing joins and group by
	
	val innerJoin = for ( (e,s) <- 
				Tables.Employees.
					filter { emp => emp.empNo <= 10010}
				join 
				Tables.Salaries 
				on( _.empNo === _.empNo)) 
				yield ( e.lastName, s.salary )
	
	//Group by first column
	val groupBy = innerJoin.groupBy(_._1)
	
	//Aggregate
	val aggregate = groupBy.map { 
		case (lastName, salRecord) =>
  			(lastName, salRecord.length, salRecord.map(_._2).avg)
}
	
	val joinResult = aggregate.result
	val joinFuture: Future[Seq[(String,Int,Option[Int])]] 
						= db.run(joinResult)
	
	println("\n Results on Join")
	joinFuture.onSuccess {
		case res => 
			println("\ntotal join records = " + res.length)
			res.map {
				resRow => println(resRow._1 
								+ "," + resRow._2
								+ "," + resRow._3.get )
			}
	}
	
	while (! joinFuture.isCompleted ) {
		println("waiting for Join")
		Thread.sleep(1000)
	}

}