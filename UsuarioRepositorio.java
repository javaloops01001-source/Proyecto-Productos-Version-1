import java.util.ArrayList;
import java.util.List;
public class UsuarioRepositorio {

    private List<Usuario> usuarios = new ArrayList<>();
    private int siguienteId = 1;

    public int generarId() {
        int id = siguienteId;
        siguienteId = siguienteId + 1;
        return id;
    }

    public void agregar(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario buscarPorId(int id) {
        Usuario encontrado = null;
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                encontrado = u;
            }
        }
        return encontrado;
    }

    public List<Usuario> listar() {
        return usuarios;
    }

    public boolean estaVacio() {
        return usuarios.isEmpty();
    }
}
