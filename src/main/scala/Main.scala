import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.annotation.tailrec
import scala.sys.exit

object Main {

  private var isAdmin = false

  def main(args:Array[String]): Unit = {
    UserDB.connect()
    Hive.connect()
    startmenu()
  }

  @tailrec
  def startmenu() : Unit = {
    println("NFL 2021-2022 DATA")
    println("(1) Login")
    println("(2) Sign Up")
    println("(3) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => login()
      case "2" => signup()
      case "3" => exitApp()
      case _ => startmenu()
    }
  }

  def login() : Unit = {
    print("Email: ")
    val email = scala.io.StdIn.readLine()
    print("Password: ")
    val password = scala.io.StdIn.readLine()
    val checkLogin = UserDB.checkLogin(email,password)
    if(checkLogin){
      isAdmin = UserDB.checkAdmin(email)
      if(isAdmin){
//        println("Is Admin")
        dataMenuAdmin()
      }
      else{
//        println("Not Admin")
        dataMenu()
      }
    }
    else{
      login()
    }
  }

  @tailrec
  def signup() : Unit = {
    println("====Sign Up====")
    print("First Name: ")
    val fname = scala.io.StdIn.readLine()
    print("Last Name: ")
    val lname = scala.io.StdIn.readLine()
    print("Email: ")
    val email = scala.io.StdIn.readLine()
    print("Password: ")
    val password = scala.io.StdIn.readLine()
    val admin = false
    val result = UserDB.createUser(fname, lname, email, password, admin)
    if(result == 1){
      println("Account Created!")
      println("")
      dataMenu()
    }
    else{
      signup()
    }
  }


  def dataMenuAdmin() : Unit = {
    println("NFL Data (ADMIN)")
    println("(1) Show data")
    println("(2) Users")
    println("(3) Logout")
    println("(4) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => Hive.showData(option)
      case "2" => userCRUD()
      case "3" => startmenu()
      case "4" => exitApp()
      case _ => dataMenu()
    }
  }

  def userCRUD() : Unit = {

  }

  def dataMenu() : Unit = {
    println("NFL Data")
    println("(1) Total Sacks")
    println("(2) Total Rushing Yards")
    println("(3) Total Penalty Yards")
    println("(4) Total run plays through the right guard position")
    println("(5) Total plays in ShotGun")
    println("(6) Prediction for next week")
    println("(7) Logout")
    println("(8) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => Hive.showData(option)
      case "2" => Hive.showData(option)
      case "3" => Hive.showData(option)
      case "4" => Hive.showData(option)
      case "5" => Hive.showData(option)
      case "6" => Hive.showData(option)
      case "7" => startmenu()
      case "8" => exitApp()
      case _ => dataMenu()
    }
    dataMenu()
  }


  def exitApp() : Unit = {
    UserDB.closeConnection()
    println("Exiting...")
    exit(0)
  }

}
