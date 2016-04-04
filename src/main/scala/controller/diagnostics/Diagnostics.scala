package controller.diagnostics

import java.awt.Point
import java.awt.geom.GeneralPath

import rx.lang.scala.{Subscription, Observable}

import scala.concurrent.duration.Duration

import sodium.{Listener, CellSink, Stream}

object Diagnostics {

  def obsStream[T](obs: Observable[T], subFun: ((String, Long) => Unit)) : Subscription = {
    val obsInterval = Observable.interval(Duration(1, scala.concurrent.duration.SECONDS))

    val obsIntMouseDownLatestFrom = obs.
      map(valueToString).
      withLatestFrom(obsInterval)((value, time) => (value, time))

    val obsIntMouseDownFirst = obs.
      map(valueToString).
      combineLatest(obsInterval).first

    obsIntMouseDownLatestFrom.
      merge(obsIntMouseDownFirst).
      subscribe (_ match {
          case (a: String, b: Long) => subFun(a,b)
        }
    )
  }

  def sodStream[T](s: Stream[T], subFun: (String, Long) => Unit) : Listener = {
    val timer         = new java.util.Timer()
    val sInterval     = new CellSink[Long](0);

    val taskInterval  = new java.util.TimerTask {
      var counter = 0;
      def run() = {
        counter = counter + 1;
        sInterval.send(counter);
      }
    }

    timer.schedule(taskInterval, 0L, 1000L)
    val sDiagnostic: Stream[(String, Long)] =  s.map(valueToString).snapshot(sInterval, (v: String, t: Long) => (v, t))

    new Listener {
      override def unlisten(): Unit = taskInterval.cancel()
    }.append(
      sDiagnostic.listen {
        case ((v, t)) => subFun(v, t)
    })
  }


  def valueToString[T](value: T): String = value match {
    case point: Point => "(" + point.getX.toInt + ", " + point.getY.toInt + ")"
    case path: GeneralPath => "(" + path.getBounds().width.toString + ", " + path.getBounds().height.toString + ")"
    case None         => "None"
    case Some(v)      => valueToString(v)
    case value        => value.toString()
  }
}
