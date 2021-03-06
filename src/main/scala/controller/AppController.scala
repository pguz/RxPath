package controller

import controller.paradigm.reactive.SodiumPathController
import view.ViewApi

import scala.swing.Reactions
import scala.swing.event.ButtonClicked

import protocols._
import controller.paradigm._

class AppController(view: ViewApi, pathReactions: Reactions, configReactions: Reactions) {

  var pathController: controller.paradigm.PathController =
    new imperative.ImperPathController(view, pathReactions)

  configReactions += {
    case ButtonClicked(button) => button.name match {

      case Paradigms.COORD_IMPERATIVE_TITLE => {
        pathController.stop()
        pathController = new imperative.ImperPathController(view, pathReactions)
      }

      case Paradigms.COORD_REACTIVE_RX_TITLE => {
        pathController.stop()
        pathController = new reactive.RxPathController(view)
      }

      case Paradigms.COORD_REACTIVE_SODIUM_TITLE => {
        pathController.stop()
        pathController = new SodiumPathController(view)
      }
    }
  }
}