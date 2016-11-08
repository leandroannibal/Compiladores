package analisador.utils;

import java.util.ArrayList;
import java.util.Arrays;

public interface LexicoConstantes {
	final static ArrayList<Character> letras = new ArrayList<Character>(
			Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
					'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
					'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
	final static ArrayList<Character> numeros = new ArrayList<Character>(
			Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));
	final static ArrayList<Character> operadorAritimetico = new ArrayList<Character>(Arrays.asList('-', '+', '*'));
	final static ArrayList<Character> pontuacao = new ArrayList<Character>(Arrays.asList(';', ',', '(', ')', '.'));
	final static ArrayList<Character> operadorRelacional = new ArrayList<Character>(Arrays.asList('>', '=', '<', '!'));
	final static ArrayList<Character> caracteresNaoReconhecidos = new ArrayList<Character>(
			Arrays.asList('@', '#', '$', '%', '&', '/'));

	final static long sprograma = 1;
	final static long sse = 2;
	final static long sentao = 3;
	final static long ssenao = 4;
	final static long senquanto = 5;
	final static long sfaca = 6;
	final static long sinicio = 7;
	final static long sfim = 8;
	final static long sprocedimento = 9;
	final static long sfuncao = 10;
	final static long satribuicao = 11;
	final static long sescreva = 12;
	final static long sleia = 13;
	final static long svar = 14;
	final static long sinteiro = 15;
	final static long sbooleano = 16;
	final static long snumero = 17;
	final static long sponto = 18;
	final static long spontoVirgula = 19;
	final static long svirgula = 20;
	final static long sabreParenteses = 21;
	final static long sfechaParenteses = 22;
	final static long smaior = 23;
	final static long smaiorig = 24;
	final static long sigual = 25;
	final static long smenor = 26;
	final static long smenorig = 27;
	final static long sdif = 28;
	final static long smais = 29;
	final static long smenos = 30;
	final static long smult = 31;
	final static long sdiv = 32;
	final static long se = 33;
	final static long sou = 34;
	final static long snao = 35;
	final static long sdoisPontos = 36;
	final static long sidentificador = 37;

}
