
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private String rol;
    private Rol validadorRol = new Rol();
    public Usuario(int id, String nombre, String email, String password, String rol) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }
        if (validadorRol.normalizar(rol) == null) {
            throw new IllegalArgumentException("El rol del usuario no es valido.");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = validadorRol.normalizar(rol);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public boolean esAdmin() {
        return validadorRol.esAdmin(rol);
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " (" + email + ") - Rol: " + rol;
    }
}
