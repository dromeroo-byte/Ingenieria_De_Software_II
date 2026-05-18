# Implementación básica de SOLID en Angular

## ¿Qué es SOLID?

S: Single Responsiblity Principle: Cada módulo, clase, función, interfaz, debe tener una sola razón para cambiar y, por lo tanto, cumplir solo con una tarea o tener solo una responsabilidad. En el ejemplo del video, si este principio no está aplicado, el componente invoca a la API, filtra la lista de productos y luego los muestra. En caso de añadir una nueva función a aplicar en la lista o modificar el API, debo modificar este componente siempre. En cambio, al aplicar Single Responsility, creo un componente para cada tarea, de esta forma es más fácil añadir nuevas funcionalidades simplemente creando nuevos componentes que se encarguen de las nuevas tareas, y si necesito modificar alguno de los componentes que ya tengo, solo tengo que modificar uno de ellos. Esto permite testear más fácilmente cada componente por separado.

O: Open/Closed Principle: Abierto a extensión, cerrado a modificación. Este principio nos indica que podemos añadir nuevas funcionalidades sin problema, pero que no podemos modificar lo que ya está funcionando. En el ejemplo del video, cuando no se implementa este principio, se tiene un componente con una serie de if/else dependiendo del tipo de notificación. El problema es que, si se quiere remover o añadir un nuevo tipo de notificación, es necesario modificar este componente. Aplicando este principio, el método que orquesta la notificación no cambia, pese a añadir o remover nuevos tipos de notificaciones, ya que es un método genérico el cual recibe como parámetro el tipo e invoca el método correspondiente al tipo de notificación. 

L: Liskov Substitution Principle: Las subclases o implementaciones deben poder sustituir a sus clases base o interfaces sin modificar el comportamiento esperado. En mi caso, lo asocio al uso de interfaces en vez de herencia de clases, ya que, al implementar la interfaz, estoy obligado a implementar las funciones de la interfaz como se indique. Al usar herencia de clases, existe el riesgo de sobrescribir el método y romper el comportamiento original. 

I: Interface Segregation Principle: Las piezas no deben obligarse a usar cosas que no usan. Lo que busca este principio es evitar crear interfaces que obliguen a implementar muchas cosas, donde en muchos casos no se va a requerir todo lo que provee la interfaz, obligando a ingresar parámetros vacíos cuando no se requiera. Al aplicar el principio, las interfaces incluyen funcionalidades más concretas, lo que da lugar a que, al implementarlas, se use todo lo que la interfaz provee sin dejar partes sin usar. 

D: Dependency Inversion Principle: Depende de abstracciones, no de implementaciones concretas. Este principio indica que no se puede depender de una implementación directa, más concretamente, escribir en código new Algo() ya que esto implica depender directamente de una clase concreta, lo que es rígido, y cualquier nueva implementación implica editar la clase base, crear una nueva o que el componente conozca demasiado. Con este principio, se busca que se aplique un “contrato” pero no necesariamente el cómo. La idea es crear una interfaz que defina lo que el “contrato” pide, haciéndolo abstracto, luego crear implementaciones concretas, y finalmente crear el servicio concreto que se desee.

---

## Objetivo del proyecto

El objetivo de este proyecto fue construir una pequeña aplicación de tareas en Angular aplicando una estructura inspirada en los principios SOLID.

La aplicación permite:

- Crear tareas
- Mostrar tareas
- Eliminar tareas
- Mantener responsabilidades separadas
- Facilitar futuras extensiones del sistema

---

# Estructura general del proyecto

La aplicación fue organizada en varias carpetas para separar responsabilidades:

```text
src/app/
│
├── models/
├── repositories/
├── services/
├── components/
```

Cada carpeta cumple una función específica.

---

# Modelo de datos

Se creó un modelo `Task` para representar las tareas.

Archivo:

```text
src/app/models/task.ts
```

Código:

```ts
export interface Task {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
}
```

Este modelo define únicamente la estructura de los datos de una tarea.

---

# Repositorio de tareas

Se creó una interfaz llamada `TaskRepository`.

Archivo:

```text
src/app/repositories/task-repository.ts
```

Código:

```ts
import { Task } from '../models/task';

export interface TaskRepository {

  getTasks(): Task[];

  addTask(task: Task): void;

  deleteTask(id: number): void;

}
```

La idea principal fue definir un contrato común para cualquier sistema de almacenamiento. Gracias a esto, el componente principal no depende directamente de una implementación específica.

---

# Servicio de almacenamiento

Se creó un servicio llamado `LocalTaskService`.

Archivo:

```text
src/app/services/local-task.service.ts
```

Código simplificado:

```ts
@Injectable({
  providedIn: 'root'
})
export class LocalTaskService implements TaskRepository {

  private tasks: Task[] = [];

  getTasks(): Task[] {
    return this.tasks;
  }

  addTask(task: Task): void {
    this.tasks.push(task);
  }

  deleteTask(id: number): void {

    const index = this.tasks.findIndex(
      task => task.id === id
    );

    if (index !== -1) {
      this.tasks.splice(index, 1);
    }

  }

}
```

Este servicio se encarga exclusivamente de administrar las tareas.

---

# Componente principal

Se creó el componente:

```text
TaskListComponent
```

Este componente se encarga de:

- Capturar información del usuario
- Mostrar tareas en pantalla
- Invocar métodos del servicio

Código simplificado:

```ts
export class TaskList {

  tasks: Task[] = [];

  title: string = '';

  description: string = '';

  constructor(
    private taskService: LocalTaskService
  ) {}

  ngOnInit() {
    this.tasks = this.taskService.getTasks();
  }

  addTask() {

    const task: Task = {

      id: Date.now(),

      title: this.title,

      description: this.description,

      createdAt: new Date()

    };

    this.taskService.addTask(task);

  }

}
```

---

# Interfaz gráfica

La interfaz se construyó usando Angular templates.

Código simplificado:

```html
<input
  type="text"
  placeholder="Título"
  [(ngModel)]="title"
>

<textarea
  placeholder="Descripción"
  [(ngModel)]="description"
></textarea>

<button (click)="addTask()">
  Agregar tarea
</button>

<div *ngFor="let task of tasks">

  <h3>{{ task.title }}</h3>

  <p>{{ task.description }}</p>

  <small>
    {{ task.createdAt | date:'short' }}
  </small>

  <button (click)="deleteTask(task.id)">
  Eliminar
  </button>

</div>
```

---

# Uso de FormsModule

Para utilizar `ngModel`, fue necesario importar `FormsModule`.

Archivo:

```text
src/app/app.module.ts
```

Código:

```ts
import { FormsModule } from '@angular/forms';

imports: [
  BrowserModule,
  FormsModule
]
```

---

# Problema encontrado durante el desarrollo

Al implementar la función de eliminar tareas, inicialmente el HTML no se actualizaba automáticamente. El problema ocurrió porque se estaba reemplazando el arreglo completo:

```ts
this.tasks = this.tasks.filter(...)
```

El componente conservaba una referencia al arreglo anterior. La solución fue modificar directamente el mismo arreglo usando `splice()`:

```ts
this.tasks.splice(index, 1);
```

Esto permitió que Angular detectara correctamente los cambios.

---

# Relación con SOLID

Durante el proyecto se aplicaron varias ideas relacionadas con SOLID:

- Separación de responsabilidades entre componentes, modelos y servicios.
- Uso de interfaces para desacoplar implementaciones.
- Posibilidad de extender el sistema sin modificar componentes principales.
- Uso de inyección de dependencias de Angular.

---

# Posibles mejoras futuras

El proyecto puede extenderse fácilmente agregando:

- Persistencia con LocalStorage
- Conexión a API REST
- Base de datos
- Edición de tareas
- Validaciones más avanzadas
- Componentes separados
- Uso de observables y RxJS

---

# Conclusiones

Este proyecto permitió entender cómo Angular facilita naturalmente una arquitectura basada en SOLID.

La separación entre componentes, servicios e interfaces hace que el sistema sea más organizado y mantenible.

También se observó que pequeñas decisiones en la estructura del código pueden facilitar mucho el crecimiento futuro de la aplicación.

La implementación mostró que aplicar SOLID no significa escribir código complicado, sino organizar correctamente las responsabilidades y dependencias.