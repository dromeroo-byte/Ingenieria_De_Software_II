// ============================================================
// IMPLEMENTACIÓN 2 — PATRÓN FACTORY METHOD (creacional)
// ============================================================
//
// ¿QUÉ PROBLEMA RESUELVE?
// Cuando hay que crear objetos de varios tipos parecidos, llenar
// el código de 'if' con 'new EstoOtro()' por todos lados se vuelve
// desordenado y difícil de mantener.
//
// IDEA CLAVE: el Factory Method ("método fábrica") concentra la
// CREACIÓN de objetos en un solo lugar. El resto del programa
// solo pide "dame una bebida tipo X" y no necesita saber cómo se
// construye cada una.
//
// ============================================================

// ---- 1) Un "contrato" común para todas las bebidas ----
// 'interface' = una lista de lo que TODA bebida debe saber hacer.
// No es código que se ejecute; es una promesa de forma.
interface Bebida {
  preparar(): void;
}

// ---- 2) Las bebidas concretas, cada una cumple el contrato ----
class Cafe implements Bebida {
  preparar(): void {
    console.log("Preparando un café: moler granos y verter agua caliente.");
  }
}

class Te implements Bebida {
  preparar(): void {
    console.log("Preparando un té: calentar agua e infusionar la hoja.");
  }
}

class Chocolate implements Bebida {
  preparar(): void {
    console.log("Preparando un chocolate: derretir cacao en leche tibia.");
  }
}

// ---- 3) LA FÁBRICA ----
// Esta clase es la ÚNICA que sabe hacer 'new'. Si mañana agregas
// una bebida nueva, solo tocas este lugar.
class FabricaDeBebidas {
  // Recibe un texto y devuelve "algo que es una Bebida".
  public crear(tipo: string): Bebida {
    switch (tipo) {
      case "cafe":
        return new Cafe();
      case "te":
        return new Te();
      case "chocolate":
        return new Chocolate();
      default:
        // Si piden algo desconocido, avisamos con un error claro.
        throw new Error(`No sabemos preparar: ${tipo}`);
    }
  }
}

// ---------------- USO / PRUEBA ----------------

const fabrica = new FabricaDeBebidas();

// El "cliente" solo pide por nombre. No escribe 'new Cafe()'.
// No le importa cómo se construye internamente cada bebida.
const pedido1 = fabrica.crear("cafe");
const pedido2 = fabrica.crear("chocolate");

pedido1.preparar();
pedido2.preparar();

// Ventaja: para añadir "Limonada" solo creas la clase Limonada
// y agregas un 'case' en la fábrica. El resto del código no cambia.
