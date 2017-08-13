package com.package.scala.scalaDataBase
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
  lazy val schema: profile.SchemaDescription = Array(CurrentDeptEmp.schema, Departments.schema, DeptEmp.schema, DeptEmpLatestDate.schema, DeptManager.schema, Employees.schema, Salaries.schema, Titles.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table CurrentDeptEmp
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param deptNo Database column dept_no SqlType(CHAR), Length(4,false)
   *  @param fromDate Database column from_date SqlType(DATE), Default(None)
   *  @param toDate Database column to_date SqlType(DATE), Default(None) */
  case class CurrentDeptEmpRow(empNo: Int, deptNo: String, fromDate: Option[java.sql.Date] = None, toDate: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching CurrentDeptEmpRow objects using plain SQL queries */
  implicit def GetResultCurrentDeptEmpRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[java.sql.Date]]): GR[CurrentDeptEmpRow] = GR{
    prs => import prs._
    CurrentDeptEmpRow.tupled((<<[Int], <<[String], <<?[java.sql.Date], <<?[java.sql.Date]))
  }
  /** Table description of table current_dept_emp. Objects of this class serve as prototypes for rows in queries. */
  class CurrentDeptEmp(_tableTag: Tag) extends Table[CurrentDeptEmpRow](_tableTag, "current_dept_emp") {
    def * = (empNo, deptNo, fromDate, toDate) <> (CurrentDeptEmpRow.tupled, CurrentDeptEmpRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(deptNo), fromDate, toDate).shaped.<>({r=>import r._; _1.map(_=> CurrentDeptEmpRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column dept_no SqlType(CHAR), Length(4,false) */
    val deptNo: Rep[String] = column[String]("dept_no", O.Length(4,varying=false))
    /** Database column from_date SqlType(DATE), Default(None) */
    val fromDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("from_date", O.Default(None))
    /** Database column to_date SqlType(DATE), Default(None) */
    val toDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("to_date", O.Default(None))
  }
  /** Collection-like TableQuery object for table CurrentDeptEmp */
  lazy val CurrentDeptEmp = new TableQuery(tag => new CurrentDeptEmp(tag))

  /** Entity class storing rows of table Departments
   *  @param deptNo Database column dept_no SqlType(CHAR), PrimaryKey, Length(4,false)
   *  @param deptName Database column dept_name SqlType(VARCHAR), Length(40,true) */
  case class DepartmentsRow(deptNo: String, deptName: String)
  /** GetResult implicit for fetching DepartmentsRow objects using plain SQL queries */
  implicit def GetResultDepartmentsRow(implicit e0: GR[String]): GR[DepartmentsRow] = GR{
    prs => import prs._
    DepartmentsRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table departments. Objects of this class serve as prototypes for rows in queries. */
  class Departments(_tableTag: Tag) extends Table[DepartmentsRow](_tableTag, "departments") {
    def * = (deptNo, deptName) <> (DepartmentsRow.tupled, DepartmentsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(deptNo), Rep.Some(deptName)).shaped.<>({r=>import r._; _1.map(_=> DepartmentsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column dept_no SqlType(CHAR), PrimaryKey, Length(4,false) */
    val deptNo: Rep[String] = column[String]("dept_no", O.PrimaryKey, O.Length(4,varying=false))
    /** Database column dept_name SqlType(VARCHAR), Length(40,true) */
    val deptName: Rep[String] = column[String]("dept_name", O.Length(40,varying=true))

    /** Uniqueness Index over (deptName) (database name dept_name) */
    val index1 = index("dept_name", deptName, unique=true)
  }
  /** Collection-like TableQuery object for table Departments */
  lazy val Departments = new TableQuery(tag => new Departments(tag))

  /** Entity class storing rows of table DeptEmp
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param deptNo Database column dept_no SqlType(CHAR), Length(4,false)
   *  @param fromDate Database column from_date SqlType(DATE)
   *  @param toDate Database column to_date SqlType(DATE) */
  case class DeptEmpRow(empNo: Int, deptNo: String, fromDate: java.sql.Date, toDate: java.sql.Date)
  /** GetResult implicit for fetching DeptEmpRow objects using plain SQL queries */
  implicit def GetResultDeptEmpRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[DeptEmpRow] = GR{
    prs => import prs._
    DeptEmpRow.tupled((<<[Int], <<[String], <<[java.sql.Date], <<[java.sql.Date]))
  }
  /** Table description of table dept_emp. Objects of this class serve as prototypes for rows in queries. */
  class DeptEmp(_tableTag: Tag) extends Table[DeptEmpRow](_tableTag, "dept_emp") {
    def * = (empNo, deptNo, fromDate, toDate) <> (DeptEmpRow.tupled, DeptEmpRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(deptNo), Rep.Some(fromDate), Rep.Some(toDate)).shaped.<>({r=>import r._; _1.map(_=> DeptEmpRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column dept_no SqlType(CHAR), Length(4,false) */
    val deptNo: Rep[String] = column[String]("dept_no", O.Length(4,varying=false))
    /** Database column from_date SqlType(DATE) */
    val fromDate: Rep[java.sql.Date] = column[java.sql.Date]("from_date")
    /** Database column to_date SqlType(DATE) */
    val toDate: Rep[java.sql.Date] = column[java.sql.Date]("to_date")

    /** Primary key of DeptEmp (database name dept_emp_PK) */
    val pk = primaryKey("dept_emp_PK", (empNo, deptNo))

    /** Foreign key referencing Departments (database name dept_emp_ibfk_2) */
    lazy val departmentsFk = foreignKey("dept_emp_ibfk_2", deptNo, Departments)(r => r.deptNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Employees (database name dept_emp_ibfk_1) */
    lazy val employeesFk = foreignKey("dept_emp_ibfk_1", empNo, Employees)(r => r.empNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table DeptEmp */
  lazy val DeptEmp = new TableQuery(tag => new DeptEmp(tag))

  /** Entity class storing rows of table DeptEmpLatestDate
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param fromDate Database column from_date SqlType(DATE), Default(None)
   *  @param toDate Database column to_date SqlType(DATE), Default(None) */
  case class DeptEmpLatestDateRow(empNo: Int, fromDate: Option[java.sql.Date] = None, toDate: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching DeptEmpLatestDateRow objects using plain SQL queries */
  implicit def GetResultDeptEmpLatestDateRow(implicit e0: GR[Int], e1: GR[Option[java.sql.Date]]): GR[DeptEmpLatestDateRow] = GR{
    prs => import prs._
    DeptEmpLatestDateRow.tupled((<<[Int], <<?[java.sql.Date], <<?[java.sql.Date]))
  }
  /** Table description of table dept_emp_latest_date. Objects of this class serve as prototypes for rows in queries. */
  class DeptEmpLatestDate(_tableTag: Tag) extends Table[DeptEmpLatestDateRow](_tableTag, "dept_emp_latest_date") {
    def * = (empNo, fromDate, toDate) <> (DeptEmpLatestDateRow.tupled, DeptEmpLatestDateRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), fromDate, toDate).shaped.<>({r=>import r._; _1.map(_=> DeptEmpLatestDateRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column from_date SqlType(DATE), Default(None) */
    val fromDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("from_date", O.Default(None))
    /** Database column to_date SqlType(DATE), Default(None) */
    val toDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("to_date", O.Default(None))
  }
  /** Collection-like TableQuery object for table DeptEmpLatestDate */
  lazy val DeptEmpLatestDate = new TableQuery(tag => new DeptEmpLatestDate(tag))

  /** Entity class storing rows of table DeptManager
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param deptNo Database column dept_no SqlType(CHAR), Length(4,false)
   *  @param fromDate Database column from_date SqlType(DATE)
   *  @param toDate Database column to_date SqlType(DATE) */
  case class DeptManagerRow(empNo: Int, deptNo: String, fromDate: java.sql.Date, toDate: java.sql.Date)
  /** GetResult implicit for fetching DeptManagerRow objects using plain SQL queries */
  implicit def GetResultDeptManagerRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[DeptManagerRow] = GR{
    prs => import prs._
    DeptManagerRow.tupled((<<[Int], <<[String], <<[java.sql.Date], <<[java.sql.Date]))
  }
  /** Table description of table dept_manager. Objects of this class serve as prototypes for rows in queries. */
  class DeptManager(_tableTag: Tag) extends Table[DeptManagerRow](_tableTag, "dept_manager") {
    def * = (empNo, deptNo, fromDate, toDate) <> (DeptManagerRow.tupled, DeptManagerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(deptNo), Rep.Some(fromDate), Rep.Some(toDate)).shaped.<>({r=>import r._; _1.map(_=> DeptManagerRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column dept_no SqlType(CHAR), Length(4,false) */
    val deptNo: Rep[String] = column[String]("dept_no", O.Length(4,varying=false))
    /** Database column from_date SqlType(DATE) */
    val fromDate: Rep[java.sql.Date] = column[java.sql.Date]("from_date")
    /** Database column to_date SqlType(DATE) */
    val toDate: Rep[java.sql.Date] = column[java.sql.Date]("to_date")

    /** Primary key of DeptManager (database name dept_manager_PK) */
    val pk = primaryKey("dept_manager_PK", (empNo, deptNo))

    /** Foreign key referencing Departments (database name dept_manager_ibfk_2) */
    lazy val departmentsFk = foreignKey("dept_manager_ibfk_2", deptNo, Departments)(r => r.deptNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Employees (database name dept_manager_ibfk_1) */
    lazy val employeesFk = foreignKey("dept_manager_ibfk_1", empNo, Employees)(r => r.empNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table DeptManager */
  lazy val DeptManager = new TableQuery(tag => new DeptManager(tag))

  /** Entity class storing rows of table Employees
   *  @param empNo Database column emp_no SqlType(INT), PrimaryKey
   *  @param birthDate Database column birth_date SqlType(DATE)
   *  @param firstName Database column first_name SqlType(VARCHAR), Length(14,true)
   *  @param lastName Database column last_name SqlType(VARCHAR), Length(16,true)
   *  @param gender Database column gender SqlType(ENUM), Length(2,false)
   *  @param hireDate Database column hire_date SqlType(DATE) */
  case class EmployeesRow(empNo: Int, birthDate: java.sql.Date, firstName: String, lastName: String, gender: String, hireDate: java.sql.Date)
  /** GetResult implicit for fetching EmployeesRow objects using plain SQL queries */
  implicit def GetResultEmployeesRow(implicit e0: GR[Int], e1: GR[java.sql.Date], e2: GR[String]): GR[EmployeesRow] = GR{
    prs => import prs._
    EmployeesRow.tupled((<<[Int], <<[java.sql.Date], <<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table employees. Objects of this class serve as prototypes for rows in queries. */
  class Employees(_tableTag: Tag) extends Table[EmployeesRow](_tableTag, "employees") {
    def * = (empNo, birthDate, firstName, lastName, gender, hireDate) <> (EmployeesRow.tupled, EmployeesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(birthDate), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(gender), Rep.Some(hireDate)).shaped.<>({r=>import r._; _1.map(_=> EmployeesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT), PrimaryKey */
    val empNo: Rep[Int] = column[Int]("emp_no", O.PrimaryKey)
    /** Database column birth_date SqlType(DATE) */
    val birthDate: Rep[java.sql.Date] = column[java.sql.Date]("birth_date")
    /** Database column first_name SqlType(VARCHAR), Length(14,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(14,varying=true))
    /** Database column last_name SqlType(VARCHAR), Length(16,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(16,varying=true))
    /** Database column gender SqlType(ENUM), Length(2,false) */
    val gender: Rep[String] = column[String]("gender", O.Length(2,varying=false))
    /** Database column hire_date SqlType(DATE) */
    val hireDate: Rep[java.sql.Date] = column[java.sql.Date]("hire_date")
  }
  /** Collection-like TableQuery object for table Employees */
  lazy val Employees = new TableQuery(tag => new Employees(tag))

  /** Entity class storing rows of table Salaries
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param salary Database column salary SqlType(INT)
   *  @param fromDate Database column from_date SqlType(DATE)
   *  @param toDate Database column to_date SqlType(DATE) */
  case class SalariesRow(empNo: Int, salary: Int, fromDate: java.sql.Date, toDate: java.sql.Date)
  /** GetResult implicit for fetching SalariesRow objects using plain SQL queries */
  implicit def GetResultSalariesRow(implicit e0: GR[Int], e1: GR[java.sql.Date]): GR[SalariesRow] = GR{
    prs => import prs._
    SalariesRow.tupled((<<[Int], <<[Int], <<[java.sql.Date], <<[java.sql.Date]))
  }
  /** Table description of table salaries. Objects of this class serve as prototypes for rows in queries. */
  class Salaries(_tableTag: Tag) extends Table[SalariesRow](_tableTag, "salaries") {
    def * = (empNo, salary, fromDate, toDate) <> (SalariesRow.tupled, SalariesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(salary), Rep.Some(fromDate), Rep.Some(toDate)).shaped.<>({r=>import r._; _1.map(_=> SalariesRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column salary SqlType(INT) */
    val salary: Rep[Int] = column[Int]("salary")
    /** Database column from_date SqlType(DATE) */
    val fromDate: Rep[java.sql.Date] = column[java.sql.Date]("from_date")
    /** Database column to_date SqlType(DATE) */
    val toDate: Rep[java.sql.Date] = column[java.sql.Date]("to_date")

    /** Primary key of Salaries (database name salaries_PK) */
    val pk = primaryKey("salaries_PK", (empNo, fromDate))

    /** Foreign key referencing Employees (database name salaries_ibfk_1) */
    lazy val employeesFk = foreignKey("salaries_ibfk_1", empNo, Employees)(r => r.empNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Salaries */
  lazy val Salaries = new TableQuery(tag => new Salaries(tag))

  /** Entity class storing rows of table Titles
   *  @param empNo Database column emp_no SqlType(INT)
   *  @param title Database column title SqlType(VARCHAR), Length(50,true)
   *  @param fromDate Database column from_date SqlType(DATE)
   *  @param toDate Database column to_date SqlType(DATE), Default(None) */
  case class TitlesRow(empNo: Int, title: String, fromDate: java.sql.Date, toDate: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching TitlesRow objects using plain SQL queries */
  implicit def GetResultTitlesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date], e3: GR[Option[java.sql.Date]]): GR[TitlesRow] = GR{
    prs => import prs._
    TitlesRow.tupled((<<[Int], <<[String], <<[java.sql.Date], <<?[java.sql.Date]))
  }
  /** Table description of table titles. Objects of this class serve as prototypes for rows in queries. */
  class Titles(_tableTag: Tag) extends Table[TitlesRow](_tableTag, "titles") {
    def * = (empNo, title, fromDate, toDate) <> (TitlesRow.tupled, TitlesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(empNo), Rep.Some(title), Rep.Some(fromDate), toDate).shaped.<>({r=>import r._; _1.map(_=> TitlesRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column emp_no SqlType(INT) */
    val empNo: Rep[Int] = column[Int]("emp_no")
    /** Database column title SqlType(VARCHAR), Length(50,true) */
    val title: Rep[String] = column[String]("title", O.Length(50,varying=true))
    /** Database column from_date SqlType(DATE) */
    val fromDate: Rep[java.sql.Date] = column[java.sql.Date]("from_date")
    /** Database column to_date SqlType(DATE), Default(None) */
    val toDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("to_date", O.Default(None))

    /** Primary key of Titles (database name titles_PK) */
    val pk = primaryKey("titles_PK", (empNo, title, fromDate))

    /** Foreign key referencing Employees (database name titles_ibfk_1) */
    lazy val employeesFk = foreignKey("titles_ibfk_1", empNo, Employees)(r => r.empNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Titles */
  lazy val Titles = new TableQuery(tag => new Titles(tag))
}
