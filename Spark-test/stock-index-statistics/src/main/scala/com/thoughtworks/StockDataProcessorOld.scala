//import kafka.serializer.StringDecoder
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming._
//import org.apache.spark.streaming.kafka._
//
//
//object StockDataProcessorOld {
//
//  def main(args: Array[String]): Unit = {
//    if (args.length < 2) {
//      System.err.println(s"""
//                            |Usage: DirectKafkaWordCount <brokers> <topics>
//                            |  <brokers> is a list of one or more Kafka brokers
//                            |  <topics> is a list of one or more kafka topics to consume from
//                            |
//        """.stripMargin)
//      System.exit(1)
//    }
//
//    StreamingExamples.setStreamingLogLevels()
//
//    val Array(brokers, topics) = args
//
//    // Create context with 2 second batch interval
//    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount")
//    val ssc = new StreamingContext(sparkConf, Seconds(2))
//
//    // Create direct kafka stream with brokers and topics
//    val topicsSet = topics.split(",").toSet
//    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
//    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
//      ssc, kafkaParams, topicsSet)
//
//  }
//}