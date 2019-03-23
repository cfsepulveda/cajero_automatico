package ejemplo.cajero.modelo;

public class Credenciales {

	public Credenciales(String clave) {
		super();
		this.clave = clave;
	}

	private String clave;

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
