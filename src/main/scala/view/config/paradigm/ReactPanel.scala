package view.config.paradigm

import javax.swing.border.EtchedBorder
import javax.swing.table.DefaultTableModel

import scala.swing._

class ReactPanel(btnParadigm: RadioButton) extends BoxPanel(Orientation.Vertical) {
  border = scala.swing.Swing.EtchedBorder

  def setObsMouseDown(value: String, seconds: Long): Unit =
    updateTable(0, value, seconds)

  def setObsMouseUp(value: String, seconds: Long): Unit =
    updateTable(1, value, seconds)

  def setObsMouseMove(value: String, seconds: Long): Unit =
    updateTable(2, value, seconds)

  def setObsPathController(value: String, seconds: Long): Unit =
    updateTable(3, value, seconds)

  def setObsPath(value: String, seconds: Long): Unit =
    updateTable(4, value, seconds)

  def updateTable(row: Int, value: String, seconds: Long) = {
    tableValues.update(row, 1, value)
    tableValues.update(4, 2, seconds)
  }

  val mdlTableValues =
    new DefaultTableModel( new Array[Array[AnyRef]](0), Array[AnyRef]("Name", "Value", "Time") ) {

      override def isCellEditable(r: Int, c: Int): Boolean = false
      addRow(Array[AnyRef]("obsMouseDown",      "null", "0"))
      addRow(Array[AnyRef]("obsMouseUp",        "null", "0"))
      addRow(Array[AnyRef]("obsMouseMove",      "null", "0"))
      addRow(Array[AnyRef]("obsPathController", "null", "0"))
      addRow(Array[AnyRef]("obsPath",           "null", "0"))

      val columnClass = Array(classOf[String], classOf[String], classOf[Long])
      override def getColumnClass(columnIndex: Int) = columnClass(columnIndex)

    }

  val tableValues = new Table(3, 6) {
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
