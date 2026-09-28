import java.util.ArrayList;
import java.util.List;

public class TiendaRepositorio {
    private List<Tienda> tiendas = new ArrayList<>();
    private int siguienteId = 1;
    public int generarId() {
        int id = siguienteId;
        siguienteId = siguienteId + 1;
        return id;
    }

    public void agregar(Tienda tienda) {
        tiendas.add(tienda);
    }

    public Tienda buscarPorId(int id) {
        Tienda encontrada = null;
        for (Tienda t : tiendas) {
            if (t.getId() == id) {
                encontrada = t;
            }
        }
        return encontrada;
    }

    public String obtenerNombre(int id) {
        String nombre;
        Tienda tienda = buscarPorId(id);
        if (tienda != null) {
            nombre = tienda.getNombre();
        } else {
            nombre = "Desconocida";
        }
        return nombre;
    }

    public List<Tienda> listar() {
        return tiendas;
    }

    public boolean estaVacio() {
        return tiendas.isEmpty();
    }
}
