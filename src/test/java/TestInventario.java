import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TestInventario {

    private Inventario inv;

    @BeforeEach
    void setUp() {
        inv = new Inventario();
    }

    // R1 — agregarProducto

    @Test
    @DisplayName("CB— R1: Agregar producto básico y consultar stock")
    void testAgregarProductoBasico() {
        inv.agregarProducto("Lavadora", 999.99, 10, "Electronica");
        assertEquals(10, inv.consultarStock("Lavadora"));
    }

    @Test
    @DisplayName("CB03 — R1: Agregar producto con cantidad cero es válido")
    void testAgregarCantidadCero() {
        inv.agregarProducto("Cable", 5.00, 0, "Accesorios");
        assertEquals(0, inv.consultarStock("Cable"));
    }

    @Test
    @DisplayName("CB08 — R1: Agregar producto con nombre null lanza excepción")
    void testAgregarNombreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto(null, 10.00, 5, "Accesorios"));
    }

    @Test
    @DisplayName("CB09 — R1: Agregar producto con nombre vacío lanza excepción")
    void testAgregarNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto("", 10.00, 5, "Accesorios"));
    }

    @Test
    @DisplayName("CB12 — R1: Agregar producto duplicado lanza excepción")
    void testAgregarProductoDuplicado() {
        inv.agregarProducto("Mouse", 25.00, 10, "Accesorios");
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto("Mouse", 30.00, 5, "Accesorios"));
    }

    @Test
    @DisplayName("CB13 — R1: Agregar duplicado con diferente categoría lanza excepción")
    void testAgregarDuplicadoOtraCategoria() {
        inv.agregarProducto("Mouse", 25.00, 10, "Accesorios");
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto("Mouse", 30.00, 5, "Electronica"));
    }

    @Test
    @DisplayName("CB14 — R1: Agregar producto con precio cero lanza excepción")
    void testAgregarPrecioCero() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto("Teclado", 0.0, 5, "Accesorios"));
    }

    @Test
    @DisplayName("CB15 — R1: Agregar producto con precio negativo lanza excepción")
    void testAgregarPrecioNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.agregarProducto("Teclado", -10.0, 5, "Accesorios"));
    }

    // R2 — vender

    @Test
    @DisplayName("CB— R2: Vender reduce el stock correctamente")
    void testVenderReduceStock() {
        inv.agregarProducto("Mouse", 25.00, 20, "Accesorios");
        inv.vender("Mouse", 3);
        assertEquals(17, inv.consultarStock("Mouse"));
    }

    @Test
    @DisplayName("CB01 — R2: Vender exactamente todo el stock deja stock en 0")
    void testVenderTodoElStock() {
        inv.agregarProducto("Cable", 5.00, 3, "Accesorios");
        inv.vender("Cable", 3);
        assertEquals(0, inv.consultarStock("Cable"));
    }

    @Test
    @DisplayName("CB02 — R2: Vender exactamente 1 unidad")
    void testVenderUnaUnidad() {
        inv.agregarProducto("Audifono", 15.00, 5, "Accesorios");
        inv.vender("Audifono", 1);
        assertEquals(4, inv.consultarStock("Audifono"));
    }

    @Test
    @DisplayName("CB— R2: Vender más que el stock lanza excepción")
    void testVenderMasQueStock() {
        inv.agregarProducto("Teclado", 50.00, 2, "Accesorios");
        assertThrows(IllegalArgumentException.class,
                () -> inv.vender("Teclado", 5));
    }

    @Test
    @DisplayName("CB16 — R2: Vender cantidad cero lanza excepción")
    void testVenderCantidadCero() {
        inv.agregarProducto("Monitor", 300.00, 5, "Electronica");
        assertThrows(IllegalArgumentException.class,
                () -> inv.vender("Monitor", 0));
    }

    @Test
    @DisplayName("CB17 — R2: Vender cantidad negativa lanza excepción")
    void testVenderCantidadNegativa() {
        inv.agregarProducto("Monitor", 300.00, 5, "Electronica");
        assertThrows(IllegalArgumentException.class,
                () -> inv.vender("Monitor", -3));
    }

    @Test
    @DisplayName("CB— R2: Vender producto inexistente lanza excepción")
    void testVenderProductoInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.vender("ProductoFantasma", 1));
    }

    // R3 — restock

    @Test
    @DisplayName("CB— R3: Restock aumenta el stock correctamente")
    void testRestockAumentaStock() {
        inv.agregarProducto("Laptop", 999.99, 5, "Electronica");
        inv.restock("Laptop", 10);
        assertEquals(15, inv.consultarStock("Laptop"));
    }

    @Test
    @DisplayName("CB— R3: Restock con cantidad cero lanza excepción")
    void testRestockCantidadCero() {
        inv.agregarProducto("Laptop", 999.99, 5, "Electronica");
        assertThrows(IllegalArgumentException.class,
                () -> inv.restock("Laptop", 0));
    }

    @Test
    @DisplayName("CB— R3: Restock de producto inexistente lanza excepción")
    void testRestockProductoInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.restock("ProductoFantasma", 5));
    }

    // R4 — consultarStock

    @Test
    @DisplayName("CB— R4: Consultar stock de producto existente")
    void testConsultarStockExistente() {
        inv.agregarProducto("TV", 1000.00, 7, "Electronica");
        assertEquals(7, inv.consultarStock("TV"));
    }

    @Test
    @DisplayName("CB— R4: Consultar stock de producto inexistente lanza excepción")
    void testConsultarStockInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.consultarStock("ProductoFantasma"));
    }

    // R5 — productosStockBajo

    @Test
    @DisplayName("CB07 — R5: Inventario vacío retorna lista vacía")
    void testStockBajoInventarioVacio() {
        assertTrue(inv.productosStockBajo().isEmpty());
    }

    @Test
    @DisplayName("CB— R5: Retorna correctamente productos bajo el umbral")
    void testStockBajoRetornaCorrectos() {
        inv.agregarProducto("CableUSB", 5.00, 2, "Accesorios");
        inv.agregarProducto("Laptop", 999.99, 20, "Electronica");
        List<Producto> bajos = inv.productosStockBajo();
        assertEquals(1, bajos.size());
        assertEquals("cableusb", bajos.get(0).getNombre());
    }

    @Test
    @DisplayName("CB20 — R5: Producto con stock exactamente igual al umbral NO aparece")
    void testStockExactoUmbralNoAparece() {
        inv.agregarProducto("CableHDMI", 8.00, 5, "Accesorios"); // umbral default = 5
        assertTrue(inv.productosStockBajo().isEmpty());
    }

    // R6 — valorTotal

    @Test
    @DisplayName("CB06 — R6: Valor total de inventario vacío es 0.0")
    void testValorTotalInventarioVacio() {
        assertEquals(0.0, inv.valorTotal(), 0.001);
    }

    @Test
    @DisplayName("CB— R6: Valor total con un solo producto")
    void testValorTotalUnProducto() {
        inv.agregarProducto("TV", 1000.00, 3, "Electronica");
        assertEquals(3000.0, inv.valorTotal(), 0.001);
    }

    @Test
    @DisplayName("CB— R6: Valor total con varios productos")
    void testValorTotalVarios() {
        inv.agregarProducto("TV", 1000.00, 2, "Electronica");
        inv.agregarProducto("Mouse", 25.00, 4, "Accesorios");
        assertEquals(2100.0, inv.valorTotal(), 0.001);
    }

    // R7 — aplicarDescuento

    @Test
    @DisplayName("CB04 — R7: Descuento del 50% reduce precio a la mitad")
    void testDescuento50Porciento() {
        inv.agregarProducto("TV", 1000.00, 5, "Electronica");
        inv.aplicarDescuento("Electronica", 50.0);
        assertEquals(2500.0, inv.valorTotal(), 0.01);
    }

    @Test
    @DisplayName("CB05 — R7: Descuento del 0% no modifica el precio")
    void testDescuento0Porciento() {
        inv.agregarProducto("TV", 1000.00, 5, "Electronica");
        inv.aplicarDescuento("Electronica", 0.0);
        assertEquals(5000.0, inv.valorTotal(), 0.01);
    }

    @Test
    @DisplayName("CB18 — R7: Descuento mayor a 50% lanza excepción")
    void testDescuentoInvalidoMayor50() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.aplicarDescuento("Electronica", 75.0));
    }

    @Test
    @DisplayName("CB19 — R7: Descuento negativo lanza excepción")
    void testDescuentoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> inv.aplicarDescuento("Electronica", -10.0));
    }

    @Test
    @DisplayName("CB— R7: Descuento solo afecta productos de la categoría indicada")
    void testDescuentoSoloAfectaCategoria() {
        inv.agregarProducto("TV", 1000.00, 1, "Electronica");
        inv.agregarProducto("Mouse", 100.00, 1, "Accesorios");
        inv.aplicarDescuento("Electronica", 50.0);
        // TV: 500.0 + Mouse: 100.0 = 600.0
        assertEquals(600.0, inv.valorTotal(), 0.01);
    }

    @Test
    @DisplayName("CB— R7: Descuento a categoría sin productos no lanza excepción")
    void testDescuentoCategoriaInexistente() {
        assertDoesNotThrow(() -> inv.aplicarDescuento("CategoriaFantasma", 10.0));
    }
}