package com.package.scala.exo1
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Movielist.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Movielist
   *  @param year Database column Year SqlType(INT)
   *  @param length Database column Length SqlType(INT), Default(None)
   *  @param title Database column Title SqlType(VARCHAR), Length(255,true)
   *  @param subject Database column Subject SqlType(VARCHAR), Length(50,true), Default(None)
   *  @param actor Database column Actor SqlType(VARCHAR), Length(255,true), Default(None)
   *  @param popularity Database column Popularity SqlType(INT), Default(None) */
  case class MovielistRow(year: Int, length: Option[Int] = None, title: String, subject: Option[String] = None, actor: Option[String] = None, popularity: Option[Int] = None)
  /** GetResult implicit for fetching MovielistRow objects using plain SQL queries */
  implicit def GetResultMovielistRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[String], e3: GR[Option[String]]): GR[MovielistRow] = GR{
    prs => import prs._
    MovielistRow.tupled((<<[Int], <<?[Int], <<[String], <<?[String], <<?[String], <<?[Int]))
  }
  /** Table description of table movielist. Objects of this class serve as prototypes for rows in queries. */
  class Movielist(_tableTag: Tag) extends Table[MovielistRow](_tableTag, "movielist") {
    def * = (year, length, title, subject, actor, popularity) <> (MovielistRow.tupled, MovielistRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(year), length, Rep.Some(title), subject, actor, popularity).shaped.<>({r=>import r._; _1.map(_=> MovielistRow.tupled((_1.get, _2, _3.get, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Year SqlType(INT) */
    val year: Rep[Int] = column[Int]("Year")
    /** Database column Length SqlType(INT), Default(None) */
    val length: Rep[Option[Int]] = column[Option[Int]]("Length", O.Default(None))
    /** Database column Title SqlType(VARCHAR), Length(255,true) */
    val title: Rep[String] = column[String]("Title", O.Length(255,varying=true))
    /** Database column Subject SqlType(VARCHAR), Length(50,true), Default(None) */
    val subject: Rep[Option[String]] = column[Option[String]]("Subject", O.Length(50,varying=true), O.Default(None))
    /** Database column Actor SqlType(VARCHAR), Length(255,true), Default(None) */
    val actor: Rep[Option[String]] = column[Option[String]]("Actor", O.Length(255,varying=true), O.Default(None))
    /** Database column Popularity SqlType(INT), Default(None) */
    val popularity: Rep[Option[Int]] = column[Option[Int]]("Popularity", O.Default(None))

    /** Primary key of Movielist (database name movielist_PK) */
    val pk = primaryKey("movielist_PK", (year, title))
  }
  /** Collection-like TableQuery object for table Movielist */
  lazy val Movielist = new TableQuery(tag => new Movielist(tag))
}
