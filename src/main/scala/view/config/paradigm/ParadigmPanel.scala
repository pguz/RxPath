package view.config.paradigm

import protocols.Paradigms

import scala.swing._

object ParadigmPanel {
  val COORD_PARADIGM_TITLE: String = "Choose Paradigm:"
}

class ParadigmPanel extends BoxPanel(Orientation.Vertical) {
  import ParadigmPanel._

  border = scala.swing.Swing.TitledBorder(
    scala.swing.Swing.EtchedBorder,
    COORD_PARADIGM_TITLE)

  val btnImperative = new RadioButton() {
    name = Paradigms.COORD_IMPERATIVE_TITLE
    text = Paradigms.COORD_IMPERATIVE_TITLE
    selected = true
  }

  val btnReactiveRx = new RadioButton() {
    name = Paradigms.COORD_REACTIVE_RX_TITLE
    text = Paradigms.COORD_REACTIVE_RX_TITLE
  }

  val btnReactiveSodium = new RadioButton() {
    name = Paradigms.COORD_REACTIVE_SODIUM_TITLE
    text = Paradigms.COORD_REACTIVE_SODIUM_TITLE
  }

  val lstRadioButtons = List(
    btnImperative,
    btnReactiveRx,
    btnReactiveSodium
  )

  val btnGroupParadigm = new ButtonGroup {
    buttons ++= lstRadioButtons
  }

  val imperPanel = new ImperPanel(btnImperative)
  val reactRxPanel = new ReactPanel(btnReactiveRx)
  val reactSodiumPanel = new SodiumPanel(btnReactiveSodium)

  contents += Swing.VStrut(5) +=
    imperPanel += Swing.VStrut(5) +=
    reactRxPanel += Swing.VStrut(5) +=
    reactSodiumPanel += Swing.VStrut(5)

  lstRadioButtons.foreach(listenTo(_))
}
