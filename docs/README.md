# Informes

Aquí van los dos informes que pide el enunciado, en Markdown:

- `proceso.md`: el informe de proceso, cómo se ejecuta cada función paso a
  paso. Para este taller, en qué operaciones se traduce cada expresión `for`
  que haya usado, y la traza de `replicas` mostrando por qué `take(4)` solo
  calcula cuatro elementos de una secuencia que no termina.
- `correccion.md`: el informe de corrección, la argumentación de que cada
  función hace lo que el enunciado pide. Hay una que se pide en concreto: que
  el resultado de los agrupamientos no depende del orden en que se recorra el
  registro.

Los dos archivos ya existen y traen un ejemplo de lo que se espera:
`proceso.md` sigue la traza de `factorial(5)` con recursión de cola y dibuja la
pila con `mermaid`; `correccion.md` argumenta por inducción estructural la
corrección del factorial y del máximo de una lista, y con estado, invariante y
transformación la de sus versiones iterativas. El informe del taller reemplaza
ese contenido y sigue la misma forma sobre las funciones del enunciado.

La inducción del ejemplo se traslada a este taller por la lista que recorren
las expresiones `for`: el caso base es el registro vacío y el paso de
inducción añade un sismo al frente. Para la secuencia perezosa del punto 4 el
argumento es otro, y conviene decirlo así: cada elemento se calcula cuando se
pide, de modo que la propiedad se enuncia sobre los primeros `n`, no sobre
toda la secuencia.

La notación matemática se escribe en LaTeX dentro del Markdown y los
diagramas se generan con `mermaid`. No se aceptan imágenes insertadas.
