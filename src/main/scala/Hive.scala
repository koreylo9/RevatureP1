import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf,SparkContext}

object Hive {

  def connect() : Unit = {
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    val spark = SparkSession
      .builder()
      .appName("NFL DATA")
      .config("spark.master","local")
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    spark.sql("DROP table IF EXISTS nfl_data")
    spark.sql("CREATE table IF NOT exists nfl_data(GameId int, GameDate Date, OffenseTeam String," +
                  "DefenseTeam String, Description String,SeasonYear int, Yards int, Formation String, IsRush int," +
                  "IsPass int, IsSack int, IsPenalty int, RushDirection String, PenaltyYards int)" +
                  "ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'")

    spark.sql("Load data Local Inpath 'nfl_data.csv' into table nfl_data")
    spark.sql("select * from nfl_data ").show()
  }

  //    println("How many sacks were there?")
  //    spark.sql("SELECT count(isSack) as Sack_Count " +
  //      "FROM nfl_data WHERE isSack = 1").show()
  //
  //    println("How many run plays were run through the right guard?")
  //    spark.sql("SELECT count(isRush) as Run_Plays_Right_Guard " +
  //      "FROM nfl_data WHERE isRush = 1 AND rushDirection = 'RIGHT GUARD'").show()
  //
  //    println("How many penalty yards were there?")
  //    spark.sql("SELECT sum(penaltyYards) as Penalty_Yards_Total " +
  //      "FROM nfl_data").show()
  //
  //    println("How many rushing yards were there?")
  //    spark.sql("SELECT sum(yards) as Rushing_Yards_Total " +
  //      "FROM nfl_data").show()
  //
  //    println("How many plays in the past two weeks were under Shotgun?")
  //    spark.sql("SELECT count(formation) as Shotgun_Plays " +
  //      "FROM nfl_Data WHERE formation = 'SHOTGUN'").show()
  //
  //    println("Will there be more run plays or pass plays for next week?")
  //    spark.sql("SELECT r.count as run_count, m.count as pass_count FROM " +
  //      "(SELECT count(isRush) count FROM nfl_data WHERE isRush = 1 AND (gamedate = '2021-11-01' OR gamedate = '2021-10-31')) r, " +
  //      "(SELECT count(isPAss) count FROM nfl_data WHERE isPass = 1 AND (gamedate = '2021-11-01' OR gamedate = '2021-10-31')) m").show()


}
