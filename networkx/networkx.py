## python network.py

## Imports
import matplotlib.pyplot as plt
import networkx as nx

## Main functionality


def main():

    rdd_file = open("./followers.txt", "r")

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
    # Execute Main functionality
    main()
