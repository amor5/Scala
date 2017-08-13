package com.package.scala.exo1

object RESTServer extends {

	import org.http4s._, org.http4s.dsl._

	import org.http4s.headers.{ `Content-Type`, `Content-Length` }
	import org.http4s.MediaType._
	import com.package.scala.exo1._

	//Define the movie service
	val movieService = HttpService {
		//root access. Return that the service works
		case GET -> Root =>
			println("Website Works")
			Ok("Movies Website works. Pleas use appropriate actions")

		//Query for movies released after this year
		case request @ GET -> Root / "searchActor" / actor => {
			val params = Map("actor" -> actor)
			Ok(MoviesFacade.getMoviesList(params))
				.putHeaders(`Content-Type`(`text/plain`))
		}

		//Query for movies by year and title
		case request @ GET -> Root / "searchYearTitle" / year / title => {
			val params = Map("year" -> year,
				"title" -> title)
			Ok(MoviesFacade.getMoviesList(params))
				.putHeaders(`Content-Type`(`text/plain`))
		}
		
		//Add a movie
		case request @ POST -> Root / "addMovie" => {
			val rbody = request.as[String]
			rbody.flatMap { movieJson =>
				Ok(MoviesFacade.addMovie(movieJson))
					.putHeaders(`Content-Type`(`text/plain`))
			}
		}

		//Modify popularity
		case request @ PUT -> Root / "updatePopularity" => {
			val rbody = request.as[String]
			rbody.flatMap { movieJson =>
				Ok(MoviesFacade.updatePopularity(movieJson))
					.putHeaders(`Content-Type`(`text/plain`))
			}
		}
		
		//Delete a movie
		case request @ DELETE -> Root / "deleteMovie" => {
			val rbody = request.as[String]
			rbody.flatMap { movieJson =>
				Ok(MoviesFacade.deleteMovie(movieJson))
					.putHeaders(`Content-Type`(`text/plain`))
			}
		}
	}

	import org.http4s.server.blaze._

	//Build and run the service.
	val builder = BlazeBuilder.bindHttp(8080, "localhost").
		mountService(movieService, "/")

	val server = builder.run

	//Dont use this if you are running standalone (
	server.shutdownNow()
}