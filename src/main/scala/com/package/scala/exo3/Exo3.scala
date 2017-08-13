package com.package.scala.exo3

object Exo3OOP extends App {
  
	trait Contact {
		val email:String
		var zipcode:String = "0"
		
		def sendMail = {
			println("Sending email to " + email)
			//send actual email
		}
	}
	
	class Visitor(val name:String, val email:String) extends Contact{
		
		var age=0
		
		def isSenior:Boolean = {
			 ( age > 60)
		}
	}
	
	val visit = new Visitor("Jhon","Jhon@hotmail.com")
	visit.sendMail
	visit.age=35
	
	println( visit.name + " Is senior citizen = " + visit.isSenior)
}