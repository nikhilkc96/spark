package com.nmit.spark.ipltosswinstats

import org.apache.spark.sql.SparkSession

/**
  *  Problem Statement:
  *  We want to find the percentage of game wins by teams which win the toss.
  *  So lets say N games have been played.
  *  Let us say there are M games where the team which has won the toss has
  *  also won the game.
  *  It means that in (N-M) games, the team which won the toss lost the game.
  *  We are looking for the percentage (M * 100 / N).
  *  Perform the task using SQL code only.

  *  use the indian-premier-league-csv-dataset.
  * */

object ipltosswinstats {

  def main(args: Array[String]) {

    val pathToDB = "/home/subhrajit/sparkProjects/data/indian-premier-league-csv-dataset"
    val spark = SparkSession.builder().appName("My SQL Session").getOrCreate()
    import spark.implicits._
  
    // The Match.csv file has the toss won/match won data for every game.
    // Read the file into a dataframe.

    val matchDF = spark.read.format("csv").
      option("sep", ",").
      option("inferSchema", "true").
      option("header", "true").
      load(pathToDB + "/Match.csv")

    // Since we have to use SQL queries, the dataframe has to be registered as a table.
    // We can create a temporary table view.
    matchDF.createOrReplaceTempView("matchStats")

    // find the total number of entries in the table. this is equal to number of matches played or N.
    // note that spark.sql commands return a dataframe. If we were to run the following command in the shell:
    // val tempDF = spark.sql("SELECT COUNT(*) FROM matchstats")
    // then the shell would return the following:
    // tempDF: org.apache.spark.sql.DataFrame = [count(1): bigint]
    // i.e. tempDF is a dataframe with one row and 1 column of bigints.
    // this row with one element contains the count or number of rows of the matchstats
    // view. To get this count, we have to use the remainder of the code:
    // .first()(0).asInstanceOf[Long]

    val N = spark.sql("SELECT COUNT(*) FROM matchstats")
      .first()(0)
      .asInstanceOf[Long]
    // N.show()

    // Find the subset of entries where the toss winner is also the match winner. This will be a dataframe.
    val tossNMatchwinnersDF = spark.sql("SELECT * FROM matchstats WHERE Toss_Winner_Id = Match_Winner_Id")

    // register the dataframe as a temporary table so that you can use SQL queries on it.
    tossNMatchwinnersDF.createOrReplaceTempView("tossNMatchwinners")

    // find the count of entries in this Table. This gives us M.
    val M = spark.sql("SELECT COUNT(*) FROM tossNMatchwinners")
      .first()(0)
      .asInstanceOf[Long]
    // M.show()

    // print M * 100 / N.
    println("Percentage of times Toss Winners have won the match = " + (M*100.0)/N + "%")
  }
}
