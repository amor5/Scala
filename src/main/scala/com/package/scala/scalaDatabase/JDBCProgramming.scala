package com.package.scala.scalaDatabase

//Database source : https://github.com/datacharmer/test_db/
object JDBCProgramming extends App {
  
	import java.sql._
	//Initiate the Driver
	Class.forName("com.mysql.jdbc.Driver")
	
	//Making a connection
	val conn = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/employees",
			"root", "" )
	
	//Executing INSERT
	val insertSql="INSERT INTO employees VALUES (300001, '1978-04-18', 'Robert', 'Aubert', 'M', '1998-01-01')"
	val insertStmt = conn.prepareStatement(insertSql)
	insertStmt.executeUpdate()
	
	//Executing UPDATE
	val updateSql="UPDATE employees SET first_name='Richard' " +
			" WHERE emp_no = 300001"
	val updateStmt = conn.prepareStatement(updateSql)
	updateStmt.executeUpdate()
	
	//Executing DELETE
	val deleteSql="DELETE FROM employees WHERE emp_no = 300001"
	val deleteStmt = conn.prepareStatement(deleteSql)
	deleteStmt.executeUpdate()
	
	//Executing Queries
	val selectSQL="SELECT emp_no,first_name,last_name FROM employees "+
					"WHERE hire_date > '1993-01-01'"
	val selectStmt = conn.prepareStatement(selectSQL)
	val rsEmp = selectStmt.executeQuery()
	
	while (rsEmp.next()) {
		println(rsEmp.getString("emp_no") + " = " +
				rsEmp.getString("first_name") + " " +
				rsEmp.getString("last_name") )
				
	}
	
	
}