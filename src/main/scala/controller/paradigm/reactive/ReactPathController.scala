package controller.paradigm.reactive

import java.awt.geom.GeneralPath

import controller.diagnostics.Diagnostics
import controller.paradigm.PathController
import rx.lang.scala.{Subscription, Observable}
import view.ViewApi

class ReactPathController(view: ViewApi) extends PathController {

  val obsMouseDown = view.obsMouseDown.map(_ => true)
  val obsMouseUp   = view.obsMouseUp.map(_ => false)
  val obsMouseMove = view.obsMouseMove

  val obsPathController: Observable[Boolean]
    = obsMouseDown.merge(obsMouseUp)

  val obsPath: Observable[GeneralPath]
    = obsMouseMove.combineLatest(obsPathController).scan(null: GeneralPath) {
    case (_, (_, false)) => null
    case (prevPath, (coords, true)) => prevPath match {
      case null =>
        val path = new GeneralPath()
        path.moveTo(coords.getX(), coords.getY())
        path
      case path =>
        path.lineTo(coords.getX(), coords.getY())
        path
    };
  }.filter(path => path != null)

  val subPath = obsPath.subscribe(path => {
     view.drawPath(path)})

  val subsDiagnoticts: List[Subscription] = List(
    Diagnostics.stream(obsMouseDown,        {(value: String, time: Long) => view.setObsMouseDown(value, time)}),
    Diagnostics.stream(obsMouseUp,          {(value: String, time: Long) => view.setObsMouseUp(value, time)}),
    Diagnostics.stream(obsMouseMove,        {(value: String, time: Long) => view.setObsMouseMove(value, time)}),
    Diagnostics.stream(obsPathController,   {(value: String, time: Long) => view.setObsPathController(value, time)}),
    Diagnostics.stream(obsPath,             {(value: String, time: Long) => view.setObsPath(value, time)})
  )

  override def stop(): Unit = {
    subPath.unsubscribe()
    subsDiagnoticts.map(subDiagnoticts => subDiagnoticts.unsubscribe())
  }
}
