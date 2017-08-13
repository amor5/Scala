package com.package.scala.exo1

object MoviesFacade extends App {

	/*initializing code*/
	import slick.driver.MySQLDriver.api._
	import scala.concurrent.ExecutionContext.Implicits.global
	import com.package.scala.exo1.Tables
	import slick.backend._
	import scala.concurrent._
	import scala.concurrent.duration._
	import java.text.{ DateFormat, SimpleDateFormat }
	import org.json4s._
	import org.json4s.native.JsonMethods._
	import org.json4s.JsonDSL._

	//Case class that maps to the input JSON
	//Using MovielistRow would give errors - Scala bug
	case class MovieInput(year: Int, length: Option[Int] = None, title: String, subject: Option[String] = None, actor: Option[String] = None, popularity: Option[Int] = None)

	//Function that returns a Session to the database
	def getSession(): Session = {

		val db = Database.forURL(
			driver = "com.mysql.jdbc.Driver",
			user = "root", password = "",
			url = "jdbc:mysql://localhost:3306/movies")

		val session = db.createSession()
		return session
	}

	//Function that converts a MovielistRow to a JSON String
	def convertToJson(movie: Tables.MovielistRow): String = {

		implicit val formats = DefaultFormats

		val jsonMap: Map[String, String] = Map("title" -> movie.title,
			"year" -> movie.year.toString(),
			"length" -> movie.length.getOrElse("N/A").toString(),
			"subject" -> movie.subject.get,
			"actor" -> movie.actor.get,
			"popularity" -> movie.popularity.getOrElse("N/A").toString())

		compact(render(jsonMap))
	}

	//General query creator based on input filters
	def getQuery(filters: Map[String, String]): 
			Query[Tables.Movielist, Tables.MovielistRow, Seq] = {

		//Title like
		val titleFilter = if (filters.contains("title"))
			filters.get("title").get + "%"
		else "%"
		//Year greater than said value
		val yearFilter = if (filters.contains("year"))
			filters.get("year").get.toInt else 0
		//Actor like
		val actorFilter = if (filters.contains("actor"))
			filters.get("actor").get + "%"
		else "%"

		val movieQuery = for (
			movies <- Tables.Movielist 
				if ((movies.year >= yearFilter)
				&& (movies.actor like actorFilter)
				&& (movies.title like titleFilter))
		) yield movies

		return movieQuery
	}

	//Get Movies List based on input filter JSON String
	def getMoviesList(movieStr: String): String = {
		implicit val formats = DefaultFormats
		return getMoviesList(parse(movieStr)
				.extract[Map[String, String]])
	}

	//Get Movies List based on input Map
	def getMoviesList(filters: Map[String, String]): String = {

		println(filters)
		val movieQuery = getQuery(filters)

		val movieResult = movieQuery.result

		val queryFuture: Future[Seq[Tables.MovielistRow]] 
			= getSession().database.run(movieResult)

		var retval: String = ""
		var processOver = false
		queryFuture.onSuccess {

			case movies =>
				movies.map {
					movie => retval = retval + convertToJson(movie) + "\n"
				}
				processOver = true
		}

		queryFuture.onFailure {
			case error =>
				println("Error found " + error.getMessage)
				retval = "{error:\"Error executing Query. Pl. contact admin\"}"
				processOver = true
		}

		while (!processOver) {
			Thread.sleep(1000)
		}
		return retval
	}

	//Convert JSON String to Movie Object
	@throws(classOf[Exception])
	def getMovieObject(movieJson: String): MovieInput = {
		implicit val formats = DefaultFormats
		try {
			val movieObj = parse(movieJson).extract[MovieInput]
			return movieObj
		} catch {
			case ex: Exception => {
				ex.printStackTrace()
				throw new Exception("Invalid input JSON :" + ex.getMessage)
			}
		}
	}

	//Check if movie does not exist in database
	@throws(classOf[Exception])
	def checkIfMovieNotExists(movieObj: MovieInput): Unit = {
		//Check if movie already exists
		val existMovies = getMoviesList(
			Map("year" -> movieObj.year.toString(),
				"title" -> movieObj.title))
		if (existMovies.contains("title")) {
			throw new Exception("Movie already exists")
		}
	}

	//Check inf movie exists in database
	@throws(classOf[Exception])
	def checkIfMovieExists(movieObj: MovieInput): Unit = {
		//Check if movie already exists
		val existMovies = getMoviesList(
			Map("year" -> movieObj.year.toString(),
				"title" -> movieObj.title))
		if (!existMovies.contains("title")) {
			throw new Exception("Movie does not exist")
		}
	}

	//General utility function to execute Inserts
	@throws(classOf[Exception])
	def executeFutureOperationUnit(operation: DBIOAction[Unit, NoStream, Effect.Write]): Unit = {

		val opsFuture = getSession().database.run(operation)

		opsFuture.onFailure {
			case error =>
				error.printStackTrace()
				throw new Exception("Error executing Operation " + error.getMessage)
		}

		while (!opsFuture.isCompleted) {
			Thread.sleep(1000)
		}
	}

	//General utility function to execute updates and deletes
	@throws(classOf[Exception])
	def executeFutureOperationInt(operation: DBIOAction[Int, NoStream, Effect.Write]): Unit = {

		val opsFuture = getSession().database.run(operation)

		opsFuture.onFailure {
			case error =>
				error.printStackTrace()
				throw new Exception("Error executing Operation " + error.getMessage)
		}

		while (!opsFuture.isCompleted) {
			Thread.sleep(1000)
		}
	}

	//Add a movie to the database
	def addMovie(movieJson: String): String = {

		implicit val formats = DefaultFormats

		try {

			val movieObj = getMovieObject(movieJson)
			checkIfMovieNotExists(movieObj)

			val insert = DBIO.seq(
				Tables.Movielist += Tables.MovielistRow(
					movieObj.year, movieObj.length,
					movieObj.title, movieObj.subject,
					movieObj.actor, movieObj.popularity))

			executeFutureOperationUnit(insert)

			return "Add successfully completed for " + movieJson
		} catch {
			case ex: Exception => {
				ex.printStackTrace()
				return ("Error adding movie : " + ex.getMessage)
			}

		}
	}

	//Update popularity of a movie
	def updatePopularity(movieJson: String): String = {
		try {

			val movieObj = getMovieObject(movieJson)
			checkIfMovieExists(movieObj)

			val updateQuery = Tables.Movielist.
				filter { movie =>
					(movie.title === movieObj.title
						&& movie.year === movieObj.year)
				}.
				map { movie => movie.popularity }.
				update(movieObj.popularity)

			executeFutureOperationInt(updateQuery)

			return "Update successfully completed for : " + movieObj
		} catch {
			case ex: Exception => {
				ex.printStackTrace()
				return ("Error updating movie : " + ex.getMessage)
			}

		}
	}

	//Delete a movie
	def deleteMovie(movieJson: String): String = {
		try {

			val movieObj = getMovieObject(movieJson)
			checkIfMovieExists(movieObj)

			val deleteQuery = Tables.Movielist.
				filter { movie =>
					(movie.title === movieObj.title
						&& movie.year === movieObj.year)
				}.
				delete

			executeFutureOperationInt(deleteQuery)

			return ("Delete successfully completed for : " + movieJson)

		} catch {
			case ex: Exception => {
				ex.printStackTrace()
				return ("Error deleting movie : " + ex.getMessage)
			}

		}
	}
	
	/*	
	implicit val formats = DefaultFormats
	val inStr = """{"actor":"Ford, Harrison","popularity":8,"subject":"Action","year":1981,"length":116,"title":"Raiders of the Lost Ark"}"""
	val movieJson = addMovie(inStr)
	println(movieJson)

		println(getMoviesList(Map("title" -> "Raiders of the Lost Ark",
								"year" -> "1981")))

	val inStr="""{"popularity":12,"year":1981,"title":"Raiders of the Lost Ark"}"""
	val movieJson = deleteMovie(inStr)
	println(movieJson)*/
}