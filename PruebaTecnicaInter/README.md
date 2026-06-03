# Prueba Técnica Android — Aplicación Controller

Aplicación Android desarrollada en Kotlin como solución a la prueba técnica. La app consume varios servicios REST de un entorno de pruebas, gestiona una base de datos local y presenta la información en tres pantallas navegables.

## Autor

Diego Alberto Romero Olmos

## Descripción general

La aplicación se organiza en las tres capas que plantea el enunciado:

1. **Seguridad.** Al arrancar, la app consulta la versión vigente del aplicativo contra el servidor y la compara con la versión local definida en el código, mostrando un mensaje según el resultado. A continuación realiza el proceso de autenticación; si responde correctamente, extrae los datos del usuario (usuario, identificación y nombre) y los guarda en la base de datos local.

2. **Datos.** Se consume el endpoint de esquema, que devuelve el listado de tablas del sistema. Ese listado se almacena en la base de datos SQLite local mediante Room.

3. **Presentación.** Tres pantallas: una pantalla principal (Home) con los datos del usuario y dos botones de navegación; una pantalla que muestra las tablas obtenidas del esquema; y una pantalla que consume el servicio de localidades y muestra la abreviación de ciudad junto con el nombre completo de cada registro.

El arranque se resuelve con una pantalla de carga (splash) que ejecuta el control de versiones y el login antes de pasar a Home.

## Tecnologías y librerías

- **Kotlin** como lenguaje principal.
- **Jetpack Compose** para la interfaz de usuario.
- **Retrofit** + **Gson** para el consumo de los servicios REST.
- **OkHttp** (interceptor de logging) para inspeccionar las peticiones durante el desarrollo.
- **Room** para la persistencia local sobre SQLite.
- **Corrutinas de Kotlin** para las operaciones asíncronas de red y base de datos.
- **Navigation Compose** para la navegación entre pantallas.

## Estructura del proyecto

El código se separa por responsabilidades, siguiendo la idea de los principios SOLID:

```
data/
  remote/      → interfaz de Retrofit, cliente HTTP y modelos de las respuestas
  local/       → entidades, DAOs y la base de datos Room
  repository/  → repositorios que median entre la red, la BD y la UI
ui/
  seguridad/   → splash, ViewModel y modelo de resultado del login
  datos/       → pantalla de tablas y su ViewModel
  localidades/ → pantalla de localidades y su ViewModel
  home/        → pantalla principal
  navigation/  → definición de rutas y grafo de navegación
```

Cada pantalla observa su propio `ViewModel`, que a su vez se apoya en un repositorio. Los repositorios son los únicos que conocen de dónde vienen los datos (API o base de datos), de modo que la interfaz no depende directamente de Retrofit ni de Room.

## Decisiones de implementación

- **Separación de modelos.** Los modelos que recibe la API (en `data/remote`) están separados de las entidades que se guardan en la base de datos (en `data/local`). Cada uno puede cambiar sin afectar al otro.
- **Manejo de nulos.** El entorno de pruebas devuelve algunos campos en `null` (por ejemplo, identificación y nombre en el login). Los modelos contemplan esos casos y la app no se interrumpe; muestra un valor por defecto cuando el dato no llega.
- **Manejo de errores.** Todas las llamadas de red están envueltas en `try/catch` y se verifica el código de respuesta HTTP. Cuando un servicio responde con un código distinto de 200, se informa al usuario en lugar de fallar silenciosamente.
- **Headers de autenticación.** El endpoint de esquema exige los mismos headers de identificación que el login; el de localidades, en cambio, es de acceso libre. Esto se determinó probando los servicios antes de integrarlos.
- **Persistencia del esquema.** Antes de guardar el esquema nuevo se limpia el anterior, para evitar registros duplicados entre sincronizaciones.

## Configuración y ejecución

1. Clonar el repositorio y abrir el proyecto en Android Studio.
2. Esperar a que Gradle sincronice y descargue las dependencias.
3. Conectar un dispositivo físico (con depuración USB activada) o iniciar un emulador.
4. Ejecutar el proyecto con el botón *Run*.

Se requiere conexión a internet, ya que la app consume servicios en línea durante el arranque.

## Notas

- La versión local del aplicativo está definida como una constante en el `ViewModel` de seguridad. Modificándola se pueden comprobar los distintos mensajes del control de versiones (versión inferior, superior o igual a la del servidor).
- La pantalla de carga incluye una pausa breve para que el mensaje del control de versiones alcance a visualizarse antes de pasar a la pantalla principal.
- Las credenciales utilizadas son las provistas por el enunciado de la prueba y apuntan a un entorno de pruebas.