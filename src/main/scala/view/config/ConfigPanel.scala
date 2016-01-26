package view.config

import view.config.paradigm.ParadigmPanel

import scala.swing.{Swing, BorderPanel}

object ConfigPanel {
  val DEFAULT_VERTICAL_GAP = 10
}

class ConfigPanel extends BorderPanel {
  import ConfigPanel._

  val paradigmPanel = new ParadigmPanel()
  val coordsPanel = new CoordsPanel()

  border = scala.swing.Swing.EmptyBorder(10)

  layout( new BorderPanel {
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.North
    layout(paradigmPanel) = BorderPanel.Position.Center
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.South
  }) = BorderPanel.Position.North

  layout( new BorderPanel {
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.North
    layout(coordsPanel) = BorderPanel.Position.Center
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.South
  }) = BorderPanel.Position.Center

  layout( new BorderPanel {
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.North
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.Center
    layout(Swing.VStrut(DEFAULT_VERTICAL_GAP)) = BorderPanel.Position.South
  }) = BorderPanel.Position.South
}
