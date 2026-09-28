public class Rol {
    private String admin = "admin";
    private String consumidor = "consumidor";

    public String getAdmin() {
        return admin;
    }

    public String normalizar(String texto) {
        String resultado;
        if (texto == null) {
            resultado = null;
        } else {
            String limpio = texto.trim().toLowerCase();
            if (limpio.equals(admin) || limpio.equals(consumidor)) {
                resultado = limpio;
            } else {
                resultado = null;
            }
        }
        return resultado;
    }

    public boolean esAdmin(String rol) {
        return rol.equals(admin);
    }
}
