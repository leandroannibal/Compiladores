package analisador.semantico;

public class GeracaoCodigo {
	
	String codigoObjetoGerado;
	
	public GeracaoCodigo() {
		this.codigoObjetoGerado = "";
	}
	
	public String getCodigoObjetoGerado() {
		return codigoObjetoGerado;
	}
	public void setCodigoObjeto(String CodigoObjeto){
		codigoObjetoGerado += CodigoObjeto;
	}
	public void geraCodigo( String comando){
		setCodigoObjeto("   "+comando + "\r\n");
		//System.out.println("   "+comando + "\r\n");
	}	
	public void geraCodigo( String comando, String comentario){
		
	}
	public void geraCodigo( String comando,String label, String comentario){ 
		setCodigoObjeto("   "+comando+" "+label+"\r\n");
	}
	public void geraLalel( String label,String comando, String comentario){ //gerar Label EX: L1 , L2 , L3.
		setCodigoObjeto(label +" "+comando+"\r\n");
	}	
	public void geraCodigo( String comando, int primeiroValor, int segundoValor, String comentario){
		setCodigoObjeto("   "+comando + " " + primeiroValor + "," + segundoValor + "         "+ comentario+"\r\n");
		//System.out.println("   "+comando + " " + primeiroValor + "," + segundoValor + "         "+ comentario+"\r\n");
	}
	public void geraCodigo( String comando, int valor, String comentario ){
		
	}

	@Override
	public String toString() {
		return "GeracaoCodigo [codigoObjetoGerado=" + codigoObjetoGerado + "]";
	}
}
