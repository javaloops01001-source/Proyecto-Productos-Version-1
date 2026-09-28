
public class Tienda {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    public Tienda(int id, String nombre, String direccion, String telefono) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tienda es obligatorio.");
        }
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " - " + direccion + " - Tel: " + telefono;
    }
}
