import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestiona el inventario de productos de la tienda.
 * Los nombres de productos son case-insensitive y se almacenan en minusculas.
 */
public class Inventario {

    private final Map<String, Producto> productos;
    private final int umbralMinimo;

    /** Crea un inventario con umbral minimo de stock por defecto (5 unidades). */
    public Inventario() {
        this(5);
    }

    /**
     * Crea un inventario con umbral minimo de stock configurable.
     * 
     * @param umbralMinimo umbral para considerar stock bajo
     */
    public Inventario(int umbralMinimo) {
        this.productos = new HashMap<>();
        this.umbralMinimo = umbralMinimo;
    }

    // Operaciones

    /**
     * Agrega un nuevo producto al inventario.
     *
     * @param nombre    nombre del producto
     * @param precio    precio del producto (mayor a 0)
     * @param cantidad  cantidad inicial (mayor o igual a 0)
     * @param categoria categoria del producto
     * @throws IllegalArgumentException si el nombre ya existe o los datos son
     *                                  invalidos
     */
    public void agregarProducto(String nombre, double precio,
            int cantidad, String categoria) {
        // Producto() ya valida nombre, precio, cantidad y categoria
        Producto nuevo = new Producto(nombre, precio, cantidad, categoria);
        if (productos.containsKey(nuevo.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el nombre: " + nombre);
        }
        productos.put(nuevo.getNombre(), nuevo);
    }

    /**
     * Registra la venta de N unidades de un producto.
     *
     * @param nombre   nombre del producto
     * @param cantidad unidades a vender (mayor a 0, no puede exceder stock)
     * @throws IllegalArgumentException si el producto no existe, cantidad invalida
     *                                  o excede stock
     */
    public void vender(String nombre, int cantidad) {
        Producto p = buscarProducto(nombre);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a vender debe ser mayor a 0");
        }
        if (cantidad > p.getCantidad()) {
            throw new IllegalArgumentException(
                    "Stock insuficiente. Stock actual: " + p.getCantidad());
        }
        p.setCantidad(p.getCantidad() - cantidad);
    }

    /**
     * Registra la reposicion de N unidades de un producto.
     *
     * @param nombre   nombre del producto
     * @param cantidad unidades a reponer (mayor a 0)
     * @throws IllegalArgumentException si el producto no existe o cantidad invalida
     */
    public void restock(String nombre, int cantidad) {
        Producto p = buscarProducto(nombre);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de restock debe ser mayor a 0");
        }
        if (cantidad == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("La cantidad es demasiado grande");
        }
        p.setCantidad(p.getCantidad() + cantidad);
    }

    /**
     * Consulta el stock actual de un producto.
     *
     * @param nombre nombre del producto
     * @return cantidad actual en stock
     * @throws IllegalArgumentException si el producto no existe
     */
    public int consultarStock(String nombre) {
        return buscarProducto(nombre).getCantidad();
    }

    /**
     * Retorna todos los productos con stock por debajo del umbral minimo.
     *
     * @return lista de productos con stock menor al umbral (vacia si ninguno
     *         califica)
     */
    public List<Producto> productosStockBajo() {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos.values()) {
            if (p.getCantidad() < umbralMinimo) {
                resultado.add(p);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    /**
     * Calcula el valor total del inventario (precio x cantidad de todos los
     * productos).
     *
     * @return suma de precio*cantidad para todos los productos; 0.0 si inventario
     *         vacio
     */
    public double valorTotal() {
        double total = 0.0;
        for (Producto p : productos.values()) {
            total += p.getPrecio() * p.getCantidad();
        }
        return total;
    }

    /**
     * Aplica un descuento porcentual a todos los productos de una categoria.
     *
     * @param categoria  categoria a la que se aplica el descuento
     * @param porcentaje descuento entre 0.0 y 50.0 inclusive
     * @throws IllegalArgumentException si el porcentaje esta fuera del rango
     *                                  permitido
     */
    public void aplicarDescuento(String categoria, double porcentaje) {
        if (porcentaje < 0.0 || porcentaje > 50.0) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre 0% y 50%. Recibido: " + porcentaje);
        }
        for (Producto p : productos.values()) {
            if (p.getCategoria().equals(categoria)) {
                p.setPrecio(p.getPrecio() * (1 - porcentaje / 100.0));
            }
        }
    }

    // Metodos privados

    /**
     * Busca un producto por nombre (case-insensitive).
     *
     * @param nombre nombre del producto a buscar
     * @return el producto encontrado
     * @throws IllegalArgumentException si no existe
     */
    private Producto buscarProducto(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacio");
        }
        Producto p = productos.get(nombre.toLowerCase());
        if (p == null) {
            throw new IllegalArgumentException(
                    "No existe un producto con el nombre: " + nombre);
        }
        return p;
    }
}