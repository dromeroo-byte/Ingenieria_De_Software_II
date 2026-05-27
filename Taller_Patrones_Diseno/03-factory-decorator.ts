// ============================================================
// IMPLEMENTACIÓN 3 — FACTORY METHOD (creacional) + DECORATOR (estructural)
// ============================================================
//
// Aquí combinamos DOS patrones:
//
//  - FACTORY METHOD (ya lo viste): crea la bebida base.
//  - DECORATOR (nuevo, estructural): ENVUELVE un objeto para
//    añadirle funcionalidad SIN modificar su clase original.
//
// ¿QUÉ PROBLEMA RESUELVE EL DECORATOR?
// Imagina un café al que le quieres poner leche, y azúcar, y crema...
// Crear una clase "CafeConLecheConAzucarConCrema" para cada
// combinación sería una locura. El Decorator permite ir "envolviendo"
// el café con extras, uno encima de otro, como capas de cebolla.
// ============================================================

// ---- Contrato común: toda bebida tiene descripción y precio ----
interface Bebida {
  descripcion(): string;
  precio(): number;
}

// ---- Bebida base concreta ----
class CafeSimple implements Bebida {
  descripcion(): string {
    return "Café";
  }
  precio(): number {
    return 5;
  }
}

class TeSimple implements Bebida {
  descripcion(): string {
    return "Té";
  }
  precio(): number {
    return 4;
  }
}

// ---- FACTORY METHOD: crea la bebida base ----
class FabricaDeBebidas {
  crear(tipo: string): Bebida {
    if (tipo === "cafe") return new CafeSimple();
    if (tipo === "te") return new TeSimple();
    throw new Error(`Bebida desconocida: ${tipo}`);
  }
}

// ---- DECORATOR: la pieza clave de esta implementación ----
//
// Un decorador es una clase que:
//   1) TAMBIÉN cumple el contrato 'Bebida' (por fuera parece una bebida).
//   2) GUARDA dentro otra 'Bebida' (la que va a envolver).
//   3) Llama a la bebida envuelta y le SUMA su propio aporte.
//
// 'abstract' = es una plantilla base, no se usa directamente.
abstract class ExtraDecorator implements Bebida {
  // Guardamos la bebida que estamos envolviendo.
  constructor(protected bebida: Bebida) {}

  // Por defecto, cada decorador concreto definirá estos métodos.
  abstract descripcion(): string;
  abstract precio(): number;
}

// Decorador concreto: añade leche.
class ConLeche extends ExtraDecorator {
  descripcion(): string {
    // Pide la descripción de lo que envuelve y le añade "+ leche".
    return this.bebida.descripcion() + " + leche";
  }
  precio(): number {
    // Toma el precio de lo envuelto y le suma su costo.
    return this.bebida.precio() + 2;
  }
}

// Decorador concreto: añade azúcar.
class ConAzucar extends ExtraDecorator {
  descripcion(): string {
    return this.bebida.descripcion() + " + azúcar";
  }
  precio(): number {
    return this.bebida.precio() + 1;
  }
}

// ---------------- USO / PRUEBA ----------------

const fabrica = new FabricaDeBebidas();

// 1) La FÁBRICA crea la bebida base.
let miBebida: Bebida = fabrica.crear("cafe");

// 2) Vamos ENVOLVIENDO con decoradores. Cada 'new' recibe la
//    bebida anterior y devuelve una versión "mejorada".
miBebida = new ConLeche(miBebida);   // ahora es: Café + leche
miBebida = new ConAzucar(miBebida);  // ahora es: Café + leche + azúcar

// El resultado por fuera SIGUE siendo una 'Bebida' normal.
console.log("Pedido:", miBebida.descripcion());
console.log("Precio total: $" + miBebida.precio());

// Resultado: "Café + leche + azúcar", precio 5 + 2 + 1 = 8.
// Nunca tuvimos que crear una clase "CafeConLecheYAzucar".
