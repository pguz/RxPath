package view

import javax.swing.UIManager

import view.board.DrawingBoard
import view.config.ConfigPanel

import scala.swing.GridBagPanel.Fill
import scala.swing.{Dimension, Frame, GridBagPanel}

object RxPathFrame {
  val TITLE: String = "RxPath"

  val DEFAULT_BOARD_WIDTH     :Int  = 640;
  val DEFAULT_CONFIG_WIDTH    :Int  = 0;

  val DEFAULT_WIDTH   :Int  = DEFAULT_BOARD_WIDTH + DEFAULT_CONFIG_WIDTH;
  val DEFAULT_HEIGHT  :Int  = 480;
}

class RxPathFrame extends Frame with SwingApi {
  import RxPathFrame._

  {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch {
      case t: Throwable =>
    }
  }

  title = TITLE
  visible = true
  minimumSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)
  preferredSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)
  maximumSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)

  val drawingBoard = new DrawingBoard()
  val configPanel = new ConfigPanel()

  contents = new GridBagPanel() {
    val drawingBoardConstraints: Constraints = new Constraints() {
      gridx = 0
      gridy = 0
      weightx = DEFAULT_BOARD_WIDTH / DEFAULT_WIDTH.toDouble
      weighty = 1
      fill = Fill.Both
    }
    val configPanelConstraints: Constraints = new Constraints() {
      gridx = 1
      gridy = 0
      weightx = DEFAULT_CONFIG_WIDTH / DEFAULT_WIDTH.toDouble
      weighty = 1
      fill = Fill.Both
    }

    layout(drawingBoard)  = drawingBoardConstraints
    layout(configPanel)   = configPanelConstraints
  }

  def run: Unit = {
    pack()
  }

}
