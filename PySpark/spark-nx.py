## Spark Application - execute with spark-submit

## Imports
import csv
import matplotlib.pyplot as plt
import networkx as nx

from io import StringIO
from datetime import datetime
from collections import namedtuple
from operator import add, itemgetter
from pyspark import SparkConf, SparkContext

## Module Constants
APP_NAME = "Page Rank"
DATE_FMT = "%Y-%m-%d"
TIME_FMT = "%H%M"

## Main functionality

def main(sc):
    # Load the airlines lookup dictionary
    rdd_file = sc.textFile(
        "file:///usr/local/spark/mycode/ConnectedComponents/src/main/scala/data/graphx/followers.txt").collect()

    Graph = nx.DiGraph()
    node_list = {}

    for line in rdd_file:
        line = line.rstrip("\n")
        a, b = line.split("\t")
        Graph.add_edge(a, b)
        if b not in node_list:
            node_list[b] = 0
        node_list[b] = node_list[b] + 1

    node_list = sorted(node_list.items(),
                       key=lambda item: item[1], reverse=True)
    print("InDegree")
    for i in range(20):
        print("(", node_list[i][0], ",", node_list[i][1], ")")
    print("\n")

    pr = nx.pagerank(Graph, max_iter=10000, alpha=0.00001)
    pr = sorted(pr.items(), key=lambda item: item[1], reverse=True)

    print("Page Rank")
    for i in range(20):
        print("(", pr[i][0], ",", pr[i][1], ")")


if __name__ == "__main__":
    # Configure Spark
    conf = SparkConf().setMaster("local[*]")
    conf = conf.setAppName(APP_NAME)
    sc = SparkContext(conf=conf)

    # Execute Main functionality
    main(sc)
