import java.sql.{Connection, DriverManager}


object UserDB {

  private var connection:Connection = _

  def connect(): Unit = {
    val url = "jdbc:mysql://localhost:3306/nflPlays"
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

  def showUserInfo(email:String) : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(s"SELECT * FROM users WHERE email = '$email'")
    if (!resultSet.next()) {
      println("No user found!")
    }
    else {
      printf("%-25s%-25s%-25s%-25s\n","FirstName", "LastName", "Email", "Password")
      do {
        val fname = resultSet.getString("firstname")
        val lname = resultSet.getString("lastname")
        val email = resultSet.getString("email")
        val password = resultSet.getString("password")
        val isadmin = resultSet.getBoolean("isAdmin")
        printf("%-25s%-25s%-25s%-25s\n",fname, lname, email, password)
      } while (resultSet.next())
    }
  }

  def updateEmail(newEmail: String, oldEmail: String) : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeUpdate(s"UPDATE users SET email = '$newEmail' WHERE email = '$oldEmail'")
    if(resultSet == 0){
      println("Error Please Try Again!")
    }
    else{
      println("Email Updated!")
      println("")
    }
  }

  def updatePass(email: String, newPass:String) : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeUpdate(s"UPDATE users SET password = '$newPass' WHERE email = '$email'")
    if(resultSet == 0){
      println("Error Please try again!")
    }
    else{
      println("Password Updated!")
      println("")
    }
  }

  def updateAdmin(id:Int) : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeUpdate(s"UPDATE users SET isAdmin = true WHERE id = '$id'")
    if(resultSet == 0){
      println("User does not exist!")
    }
    else{
      println("Update Completed!")
    }

  }

  def deleteUser(id:Int) : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeUpdate(s"DELETE FROM users WHERE id = '$id'")
    if(resultSet == 0){
      println("User does not exist!")
    }
    else{
      println(s"User '$id' deleted!'")
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
    if(!resultSet.next()){
      false
    }
    else{
      resultSet.getBoolean("isAdmin")
    }
  }

  def checkLogin(email:String, password: String) : Boolean = {
    //var resultSet = 0
    try {
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(s"SELECT * FROM users WHERE email = '$email' AND password = '$password'")
      if(!resultSet.next()){
        println("Username or password incorrect!")
        println("")
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

  def showUsers() : Unit = {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM users")
    if(!resultSet.next()){
      println("No users found!")
    }
    else{
      printf("%-25s%-25s%-25s%-25s%-25s%-25s\n","ID","FirstName","LastName","Email","Password","isAdmin")
      do{
        val ID = resultSet.getInt("id")
        val fname = resultSet.getString("firstname")
        val lname = resultSet.getString("lastname")
        val email = resultSet.getString("email")
        val password = resultSet.getString("password")
        val isadmin = resultSet.getBoolean("isAdmin")
        printf("%-25s%-25s%-25s%-25s%-25s%-25s\n",ID,fname,lname,email,password,isadmin)
      }while(resultSet.next())
    }
  }

  def closeConnection() : Unit = {
    connection.close()
  }
}
