package analisador.lexico;

public class Token {

	public String lexema = null;
	public String simbolo = null;
	public long simboloEnumerado = 0;
	public int numeroLinha;
	public int numeroColuna;

	public Token(String lexema, String simbolo, long simboloEnumerado, int numeroLinha, int numeroColuna) {
		super();
		this.lexema = lexema;
		this.simbolo = simbolo;
		this.simboloEnumerado = simboloEnumerado;
		this.numeroLinha = numeroLinha;
		this.numeroColuna = numeroColuna;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public int getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public long getSimboloEnumerado() {
		return simboloEnumerado;
	}

	public void setSimboloEnumerado(long simboloEnumerado) {
		this.simboloEnumerado = simboloEnumerado;
	}

	public int getNumeroColuna() {
		return numeroColuna;
	}

	public void setNumeroColuna(int numeroColuna) {
		this.numeroColuna = numeroColuna;
	}

}
