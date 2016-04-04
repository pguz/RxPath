package view

import java.awt.Point

import rx.lang.scala.{Observable, Subscription}
import sodium._
import scala.swing.{Component, Button}
import scala.swing.event._

trait SwingApi {
  implicit class ComponentOps(val self: Component) {
    def presses = Observable.create[Point] { obs =>
      self.reactions += {
        case MousePressed(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
          obs.onNext(point)
      }
      Subscription {
        obs.onCompleted()
      }
    }
    def releases = Observable.create[Point] { obs =>
      self.reactions += {
        case MouseReleased(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
          obs.onNext(point)
      }
      Subscription {
        obs.onCompleted()
      }
    }
    def moves = Observable.create[Point] { obs =>
      self.reactions += {
        case MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers) =>
          obs.onNext(point)
        case MouseMoved(source: Component, point: Point, modifiers: Key.Modifiers) =>
          obs.onNext(point)
      }
      Subscription {
        obs.onCompleted()
      }
    }

    self.reactions += {
      case MousePressed(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) => {
        sPressedSink.send(point);
      }
      case MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers) => {
        sMovedSink.send(point);
      }
      case MouseMoved(source: Component, point: Point, modifiers: Key.Modifiers) => {
        sMovedSink.send(point);
      }
      case MouseReleased(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) => {
        sReleasedSink.send(point);
      }
    }

    private val sPressedSink  = new StreamSink[Point]();
    private val sMovedSink    = new StreamSink[Point]();
    private val sReleasedSink = new StreamSink[Point]();
    val sPressed :Stream[Point]   = sPressedSink;
    val sMoved :Stream[Point]     = sMovedSink;
    val sReleased :Stream[Point]  = sReleasedSink;

  }

}
