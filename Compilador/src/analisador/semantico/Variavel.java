package analisador.semantico;

public class Variavel extends Simbolo {
	
	public String tipo;
	public int nivel;
	
	public Variavel(String nome, String simbolo, long simboloEnumerado,
			int numeroLinha, int numeroColuna, String tipo, int nivel) {
		super(nome, simbolo, simboloEnumerado, numeroLinha, numeroColuna);
		this.tipo = tipo;
		this.nivel = nivel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int getNivel() {
		return nivel;
	}
	@Override
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	
}
