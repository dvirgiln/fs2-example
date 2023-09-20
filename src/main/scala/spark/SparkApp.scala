package spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkApp extends App {

  // Akka Actors, fs2, doobie (Functional SQL), quill, akka streams, type classes pattern

  //Beginning

  //Apache Spark 2016
  //Distributed computation
  //http://homepage.cs.latrobe.edu.au/zhe/ZhenHeSparkRDDAPIExamples.html

  val path = "src/main/resources/names.txt"
  val conf = new SparkConf().setAppName("words_count").setMaster("local")
  val spark = new SparkContext(conf)
  val rdd = spark.parallelize(List(1, 2, 3, 4, 5))
  val filterFunction = (i: Int) => i % 2 == 0
  val multiplyBy2 = (i: Int) => i * 2
  val resultRdd: RDD[Int] = rdd.filter(filterFunction).map(multiplyBy2)
  println(resultRdd.collect.toList)
  println(s"Reduced Result: ${resultRdd.reduce(_ + _)}")

  val linesRDD: RDD[String] = spark.textFile(path)
  val wordsRDD: RDD[String] = linesRDD.flatMap(line => line.split(" "))
  val wordsCount: RDD[(String, Int)] = wordsRDD.groupBy(a => a).map { case (word, listWords) => (word, listWords.size) }
  val reducedCount: RDD[(String, Int)] = wordsCount.reduceByKey { (a, b) => a + b }
  println(reducedCount.collect.toList)


}
