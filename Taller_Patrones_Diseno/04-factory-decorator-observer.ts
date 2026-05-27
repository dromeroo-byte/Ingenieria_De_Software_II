// ============================================================
// IMPLEMENTACIÓN 4 — FACTORY (creacional) + DECORATOR (estructural)
//                    + OBSERVER (de comportamiento)
// ============================================================
//
// Combinamos TRES patrones.
// El nuevo es:
//
//  - OBSERVER (de comportamiento): permite que varios objetos
//    "se enteren" automáticamente cuando algo cambia, sin que el
//    que cambia tenga que conocerlos uno por uno.
//
// ¿QUÉ PROBLEMA RESUELVE EL OBSERVER?
// Cuando se confirma un pedido en la cafetería, varias áreas
// quieren enterarse: la COCINA (para prepararlo) y la PANTALLA
// del cliente (para mostrar el estado). El Observer hace que el
// pedido solo diga "¡listo!" una vez, y todos los interesados
// reaccionen solos. Es la idea detrás de los "eventos".
// ============================================================

// ====== PARTE A: FACTORY + DECORATOR (resumido de antes) ======

interface Bebida {
  descripcion(): string;
  precio(): number;
}

class CafeSimple implements Bebida {
  descripcion() { return "Café"; }
  precio() { return 5; }
}

class FabricaDeBebidas {
  crear(tipo: string): Bebida {
    if (tipo === "cafe") return new CafeSimple();
    throw new Error(`Bebida desconocida: ${tipo}`);
  }
}

abstract class ExtraDecorator implements Bebida {
  constructor(protected bebida: Bebida) {}
  abstract descripcion(): string;
  abstract precio(): number;
}

class ConLeche extends ExtraDecorator {
  descripcion() { return this.bebida.descripcion() + " + leche"; }
  precio() { return this.bebida.precio() + 2; }
}

// ====== PARTE B: OBSERVER (el patrón nuevo) ======
//
// El Observer tiene dos roles:
//   - SUJETO (u "observable"): el objeto que cambia y avisa.
//   - OBSERVADOR: cada objeto que quiere ser avisado.

// Contrato de un observador: debe saber "recibir noticias".
interface Observador {
  notificar(mensajeDelPedido: string): void;
}

// Observadores concretos: cada uno reacciona a su manera.
class Cocina implements Observador {
  notificar(mensaje: string): void {
    console.log(`[COCINA] Empezando a preparar: ${mensaje}`);
  }
}

class PantallaCliente implements Observador {
  notificar(mensaje: string): void {
    console.log(`[PANTALLA] Tu pedido está confirmado: ${mensaje}`);
  }
}

// EL SUJETO: el pedido. Mantiene una lista de interesados
// y, cuando se confirma, les avisa a TODOS de una sola vez.
class Pedido {
  private observadores: Observador[] = [];

  constructor(private bebida: Bebida) {}

  // Permite que un observador se "suscriba" a este pedido.
  public suscribir(observador: Observador): void {
    this.observadores.push(observador);
  }

  // Al confirmar, recorre la lista y avisa a cada uno.
  // El pedido NO sabe qué hace cada observador; solo los notifica.
  public confirmar(): void {
    const mensaje = `${this.bebida.descripcion()} ($${this.bebida.precio()})`;
    console.log("--- Pedido confirmado, avisando a todos ---");
    for (const obs of this.observadores) {
      obs.notificar(mensaje);
    }
  }
}

// ---------------- USO / PRUEBA ----------------

// 1) FACTORY crea la bebida base.
const fabrica = new FabricaDeBebidas();
let bebida: Bebida = fabrica.crear("cafe");

// 2) DECORATOR le añade un extra.
bebida = new ConLeche(bebida);

// 3) OBSERVER: creamos el pedido y suscribimos a los interesados.
const pedido = new Pedido(bebida);
pedido.suscribir(new Cocina());
pedido.suscribir(new PantallaCliente());

// 4) Con UNA sola llamada, cocina y pantalla reaccionan solas.
pedido.confirmar();

// Ventaja: si mañana se quiere avisar también a "Contabilidad",
// solo se crea esa clase y se suscribe. El Pedido no se modifica.
