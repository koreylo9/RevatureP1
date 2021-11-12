import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.nio.charset.StandardCharsets

import scala.annotation.tailrec
import scala.sys.exit


object Main {
//  DigestUtils.sha256Hex("foo".getBytes(StandardCharsets.UTF_8))
  private var isAdmin = false
  private var userEmail = ""

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
        userEmail = email
        userMainMenu()
      }
    }
    else{
      startmenu()
    }
  }

  def userMainMenu() : Unit = {
    println("==== Main Menu ====")
    println("(1) NFL Data Menu")
    println("(2) Show User Info")
    println("(3) Change Email")
    println("(4) Change Password")
    println("(5) Logout")
    println("(6) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => dataMenu()
      case "2" => showUserInfo()
      case "3" => changeEmail()
      case "4" => changePass()
      case "5" => userEmail = ""
                  startmenu()
      case "6" => exitApp()
      case _ => userMainMenu()
    }
    userMainMenu()
  }

  def showUserInfo() : Unit = {
    UserDB.showUserInfo(userEmail)
  }

  def changePass() : Unit = {
    print("Enter New Password: ")
    val pass = scala.io.StdIn.readLine()
    UserDB.updatePass(userEmail,pass)
  }

  def changeEmail() : Unit = {
    print("Enter New Email: ")
    val newEmail = scala.io.StdIn.readLine()
    UserDB.updateEmail(newEmail,userEmail)
    userEmail = newEmail
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
      userEmail = email
      userMainMenu()
    }
    else{
      signup()
    }
  }

  @tailrec
  def dataMenuAdmin() : Unit = {
    println("NFL Data (ADMIN)")
    println("(1) Show Table Data")
    println("(2) Users")
    println("(3) Logout")
    println("(4) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => Hive.showData("7")
      case "2" => userCRUD()
      case "3" => userEmail = ""
                  startmenu()
      case "4" => exitApp()
      case _ => dataMenu()
    }
    dataMenuAdmin()
  }

  @tailrec
  def userCRUD() : Unit = {
    println("User Editor")
    println("(1) Show Users")
    println("(2) Create User")
    println("(3) Give Admin Privileges")
    println("(4) Delete User")
    println("(5) Return to Admin Menu")
    println("(6) Logout")
    println("(7) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => UserDB.showUsers()
      case "2" => adminCreateUser()
      case "3" => giveAdmin()
      case "4" => deleteUser()
      case "5" => dataMenuAdmin()
      case "6" => userEmail = ""
                  startmenu()
      case "7" => exitApp()
      case _ => dataMenuAdmin()
    }
    userCRUD()
  }

  def deleteUser() : Unit = {
    println("==== Delete User ====")
    println("User ID you want to give Delete: ")
    var id = scala.io.StdIn.readInt()
    UserDB.deleteUser(id)
  }

  def giveAdmin() : Unit = {
    println("==== Admin Privileges ====")
    println("User ID you want to give admin privileges: ")
    var id = scala.io.StdIn.readInt()
    UserDB.updateAdmin(id)
  }

  @tailrec
  def adminCreateUser() : Unit = {
    var admin = false
    println("==== Admin Create User ====")
    print("First Name: ")
    val fname = scala.io.StdIn.readLine()
    print("Last Name: ")
    val lname = scala.io.StdIn.readLine()
    print("Email: ")
    val email = scala.io.StdIn.readLine()
    print("Password: ")
    val password = scala.io.StdIn.readLine()
    print("Admin Privileges (1 or 0):")
    val isAdmin = scala.io.StdIn.readLine()
    if(isAdmin == "1"){
      admin = true
    }
    val result = UserDB.createUser(fname, lname, email, password, admin)
    if(result == 1){
      println("Account Created!")
      println("")
    }
    else{
      adminCreateUser()
    }
  }

  def dataMenu() : Unit = {
    println("NFL Data")
    println("(1) Total Sacks")
    println("(2) Total Rushing Yards")
    println("(3) Total Penalty Yards")
    println("(4) Total run plays through the right guard position")
    println("(5) Total plays in ShotGun")
    println("(6) How many yards will a San Fran RB gain on a single run play for Week 10?")
    println("(7) Return to Main")
    println("(8) Logout")
    println("(9) Exit")
    val option = scala.io.StdIn.readLine()
    option match {
      case "1" => Hive.showData(option)
      case "2" => Hive.showData(option)
      case "3" => Hive.showData(option)
      case "4" => Hive.showData(option)
      case "5" => Hive.showData(option)
      case "6" => Hive.showData(option)
      case "7" => userMainMenu()
      case "8" => userEmail = ""
                  startmenu()
      case "9" => exitApp()
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
