import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public aspect Operaciones {

	// defina un pointcut con el nombre "metodosDelMundo"
	// para todas las invocaciones de los métodos (call)
	// - métodos con cualquier tipo de retorno (*)
	// - métodos de cualquier clase en el paquete "mundo"
	// - métodos con cualquier tipo de argumentos
	pointcut ejecutar() : call ( * ejemplo.cajero.control.Comando.ejecutar(..));

	// ejecución antes de ejecutar el método
	after(): ejecutar() {
		BufferedWriter out = null;
		try {
			FileWriter fstream = new FileWriter("operaciones.txt", true); // true tells to append data.
			out = new BufferedWriter(fstream);
			switch (thisJoinPoint.getTarget().getClass().getSimpleName()) {
			case "ComandoListarCuentas":
				out.write("Usuario ha consultado su cuenta\n");
				break;
			case "ComandoRetirar":
				out.write("Usuario ha retirado dinero de su cuenta\n");
				break;
			case "ComandoConsignar":
				out.write("Usuario ha consignado dinero\n");
				break;
			case "ComandoTransferir":
				out.write("Usuario ha transferido\n");
				break;
			default:
				break;
			}
			out.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
