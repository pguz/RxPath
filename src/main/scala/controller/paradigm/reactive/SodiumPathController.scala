package controller.paradigm.reactive

import java.awt.geom.GeneralPath

import controller.diagnostics.Diagnostics
import controller.paradigm.PathController
import view.ViewApi

import scala.swing.Point

import sodium._

class SodiumPathController(view: ViewApi) extends PathController {

  val l: Listener = new Listener {
    override def unlisten(): Unit = {}
  }

  Transaction.run(_ => {
    val sMouseDown: Stream[Boolean] =
      view.sMouseDown.map(_ => true)
    val sMouseUp: Stream[Boolean] =
      view.sMouseUp.map(_ => false)
    val sMouseMove: Stream[Point] =
      view.sMouseMove
    val sPathController: Stream[Boolean] =
      sMouseDown.merge(sMouseUp)

    val cPoint: Cell[Point] = sMouseMove.hold(new Point(0, 0))

    val cPath: CellLoop[Option[GeneralPath]] = new CellLoop[Option[GeneralPath]]
    val sPathInitializer: Stream[Option[GeneralPath]] = sPathController.map {
        case false => None
        case true  => {
          val path = new GeneralPath();
          path.moveTo(cPoint.sample().getX, cPoint.sample().getY);
          Some(path)
        }
      }
    val sPathBuilder: Stream[Option[GeneralPath]] =
      sMouseMove.snapshot(cPath, (coord: Point, prevPath: Option[GeneralPath]) => {
        prevPath.flatMap(path => {path.lineTo(coord.getX(), coord.getY()); Some(path)})
      })

    val sPathStream: Stream[Option[GeneralPath]] =
      sPathInitializer.merge(sPathBuilder, (pController, pBuilder) => pController);

    cPath.loop(sPathStream.hold(None));
    l.append(Stream.filterOption(sPathStream).listen(view.drawPath(_)))

    List(
      Diagnostics.sodStream(sMouseDown,       view.setSodMouseDown),
      Diagnostics.sodStream(sMouseUp,         view.setSodMouseUp),
      Diagnostics.sodStream(sMouseMove,       view.setSodMouseMove),
      Diagnostics.sodStream(sPathController,  view.setSodPathController),
      Diagnostics.sodStream(Stream.filterOption(sPathStream), view.setSodPath)
    ).foreach(l.append(_))
  }
  )

  override def stop(): Unit = {
    l.unlisten()
  }
}
