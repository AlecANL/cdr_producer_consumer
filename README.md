# Procesamiento de CDR de Telefonía con Hilos en Java

### Descripción del Proyecto
En este proyecto, los estudiantes implementarán un sistema de procesamiento de registros de
detalle de llamadas (CDR) utilizando hilos en Java. El objetivo es simular un sistema que procese
grandes volúmenes de datos de llamadas telefónicas, utilizando el patrón productor-consumidor
para manejar la concurrencia y la sincronización entre hilos.

### Descripción:
**Entrada de Datos**

Los datos de entrada serán registros de detalle de llamadas (CDR) almacenados en un archivo
CSV. Cada línea del archivo contiene la siguiente información, separada por comas:
* Número de cuenta
* Número del que llama
* Número al que se llama
* Timestamp de la llamada
* Duración de la llamada en minutos

Ejemplo de una línea de entrada:

**50681517,555-4132,555-4554,26/07/2024 16:02:00,18,0.08,internacional**

1. **CDR:** Representar un registro de llamada con atributos como número de cuenta, número
del que llama, número al que se llama, timestamp y duración en minutos.

2. **CDRProducer:** Leer los registros del archivo CSV y ponerlos en una cola para ser
procesados por los consumidores.

3. **CDRConsumer:** Tomar los registros de la cola, procesarlos y calcular el total de minutos
hablados y la tarifa total por cada número de cuenta.

4. **Sincronización:** Implementar la sincronización adecuada para manejar la concurrencia
entre los productores y consumidores.

5. **Almacenamiento de Datos:** Se debe de crear una estructura de datos que permita
almacenar el detalle de cada CDR almacenado. La estructura es definida por el
estudiante.

6. **Resumen de Resultados en UI:** Al finalizar el procesamiento, imprimir en pantalla un
resumen que muestre el total de minutos hablados y la tarifa total para cada número de
cuenta. Se puede utilizar los controles de interfaz que el estudiante considere correcta.

### Condiciones de evaluación:
* Por cada proceso producto se debe de levantar, como mínimo, 3 hilos.
* Por cada proceso consumidor se debe de levantar, como mínimo, 2 hilos.
* El día de presentación de la clase, el catedrático le proporcionará un archivo de prueba de
CDR y los resultados deben de coincidir con los resultados del catedrático.
* La aplicación debe de contar con una interfaz gráfica que muestre dos secciones:
    * Sección de ejecución:
        * Selección de archivo.
        * Botón para iniciar ejecución de carga de CDR.
        * Indicador que muestre por cada hilo productor:
        * Fecha hora que inició el hilo.
        * Cantidad de registros que lleva producidos.
        * Indicador que muestre por cada hilo consumidor:
        * Fecha hora que inició el hilo.
        * Cantidad de registros que lleva consumidos.
        * Total de minutos que lleva procesados por hilo.

    * Sección de resultados:
        * Grid que permita buscar por número de cuenta.
        * Los resultados debe de mostrar agrupado por cuentas la cantidad de minutos y tarifa por cuenta. Por ejemplo, suponiendo que se reciben 5 CDR a diferentes hora del día, todos la cuenta X55 con duración de 10 minutos c/u, entonces el resultado debe de ser que X55 habló 50 minutos a tarifa de QXX.XX
* La sección de resultados se puede omitir, siempre y cuando se genere y se muestre en clase los resultados directamente en la base de datos mediante queries.
* El almacenamiento en base de datos NO es opcional.
* El punteo del proyecto es de 50% código fuente, 50% presentación funcional en clase.
* El código fuente se debe de versionar en un repositorio público de Github, y debe de tener al menos 5 commits registrados en diferentes fechas del semestre.
    * El proyecto debe de contar con:
        - Clase CDR: Clase orquestradora del aplicativo.
        - Clase CDRProducer: Producto de los CDR.
        - Clase CDRConsumer: Consumidor de los CDR.

**Requisitos Técnicos**
* Utilizar Java 8 o superior.
* Utiliza MySQL.
* Implementar el manejo adecuado de excepciones.
* Asegurarse de que el programa maneja correctamente la sincronización y concurrencia.

**Fecha de Entrega, sábado 20/09/2024**
* Informe escrito.
* Código fuente.
* Presentación.

**Evaluación**

La evaluación se basará en:
* Funcionamiento correcto del código.
* Implementación adecuada de hilos y sincronización.
* Claridad y organización del código.
* Documentación y comentarios en el código.
* Informe escrito detallado.
