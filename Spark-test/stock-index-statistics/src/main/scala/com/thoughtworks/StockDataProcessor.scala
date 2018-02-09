package com.thoughtworks

import com.thoughtworks.stockindex.StockIndexConf
import org.apache.spark.sql.{DataFrame, SparkSession}

case class StockIndexSchema(key:String, time: String, indexs:Array[Double]) {
  def toSeq:(String, String) = (key, (indexs + time).mkString(","))
}

object StockDataProcessor {

  val warehouseLocation: String = "/user/yzh/spark-warehouse"
  val stockDataTopic = "stock-mins"
  val stockIndexDataTopic = "stock-indexs"
  val stockTable = "stockData"
  val stockIndexTable = "stockIndexData"
  val prod_hosts = "hdp2:6667,hdp3:6667,hdp4:6667"
  val dev_hosts = "localhost:9092"

  def main(args: Array[String]): Unit = {
    StockIndexConf.stockIndexManager.Init()


    val spark: SparkSession = SparkSession
        .builder()
        .master("local")
        .config("spark.sql.warehouse.dir", warehouseLocation)
        .enableHiveSupport()
        .getOrCreate()

    val df: DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", dev_hosts)
      .option("subscribe", stockDataTopic)
      .option("startingOffsets", "latest")
      .load()


//      df.printSchema()

    import spark.implicits._

//     var tempDf =  df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String,String)]
//        .writeStream
//        .format("csv")
//        .option("format","append")
//        .option("path","/user/yzh/output")
//        .option("checkpointLocation", warehouseLocation)
//        .outputMode("append")
//        .start()

    val dfDSet = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)]

//    dfDSet.write.mode("append").partitionBy("value").saveAsTable(stockTable)


    val indexdf= dfDSet.map(item => {
      println(item._1 + " : " + item._2)
      calculateIndex(item._2.split(" ")).toSeq
    }).toDF("key", "value")

//    indexdf.write.mode("append").saveAsTable(stockIndexTable)

    indexdf
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .writeStream
      .format("kafka")
      .option("topic", stockIndexDataTopic)
      .option("kafka.bootstrap.servers", dev_hosts)
      .option("checkpointLocation", warehouseLocation)
      .start()
      .awaitTermination()
  }

  def calculateIndex(items: Array[String]): StockIndexSchema = StockIndexSchema(items(0),items(31),
      StockIndexConf.stockIndexManager.StockIndexCaluators.map(c => c._2.getValue(items)).toArray)
}
