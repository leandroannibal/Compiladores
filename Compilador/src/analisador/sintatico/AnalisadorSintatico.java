package analisador.sintatico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import analisador.lexico.AnalizadorLexical;
import analisador.lexico.Token;
import analisador.semantico.Funcao;
import analisador.semantico.GeracaoCodigo;
import analisador.semantico.NomePrograma;
import analisador.semantico.Procedimento;
import analisador.semantico.Simbolo;
import analisador.semantico.Variavel;
import analisador.utils.ErrorConstantes;

public class AnalisadorSintatico {

	static ArrayList<Token> tokens = new ArrayList<Token>();
	static FileInputStream entrada;
	static InputStreamReader entradaFormatada;
	static AnalizadorLexical lexico;
	static Token tok;
	static ArrayList<Simbolo> TabelaSimbolos = new ArrayList<>();  
	static ArrayList<Variavel> variaveis = new	ArrayList<Variavel>();
	static int numeroElementosTabelaSimbolos = 0;
	static int auxNumeroElementosTabelaSimbolos = 0;
	static int nivelTabelaSimbolos = 0;
	static int controleDeVariaveis = 0;
	static int VariaveisAlocadas = 0;
	static int rotulo = 1;
	static GeracaoCodigo codigoObjeto = new GeracaoCodigo();

	public static void main(String[] args) throws IOException {

		entrada = new FileInputStream(args[0]);
		entradaFormatada = new InputStreamReader(entrada);
		lexico = new AnalizadorLexical(entradaFormatada, ' ', 0);
		tok = lexico.get();
		while(tok == null){
			tok = lexico.get();
		} 
		if (tok.simboloEnumerado == 1) // Sprograma
		{
			codigoObjeto.geraCodigo("START");
			tok = lexico.get();
			while(tok == null){
				tok = lexico.get();
			}
			if (tok.simboloEnumerado == 37) // Sidentificador
			{
				TabelaSimbolos.add(new NomePrograma(tok.lexema, tok.simbolo, tok.simboloEnumerado, tok.numeroLinha, tok.numeroColuna));
				numeroElementosTabelaSimbolos ++;
				tok = lexico.get(); while(tok == null){tok = lexico.get();}
				if (tok.simboloEnumerado == 19) // SpontoVirgula
				{
					try{
						AnalisaBloco();
						if (tok.simboloEnumerado == 18) // Sponto
						{
							System.out.println("Sucesso!!");
							codigoObjeto.geraCodigo("DALLOC", 0, 1, "");
							codigoObjeto.geraCodigo("HLT");
							System.out.println(codigoObjeto.getCodigoObjetoGerado());
							for (Simbolo s : TabelaSimbolos){
								System.out.println(s.getClass().toString());
								//if(s.getClass().toString() == "analisador.semantico.Variavel")
								System.out.println(s.getNivel());
								System.out.println(s.getTipo());
							}
						} else {// ------------------------------arrumar erro
								// --------------------------------
							System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA );
							System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna() );
							System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
							System.exit(0);
						}
					}catch(Exception e)
					{
						System.out.println("Final de arquivo nao encontrado!!!!");
						e.printStackTrace();
					}
				} else {
					System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA );
					System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR );
				System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
				System.exit(0);
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SPROGRAMA );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static void AnalisaBloco() throws IOException {
		tok = lexico.get(); while(tok == null){tok = lexico.get();}

		AnalisaEtVariáveis();
		AnalisaSubRotinas();
		AnalisaComandos();

	}

	private static void AnalisaEtVariáveis() throws IOException {
		if (tok.simboloEnumerado == 14) // Svar
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			if (tok.simboloEnumerado == 37) // Sidentificador
			{
				while (tok.simboloEnumerado == 37) {
					
					AnalisaVariáveis();
					codigoObjeto.geraCodigo("ALLOC", VariaveisAlocadas, controleDeVariaveis, "");
					VariaveisAlocadas += controleDeVariaveis;
					controleDeVariaveis = 0;
					if (tok.simboloEnumerado == 19) // SpontoVirgula
					{
						tok = lexico.get(); while(tok == null){tok = lexico.get();}
					} else {
						System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA );
						System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
						System.exit(0);
					}
				}
			} else {
				System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR );
				System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
				System.exit(0);
			}
		}
	}

	private static void AnalisaVariáveis() throws IOException {
		boolean controle = false;
		do {
			if (tok.simboloEnumerado == 37) // Sidentificador
			{
				for(Simbolo s :variaveis)
				{
					if (s.getNome().equals(tok.lexema))
						controle=true;
				}
				if (controle == false) {
					variaveis.add(new Variavel(tok.lexema, tok.simbolo,
							tok.simboloEnumerado, tok.numeroLinha,
							tok.numeroColuna, "no teM ainda",
							nivelTabelaSimbolos));
					auxNumeroElementosTabelaSimbolos++;
					controleDeVariaveis++;
					tok = lexico.get();
					while (tok == null) {
						tok = lexico.get();
					}
					if (tok.simboloEnumerado == 20
							|| tok.simboloEnumerado == 36) // Svirgula
					// ou
					// SdoisPontos
					{
						if (tok.simboloEnumerado == 20) // Svirgula
						{
							tok = lexico.get();
							while (tok == null) {
								tok = lexico.get();
							}
							if (tok.simboloEnumerado == 36) // SdoisPontos
							{
								System.out
										.print(ErrorConstantes.ERRO_SIDENTIFICADOR);
								System.out.println(tok.getNumeroLinha()
										+ "      Coluna: "
										+ tok.getNumeroColuna());
								System.out
										.println(ErrorConstantes.ERRO_SSIMBOLO
												+ tok.lexema + "\"");
								System.exit(0);
							}
						}
					} else {
						System.out.print(ErrorConstantes.ERRO_INDEFINIDO);
						System.out.println(tok.getNumeroLinha()
								+ "      Coluna: " + tok.getNumeroColuna());
						System.out.println(ErrorConstantes.ERRO_SSIMBOLO
								+ tok.lexema + "\"");
						System.exit(0);
					}
				}else{
					System.out.print(ErrorConstantes.DUPLICIDADEVARIAVEL );
					System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR );
				System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
				System.exit(0);
			}
		} while (tok.simboloEnumerado != 36); // sdoisPontos
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		AnalisaTipo();
	}

	private static void AnalisaTipo() throws IOException {
		if (tok.simboloEnumerado != 15 && tok.simboloEnumerado != 16) // Sinteiro
		// ou
		// Sbooleano
		{
			System.out.print(ErrorConstantes.ERRO_STIPO );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
		for(int a= 0;a<auxNumeroElementosTabelaSimbolos;a++)
		{
			variaveis.get(a).setTipo(tok.lexema);
		}
		TabelaSimbolos.addAll(variaveis);
		variaveis.clear();
		auxNumeroElementosTabelaSimbolos = 0;
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
	}

	private static void AnalisaSubRotinas() throws IOException {
		Simbolo s;

		int flag = 0;
		int auxrot = 0;
		if (tok.simboloEnumerado == 9 || tok.simboloEnumerado == 10) // Sprocedimento
																		// ou
																		// Sfuncao
		{
			auxrot = rotulo;
			codigoObjeto.geraCodigo("JMP","L"+rotulo,""); // {Salta sub-rotinas} 
			rotulo = rotulo + 1;
			flag = 1;
		}
		while (tok.simboloEnumerado == 9 || tok.simboloEnumerado == 10) // Sprocedimento
																		// ou
																		// Sfuncao
		{
			
			System.out.println(nivelTabelaSimbolos + "   sprocediento" );
			if (tok.simboloEnumerado == 9) // Sprocedimento
			{
				analisaDeclaraçãoProcedimento();
			} else {
				analisaDeclaraçãoFunção();
			}
			if (tok.simboloEnumerado == 19) // SpontoVirgula
			{
				tok = lexico.get(); while(tok == null){tok = lexico.get();}
			} else {
				System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA );
				System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
				System.exit(0);
			}	

		}
		if (flag == 1) {
			
			codigoObjeto.geraLalel("L"+auxrot, "NULL", "");// Gera(auxrot,NULL,´ ´,´ ´) {início do principal} 
			
		}
	}
	
	

	private static void analisaDeclaraçãoFunção() throws IOException {

		boolean controle = false;
		Simbolo s;
		nivelTabelaSimbolos++;
		
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		if (tok.simboloEnumerado == 37) // Sidentifiador
		{
			controle = pesquisaDeclaracaoFuncaoTabela(tok);
			if (controle == true) {
				TabelaSimbolos.add(new Funcao(tok.lexema, tok.simbolo,
						tok.simboloEnumerado, tok.numeroLinha,
						tok.numeroColuna, "", nivelTabelaSimbolos, rotulo));

				tok = lexico.get();
				while (tok == null) {
					tok = lexico.get();
				}
				if (tok.simboloEnumerado == 36) // SdoisPontos
				{
					tok = lexico.get();
					while (tok == null) {
						tok = lexico.get();
					}
					if (tok.simboloEnumerado == 15
							|| tok.simboloEnumerado == 16) // Sinteiro
															// ou
															// Sbooleano
					{
						
						if (tok.simboloEnumerado == 15){
							TabelaSimbolos.get(TabelaSimbolos.size()-1).setTipo("Inteiro");
						}else{
							TabelaSimbolos.get(TabelaSimbolos.size()-1).setTipo("Boolean");
						}
						tok = lexico.get();
						while (tok == null) {
							tok = lexico.get();
						}
						if (tok.simboloEnumerado == 19) // SpontoVirgula
						{
							AnalisaBloco();
						}
					} else {
						System.out.print(ErrorConstantes.ERRO_TIPO);
						System.out.println(tok.getNumeroLinha()
								+ "      Coluna: " + tok.getNumeroColuna());
						System.out.println(ErrorConstantes.ERRO_SSIMBOLO
								+ tok.lexema + "\"");
						System.exit(0);
					}
				} else {
					System.out.print(ErrorConstantes.ERRO_SDOISPONTOS);
					System.out.println(tok.getNumeroLinha() + "      Coluna: "
							+ tok.getNumeroColuna());
					System.out.println(ErrorConstantes.ERRO_SSIMBOLO
							+ tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.VARIAVELFUNCAONAODECLARADA);
				System.out.println(tok.getNumeroLinha() + "      Coluna: "
						+ tok.getNumeroColuna());
				System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema
						+ "\"");
				System.exit(0);
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
		for (int i = TabelaSimbolos.size()-1; i >= 0; i--) {
			s = TabelaSimbolos.get(i);
			if (s.getNivel() == nivelTabelaSimbolos) {
				System.out.println(s.getClass().toString());
				if (!s.getClass().toString()
						.equals("class analisador.semantico.Procedimento")) {
					TabelaSimbolos.remove(i);
					controleDeVariaveis++;
					System.out.print("Remove aqui");
				}
			}
		}
		codigoObjeto.geraCodigo("DALLOC", VariaveisAlocadas - 1, controleDeVariaveis, "");
		VariaveisAlocadas = VariaveisAlocadas - controleDeVariaveis;
		controleDeVariaveis = 0;

		nivelTabelaSimbolos--;
	}

	public static boolean pesquisaDeclaraaoProcedimentoTabela(Token tok){
		boolean controle = false;
		for (Simbolo s : TabelaSimbolos) {
			if (s.getNome().equals(tok.getLexema()) && s instanceof Procedimento)
				controle = true;
		}			
		return controle;		
	}
	
	private static void analisaDeclaraçãoProcedimento() throws IOException {
		boolean controle = false;
		Simbolo s;
		nivelTabelaSimbolos++;
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		if (tok.simboloEnumerado == 37) // Sidentifiador
		{
			controle = pesquisaDeclaraaoProcedimentoTabela(tok);
			if (controle == false) {
				TabelaSimbolos.add(new Procedimento(tok.lexema, tok.simbolo,
						tok.simboloEnumerado, tok.numeroLinha,
						tok.numeroColuna, nivelTabelaSimbolos, rotulo));
				
				codigoObjeto.geraLalel("L"+rotulo, "NULL", "");//Gera(rotulo,NULL,´ ´,´ ´)	 {CALL irá buscar este rótulo na TabSimb} 
				rotulo = rotulo + 1;		
				tok = lexico.get();
				while (tok == null) {
					tok = lexico.get();
				}
				if (tok.simboloEnumerado == 19) // SpontoVirgula
				{
					AnalisaBloco();
				} else {
					System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA);
					System.out.println(tok.getNumeroLinha() + "      Coluna: "
							+ tok.getNumeroColuna());
					System.out.println(ErrorConstantes.ERRO_SSIMBOLO
							+ tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.VARIAVELFUNCAONAODECLARADA);
				System.out.println(tok.getNumeroLinha() + "      Coluna: "
						+ tok.getNumeroColuna());
				System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema
						+ "\"");
				System.exit(0);
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}

		for (int i = TabelaSimbolos.size()-1; i >= 0; i--) {
			s = TabelaSimbolos.get(i);
			if (s.getNivel() == nivelTabelaSimbolos) {
				System.out.println(s.getClass().toString());
				if (!s.getClass().toString()
						.equals("class analisador.semantico.Procedimento")) {
					TabelaSimbolos.remove(i);
					controleDeVariaveis++;
					System.out.print("Remove aqui");
				}
			}
		}
		codigoObjeto.geraCodigo("DALLOC", VariaveisAlocadas -1, controleDeVariaveis, "");
		VariaveisAlocadas = VariaveisAlocadas - controleDeVariaveis;
		controleDeVariaveis = 0;

		nivelTabelaSimbolos--;
		
	}

	private static void AnalisaComandos() throws IOException {
		if (tok.simboloEnumerado == 7) // Sinicio
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaComandoSimples();
			while (tok.simboloEnumerado != 8) // Sfim
			{
				if (tok.simboloEnumerado == 19) // SpontoVirgula
				{
					tok = lexico.get(); while(tok == null){tok = lexico.get();}
					if (tok.simboloEnumerado != 8) // Sfim
					{
						AnalisaComandoSimples();
					}
				} else {
					System.out.print(ErrorConstantes.ERRO_SPONTOVIRGULA );
					System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
					System.exit(0);
				}
			}
			for(int z = TabelaSimbolos.size() ; z >= 0 ; z--)
			{
				//System.out.println(TabelaSimbolos.get(z-1).get == "analisador.semantico.Procedimento");
			}
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
		} else {
			System.out.print(ErrorConstantes.ERRO_SINICIO );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static void AnalisaComandoSimples() throws IOException {
		if (tok.simboloEnumerado == 37) // Sidentificador
		{
			AnalisaAtribChProcedimento();
		} else if (tok.simboloEnumerado == 2) // Sse
		{
			AnalisaSe();
		} else if (tok.simboloEnumerado == 5) // Senquanto
		{
			AnalisaEnquanto();
		} else if (tok.simboloEnumerado == 13) // Sleia
		{
			AnalisaLeia();
		} else if (tok.simboloEnumerado == 12) // Sescreva
		{
			AnalisaEscreva();
		} else {
			AnalisaComandos();
		}

	}

	private static void AnalisaEscreva()  throws IOException {
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		if (tok.getSimboloEnumerado() == 21) // sabreParenteses
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			if (tok.getSimboloEnumerado() == 37) // sidentificador
			{
				
				boolean controle = pesquisaDeclaracaoFuncaoTabela(tok);

				if (controle == true) {

					tok = lexico.get();
					while (tok == null) {
						tok = lexico.get();
					}
					if (tok.getSimboloEnumerado() == 22) // sfechaParenteses
					{
						tok = lexico.get();
						while (tok == null) {
							tok = lexico.get();
						}
					} else {
						System.out.print(ErrorConstantes.ERRO_SFECHAPARENTESES);
						System.out.println(tok.getNumeroLinha()
								+ "      Coluna: " + tok.getNumeroColuna());
						System.out.println(ErrorConstantes.ERRO_SSIMBOLO
								+ tok.lexema + "\"");
						System.exit(0);
					}
				} else {
					System.out
							.print(ErrorConstantes.VARIAVELFUNCAONAODECLARADA);
					System.out.println(tok.getNumeroLinha() + "      Coluna: "
							+ tok.getNumeroColuna());
					System.out.println(ErrorConstantes.ERRO_SSIMBOLO
							+ tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR);
				System.out.println(tok.getNumeroLinha() + "      Coluna: "
						+ tok.getNumeroColuna());
				System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema
						+ "\"");
				System.exit(0);
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SABREPARENTESES );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}
	
	
	
	
	
	
	
	
	public static boolean pesquisaDeclaracaoVariaveisTabela(Token tok){
		boolean controle = false;
		for (Simbolo s : TabelaSimbolos) {
			System.out.println(s.getClass());
			System.out.println(s.getNome());
			System.out.println(tok.getLexema());
			if (tok.getLexema().equals(s.getNome()) && s instanceof Variavel)
				controle = true;
		}	
		return controle;
	}
	public static boolean pesquisaDeclaracaoFuncaoTabela(Token tok){
		boolean controle = false;
		for (Simbolo s : TabelaSimbolos) {
			if (s.getNome().equals(tok.getLexema()) && s instanceof Variavel)
				controle = true;
		}	
		return controle;
	}
	
	
	
	
	
	
	
	
	
	

	private static void AnalisaLeia() throws IOException {
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		if (tok.getSimboloEnumerado() == 21) // sabreParenteses
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			if (tok.getSimboloEnumerado() == 37) // sidentificador
			{
				boolean controle = pesquisaDeclaracaoVariaveisTabela(tok);
				
				if (controle == true) {
					tok = lexico.get();
					while (tok == null) {
						tok = lexico.get();
					}
					if (tok.getSimboloEnumerado() == 22) // sfechaParenteses
					{
						tok = lexico.get();
						while (tok == null) {
							tok = lexico.get();
						}
					} else {
						System.out.print(ErrorConstantes.ERRO_SFECHAPARENTESES);
						System.out.println(tok.getNumeroLinha()
								+ "      Coluna: " + tok.getNumeroColuna());
						System.out.println(ErrorConstantes.ERRO_SSIMBOLO
								+ tok.lexema + "\"");
						System.exit(0);
					}
				} else {
					System.out.print(ErrorConstantes.VARIAVELNAODECLARADA );
					System.out.println(tok.getNumeroLinha() + "      Coluna: "
							+ tok.getNumeroColuna());
					System.out.println(ErrorConstantes.ERRO_SSIMBOLO
							+ tok.lexema + "\"");
					System.exit(0);
				}
			} else {
				System.out.print(ErrorConstantes.ERRO_SIDENTIFICADOR);
				System.out.println(tok.getNumeroLinha() + "      Coluna: "
						+ tok.getNumeroColuna());
				System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema
						+ "\"");
				System.exit(0);
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SABREPARENTESES);
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static void AnalisaEnquanto() throws IOException {
		int auxrot1,auxrot2;
		
		auxrot1 = rotulo;
		
		codigoObjeto.geraLalel("L"+rotulo,"NULL", "");//Gera(rotulo,NULL,´ ´,´ ´) {início do while} 
		
		rotulo = rotulo + 1;
		
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		AnalisaExpressao();
		if (tok.getSimboloEnumerado() == 6) // sfaca
		{
			auxrot2 = rotulo;
			codigoObjeto.geraCodigo("JMPF","L"+rotulo);//Gera(´ ´,JMPF,rotulo,´ ´) {salta se falso} 
			rotulo = rotulo + 1;
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaComandoSimples();
			codigoObjeto.geraCodigo("JMP","L"+auxrot1);//Gera(´ ´,JMP,auxrot1,´ ´) {retorna início loop} 
			codigoObjeto.geraLalel("L"+auxrot2,"NULL", "");//Gera(auxrot2,NULL,´ ´,´ ´) {fim do while} 

		} else {
			System.out.print(ErrorConstantes.ERRO_SFACA );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}

	}

	private static void AnalisaSe() throws IOException {
		tok = lexico.get(); while(tok == null){tok = lexico.get();}
		AnalisaExpressao();
		if (tok.getSimboloEnumerado() == 3) // sentao
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaComandoSimples();
			if (tok.getSimboloEnumerado() == 4) // ssenao
			{
				tok = lexico.get(); while(tok == null){tok = lexico.get();}
				AnalisaComandoSimples();
			}
		} else {
			System.out.print(ErrorConstantes.ERRO_SENTAO );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}

	}

	private static void AnalisaExpressao() throws IOException {
		AnalisaExpressãoSimples();
		if (tok.simboloEnumerado == 23 || tok.simboloEnumerado == 24 || tok.simboloEnumerado == 25
				|| tok.simboloEnumerado == 26 || tok.simboloEnumerado == 27 || tok.simboloEnumerado == 28) // Smaior
																											// ou
																											// SmaiorIgual
																											// ou
																											// SIgual
																											// ou
																											// Smenor
																											// ou
																											// SmenorIgual
																											// ou
																											// Sdif
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaExpressãoSimples();
		}
	}

	private static void AnalisaExpressãoSimples() throws IOException {
		if (tok.simboloEnumerado == 29 || tok.simboloEnumerado == 30) // Smaios
																		// ou
																		// Smenos
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
		}
		AnalisaTermo();
		while (tok.simboloEnumerado == 29 || tok.simboloEnumerado == 30 || tok.simboloEnumerado == 34) // smais
																										// ou
																										// smenos
																										// ou
																										// sou
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaTermo();
		}

	}

	private static void AnalisaTermo() throws IOException {
		AnalisaFator();
		while (tok.simboloEnumerado == 31 || tok.simboloEnumerado == 32 || tok.simboloEnumerado == 33) // smult
																										// ou
																										// sdiv
																										// ou
																										// se
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaFator();
		}
	}

	private static void AnalisaFator() throws IOException {
		if (tok.getSimboloEnumerado() == 37) // sidentificador
		{
			AnalisaChamadaFunção();
		} else if (tok.getSimboloEnumerado() == 17) // snumero
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
		} else if (tok.getSimboloEnumerado() == 35) // snao
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaFator();
		} else if (tok.getSimboloEnumerado() == 21) // sabreParenteses
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaExpressao();
			if (tok.getSimboloEnumerado() == 22) // sfecahParenteses
			{
				tok = lexico.get(); while(tok == null){tok = lexico.get();}
			} else {
				System.out.print(ErrorConstantes.ERRO_SFECHAPARENTESES );
				System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
				System.exit(0);
			}
		} else if (tok.lexema.equals("verdadeiro") || tok.lexema.equals("falso")) {
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
		} else {
			System.out.print(ErrorConstantes.ERRO_VARIOS );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static void AnalisaChamadaFunção() throws IOException {
		if (tok.getSimboloEnumerado() == 37) // sidentificador
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
		} else {// -------------------------arrumar
			// erro--------------------------------------------
			System.out.print(ErrorConstantes.ERRO_INDEFINIDO );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);

		}
	}

	private static void AnalisaAtribChProcedimento() throws IOException {
		tok = lexico.get(); while(tok == null){tok = lexico.get();} 
		if (tok.getSimboloEnumerado() == 11) // satribuicao
		{
			AnalisaAtribuicao();
		} else {
			ChamadaProcedimento();
		}

	}

	private static void ChamadaProcedimento() throws IOException {
		if (tok.getSimboloEnumerado() != 19) // spontoVirgula
		{
			// -------------------------arrumar
			// erro--------------------------------------------
			System.out.print(ErrorConstantes.ERRO_SATRIBUICAO );
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static void AnalisaAtribuicao() throws IOException {
		if (tok.getSimboloEnumerado() == 11) // satribuicao
		{
			tok = lexico.get(); while(tok == null){tok = lexico.get();}
			AnalisaExpressao();

		} else {// -------------------------arrumar
				// erro--------------------------------------------
			System.out.print(ErrorConstantes.ERRO_INDEFINIDO);
			System.out.println(tok.getNumeroLinha() + "      Coluna: " + tok.getNumeroColuna());  System.out.println(ErrorConstantes.ERRO_SSIMBOLO + tok.lexema + "\"");
			System.exit(0);
		}
	}

	private static Token TokenAToken() {
		Token tok = tokens.get(0);
		tokens.remove(tok);

		return tok;
	}
}
