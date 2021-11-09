import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf,SparkContext}

object Main {
  def main(args:Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    val spark = SparkSession
      .builder()
      .appName("NFL DATA")
      .config("spark.master","local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sql("DROP table IF EXISTS nfl_data")
    spark.sql("CREATE table IF NOT exists nfl_data(gameId int, gameDate Date, offenseTeam String," +
              "defenseTeam String, description String,seasonYear int, yards int, formation String, isRush int," +
              "isPass int, isSack int, isPenalty int, rushDirection String, penaltyYards int)" +
              "ROW FORMAT Delimited fields terminated by ','")
//    spark.sql("create table if not exists test_table(name varchar(255), age int)")
    spark.sql("Show tables ").show()
  }
}
