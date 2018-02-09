package com.thoughtworks.stockindex

import scala.collection.immutable.TreeMap

sealed class StockIndexManager {
  var StockIndexCaluators: TreeMap[String, StockIndex] = TreeMap()
  def addStockIndex(stockIndex: StockIndex): StockIndexManager = {
    if (StockIndexCaluators.contains(stockIndex.getClass.getName)) this
    else {
      StockIndexCaluators + (stockIndex.getClass.getName -> stockIndex)
      this
    }
  }

  def Init() =
    addStockIndex(ObvStockIndex())
      .addStockIndex(AmoStockIndex())
}

object StockIndexConf {
  var stockIndexManager : StockIndexManager = if (stockIndexManager == null) new StockIndexManager else stockIndexManager
}
