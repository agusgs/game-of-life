#Game of life

Se desea implementar en Java usando metodos synchronized una clase “monitor” que encapsule el comportamiento del Juego
de la Vida de John Conway. El juego de la vida es una simulacion en un universo en forma de grilla bidimensional en
donde cada celda puede estar viva o muerta. La simulacion progresa generacion tras generacion siguiendo los cambios en
las celdas de la grilla a partir de una configuracion inicial. Cada celda interactua con sus 8 celdas vecinas siguiendo
las siguientes reglas:

1. Una celda viva con menos de dos vecinos vivos muere de soledad.

2. Una celda viva con dos o tres vecinos vivos, continua viviendo en la siguiente generacion.

3. Una celda viva con mas de tres vecinos vivos, muere por sobrepoblacion.

4. Una celda muerta con exactamente tres vecinos vivos se convierte en una celda viva por efecto de reproduccion.

Junto con el enunciado se provee una interfaz grafica (adaptada de la publicada por Edwin Martin) que utiliza la grilla
y la muestra por pantalla. El codigo entregado esta compuesto por tres paquetes. El paquete gameoflife contiene los
archivos necesarios para levantar la interfaz grafica y no deben ser modificados. El paquete shapes contiene utilidades
para configuraciones iniciales predefinidas. El paquete solution esta destinado a contener todos los archivos que
consideren necesarios para implementar la solucion. La entrega debe incluir ademas un test suite para el codigo
desarrollado que logre un 100 % de cobertura de sentencias (para medirlo se recomienda utilizar el plugin de eclipse
Colver). Puede experimentar creando casos de test unitarios utilizando el plugin de Eclipse Evo-suite.

Se pide programar en el paquete solution la clase GameOfLifeGrid que implemente la interfaz utilizada por la interfaz
grafica que se presenta a continuacion. Tenga en cuenta que la solucion solo sera correcta si distribuye el trabajo
equitativamente entre todos los threads y genera el mismo resultado independientemente de la cantidad de threads
utilizados.
