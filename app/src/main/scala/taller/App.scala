package taller

object App {
  def main(args: Array[String]): Unit = {
    val r = new RegistroSismico()
    println(r.conteoPorRegion(Datos.registro))
  }
}
