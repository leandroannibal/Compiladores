package analisador.lexico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import analisador.utils.ErrorConstantes;
import analisador.utils.LexicoConstantes;

public class AnalizadorLexical {
	static int numeroDeLinhas = 1;
	static int numeroDeColunas = 0;
	static ArrayList<Token> pilhaToken = new ArrayList<Token>();
	static char caractere;
	static int charr;
	InputStreamReader entradaFormatada;

	public AnalizadorLexical(InputStreamReader entradaFormatada, char caractere, int charr) throws IOException {
		this.caractere = caractere;
		this.charr = charr;
		this.entradaFormatada = entradaFormatada;

	}

	public Token get() throws IOException {

		ArrayList<Token> tokk = new ArrayList<Token>();
		int auxNumeroDeLinhas = 0;

		//this.charr = PegaCaracter(entradaFormatada);
		//this.caractere = (char) this.charr;
		//numeroDeColunas++;
		while (charr != -1) {
			while (caractere == '{' || caractere == ' ' && charr != -1) {
				if (caractere == '{') {
					while (caractere != '}' && charr != -1) {
						charr = PegaCaracter(entradaFormatada);
						caractere = (char) charr;
						numeroDeColunas++;
						if (caractere == '\n') {
							auxNumeroDeLinhas++;
						}

						if (charr == -1) {
							System.out.print(ErrorConstantes.ERRO_CHAVES_NAO_FECHADO + ErrorConstantes.ERRO_LINHA);
							System.out.println(numeroDeLinhas);
							System.exit(0);
						}
					}
					numeroDeLinhas = numeroDeLinhas + auxNumeroDeLinhas;
					auxNumeroDeLinhas = 0;
					charr = PegaCaracter(entradaFormatada);
					caractere = (char)charr;
					numeroDeColunas++;
					if (caractere == '\n') {
						numeroDeLinhas++;
					}
				}
				while (caractere == ' ' && charr != -1) {
					charr = PegaCaracter(entradaFormatada);
					caractere = (char) charr;
					numeroDeColunas++;
					if (caractere == '\n') {
						numeroDeLinhas++;
					}
				}

			}
			if (charr != -1) {
				
				return getTokenPalavrasReservadas(PegaToken(entradaFormatada, tokk));
			
			}
		}
		
		return getTokenPalavrasReservadas(PegaToken(entradaFormatada, tokk));
		/*for (Token token : pilhaToken) {
			System.out.println("Lexema: " + token.lexema);
			System.out.println("Simbolo:  " + token.simbolo);
			System.out.println("Numero Da Linha:  " + token.getNumeroLinha() );
			System.out.println("Numero Da Coluna:  " + token.getNumeroColuna() + "\n");
		}
		
		//pilhaToken = getTokenPalavrasReservadas(pilhaToken);

		for (Token token : pilhaToken) {
			System.out.println("Lexema: " + token.lexema);
			System.out.println("Simbolo:  " + token.simbolo);
			System.out.println("Numero Da Linha:  " + token.getNumeroLinha() );
			System.out.println("Numero Da Coluna:  " + token.getNumeroColuna() + "\n");
		}*/

	}
	
	private static Token PegaToken(InputStreamReader entradaFormatada, ArrayList<Token> tokk)
			throws IOException {
		
		if (LexicoConstantes.numeros.contains(caractere)) {
			return TrataDigito(entradaFormatada);
		} else if (LexicoConstantes.letras.contains(caractere)) {
			return TrataIdentificador(entradaFormatada);
		} else if (caractere == ':') {
			return TrataAtribuicao(entradaFormatada);
		} else if (LexicoConstantes.operadorAritimetico.contains(caractere)) {
			return TrataOperadorAritmético(entradaFormatada);
		} else if (LexicoConstantes.operadorRelacional.contains(caractere)) {
			return TrataOperadorRelacional(entradaFormatada);
		} else if (LexicoConstantes.pontuacao.contains(caractere)) {
			return TrataPontuação(entradaFormatada);
		} else if (LexicoConstantes.caracteresNaoReconhecidos.contains(caractere)) {
			System.out.print(ErrorConstantes.ERRO_CARACTER_DESCONHECIDO + caractere + ErrorConstantes.ERRO_LINHA);
			System.out.println(numeroDeLinhas);
			System.exit(0);
		} else if (caractere == '}') {
			System.out.print(ErrorConstantes.ERRO_CHAVES_FECHADA_NAO_ABERTA + caractere + ErrorConstantes.ERRO_LINHA);
			System.out.println(numeroDeLinhas);
			System.exit(0);
		} else {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}

		return null;
	}

	private static Token TrataPontuação(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		if (caractere == ';' || caractere == ',' || caractere == '(' || caractere == ')' || caractere == '.') {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}
		return new Token(palavra, null, 0, numeroDeLinhas, numeroDeColunas);
		
	}

	private static Token TrataOperadorRelacional(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		if (caractere == '<' || caractere == '>') {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
			if (caractere == '=') {
				palavra = palavra + caractere;
				charr = PegaCaracter(entradaFormatada);
				caractere = (char) charr;
				numeroDeColunas++;
				if (caractere == '\n') {
					numeroDeColunas = 0;
					numeroDeLinhas++;
				}
			}
		} else if (caractere == '=') {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		} else if (caractere == '!') {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
			if (caractere == '=') {
				palavra = palavra + caractere;
				charr = PegaCaracter(entradaFormatada);
				caractere = (char) charr;
				numeroDeColunas++;
				if (caractere == '\n') {
					numeroDeColunas = 0;
					numeroDeLinhas++;
				}
			}
		}

		return new Token(palavra, null, 0, numeroDeLinhas, numeroDeColunas);
	}

	private static Token TrataOperadorAritmético(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		if (caractere == '+' || caractere == '-' || caractere == '*') {
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}
		return new Token(palavra, null, 0, numeroDeLinhas, numeroDeColunas);
	}

	private static Token TrataAtribuicao(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		charr = PegaCaracter(entradaFormatada);
		caractere = (char) charr;
		numeroDeColunas++;
		if (caractere == '\n') {
			numeroDeColunas = 0;
			numeroDeLinhas++;
		}
		if (caractere == '=') {
			palavra = palavra + caractere;
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}

		return new Token(palavra, null, 0, numeroDeLinhas, numeroDeColunas);
	}

	private static Token TrataIdentificador(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		charr = PegaCaracter(entradaFormatada);
		caractere = (char) charr;
		numeroDeColunas++;
		if (caractere == '\n') {
			numeroDeColunas = 0;
			numeroDeLinhas++;
		}

		while (LexicoConstantes.letras.contains(caractere) || LexicoConstantes.numeros.contains(caractere)
				|| caractere == '_') {
			palavra = palavra + caractere;
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}

		return new Token(palavra, null, 0, numeroDeLinhas, numeroDeColunas);
	}

	private static Token TrataDigito(InputStreamReader entradaFormatada) throws IOException {
		ArrayList<Token> tokk = new ArrayList<Token>();
		String palavra = "";
		palavra = palavra + caractere;

		charr = PegaCaracter(entradaFormatada);
		caractere = (char) charr;
		numeroDeColunas++;
		if (caractere == '\n') {
			numeroDeColunas = 0;
			numeroDeLinhas++;
		}
		while (LexicoConstantes.numeros.contains(caractere)) {
			palavra = palavra + caractere;
			charr = PegaCaracter(entradaFormatada);
			caractere = (char) charr;
			numeroDeColunas++;
			if (caractere == '\n') {
				numeroDeColunas = 0;
				numeroDeLinhas++;
			}
		}

		return new Token(palavra, "snumero", LexicoConstantes.snumero, numeroDeLinhas, numeroDeColunas);
	}

	private static Token getTokenPalavrasReservadas(Token tok) {
		if (tok != null && tok.simbolo == null && tok.simboloEnumerado == 0) {

				switch (tok.getLexema()) {
				case "programa":
					tok.setSimbolo("Sprograma");
					tok.setSimboloEnumerado(LexicoConstantes.sprograma);
					break;
				case "se":
					tok.setSimbolo("sse");
					tok.setSimboloEnumerado(LexicoConstantes.sse);
					break;
				case "entao":
					tok.setSimbolo("sentao");
					tok.setSimboloEnumerado(LexicoConstantes.sentao);
					break;
				case "senao":
					tok.setSimbolo("ssenao");
					tok.setSimboloEnumerado(LexicoConstantes.ssenao);
					break;
				case "enquanto":
					tok.setSimbolo("senquanto");
					tok.setSimboloEnumerado(LexicoConstantes.senquanto);
					break;
				case "faca":
					tok.setSimbolo("sfaca");
					tok.setSimboloEnumerado(LexicoConstantes.sfaca);
					break;
				case "inicio":
					tok.setSimbolo("sinicio");
					tok.setSimboloEnumerado(LexicoConstantes.sinicio);
					break;
				case "fim":
					tok.setSimbolo("sfim");
					tok.setSimboloEnumerado(LexicoConstantes.sfim);
					break;
				case "procedimento":
					tok.setSimbolo("sprocedimento");
					tok.setSimboloEnumerado(LexicoConstantes.sprocedimento);
					break;
				case "funcao":
					tok.setSimbolo("sfuncao");
					tok.setSimboloEnumerado(LexicoConstantes.sfuncao);
					break;
				case ":=":
					tok.setSimbolo("satribuicao");
					tok.setSimboloEnumerado(LexicoConstantes.satribuicao);
					break;
				case "escreva":
					tok.setSimbolo("sescreva");
					tok.setSimboloEnumerado(LexicoConstantes.sescreva);
					break;
				case "leia":
					tok.setSimbolo("sleia");
					tok.setSimboloEnumerado(LexicoConstantes.sleia);
					break;
				case "var":
					tok.setSimbolo("svar");
					tok.setSimboloEnumerado(LexicoConstantes.svar);
					break;
				case "inteiro":
					tok.setSimbolo("sinteiro");
					tok.setSimboloEnumerado(LexicoConstantes.sinteiro);
					break;
				case "booleano":
					tok.setSimbolo("sbooleano");
					tok.setSimboloEnumerado(LexicoConstantes.sbooleano);
					break;
				case ".":
					tok.setSimbolo("sponto");
					tok.setSimboloEnumerado(LexicoConstantes.sponto);
					break;
				case ";":
					tok.setSimbolo("spontoVirgula");
					tok.setSimboloEnumerado(LexicoConstantes.spontoVirgula);
					break;
				case ",":
					tok.setSimbolo("svirgula");
					tok.setSimboloEnumerado(LexicoConstantes.svirgula);
					break;
				case "(":
					tok.setSimbolo("sabreParenteses");
					tok.setSimboloEnumerado(LexicoConstantes.sabreParenteses);
					break;
				case ")":
					tok.setSimbolo("sfechaParenteses");
					tok.setSimboloEnumerado(LexicoConstantes.sfechaParenteses);
					break;
				case ">":
					tok.setSimbolo("smaior");
					tok.setSimboloEnumerado(LexicoConstantes.smaior);
					break;
				case ">=":
					tok.setSimbolo("smaiorig");
					tok.setSimboloEnumerado(LexicoConstantes.smaiorig);
					break;
				case "=":
					tok.setSimbolo("sigual");
					tok.setSimboloEnumerado(LexicoConstantes.sigual);
					break;
				case "<":
					tok.setSimbolo("smenor");
					tok.setSimboloEnumerado(LexicoConstantes.smenor);
					break;
				case "<=":
					tok.setSimbolo("smenorig");
					tok.setSimboloEnumerado(LexicoConstantes.smenorig);
					break;
				case "!=":
					tok.setSimbolo("sdif");
					tok.setSimboloEnumerado(LexicoConstantes.sdif);
					break;
				case "+":
					tok.setSimbolo("smais");
					tok.setSimboloEnumerado(LexicoConstantes.smais);
					break;
				case "-":
					tok.setSimbolo("smenos");
					tok.setSimboloEnumerado(LexicoConstantes.smenos);
					break;
				case "*":
					tok.setSimbolo("smult");
					tok.setSimboloEnumerado(LexicoConstantes.smult);
					break;
				case "div":
					tok.setSimbolo("sdiv");
					tok.setSimboloEnumerado(LexicoConstantes.sdiv);
					break;
				case "e":
					tok.setSimbolo("Se");
					tok.setSimboloEnumerado(LexicoConstantes.se);
					break;
				case "ou":
					tok.setSimbolo("sou");
					tok.setSimboloEnumerado(LexicoConstantes.sou);
					break;
				case "nao":
					tok.setSimbolo("snao");
					tok.setSimboloEnumerado(LexicoConstantes.snao);
					break;
				case ":":
					tok.setSimbolo("sdoisPontos");
					tok.setSimboloEnumerado(LexicoConstantes.sdoisPontos);
					break;
				default:
					tok.setSimbolo("sidentificador");
					tok.setSimboloEnumerado(LexicoConstantes.sidentificador);
					break;
				}
			}

		return tok;
	}

	public static int PegaCaracter(InputStreamReader entradaFormatada) throws IOException {
		int x = entradaFormatada.read();
		return x;
	}


}
