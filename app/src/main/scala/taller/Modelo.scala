package taller

/** Modelo de datos del taller 3, tal como lo entrega el material. */
case class Fecha(anio: Int, mes: Int)

case class Sismo(fecha: Fecha, region: String,
                 magnitud: Double, profundidad: Int)

case class Resumen(sismos: Int, maxima: Double, replicas: Int)

object Datos {
  type Registro = List[Sismo]

  val registro: Registro = List(
    Sismo(Fecha(2025, 3), "Valle", 4.2, 30),
    Sismo(Fecha(2025, 3), "Santander", 5.1, 150),
    Sismo(Fecha(2025, 4), "Valle", 3.8, 25),
    Sismo(Fecha(2025, 4), "Choco", 6.0, 40),
    Sismo(Fecha(2025, 5), "Santander", 4.7, 160),
    Sismo(Fecha(2025, 5), "Valle", 5.3, 35),
    Sismo(Fecha(2025, 6), "Choco", 3.1, 20))
}
