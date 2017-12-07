package com.gilt.gfc.aws.cloudwatch.periodic.metric.aggregator

import com.gilt.gfc.aws.cloudwatch.ToCloudWatchMetricsData
import org.specs2.mutable.Specification
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum


class WorkQueueSpec
  extends Specification {

  implicit
  val StringToCloudWatchMetricsData = new ToCloudWatchMetricsData[String] {
    override
    def toMetricData( a: String
                    ): Seq[MetricDatum] = {
      Seq( MetricDatum.builder.unit(a).value(1.0).build )
    }
  }

  "WorkQueue" should {

    "drain empty queue into an empty iterator" in {
      val q = new WorkQueue[String]()
      q.drain().hasNext should beFalse // should not explode
    }

    "drain non-empty queue into an iterator" in {
      val q = new WorkQueue[String]()
      q.enqueue("nsFoo", "foo1")
      q.enqueue("nsFoo", "foo2")
      q.enqueue("nsBar", "bar1")
      q.enqueue("nsBar", "bar2")

      val it = q.drain()

      it.hasNext should beTrue

      it.toList.groupBy(_._1).mapValues(_.map(_._2)).toSeq.map(_.toString).sorted.mkString("[", ", ", "]") shouldEqual(
        "[(nsBar,List({Value: 1.0,Unit: bar1}, {Value: 1.0,Unit: bar2})), (nsFoo,List({Value: 1.0,Unit: foo1}, {Value: 1.0,Unit: foo2}))]"
      )
    }
  }
}
