import java.sql.{Connection,DriverManager,SQLException}


object userAdminDB {

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
}
