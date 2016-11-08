package analisador.semantico;

public class Simbolo {
	
	public String nome;
	public String simbolo = null;
	public long simboloEnumerado = 0;
	public int numeroLinha;
	public int numeroColuna;

	

	public Simbolo(String nome, String simbolo, long simboloEnumerado,
			int numeroLinha, int numeroColuna) {
		super();
		this.nome = nome;
		this.simbolo = simbolo;
		this.simboloEnumerado = simboloEnumerado;
		this.numeroLinha = numeroLinha;
		this.numeroColuna = numeroColuna;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public long getSimboloEnumerado() {
		return simboloEnumerado;
	}

	public void setSimboloEnumerado(long simboloEnumerado) {
		this.simboloEnumerado = simboloEnumerado;
	}

	public int getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public int getNumeroColuna() {
		return numeroColuna;
	}

	public void setNumeroColuna(int numeroColuna) {
		this.numeroColuna = numeroColuna;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getNivel() {
		return new Integer(0);
	}

	public void setNivel(int nivel) {
	}
	public String getTipo() {
		return new String();
	}
	public void setTipo(String tipo) {
	}

}
