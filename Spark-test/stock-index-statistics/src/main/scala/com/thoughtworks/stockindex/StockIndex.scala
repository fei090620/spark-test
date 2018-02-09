package com.thoughtworks.stockindex

trait StockIndex {
  def getValue(paras: Array[String]):Double
}

case class ObvStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = 0d
}

case class AmoStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = 1d
}

case class EmoShortStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = 2d
}

case class EmoLangStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = 3d
}

case class MacdStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = 4d
}


