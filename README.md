 <center>云计算技术课程实验</center>

<center>519021910372 陆徐东 luxudong2001@sjtu.edu.cn</center>

#### 一、文件夹结构

```
.
├── ConnectedComponents
│   ├── project
│   ├── simple.sbt
│   ├── src
│   └── target
├── MapReduce
│   └── WordCount.java
├── NewPageRank
│   ├── project
│   ├── simple.sbt
│   ├── src
│   └── target
├── PageRank
│   ├── project
│   ├── simple.sbt
│   ├── src
│   └── target
├── PySpark
│   └── spark-nx.py
├── README.md
├── networkx
│   ├── followers.txt
│   └── networkx.py
├── wordcount
│   ├── project
│   ├── simple.sbt
│   ├── src
│   ├── target
│   └── word.txt
└── 云计算技术课程实验报告.pdf

19 directories, 11 files
```

#### 二、文件夹解释

- ConnectedComponents：实验3，在Spark中引⼊GraphX API运行Connected Component样例
- MapReduce：实验2，运⾏Hadoop自带wordcount样例（自己学习编写的MapReduce程序）
- NewPageRank：实验4，使用GraphX API执行PageRank算法（手动实现PageRank，Scala版本）
- PageRank：实验4，使用GraphX API执行PageRank算法（调用自带PageRank API，Scala版本）
- PySpark：实验4，使用GraphX API执行PageRank算法（PySpark版本）
- networkx：实验4，使用GraphX API执行PageRank算法（本地Python版本）
- wordcount：实验2，运⾏Hadoop自带wordcount样例

#### 三、运行步骤解释：

- 对于Scala版本文件，需要运行

  ```
  /usr/local/sbt/sbt package
  /usr/local/spark/bin/spark-submit /usr/local/spark/mycode/**/target/scala-2.11/simple-project_2.11-1.0.jar
  ```

  其中*"**"*部分为需要执行的脚本文件夹名称，比如wordcount

- 对于Java实现的MapReduce

  ```
  javac WordCount.java −cp $(hadoop classpath)
  jar −cvf WordCount.jar −C ./ .
  hadoop jar WordCount.jar WordCount /input /output
  ```

- 对于本地Python文件，直接运行

  ```
  python networkx.py
  ```

- 对于PySpark版本文件，需要运行

  ```
  spark−submit −−master local[2] −−num−executors 2 −−executor−memory 1G ~/spark−nx.py
  ```

  