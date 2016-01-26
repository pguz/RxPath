package controller.paradigm.imperative

import java.awt.{event, Point}
import java.awt.event.ActionListener
import java.awt.geom.GeneralPath
import javax.swing.Timer

import controller.paradigm.PathController
import view.ViewApi

import scala.swing.{Component, Reactions}
import scala.swing.event._

class ImperPathController(view: ViewApi, reactions: Reactions) extends PathController {

  var path: GeneralPath   = null
  var timeInSec: Long    = 0

  val pressObserver : Reactions.Reaction = {
    case MousePressed(source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
      path = new GeneralPath
      path.moveTo(point.x, point.y)
      view.setImpMouseDown(pointToString(point), timeInSec)
      reactions += moveObserver
  }

  val releaseObserver : Reactions.Reaction = {
    case MouseReleased    (source: Component, point: Point, modifiers: Key.Modifiers, clicks: Int, triggersPopup: Boolean) =>
      reactions -= moveObserver
      view.drawPath(path)
      view.setImpMouseUp(pointToString(point), timeInSec)
      path = null
  }

  val moveObserver : Reactions.Reaction = {
    case MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers) =>
      path.lineTo(point.x, point.y)
      view.drawPath(path)
      view.setImpMouseMove(pointToString(point), timeInSec)
  }

  val timer: Timer = new Timer(1000, new ActionListener() {
    override def actionPerformed(actionEvent: event.ActionEvent): Unit = {
      timeInSec    += 1
    }
  }) {
    start()
  }

  reactions += pressObserver += releaseObserver

  override def stop(): Unit = {
    path = null
    reactions -= pressObserver -= releaseObserver -= moveObserver
    timer.stop()
  }


  def pointToString(point: Point) =
    "(" + point.x + ", " + point.y + ")"
}

