package com.thoughtworks

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.collection.JavaConverters._


/*
* when start ut,the mock kafka could start a kafka server with mock producer
* when start ut,the mock kafka also could start a kafka server with mock consumer
* */

object MockKafkaServer {

  val TOPIC="stock-mins"

  val props_producer = new Properties()
  props_producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
    "localhost:9092")
  props_producer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer")
  props_producer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props_producer)

  def sendMessage(key:String, value: String, iteratorCount: Int, topic: String = "test") = {
    for (i <- 0 to iteratorCount) {
      val message = new ProducerRecord[String, String](topic, key +" ------ " + i.toString, value)
      producer.send(message)
    }
  }

  def mockConsumer = {
    val props_consumer = new Properties()
    props_consumer.put("bootstrap.servers", "localhost:9092")

    props_consumer.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props_consumer.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props_consumer.put("group.id", "something")

    val consumer = new KafkaConsumer[String, String](props_consumer)
    consumer.subscribe(util.Collections.singletonList(TOPIC))

    while(true) {
      val records = consumer.poll(100)
      for (record <- records.asScala) {
        println(record)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val normalKey = "zyKey"
    val stockData = "sh601006, 1, 27.55, 27.25, 26.91, 27.55, 26.20, 26.91, 26.92, 22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89, 14300, 26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150, 26.95, 15220, 26.96, 2008-01-11, 15:05:32"
    sendMessage(normalKey, stockData, 200000)
//    mockConsumer
  }
}
