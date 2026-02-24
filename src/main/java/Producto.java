
/**
 * Representa un producto en el inventario.
 * Invariantes: precio > 0, cantidad >= 0, nombre no null ni vacio.
 */

public class Producto {

    private final String nombre;
    private double precio;
    private int cantidad;
    private final String categoria;

    /**
     * Crea un nuevo producto validando todas las invariantes.
     *
     * nombre del producto (no null, no vacio, solo letras y
     * numeros)
     * precio del producto (mayor a 0)
     * cantidad inicial (mayor o igual a 0)
     * categoria del producto (no null)
     */
    public Producto(String nombre, double precio, int cantidad, String categoria) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarCantidad(cantidad);
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria no puede ser null");
        }
        this.nombre = nombre.toLowerCase();
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // Getters

    /** @return nombre del producto en minusculas */
    public String getNombre() {
        return nombre;
    }

    /** @return precio actual del producto */
    public double getPrecio() {
        return precio;
    }

    /** @return cantidad actual en stock */
    public int getCantidad() {
        return cantidad;
    }

    /** @return categoria del producto */
    public String getCategoria() {
        return categoria;
    }

    // Setters con validacion

    /**
     * Actualiza el precio del producto.
     * 
     * @param precio nuevo precio (debe ser mayor a 0)
     */
    public void setPrecio(double precio) {
        validarPrecio(precio);
        this.precio = precio;
    }

    /**
     * Actualiza la cantidad en stock.
     * 
     * @param cantidad nueva cantidad (debe ser mayor o igual a 0)
     */
    public void setCantidad(int cantidad) {
        validarCantidad(cantidad);
        this.cantidad = cantidad;
    }

    // Validaciones privadas

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacio");
        }
        if (!nombre.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y numeros");
        }
    }

    private void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }

    private void validarCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        if (cantidad == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("La cantidad es demasiado grande");
        }
    }

    @Override
    public String toString() {
        return String.format("Producto{nombre='%s', precio=%.2f, cantidad=%d, categoria='%s'}",
                nombre, precio, cantidad, categoria);
    }
}