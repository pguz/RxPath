package view.board

import java.awt.geom.GeneralPath
import java.awt.{Color, Graphics2D}

import view.SwingApi

import scala.swing.Component

class DrawingBoard extends Component with SwingApi {

  var path: GeneralPath = null

  def drawPath(path: GeneralPath) = {
    this.path = path
    repaint()
   }

  override def paint(g: Graphics2D): Unit = {
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, size.getWidth.toInt, size.getHeight.toInt)
    if(path != null) {
      g.setColor(Color.BLACK)
      g.draw(path)
    }
  }

  listenTo(mouse.clicks)
  listenTo(mouse.moves)
}
