package controller.paradigm.reactive

import java.awt.geom.GeneralPath

import controller.diagnostics.Diagnostics
import controller.paradigm.PathController
import rx.lang.scala.{Subscription, Observable}
import view.ViewApi

import scala.swing.Point

class RxPathController(view: ViewApi) extends PathController {

  val obsMouseDown: Observable[Boolean] =
    view.obsMouseDown.map(_ => true)
  val obsMouseUp: Observable[Boolean] =
    view.obsMouseUp.map(_ => false)
  val obsMouseMove: Observable[Point] =
    view.obsMouseMove

  val obsPathController: Observable[Boolean]
    = obsMouseDown.merge(obsMouseUp)

  val obsPathCoord: Observable[Option[Point]]
    = obsMouseMove.combineLatestWith(obsPathController) {
      case (coords, true) => Some(coords)
      case (_, false) => None}

  val obsPath: Observable[Option[GeneralPath]]
    = obsPathCoord.scan(None: Option[GeneralPath]) {
    (prevPath, sCoords) => sCoords.flatMap {
      coords => prevPath match {
        case None =>
          val path = new GeneralPath()
          path.moveTo(coords.getX(), coords.getY())
          Some(path)
        case Some(path) =>
          path.lineTo(coords.getX(), coords.getY())
          Some(path)}}
  }

  val subPath = obsPath.filter(_.isDefined).map(_.get).subscribe(view.drawPath(_))

  val subsDiagnoticts: List[Subscription] = List(
    Diagnostics.obsStream(obsMouseDown,        view.setObsMouseDown),
    Diagnostics.obsStream(obsMouseUp,          view.setObsMouseUp),
    Diagnostics.obsStream(obsMouseMove,        view.setObsMouseMove),
    Diagnostics.obsStream(obsPathController,   view.setObsPathController),
    Diagnostics.obsStream(obsPath,             view.setObsPath)
  )

  override def stop(): Unit = {
    subPath.unsubscribe()
    subsDiagnoticts.map(_.unsubscribe())
  }
}
