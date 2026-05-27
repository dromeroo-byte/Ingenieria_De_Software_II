// ============================================================
// IMPLEMENTACIÓN 1 — PATRÓN SINGLETON (creacional)
// ============================================================
//
// ¿QUÉ PROBLEMA RESUELVE?
// A veces necesitamos que exista UNA SOLA instancia de algo en
// todo el programa. Ejemplo típico: la "configuración" de la app,
// o una conexión a base de datos, o —como aquí— la CAJA de una
// cafetería: solo hay una caja y todos los empleados usan la misma.
//
// IDEA CLAVE: el patrón Singleton bloquea la creación libre de
// objetos (hace el constructor privado) y ofrece un único punto
// de acceso a "la" instancia.
// ============================================================

class Caja {
  // 'private static' = una propiedad que pertenece a la CLASE
  // (no a cada objeto) y que nadie de afuera puede tocar.
  // Aquí guardaremos la única instancia que existirá.
  private static instancia: Caja;

  // El dinero acumulado en la caja.
  private dineroTotal: number = 0;

  // EL TRUCO DEL SINGLETON: el constructor es 'private'.
  // Esto significa que NADIE puede escribir 'new Caja()' desde fuera.
  // Así garantizamos que nadie cree una segunda caja por error.
  private constructor() {
    console.log("Se abrió la caja por primera vez.");
  }

  // Único punto de acceso permitido. Es 'static' = se llama sobre
  // la clase: Caja.obtener(), no sobre un objeto.
  public static obtener(): Caja {
    // Si todavía no existe la instancia, la creamos UNA vez.
    if (!Caja.instancia) {
      Caja.instancia = new Caja();
    }
    // En llamadas siguientes, devolvemos siempre la misma.
    return Caja.instancia;
  }

  // Método normal para registrar una venta.
  public registrarVenta(monto: number): void {
    this.dineroTotal += monto;
    console.log(`Venta de $${monto}. Total en caja: $${this.dineroTotal}`);
  }
}

// ---------------- USO / PRUEBA ----------------

// Dos empleados distintos "piden" la caja:
const cajaEmpleadoA = Caja.obtener();
const cajaEmpleadoB = Caja.obtener();

cajaEmpleadoA.registrarVenta(10);
cajaEmpleadoB.registrarVenta(25);

// PRUEBA DE QUE ES LA MISMA: comparamos las dos variables.
// '===' comprueba si apuntan literalmente al mismo objeto.
console.log("¿Es la misma caja?", cajaEmpleadoA === cajaEmpleadoB); // true

// Resultado esperado: el total es 35 porque ambas variables
// son, en realidad, la MISMA caja.
