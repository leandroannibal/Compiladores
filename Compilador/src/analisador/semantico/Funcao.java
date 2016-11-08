package analisador.semantico;

public class Funcao extends Simbolo {
	
	public String tipo;
	public int nivel;
	public int rotulo;
	
	
	public Funcao(String nome, String simbolo, long simboloEnumerado,
			int numeroLinha, int numeroColuna, String tipo, int nivel, int rotulo) {
		super(nome, simbolo, simboloEnumerado, numeroLinha, numeroColuna);
		this.tipo = tipo;
		this.nivel = nivel;
		this.rotulo = rotulo;
	}
	@Override
	public String getTipo() {
		return tipo;
	}
	@Override
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
