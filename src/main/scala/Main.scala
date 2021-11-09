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

    println("Spark Session Created")
  }
}
