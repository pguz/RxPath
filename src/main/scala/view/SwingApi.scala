package view

import java.awt.Point

import rx.lang.scala.{Observable, Subscription}
import scala.swing.{Component}
import scala.swing.event._

trait SwingApi {
  implicit class ComponentOps(val self: Component) {
    def presses = Observable.create[Point] { obs =>
      self.reactions += {
        case MousePressed(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
          obs.onNext(point)
      }
      Subscription()
    }
    def releases = Observable.create[Point] { obs =>
      self.reactions += {
        case MouseReleased(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
          obs.onNext(point)
      }
      Subscription()
    }
    def moves = Observable.create[Point] { obs =>
      self.reactions += {
        case MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers) =>
          obs.onNext(point)
        case MouseMoved(source: Component, point: Point, modifiers: Key.Modifiers) =>
          obs.onNext(point)
      }
      Subscription()
    }
  }
}
