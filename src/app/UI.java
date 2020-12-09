package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaXadrez;

/**
 * Classe responsável pela interação com o usuário
 * @author Marcos Parrotto
 * @version 1.0
 */
public class UI {
	/**Printa a partida com o tabuleiro, as peças capturadas e informa o status da partida 
	 * @param partida, capturadas
	 * @return texto que será enviado pelo bot */
	public static String printPartida(Partida partida, List<PecaXadrez> capturadas) {
		String armazenaTexto = "";
		armazenaTexto = armazenaTexto + printTabuleiro(partida.getPecas()) + "\n";
		armazenaTexto = armazenaTexto + printPecasCapturadas(capturadas) + "\n";
		armazenaTexto = armazenaTexto + "Turno: " + partida.getTurno() + "\n";
		if (!partida.getCheckMate()) {
			armazenaTexto = armazenaTexto + "\nEsperando jogador: " + partida.getJogadorAtual();
			if (partida.getCheck()) {
				armazenaTexto = armazenaTexto + "\n" + "CHECK!!" + "\n";
			}
		} else {
			armazenaTexto = armazenaTexto + "\n" + "CHECKMATE!!" + "\n";
			armazenaTexto = armazenaTexto + "Vencedor: " + partida.getJogadorAtual();
		}
		return armazenaTexto;
	}
	
	/**Printa o tabuleiro simples
	 * @param pecas
	 * @return tabuleiro em caracteres */
	public static String printTabuleiro(PecaXadrez[][] pecas) {
		String armazenaTexto = "";
		for (int i = 0; i < pecas.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				armazenaTexto = armazenaTexto + printPeca(pecas[i][j], false);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}
	
	/**Printa o tabuleiro com os possiveis movimentos de uma peça
	 * @param pecas, possiveisMovimentos
	 * @return tabuleiro em caracteres */
	public static String printTabuleiro(PecaXadrez[][] pecas, boolean[][] possiveisMovimentos) {
		String armazenaTexto = "";
		for (int i = 0; i < pecas.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				armazenaTexto = armazenaTexto + printPeca(pecas[i][j], possiveisMovimentos[i][j]);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}

	/**Printa cada peça do tabuleiro
	 * @param pecas, corLetra
	 * @return retorna o caracter/emoji para ser colocado no tabuleiro */
	private static String printPeca(PecaXadrez peca, boolean corLetra) {
		String armazenaTexto = "";
		if (!corLetra) {
			if (peca == null) {
				armazenaTexto = armazenaTexto + "\u26CB";
			} else {
				armazenaTexto = armazenaTexto + peca;
			}
		} else {
			if (peca == null) {
				armazenaTexto = armazenaTexto + "\u2756";
			} else {
				armazenaTexto = armazenaTexto  + peca;
			}
		}
		armazenaTexto = armazenaTexto + " ";
		return armazenaTexto;
	}
	
	/**Printa lista das peças capturadas por cada jogador
	 * @param capturadas
	 * @return texto com as peças capturadas por cadda jogador */
	private static String printPecasCapturadas(List<PecaXadrez> capturadas) {
		String armazenaTexto = "";
		List<PecaXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.Brancas)
				.collect(Collectors.toList());
		List<PecaXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.Pretas)
				.collect(Collectors.toList());
		armazenaTexto = armazenaTexto + "\nPeï¿½as capturadas:\n";
		armazenaTexto = armazenaTexto + "Brancas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(brancas.toArray());
		armazenaTexto = armazenaTexto +"\nPretas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(pretas.toArray());
		return armazenaTexto;
	}
}

