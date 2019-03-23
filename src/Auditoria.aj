import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ejemplo.cajero.modelo.Banco;

public aspect Auditoria {

	// defina un pointcut con el nombre "metodosDelMundo"
	// para todas las invocaciones de los métodos (call)
	// - métodos con cualquier tipo de retorno (*)
	// - métodos de cualquier clase en el paquete "mundo"
	// - métodos con cualquier tipo de argumentos
	pointcut login() : call ( * ejemplo.cajero.Cajero.login(..));

	// ejecución antes de ejecutar el método
	after(): login() {
		BufferedWriter out = null;
		try {
			FileWriter fstream = new FileWriter("login.txt", true); // true tells to append data.
			out = new BufferedWriter(fstream);
			Banco banco = (Banco) thisJoinPoint.getArgs()[0];
			out.write("Usuario: " + banco.getUser() + " ha ingresado\n");
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
