import java.sql.{Connection, DriverManager, SQLException, SQLTimeoutException, Statement}


object UserDB {

  private var connection:Connection = _

  def connect(): Unit = {
    var url = "jdbc:mysql://localhost:3306/nflPlays"
    val driver = "com.mysql.cj.jdbc.Driver"
    val username = "root"
    val password = "Korgalnol9!"
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def createUser(fname:String, lname:String,email:String,password:String,admin:Boolean) : Int = {
    var resultSet = 0
    try{
      val statement = connection.createStatement()
      resultSet = statement.executeUpdate(s"INSERT into users(firstname,lastname,email,password,isAdmin) values ('$fname','$lname','$email','$password',$admin);")
      //println(resultSet)
      resultSet

    }
    catch {
      case sql: java.sql.SQLIntegrityConstraintViolationException =>
        println("Email already exists!")
        resultSet
    }
  }

  def checkAdmin(email:String) : Boolean = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT isAdmin FROM users WHERE email = '$email'")
    if(resultSet.next() == false){
      false
    }
    else{
      return resultSet.getBoolean("isAdmin")
    }
  }

  def checkLogin(email:String, password: String) : Boolean = {
    //var resultSet = 0
    try {
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(s"SELECT * FROM users WHERE email = '$email' AND password = '$password'")
      if(resultSet.next() == false){
        println("Username or password incorrect!")
        false
      }
      else {
        println("Login Successful")
        println("")
        true
      }
    } catch {
      case sql: java.sql.SQLException =>
        sql.printStackTrace()
        false
    }
  }

  def closeConnection() : Unit = {
    connection.close()
  }
}
