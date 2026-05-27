# Taller de Patrones de Diseño

**Asignatura:** Ingeniería de Software II

**Tema:** Patrones de diseño creacionales, estructurales y de comportamiento

**Lenguaje utilizado:** TypeScript

---

## 1. Introducción

Los patrones de diseño son soluciones probadas y reutilizables a problemas
comunes que aparecen una y otra vez en el desarrollo de software. No son código
que se copie tal cual, sino plantillas o ideas que se adaptan a cada caso.
Fueron popularizados por el libro *Design Patterns* (1994), escrito por los
autores conocidos como la "Banda de los Cuatro" (Gang of Four, GoF).

Los patrones se clasifican en tres familias:

- **Creacionales:** se ocupan de *cómo se crean los objetos*.
- **Estructurales:** se ocupan de *cómo se componen y relacionan los objetos*.
- **De comportamiento:** se ocupan de *cómo se comunican e interactúan los objetos*.

Este taller contiene cuatro implementaciones que, de forma progresiva,
combinan patrones de estas tres familias. Para que los ejemplos sean fáciles
de seguir, todos giran alrededor del mismo dominio: una cafetería.

### ¿Por qué TypeScript?

Los patrones de diseño son universales y pueden aplicarse en cualquier lenguaje
orientado a objetos (Java, C#, Python, etc.). Se eligió TypeScript porque:

- Tiene una sintaxis de clases e interfaces clara, ideal para aprender.
- Es el lenguaje base de frameworks web como Angular, y también muy usado
  en React, los cuales aplican estos patrones internamente.
- No requiere un entorno pesado para ejecutarse: basta con Node.js.

---

## 2. Preparación del entorno y ejecución del código

### 2.1. Instalación (una sola vez)

1. Instalar **Node.js** (versión LTS) desde https://nodejs.org
   Node.js incluye `npm`, el gestor de paquetes.
2. Instalar un editor de código, recomendado **Visual Studio Code**
   desde https://code.visualstudio.com
3. Abrir una terminal y ejecutar el siguiente comando para instalar
   las herramientas de TypeScript:

   ```
   npm install -g typescript ts-node
   ```

### 2.2. Ejecución de cada archivo

**Opción A — con `ts-node` (recomendada, un solo comando):**

```
npx ts-node 01-singleton.ts
npx ts-node 02-factory.ts
npx ts-node 03-factory-decorator.ts
npx ts-node 04-factory-decorator-observer.ts
```

**Opción B — compilar a JavaScript y luego ejecutar:**

```
tsc 01-singleton.ts
node 01-singleton.js
```

### 2.3. Resultados esperados

| Archivo | Salida esperada |
|---|---|
| `01-singleton.ts` | Total en caja $35 y "¿Es la misma caja? true" |
| `02-factory.ts` | Mensajes de preparación de café y chocolate |
| `03-factory-decorator.ts` | "Café + leche + azúcar", precio $8 |
| `04-factory-decorator-observer.ts` | Cocina y pantalla reaccionan al confirmar el pedido |

---

## 3. Marco teórico: patrones utilizados

### 3.1. Singleton (creacional)

**Propósito:** garantizar que una clase tenga una única instancia en todo
el programa y ofrecer un punto de acceso global a ella.

**Problema que resuelve:** existen recursos de los que solo debe haber uno
(la configuración de la aplicación, una conexión a base de datos, un registro
de logs). Si se permite crearlos libremente, pueden aparecer copias
inconsistentes que generan errores difíciles de detectar.

**Cómo funciona:** se hace el constructor *privado* para impedir el uso de
`new` desde fuera de la clase, y se ofrece un método estático (por ejemplo
`obtener()`) que crea la instancia la primera vez y devuelve siempre la misma
en las llamadas posteriores.

**Ventajas:** acceso controlado a la instancia única; se crea solo cuando se
necesita por primera vez.

**Desventajas:** puede dificultar las pruebas unitarias y, si se abusa de él,
introduce un estado global parecido a una variable global.

### 3.2. Factory Method (creacional)

**Propósito:** delegar la creación de objetos a un método especializado, de
modo que el código cliente no dependa de las clases concretas.

**Problema que resuelve:** cuando un programa debe crear distintos tipos de
objetos similares, llenar el código de condicionales con `new` repartidos por
todas partes lo vuelve frágil y difícil de mantener.

**Cómo funciona:** se define una *fábrica* que concentra toda la lógica de
creación. El cliente solo solicita un objeto indicando un tipo, y la fábrica
decide qué clase concreta instanciar y la devuelve.

**Ventajas:** centraliza la creación; agregar un nuevo tipo afecta un solo
lugar del código; el cliente trabaja contra interfaces, no contra clases
concretas.

**Desventajas:** introduce clases adicionales que pueden parecer excesivas en
problemas muy pequeños.

### 3.3. Decorator (estructural)

**Propósito:** añadir responsabilidades o funcionalidades a un objeto de forma
dinámica, sin modificar su clase original y sin crear subclases para cada
combinación posible.

**Problema que resuelve:** si se quieren múltiples combinaciones de
características (un café con leche; con leche y azúcar; con leche, azúcar y
crema...), crear una clase para cada combinación produce una explosión
inmanejable de clases.

**Cómo funciona:** un decorador es una clase que cumple la misma interfaz que
el objeto que envuelve y, además, contiene una referencia a ese objeto.
Cada decorador delega el trabajo al objeto envuelto y le suma su propio aporte.
Los decoradores pueden apilarse unos sobre otros como capas.

**Ventajas:** combina funcionalidades en tiempo de ejecución; respeta el
principio "abierto/cerrado" (abierto a extensión, cerrado a modificación).

**Desventajas:** puede generar muchos objetos pequeños y dificultar la
depuración por las múltiples capas de envoltura.

### 3.4. Observer (de comportamiento)

**Propósito:** definir una dependencia de uno-a-muchos entre objetos, de modo
que cuando un objeto cambia de estado, todos los que dependen de él son
notificados y actualizados automáticamente.

**Problema que resuelve:** cuando un cambio en un objeto debe reflejarse en
varios otros, conectarlos manualmente uno por uno crea un acoplamiento rígido
y difícil de mantener.

**Cómo funciona:** existe un *sujeto* (u observable) que mantiene una lista de
*observadores* suscritos. Cuando ocurre un evento relevante, el sujeto recorre
su lista y notifica a cada observador. El sujeto no necesita saber qué hace
cada observador, solo que debe avisarles.

**Ventajas:** bajo acoplamiento entre el sujeto y los observadores; se pueden
añadir o quitar observadores sin modificar el sujeto. Es la base de los
sistemas de eventos.

**Desventajas:** el orden de notificación no siempre es predecible; un mal uso
puede provocar cadenas de actualizaciones difíciles de seguir.

---

## 4. Implementaciones realizadas

Cada implementación está en un archivo independiente y reutiliza ideas de la
anterior, mostrando cómo los patrones se combinan de forma natural.

### 4.1. Implementación 1 — Singleton

**Archivo:** `01-singleton.ts`
**Patrones:** Singleton (creacional).

Se modela la caja registradora de la cafetería. Como solo puede existir
una caja, su constructor es privado y se accede a ella mediante el método
estático `Caja.obtener()`. La prueba demuestra que dos variables distintas que
"piden" la caja son, en realidad, el mismo objeto: las ventas registradas por
ambas se acumulan en el mismo total.

### 4.2. Implementación 2 — Factory Method

**Archivo:** `02-factory.ts`
**Patrones:** Factory Method (creacional, distinto de Singleton).

Se modela una fábrica de bebidas. Existe una interfaz `Bebida` y varias
clases concretas (`Cafe`, `Te`, `Chocolate`). La clase `FabricaDeBebidas` es
la única que ejecuta `new`: el cliente solo solicita la bebida por su nombre.
Para agregar una bebida nueva basta con crear su clase y añadir un caso en la
fábrica, sin tocar el resto del programa.

### 4.3. Implementación 3 — Factory Method + Decorator

**Archivo:** `03-factory-decorator.ts`
**Patrones:** Factory Method (creacional) + Decorator (estructural).

La fábrica crea una bebida base (un café o un té). Luego, los decoradores
`ConLeche` y `ConAzucar` la van envolviendo, añadiendo cada uno su descripción
y su precio. El resultado final ("Café + leche + azúcar", precio $8) se obtiene
apilando decoradores, sin necesidad de crear una clase específica para esa
combinación.

### 4.4. Implementación 4 — Factory Method + Decorator + Observer

**Archivo:** `04-factory-decorator-observer.ts`
**Patrones:** Factory Method (creacional) + Decorator (estructural) +
Observer (de comportamiento).

Se integran los tres patrones. La fábrica crea la bebida, el decorador le añade
un extra, y la clase `Pedido` actúa como sujeto observable. Los
observadores `Cocina` y `PantallaCliente` se suscriben al pedido; al llamar a
`confirmar()`, ambos son notificados automáticamente y reaccionan a su manera.
Añadir un nuevo interesado (por ejemplo, "Contabilidad") solo requiere crear
su clase y suscribirla, sin modificar la clase `Pedido`.

---

## 5. Conclusiones

1. Los patrones de diseño no son código rígido, sino soluciones
   conceptuales reutilizables. Lo importante es identificar el problema que
   cada patrón resuelve, para reconocer cuándo aplicarlo.

2. Las tres familias se complementan: los patrones creacionales gestionan
   la creación de objetos, los estructurales su composición, y los de
   comportamiento su comunicación. Un sistema real suele combinar varios,
   como se demostró en la cuarta implementación.

3. El uso de patrones favorece principios de buen diseño: bajo acoplamiento
   (las partes dependen poco entre sí), alta cohesión y respeto del
   principio abierto/cerrado (el código se extiende sin modificar lo ya
   existente). Esto se observó al poder agregar bebidas, extras u observadores
   nuevos sin alterar el código previo.

4. También conviene reconocer sus límites: aplicar patrones donde no se
   necesitan añade complejidad innecesaria. Un patrón debe usarse solo cuando
   el problema que resuelve está realmente presente.

5. Estos patrones no son teoría aislada: frameworks de desarrollo web como
   Angular y React los usan internamente. Los services de Angular
   funcionan como Singletons, su inyección de dependencias se apoya en fábricas,
   los decoradores (`@Component`, `@Injectable`) implementan el patrón Decorator,
   y la programación reactiva con observables aplica el patrón Observer.
   Comprender estos patrones facilita el aprendizaje posterior de dichas
   tecnologías.

