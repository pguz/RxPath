package controller.diagnostics

import java.awt.Point
import java.awt.geom.GeneralPath

import rx.lang.scala.{Subscription, Observable}

import scala.concurrent.duration.Duration

object Diagnostics {

  def stream[T](obs: Observable[T], subFun: ((String,Long) => Unit)) : Subscription = {
    val obsInterval = Observable.interval(Duration(1, scala.concurrent.duration.SECONDS))

    val obsIntMouseDownLatestFrom = obs.
      map(value => valueToString(value)).
      withLatestFrom(obsInterval)((value, time) => (value, time))

    val obsIntMouseDownFirst = obs.
      map(value => valueToString(value)).
      combineLatest(obsInterval).first

    obsIntMouseDownLatestFrom.
      //merge(obsIntMouseDownFirst).
      subscribe { elem =>
        elem match {
          case (a: String,b: Long) => subFun(a,b)
        }
      }
  }


  def valueToString[T](value: T): String = value match {
    case point: Point => "(" + point.getX.toInt + ", " + point.getY.toInt + ")"
    case path: GeneralPath => "(" + path.getBounds().width.toString + ", " + path.getBounds().height.toString + ")"
    case value        => value.toString()
  }
}
