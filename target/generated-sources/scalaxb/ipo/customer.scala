// Generated by <a href="http://scalaxb.org/">scalaxb</a>.
package ipo

case class Address(number: Int,
  street: String,
  city: String,
  state: String,
  zip: String,
  country: String)
      


case class Customer(firstname: String,
  lastname: String,
  address: ipo.Address,
  email: String,
  phone: String,
  attributes: Map[String, scalaxb.DataRecord[Any]] = Map()) {
  lazy val id = attributes("@id").as[Int]
}

      

