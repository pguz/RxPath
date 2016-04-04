package view

import java.awt.geom.GeneralPath

import rx.lang.scala.Observable
import sodium.Stream

import scala.swing.Point

trait ViewApi {
  def drawPath(path: GeneralPath)

  def setImpMouseDown         (value: String, time: Long)
  def setImpMouseUp           (value: String, time: Long)
  def setImpMouseMove         (value: String, time: Long)

  def setObsMouseDown         (value: String, time: Long)
  def setObsMouseUp           (value: String, time: Long)
  def setObsMouseMove         (value: String, time: Long)
  def setObsPathController    (value: String, time: Long)
  def setObsPath              (value: String, time: Long)

  def setSodMouseDown         (value: String, time: Long)
  def setSodMouseUp           (value: String, time: Long)
  def setSodMouseMove         (value: String, time: Long)
  def setSodPathController    (value: String, time: Long)
  def setSodPath              (value: String, time: Long)

  val obsMouseDown: Observable[Point]
  val obsMouseUp:   Observable[Point]
  val obsMouseMove: Observable[Point]

  val sMouseDown: Stream[Point]
  val sMouseUp:   Stream[Point]
  val sMouseMove: Stream[Point]
}
