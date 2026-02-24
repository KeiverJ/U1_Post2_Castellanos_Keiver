# Sistema de Gestión de Inventario

## U1_Post2 — Test-First Thinking

### Unidad 1: Modelado de Problemas y Especificación Formal — UDES

---

## Descripción

Sistema de gestión de inventario desarrollado aplicando la metodología **test-first thinking**:
las pruebas JUnit fueron escritas antes que el código de implementación, partiendo
directamente del enunciado y los casos borde identificados.

---

## Estructura del Proyecto

```
U1_Post2_Apellido_Nombre/
├── pom.xml
├── README.md
├── trazabilidad.md
├── capturas/
│   └── tests_exitosos.png
└── src/
    ├── main/java/
    │   ├── Producto.java
    │   └── Inventario.java
    └── test/java/
        └── TestInventario.java
```

---

## Requisitos

- Java JDK 17 o superior
- Maven 3.6 o superior

---

## Compilar y ejecutar pruebas

```bash
# Compilar el proyecto
mvn compile

# Ejecutar todas las pruebas
mvn test

# Ejecutar solo las pruebas del inventario
mvn test -Dtest=TestInventario
```

---

## Supuestos de Diseño

| ID  | Supuesto                                                                                                       |
| --- | -------------------------------------------------------------------------------------------------------------- |
| S1  | Los nombres son **case-insensitive**. `"Mouse"` y `"mouse"` son el mismo producto. Se almacenan en minúsculas. |
| S2  | Las categorías son Strings libres, no hay enum predefinido.                                                    |
| S3  | Los nombres no pueden tener caracteres especiales ni espacios, solo letras y números.                          |
| S4  | Operar sobre un producto inexistente lanza `IllegalArgumentException`.                                         |
| S5  | Aplicar descuento a una categoría sin productos no lanza excepción.                                            |
| S6  | `restock` con cantidad 0 lanza `IllegalArgumentException`.                                                     |
| S7  | Precios manejados como `double`. Comparaciones con delta de `0.001`.                                           |

---

## Decisiones de Diseño

**Case-insensitive:** los nombres se convierten a minúsculas en el constructor de `Producto`,
por lo que toda la lógica de búsqueda en `Inventario` es consistente sin conversiones adicionales.

**Validaciones en `Producto`:** las invariantes del producto se validan en su propio constructor,
siguiendo el principio de que cada clase es responsable de su propia integridad.

**`buscarProducto` privado:** la lógica de búsqueda está centralizada en un único método privado
dentro de `Inventario`, evitando repetición de código en `vender`, `restock` y `consultarStock`.

**`Collections.unmodifiableList`:** `productosStockBajo()` retorna una lista inmutable para
evitar que el llamador modifique el estado interno del inventario.

---

## Cobertura de Pruebas

| Requisito              | Pruebas | Casos Borde                              |
| ---------------------- | ------- | ---------------------------------------- |
| R1 — Agregar producto  | 8       | CB03, CB08, CB09, CB12, CB13, CB14, CB15 |
| R2 — Registrar venta   | 7       | CB01, CB02, CB16, CB17                   |
| R3 — Restock           | 3       | CB10                                     |
| R4 — Consultar stock   | 2       | —                                        |
| R5 — Stock bajo umbral | 3       | CB07, CB20                               |
| R6 — Valor total       | 3       | CB06                                     |
| R7 — Aplicar descuento | 6       | CB04, CB05, CB18, CB19                   |
| **Total**              | **32**  | **CB01–CB20**                            |

---
