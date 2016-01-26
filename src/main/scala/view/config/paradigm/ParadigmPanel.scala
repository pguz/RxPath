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

  val btnReactive = new RadioButton() {
    name = Paradigms.COORD_REACTIVE_TITLE
    text = Paradigms.COORD_REACTIVE_TITLE
  }

  val lstRadioButtons = List(
    btnImperative,
    btnReactive
  )

  val btnGroupParadigm = new ButtonGroup {
    buttons ++= lstRadioButtons
  }

  val imperPanel = new ImperPanel(btnImperative)
  val reactPanel = new ReactPanel(btnReactive)

  contents += Swing.VStrut(5) += imperPanel += Swing.VStrut(5) += reactPanel += Swing.VStrut(5)

  lstRadioButtons.foreach(listenTo(_))
}
