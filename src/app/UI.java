package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PeçaXadrez;

public class UI {

	public static String printPartida(Partida partida, List<PeçaXadrez> capturadas) {
		String armazenaTexto = "";
		armazenaTexto = armazenaTexto + printTabuleiro(partida.getPeças()) + "\n";
		armazenaTexto = armazenaTexto + printPeçasCapturadas(capturadas) + "\n";
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

	public static String printTabuleiro(PeçaXadrez[][] peças) {
		String armazenaTexto = "";
		for (int i = 0; i < peças.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				armazenaTexto = armazenaTexto + printPeça(peças[i][j], false);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}

	public static String printTabuleiro(PeçaXadrez[][] peças, boolean[][] possiveisMovimentos) {
		String armazenaTexto = "";
		for (int i = 0; i < peças.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				armazenaTexto = armazenaTexto + printPeça(peças[i][j], possiveisMovimentos[i][j]);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}

	private static String printPeça(PeçaXadrez peça, boolean corLetra) {
		String armazenaTexto = "";
		if (!corLetra) {
			if (peça == null) {
				armazenaTexto = armazenaTexto + "\u26CB";
			} else {
				armazenaTexto = armazenaTexto + peça;
			}
		} else {
			if (peça == null) {
				armazenaTexto = armazenaTexto + "\u2666";
			} else {
				armazenaTexto = armazenaTexto + "**" + peça +"**";
			}
		}
		armazenaTexto = armazenaTexto + " ";
		return armazenaTexto;
	}

	private static String printPeçasCapturadas(List<PeçaXadrez> capturadas) {
		String armazenaTexto = "";
		List<PeçaXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.Brancas)
				.collect(Collectors.toList());
		List<PeçaXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.Pretas)
				.collect(Collectors.toList());
		armazenaTexto = armazenaTexto + "\nPeças capturadas:\n";
		armazenaTexto = armazenaTexto + "Brancas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(brancas.toArray());
		armazenaTexto = armazenaTexto +"\nPretas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(pretas.toArray());
		return armazenaTexto;
	}
}
