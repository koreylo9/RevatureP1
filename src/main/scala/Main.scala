import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.annotation.tailrec

object Main {

  private var isAdmin = false

  def main(args:Array[String]): Unit = {
    UserDB.connect()
    Hive.connect()
    startmenu()
  }

  @tailrec
  def startmenu() : Unit = {
    println("NFL DATA")
    println("(1) Login")
    println("(2) Sign Up")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => login()
      case "2" => signup()
      case _ => startmenu()
    }
  }

  def login() : Unit = {
    print("Email: ")
    val username = scala.io.StdIn.readLine()
    print("Password: ")
    val password = scala.io.StdIn.readLine()
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
      signup()
    }
    else{
      signup()
    }
  }

}
