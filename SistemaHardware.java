import java.util.List;
import java.util.Scanner;

public class SistemaHardware {
    private int entradaInvalida = -1;
    private UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
    private TiendaRepositorio tiendaRepositorio = new TiendaRepositorio();
    private ProductoRepositorio productoRepositorio = new ProductoRepositorio();
    private Scanner sc = new Scanner(System.in);
    private Rol rolValidador = new Rol();

    public static void main(String[] args) {
        SistemaHardware sistema = new SistemaHardware();
        sistema.iniciar();
    }

    private void iniciar() {
        cargarDatos();
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerNumero();
            continuar = ejecutarOpcion(opcion);
        }
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE HARDWARE (RESERVAS) =====");
        System.out.println("0. Registrar usuario");
        System.out.println("1. Registrar tienda");
        System.out.println("2. Agregar producto a tienda");
        System.out.println("3. Mostrar catalogo de productos");
        System.out.println("4. Reservar un producto");
        System.out.println("5. Cancelar reserva");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private boolean ejecutarOpcion(int opcion) {
        boolean continuar = true;
        switch (opcion) {
            case 0:
                registrarUsuario();
                break;
            case 1:
                registrarTienda();
                break;
            case 2:
                agregarProducto();
                break;
            case 3:
                listarProductos();
                break;
            case 4:
                reservar();
                break;
            case 5:
                cancelar();
                break;
            case 6:
                System.out.println("Saliendo...");
                continuar = false;
                break;
            default:
                System.out.println("Opcion incorrecta.");
                break;
        }
        return continuar;
    }

    private void cargarDatos() {
        Usuario admin = new Usuario(usuarioRepositorio.generarId(), "Admin", "admin@hardware.com", "1234", rolValidador.getAdmin());
        usuarioRepositorio.agregar(admin);
        Tienda tienda = new Tienda(tiendaRepositorio.generarId(), "Ferreteria El Centro", "Av. Siempreviva 742", "7654321");
        tiendaRepositorio.agregar(tienda);
        Producto martillo = new Producto(productoRepositorio.generarId(), "Martillo", "Martillo 500g", 45.0, 3, tienda.getId());
        Producto taladro = new Producto(productoRepositorio.generarId(), "Taladro", "Taladro 650W", 250.0, 5, tienda.getId());
        Producto destornillador = new Producto(productoRepositorio.generarId(), "Destornillador", "Juego de destornilladores", 30.0, 0, tienda.getId());
        productoRepositorio.agregar(martillo);
        productoRepositorio.agregar(taladro);
        productoRepositorio.agregar(destornillador);
        System.out.println("Datos de prueba cargados.");
    }

    private String leerCampoObligatorio(String etiqueta) {
        System.out.print(etiqueta + ": ");
        String valor = sc.nextLine().trim();
        String resultado;
        if (valor.isEmpty()) {
            System.out.println("El campo " + etiqueta + " es obligatorio.");
            resultado = null;
        } else {
            resultado = valor;
        }
        return resultado;
    }

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRAR USUARIO ---");
        String nombre = leerCampoObligatorio("Nombre");
        String email = leerCampoObligatorio("Email");
        String password = leerCampoObligatorio("Contraseña");
        String rolTexto = leerCampoObligatorio("Rol (admin/consumidor)");
        boolean datosBasicosCompletos = nombre != null && email != null && password != null && rolTexto != null;
        if (datosBasicosCompletos) {
            String rol = rolValidador.normalizar(rolTexto);
            if (rol != null) {
                Usuario nuevo = new Usuario(usuarioRepositorio.generarId(), nombre, email, password, rol);
                usuarioRepositorio.agregar(nuevo);
                System.out.println("Usuario registrado con exito.");
            } else {
                System.out.println("Rol invalido. Debe ser 'admin' o 'consumidor'.");
            }
        }
    }

    private void registrarTienda() {
        System.out.println("\n--- REGISTRAR TIENDA ---");
        String nombre = leerCampoObligatorio("Nombre");
        if (nombre != null) {
            System.out.print("Direccion: ");
            String direccion = sc.nextLine().trim();
            System.out.print("Telefono o celular: ");
            String telefono = sc.nextLine().trim();
            Tienda nueva = new Tienda(tiendaRepositorio.generarId(), nombre, direccion, telefono);
            tiendaRepositorio.agregar(nueva);
            System.out.println("Tienda registrada. ID: " + nueva.getId());
        }
    }

    private void agregarProducto() {
        if (tiendaRepositorio.estaVacio()) {
            System.out.println("Primero registre una tienda.");
        } else {
            System.out.println("\n--- AGREGAR PRODUCTO ---");
            System.out.println("Tiendas:");
            for (Tienda t : tiendaRepositorio.listar()) {
                System.out.println(t);
            }
            System.out.print("ID de tienda: ");
            int idTienda = leerNumero();
            Tienda tiendaSeleccionada = tiendaRepositorio.buscarPorId(idTienda);
            if (tiendaSeleccionada == null) {
                System.out.println("Tienda no existe.");
            } else {
                registrarNuevoProducto(tiendaSeleccionada.getId());
            }
        }
    }

    private void registrarNuevoProducto(int idTienda) {
        String nombre = leerCampoObligatorio("Nombre del producto");
        if (nombre != null) {
            System.out.print("Descripcion: ");
            String descripcion = sc.nextLine().trim();
            System.out.print("Precio: ");
            double precio = leerDouble();
            System.out.print("Stock: ");
            int stock = leerNumero();

            if (precio <= 0) {
                System.out.println("Precio debe ser positivo.");
            } else if (stock < 0) {
                System.out.println("Stock no puede ser negativo.");
            } else {
                Producto nuevo = new Producto(productoRepositorio.generarId(), nombre, descripcion, precio, stock, idTienda);
                productoRepositorio.agregar(nuevo);
                System.out.println("Producto agregado.");
            }
        }
    }

    private void listarProductos() {
        if (productoRepositorio.estaVacio()) {
            System.out.println("No hay productos.");
        } else {
            System.out.println("\n--- CATALOGO ---");
            for (Producto p : productoRepositorio.listar()) {
                String nombreTienda = tiendaRepositorio.obtenerNombre(p.getIdTienda());
                System.out.println(p + " | Tienda: " + nombreTienda);
            }
        }
    }

    private void reservar() {
        List<Producto> disponibles = productoRepositorio.listarDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay disponibles.");
        } else {
            System.out.println("\n--- RESERVAR ---");
            for (Producto p : disponibles) {
                System.out.println(p + " | Tienda: " + tiendaRepositorio.obtenerNombre(p.getIdTienda()));
            }
            System.out.print("ID del producto: ");
            int id = leerNumero();
            Producto producto = productoRepositorio.buscarPorId(id);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
            } else if (producto.reservar()) {
                System.out.println("Reserva exitosa. Pasa a comprar en 24 horas.");
            } else {
                System.out.println("No se puede reservar.");
            }
        }
    }

    private void cancelar() {
        List<Producto> reservados = productoRepositorio.listarReservados();
        if (reservados.isEmpty()) {
            System.out.println("No hay reservas.");
        } else {
            System.out.println("\n--- CANCELAR RESERVA ---");
            System.out.println("Reservados:");
            for (Producto p : reservados) {
                System.out.println(p + " | Tienda: " + tiendaRepositorio.obtenerNombre(p.getIdTienda()));
            }
            System.out.print("ID a cancelar: ");
            int id = leerNumero();
            Producto producto = productoRepositorio.buscarPorId(id);
            if (producto == null) {
                System.out.println("No existe.");
            } else if (producto.cancelar()) {
                System.out.println("Reserva cancelada.");
            } else {
                System.out.println("Ese producto no tenia una reserva activa.");
            }
        }
    }

    private int leerNumero() {
        String entrada = sc.nextLine().trim();
        int valor;
        if (esNumeroValido(entrada, false)) {
            valor = Integer.parseInt(entrada);
        } else {
            valor = entradaInvalida;
        }
        return valor;
    }

    private double leerDouble() {
        String entrada = sc.nextLine().trim();
        double valor;
        if (esNumeroValido(entrada, true)) {
            valor = Double.parseDouble(entrada);
        } else {
            valor = entradaInvalida;
        }
        return valor;
    }

    private boolean esNumeroValido(String entrada, boolean permitirPunto) {
        boolean valido = entrada.length() > 0;
        int puntos = 0;
        int i = 0;
        while (valido && i < entrada.length()) {
            char c = entrada.charAt(i);
            if (permitirPunto && c == '.') {
                puntos = puntos + 1;
                valido = puntos <= 1;
            } else if (c < '0' || c > '9') {
                valido = false;
            }
            i = i + 1;
        }
        return valido;
    }
}
