package view

import java.awt.geom.GeneralPath

import rx.lang.scala.Observable
import sodium.Stream

import scala.swing.Point

class RxPathView extends ViewApi with SwingApi {

  val frame = new RxPathFrame

  def run(): Unit = {
    frame.run
  }

  val reactionsDrawingBoard   = frame.drawingBoard.reactions
  val reactionsParadigmSource = frame.configPanel.paradigmPanel.reactions

  override val obsMouseUp:    Observable[Point] = frame.drawingBoard.releases
  override val obsMouseMove:  Observable[Point] = frame.drawingBoard.moves
  override val obsMouseDown:  Observable[Point] = frame.drawingBoard.presses

  override val sMouseDown: Stream[Point] = frame.drawingBoard.sPressed
  override val sMouseMove: Stream[Point] = frame.drawingBoard.sMoved
  override val sMouseUp: Stream[Point] = frame.drawingBoard.sReleased

  override def setImpMouseDown(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseDown(value, time)
  override def setImpMouseMove(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseMove(value, time)
  override def setImpMouseUp(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseUp(value, time)

  override def setObsMouseDown(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactRxPanel.setMouseDown(value, time)
  override def setObsMouseUp   (value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactRxPanel.setMouseUp(value, time)
  override def setObsMouseMove (value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactRxPanel.setMouseMove(value, time)
  override def setObsPathController(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactRxPanel.setPathController(value, time)
  override def setObsPath(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactRxPanel.setPath(value, time)

  override def drawPath(path: GeneralPath): Unit = {
    frame.drawingBoard.drawPath(path)
    frame.configPanel.coordsPanel.setPoints(path.getCurrentPoint)
  }

  override def setSodMouseDown(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactSodiumPanel.setMouseDown(value, time)
  override def setSodMouseUp(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactSodiumPanel.setMouseUp(value, time)
  override def setSodMouseMove(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactSodiumPanel.setMouseMove(value, time)
  override def setSodPathController(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactSodiumPanel.setPathController(value, time)
  override def setSodPath(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactSodiumPanel.setPath(value, time)
}

