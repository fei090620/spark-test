package com.thoughtworks

import java.util.Date

import com.thoughtworks.stockindex._
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


case class StockIndexSchema(code:String, obvIndex:Double,
                            amoIndex:Double) {
  override def toString: String
  = s"""{"code":"$code","OBV":"$obvIndex","AMO":"$amoIndex"}"""
}


object StockDataProcesser {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("test")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))
    val stockDataPath = "/user/yzh/tmp/stockData"
    val stockDataIndexPath = "/user/yzh/tmp/stockIndexData"
    val prod_hosts = "hdp2:6667,hdp3:6667,hdp4:6667"
    val topic = "stock-mins"
    val dev_hosts = "localhost:9092"

    StockIndexConf.stockIndexManager.Init()

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> dev_hosts,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "stock",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array(topic)
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val dfcol = stream.map(record => record.value())
//    dfcol.saveAsTextFiles(s"$stockDataPath${new Date}")

    val df = dfcol.map(x => x.split(","))
      .map(y => StockIndexSchema(y(0),
        ObvStockIndex().getValue(y),
        AmoStockIndex().getValue(y)).toString)

//    df.saveAsTextFiles(s"$stockDataPath${new Date}")

    ssc.start()
    ssc.awaitTermination()
  }
}