package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class UI {

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

	private static String printPecasCapturadas(List<PecaXadrez> capturadas) {
		String armazenaTexto = "";
		List<PecaXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.Brancas)
				.collect(Collectors.toList());
		List<PecaXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.Pretas)
				.collect(Collectors.toList());
		armazenaTexto = armazenaTexto + "\nPe�as capturadas:\n";
		armazenaTexto = armazenaTexto + "Brancas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(brancas.toArray());
		armazenaTexto = armazenaTexto +"\nPretas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(pretas.toArray());
		return armazenaTexto;
	}
}

