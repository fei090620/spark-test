import sbtsparksubmit.SparkSubmitPlugin.autoImport.SparkSubmitSetting

object SparkSubmit {
  lazy val settings = SparkSubmitSetting(
    SparkSubmitSetting("parse",
      Seq("--class", "com.thoughtworks.StockDataProcessor")
    )
  )
}
