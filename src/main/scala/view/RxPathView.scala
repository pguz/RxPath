package view

import java.awt.geom.GeneralPath

import rx.lang.scala.Observable

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

  override def setImpMouseDown(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseDown(value, time)
  override def setImpMouseMove(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseMove(value, time)
  override def setImpMouseUp(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.imperPanel.setImpMouseUp(value, time)

  override def setObsMouseDown(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactPanel.setObsMouseDown(value, time)
  override def setObsMouseUp   (value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactPanel.setObsMouseUp(value, time)
  override def setObsMouseMove (value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactPanel.setObsMouseMove(value, time)
  override def setObsPathController(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactPanel.setObsPathController(value, time)
  override def setObsPath(value: String, time: Long): Unit =
    frame.configPanel.paradigmPanel.reactPanel.setObsPath(value, time)

  override def drawPath(path: GeneralPath): Unit = {
    frame.drawingBoard.drawPath(path)
    frame.configPanel.coordsPanel.setPoints(path.getCurrentPoint)
  }

}

