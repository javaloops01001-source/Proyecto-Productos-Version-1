
public class Producto {
    private String estadoDisponible = "DISPONIBLE";
    private String estadoReservado = "RESERVADO";
    private String estadoAgotado = "AGOTADO";
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int idTienda;
    private boolean reservado;

    public Producto(int id, String nombre, String descripcion, double precio, int stock, int idTienda) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idTienda = idTienda;
        this.reservado = false;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public boolean isReservado() {
        return reservado;
    }

    public boolean disponible() {
        return getEstado().equals(estadoDisponible);
    }

    public boolean reservar() {
        boolean exito;
        if (disponible()) {
            stock = stock - 1;
            reservado = true;
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    public boolean cancelar() {
        boolean exito;
        if (reservado) {
            stock = stock + 1;
            reservado = false;
            exito = true;
        } else {
            exito = false;
        }
        return exito;
    }

    private String getEstado() {
        String estado;
        if (reservado) {
            estado = estadoReservado;
        } else if (stock > 0) {
            estado = estadoDisponible;
        } else {
            estado = estadoAgotado;
        }
        return estado;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | Precio: " + precio + " Bs | Stock: " + stock
                + " | Estado: " + getEstado();
    }
}
