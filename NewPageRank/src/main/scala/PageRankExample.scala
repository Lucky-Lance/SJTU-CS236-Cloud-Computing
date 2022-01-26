
import org.apache.spark.graphx.{Edge, GraphLoader, VertexId, VertexRDD}
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object PageRank {
  def main(args: Array[String]): Unit = {

    // Creates a SparkSession.
    val conf = new SparkConf().setMaster("local[2]")
    val spark = SparkSession
      .builder
      .appName("SampleApp").config(conf)
      .getOrCreate()
    val sc = spark.sparkContext

    // $example on$
    // Load the edges as a graph
    val graph: Graph[Int, Int] = GraphLoader.edgeListFile(sc, "file:///usr/local/spark/mycode/NewPageRank/src/main/scala/data.txt")
    val MapGraph = graph.outerJoinVertices(graph.outDegrees)((vid, _, degOpt) => degOpt.getOrElse(0))
    val num = MapGraph.vertices.count()
    var ranks = MapGraph.mapTriplets(triplet => 1.0 / triplet.srcAttr).mapVertices((id, _) => 1.0)
    
    // Run PageRank
    for (iter <- 1 to 100) {
        ranks.cache()
        val Update = ranks.aggregateMessages[Double] (triplet => {triplet.sendToDst(triplet.attr *  triplet.srcAttr);},_ + _, TripletFields.Src)

        ranks = ranks.outerJoinVertices(Update) {(id, oldPageRank, msgSumUpdates) => 0.15 / num + 0.85 * msgSumUpdates.getOrElse(0.0)}
      }

    ranks.vertices.sortBy(_._2, false).collect.foreach(println)
    // scalastyle:on println
  }
}
