package view.config.paradigm

import javax.swing.border.EtchedBorder
import javax.swing.table.DefaultTableModel

import scala.swing._

class ImperPanel(btnParadigm: RadioButton) extends BoxPanel(Orientation.Vertical) {
  border = scala.swing.Swing.EtchedBorder

  def setImpMouseDown(value: String, seconds: Long): Unit = {
    tableValues.update(0, 1, value)
    tableValues.update(0, 2, seconds)
  }

  def setImpMouseUp(value: String, seconds: Long): Unit = {
    tableValues.update(1, 1, value)
    tableValues.update(1, 2, seconds)
  }

  def setImpMouseMove(value: String, seconds: Long): Unit = {
    tableValues.update(2, 1, value)
    tableValues.update(2, 2, seconds)
  }

  val mdlTableValues =
    new DefaultTableModel( new Array[Array[AnyRef]](0), Array[AnyRef]("Name", "Value", "Time") ) {

      override def isCellEditable(r: Int, c: Int): Boolean = false
      addRow(Array[AnyRef]("pressObserver", "null", "0"))
      addRow(Array[AnyRef]("releaseObserver", "null", "0"))
      addRow(Array[AnyRef]("moveObserver", "null", "0"))

      val columnClass = Array(classOf[String], classOf[String], classOf[Long])
      override def getColumnClass(columnIndex: Int) = columnClass(columnIndex)

    }

  val tableValues = new Table(3, 3) {
    model = mdlTableValues
    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.NextColumn
    visible = true
    showGrid = true
    border = new EtchedBorder(EtchedBorder.RAISED)
    gridColor = new java.awt.Color(150, 150, 150)
    peer.getColumnModel.getColumn(0).setPreferredWidth((preferredSize.getWidth * 0.55).toInt)
    peer.getColumnModel.getColumn(1).setPreferredWidth((preferredSize.getWidth * 0.3).toInt)
    peer.getColumnModel.getColumn(2).setPreferredWidth((preferredSize.getWidth * 0.15).toInt)
  }

  contents += Swing.VStrut(5) +=
    new BorderPanel { add(btnParadigm, BorderPanel.Position.West) } +=
    Swing.VStrut(5) +=
    tableValues +=
    Swing.VStrut(5)
}
