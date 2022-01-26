/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package org.apache.spark.examples.graphx
import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD

// $example on$
import org.apache.spark.graphx.GraphLoader
// $example off$
import org.apache.spark.sql.SparkSession

/**
 * A PageRank example on social network dataset
 * Run with
 * {{{
 * bin/run-example graphx.PageRankExample
 * }}}
 */
object PageRankExample {
  def main(args: Array[String]): Unit = {
    // Creates a SparkSession.
    val spark = SparkSession
      .builder
      .appName(s"${this.getClass.getSimpleName}")
      .getOrCreate()
    val sc = spark.sparkContext

    // $example on$
    // Load the edges as a graph
    val graph = GraphLoader.edgeListFile(sc, "file:///usr/local/spark/mycode/ConnectedComponents/src/main/scala/data/graphx/followers.txt")
    // Run PageRank
    val ranks = graph.pageRank(0.0001)

    

    ranks.vertices.sortBy(_._2, false).collect.foreach(println)
    println("===============indegrees==============")
    // indegrees
    graph.inDegrees.sortBy(_._2, false).collect.foreach(println(_))
    println("===============outDegrees==============")
    graph.outDegrees.sortBy(_._2, false).collect.foreach(println(_))
  }
}
// scalastyle:on println
