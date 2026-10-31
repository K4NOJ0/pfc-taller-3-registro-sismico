package taller

import org.scalatest.funsuite.AnyFunSuite

class PuntosUnoYDosTest extends AnyFunSuite {

  val s = new RegistroSismico()
  import s._

  private val fecha = Fecha(2026, 1)
  private def sismo(region: String, magnitud: Double): Sismo =
    Sismo(fecha, region, magnitud, 20)

  test("fuertes conserva el orden y las regiones repetidas") {
    val r = List(sismo("Norte", 4.1), sismo("Sur", 5.8), sismo("Norte", 6.2))
    assert(fuertes(r, 4.0) == List("Norte", "Sur", "Norte"))
  }

  test("fuertes excluye magnitudes iguales al umbral") {
    val r = List(sismo("Este", 2.5), sismo("Oeste", 2.6))
    assert(fuertes(r, 2.5) == List("Oeste"))
  }

  test("fuertes sobre un registro vacío devuelve una lista vacía") {
    assert(fuertes(List.empty[Sismo], 0.0).isEmpty)
  }

  test("fuertes con todos los sismos por debajo del umbral devuelve vacío") {
    val r = List(sismo("Costa", 1.2), sismo("Llanos", 2.0))
    assert(fuertes(r, 2.1).isEmpty)
  }

  test("fuertes incluye todos los sismos que superan un umbral bajo") {
    val r = List(sismo("Centro", 0.8), sismo("Sur", 1.1))
    assert(fuertes(r, 0.5) == List("Centro", "Sur"))
  }

  test("enRegion mantiene el orden de magnitudes entre registros intercalados") {
    val r = List(
      sismo("Delta", 3.4), sismo("Eco", 4.0),
      sismo("Delta", 5.7), sismo("Delta", 2.9))
    assert(enRegion(r, "Delta") == List(3.4, 5.7, 2.9))
  }

  test("enRegion no confunde regiones con nombres que solo difieren en mayúsculas") {
    val r = List(sismo("Norte", 3.3), sismo("norte", 4.4))
    assert(enRegion(r, "Norte") == List(3.3))
  }

  test("enRegion devuelve la magnitud de un único registro coincidente") {
    val r = List(sismo("Isla", 6.1), sismo("Montaña", 2.2))
    assert(enRegion(r, "Isla") == List(6.1))
  }

  test("enRegion de una región ausente devuelve vacío") {
    val r = List(sismo("Páramo", 3.1))
    assert(enRegion(r, "Desierto").isEmpty)
  }

  test("enRegion de un registro vacío devuelve vacío") {
    assert(enRegion(List.empty[Sismo], "Delta").isEmpty)
  }

  test("paresCercanos emite una pareja una sola vez aunque haya varios sismos cercanos") {
    val r = List(
      sismo("A", 4.0), sismo("B", 4.1),
      sismo("A", 4.2), sismo("B", 4.3))
    assert(paresCercanos(r, 0.3) == List(("A", "B")))
  }

  test("paresCercanos incluye diferencias exactamente iguales al límite") {
    val r = List(sismo("Alfa", 2.0), sismo("Beta", 2.5))
    assert(paresCercanos(r, 0.5) == List(("Alfa", "Beta")))
  }

  test("paresCercanos no empareja una región consigo misma") {
    val r = List(sismo("Única", 1.0), sismo("Única", 9.0))
    assert(paresCercanos(r, 10.0).isEmpty)
  }

  test("paresCercanos devuelve vacío para cero o una región") {
    val soloUna = List(sismo("Única", 3.0), sismo("Única", 3.1))
    assert(paresCercanos(List.empty[Sismo], 1.0).isEmpty)
    assert(paresCercanos(soloUna, 1.0).isEmpty)
  }

  test("paresCercanos ordena regiones según su primera aparición") {
    val r = List(
      sismo("C", 7.0), sismo("A", 2.0),
      sismo("B", 7.1), sismo("A", 7.2))
    assert(paresCercanos(r, 0.2) == List(("C", "A"), ("C", "B"), ("A", "B")))
  }

  test("porRegion conserva todos los sismos de cada región") {
    val primero = sismo("Bosque", 3.3)
    val segundo = sismo("Lago", 5.2)
    val tercero = sismo("Bosque", 4.4)
    val r = List(primero, segundo, tercero)
    assert(porRegion(r) == Map("Bosque" -> List(primero, tercero), "Lago" -> List(segundo)))
  }

  test("porRegion conserva el orden de los sismos dentro de cada grupo") {
    val r = List(sismo("Sierra", 1.0), sismo("Valle", 2.0), sismo("Sierra", 3.0))
    assert(porRegion(r)("Sierra").map(_.magnitud) == List(1.0, 3.0))
  }

  test("porRegion sobre un registro vacío produce un mapa vacío") {
    assert(porRegion(List.empty[Sismo]).isEmpty)
  }

  test("porRegion crea un grupo cuando solo hay un sismo") {
    val unico = sismo("Isla", 5.5)
    assert(porRegion(List(unico)) == Map("Isla" -> List(unico)))
  }

  test("porRegion separa regiones con nombres que difieren en mayúsculas") {
    val r = List(sismo("Norte", 2.0), sismo("norte", 3.0))
    assert(porRegion(r).keySet == Set("Norte", "norte"))
  }

  test("conteoPorRegion cuenta regiones con cantidades distintas") {
    val r = List(
      sismo("Cordillera", 2.0), sismo("Costa", 3.0),
      sismo("Cordillera", 4.0), sismo("Cordillera", 5.0))
    assert(conteoPorRegion(r) == Map("Cordillera" -> 3, "Costa" -> 1))
  }

  test("conteoPorRegion no incluye regiones que no aparecen") {
    val r = List(sismo("Llanura", 3.2))
    assert(conteoPorRegion(r) == Map("Llanura" -> 1))
    assert(!conteoPorRegion(r).contains("Montaña"))
  }

  test("conteoPorRegion de un registro vacío es un mapa vacío") {
    assert(conteoPorRegion(List.empty[Sismo]).isEmpty)
  }

  test("conteoPorRegion asigna uno al único sismo de su región") {
    assert(conteoPorRegion(List(sismo("Isla", 4.7))) == Map("Isla" -> 1))
  }

  test("conteoPorRegion cuenta por separado regiones con capitalización diferente") {
    val r = List(sismo("Sur", 2.0), sismo("sur", 3.0), sismo("Sur", 4.0))
    assert(conteoPorRegion(r) == Map("Sur" -> 2, "sur" -> 1))
  }

  test("magnitudMaxima obtiene máximos aunque no sean el último sismo de cada grupo") {
    val r = List(
      sismo("Altiplano", 6.0), sismo("Costa", 5.0),
      sismo("Altiplano", 3.0), sismo("Costa", 7.0))
    assert(magnitudMaxima(r) == Map("Altiplano" -> 6.0, "Costa" -> 7.0))
  }

  test("magnitudMaxima conserva el valor cuando todos los sismos empatan") {
    val r = List(sismo("Bosque", 4.5), sismo("Bosque", 4.5))
    assert(magnitudMaxima(r) == Map("Bosque" -> 4.5))
  }

  test("magnitudMaxima de un registro vacío es un mapa vacío") {
    assert(magnitudMaxima(List.empty[Sismo]).isEmpty)
  }

  test("magnitudMaxima funciona con una sola región y un solo sismo") {
    assert(magnitudMaxima(List(sismo("Isla", 5.9))) == Map("Isla" -> 5.9))
  }

  test("magnitudMaxima solo contiene regiones presentes en el registro") {
    val r = List(sismo("Páramo", 4.2), sismo("Desierto", 3.8))
    assert(magnitudMaxima(r).keySet == Set("Páramo", "Desierto"))
    assert(!magnitudMaxima(r).contains("Selva"))
  }
}
