package analisador.semantico;

public class Procedimento extends Simbolo{
	
	public int nivel;
	public int rotulo;
	
	public Procedimento(String nome, String simbolo, long simboloEnumerado,
			int numeroLinha, int numeroColuna, int nivel, int rotulo) {
		super(nome, simbolo, simboloEnumerado, numeroLinha, numeroColuna);
		this.nivel = nivel;
		this.rotulo = rotulo;
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
