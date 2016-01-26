package view.config

import java.awt.geom.Point2D

import scala.swing._

import javax.swing.border.EtchedBorder
import javax.swing.table.{DefaultTableModel}

object CoordsPanel {
  val TITLE: String = "Coordinates"
}

class CoordsPanel extends BoxPanel(Orientation.Horizontal) {
  import CoordsPanel._

  //border = scala.swing.Swing.TitledBorder(
    //scala.swing.Swing.EtchedBorder,
    //TITLE)

  val mdlTableSolarystem =
    new DefaultTableModel( new Array[Array[AnyRef]](0), Array[AnyRef]("X", "Y") ) {

      override def isCellEditable(r: Int, c: Int): Boolean = false
      addRow(Array[AnyRef]("0", "0"))

      val columnClass = Array(classOf[Int], classOf[Int])
      override def getColumnClass(columnIndex: Int) = columnClass(columnIndex)

    }

  val tableSolarystem = new Table(2, 1) {
    model = mdlTableSolarystem
    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.NextColumn
    preferredViewportSize = new Dimension(30, rowHeight)
    visible = true
    showGrid = true
    border = new EtchedBorder(EtchedBorder.RAISED)
    gridColor = new java.awt.Color(150, 150, 150)
  }

  //contents += new ScrollPane(tableSolarystem)

  def setPoints(point: Point2D) = {
    mdlTableSolarystem.setValueAt(point.getX.toInt, 0, 0)
    mdlTableSolarystem.setValueAt(point.getY.toInt, 0, 1)
  }
}
