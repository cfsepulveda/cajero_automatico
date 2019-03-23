package ejemplo.cajero;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import ejemplo.cajero.control.Comando;
import ejemplo.cajero.control.ComandoConsignar;
import ejemplo.cajero.control.ComandoListarCuentas;
import ejemplo.cajero.control.ComandoRetirar;
import ejemplo.cajero.control.ComandoTransferir;
import ejemplo.cajero.modelo.Banco;
import ejemplo.cajero.modelo.Cliente;
import ejemplo.cajero.modelo.Credenciales;
import ejemplo.cajero.modelo.Cuenta;

/**
 * Simulador de un Cajero de Banco
 */
public class Cajero {

	/**
	 * Programa principal
	 * 
	 * @param args
	 *            parámetros de línea de comandos. Son ignorados por el programa.
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		// crea el banco
		Banco banco = new Banco();

		// crea unas cuentas, para la prueba
		banco.agregarCuenta(new Cuenta("1", "clave", 1000));
		banco.agregarCuenta(new Cuenta("2", "clave", 2000));
		banco.agregarCuenta(new Cuenta("3", "clave", 3000));

		// crea unos clientes, para la prueba
		banco.agregarCliente(new Cliente("111", new Credenciales("111")));
		banco.agregarCliente(new Cliente("222", new Credenciales("222")));
		banco.agregarCliente(new Cliente("333", new Credenciales("333")));

		// Carga la configuraicion del cajero
		Properties prop = cargarConfiguracionCajero();

		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = cargaComandos(prop);

		// Ciclo del Programa
		// ==================

		login(banco);

		boolean fin = false;
		do {
			// muestra los nombres de los comandos
			muestraMenuConComandos(comandos);
			System.out.println("X.- Salir");

			// la clase Console no funciona bien en Eclipse
			Scanner console = new Scanner(System.in);
			String valorIngresado = console.nextLine();

			// obtiene el comando a ejecutar
			Comando comandoSeleccionado = retornaComandoSeleccionado(comandos, valorIngresado);
			if (comandoSeleccionado != null) {

				// intenta ejecutar el comando
				try {
					comandoSeleccionado.ejecutar(banco);

				} catch (Exception e) {
					// si hay una excepción, muestra el mensaje
					System.err.println(e.getMessage());
				}

			}
			// si no se obtuvo un comando, puede ser la opción de salir
			else if (valorIngresado.equalsIgnoreCase("X")) {
				fin = true;
			}

			System.out.println();
		} while (!fin);

		System.out.println("Gracias por usar el programa.");
	}

	// Manejo de los comandos de la aplicación
	// =======================================

	// carga los comandos usados en el programa
	private static List<Comando> cargaComandos(Properties prop) {

		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = new ArrayList<>();

		comandos.add(new ComandoListarCuentas());
		comandos.add(new ComandoRetirar());
		if (Boolean.valueOf(prop.getProperty("transferencias"))) {
			comandos.add(new ComandoConsignar());
		}
		if (Boolean.valueOf(prop.getProperty("consignaciones"))) {
			comandos.add(new ComandoTransferir());
		}

		return comandos;
	}

	// Muestra el listado de comandos, para mostrar un menú al usuario
	// muestra el índice en el arreglo de comandos y el nombre del comando
	private static void muestraMenuConComandos(List<Comando> comandos) {

		// muestra los nombres de los comandos
		for (int i = 0; i < comandos.size(); i++) {
			System.out.println(i + ".-" + comandos.get(i).getNombre());
		}
	}

	// dado el texto ingresado por el usuario, retorna el comando correspondiente
	// retorna null si el texto no es un número o no existe ningún comando con ese
	// índice
	private static Comando retornaComandoSeleccionado(List<Comando> comandos, String valorIngresado) {

		Comando comandoSeleccionado = null;

		// el valorIngresado es un número ?
		if (valorIngresado.matches("[0-9]")) {
			int valorSeleccionado = Integer.valueOf(valorIngresado);
			// es un índice válido para la lista de comandos
			if (valorSeleccionado < comandos.size()) {
				comandoSeleccionado = comandos.get(valorSeleccionado);
			}
		}

		return comandoSeleccionado;
	}

	public static void clearScreen() {

	}

	public static Properties cargarConfiguracionCajero() {
		Properties prop = new Properties();
		try {
			String propFileName = "config.properties";
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream(propFileName);
			prop.load(stream);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return prop;

	}

	public static void login(Banco banco) {
		boolean login = false;
		do {
			System.out.println("Cajero Automático");
			System.out.println("=================\n");
			System.out.println("Ingrese cedula");
			Scanner console = new Scanner(System.in);
			String cedula = console.nextLine();
			System.out.println("Ingrese Contraseña");
			String credenciales = console.nextLine();
			System.out.println(credenciales);
			if (banco.getClientes().get(cedula) != null
					&& banco.getClientes().get(cedula).getCredenciales().getClave().equals(credenciales)) {
				login = true;
				banco.setUser(cedula);
			} else {
				clearScreen();
				System.out.println("Login incorrecto intenta de nuevo\n");
			}

		} while (!login);
	}

}
