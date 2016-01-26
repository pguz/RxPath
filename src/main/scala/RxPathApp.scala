import controller.AppController
import view.{RxPathView, RxPathFrame}

object RxPathApp {
  def main(args: Array[String]): Unit = {
    javax.swing.SwingUtilities.invokeLater(new Runnable {

      val view = new RxPathView
      val controller = new AppController(view, view.reactionsDrawingBoard, view.reactionsParadigmSource)

      override def run(): Unit = {
        view.run
      }
    });
  }
}
