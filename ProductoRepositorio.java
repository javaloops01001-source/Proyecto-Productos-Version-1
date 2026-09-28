import java.util.ArrayList;
import java.util.List;
public class ProductoRepositorio {
    private List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;
    public int generarId() {
        int id = siguienteId;
        siguienteId = siguienteId + 1;
        return id;
    }

    public void agregar(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarPorId(int id) {
        Producto encontrado = null;
        for (Producto p : productos) {
            if (p.getId() == id) {
                encontrado = p;
            }
        }
        return encontrado;
    }

    public List<Producto> listar() {
        return productos;
    }

    public List<Producto> listarDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : productos) {
            if (p.disponible()) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }

    public List<Producto> listarReservados() {
        List<Producto> reservados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.isReservado()) {
                reservados.add(p);
            }
        }
        return reservados;
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}
