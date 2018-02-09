package com.thoughtworks.stockindex

trait StockIndex {
  def getValue(paras: Array[String]):Double
}

case class ObvStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = paras(11).toDouble
}

case class AmoStockIndex() extends StockIndex {
  override def getValue(paras: Array[String]): Double = paras(12).toDouble
}

//case class EmoShortStockIndex() extends StockIndex {
//  override def getValue(paras: Array[String]): Double = 前一日EMA（12）×11/13+今日收盘价×2/13
//}
//
//case class EmoLangStockIndex() extends StockIndex {
//  override def getValue(paras: Array[String]): Double = 3d
//}
//
//case class MacdStockIndex() extends StockIndex {
//  override def getValue(paras: Array[String]): Double = 4d
//}


