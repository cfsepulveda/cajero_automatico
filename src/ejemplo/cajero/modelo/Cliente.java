package ejemplo.cajero.modelo;

public class Cliente {

	private String cedula;

	private Credenciales credenciales;

	public Cliente(String cedula, Credenciales credenciales) {
		super();
		this.cedula = cedula;
		this.credenciales = credenciales;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

}
