package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.Pe�aXadrez;

public class UI {

	public static String printPartida(Partida partida, List<Pe�aXadrez> capturadas) {
		String armazenaTexto = "";
		armazenaTexto = armazenaTexto + printTabuleiro(partida.getPe�as()) + "\n";
		armazenaTexto = armazenaTexto + printPe�asCapturadas(capturadas) + "\n";
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

	public static String printTabuleiro(Pe�aXadrez[][] pe�as) {
		String armazenaTexto = "";
		for (int i = 0; i < pe�as.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < pe�as.length; j++) {
				armazenaTexto = armazenaTexto + printPe�a(pe�as[i][j], false);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}

	public static String printTabuleiro(Pe�aXadrez[][] pe�as, boolean[][] possiveisMovimentos) {
		String armazenaTexto = "";
		for (int i = 0; i < pe�as.length; i++) {
			armazenaTexto = armazenaTexto + ((8 - i) + " ");
			for (int j = 0; j < pe�as.length; j++) {
				armazenaTexto = armazenaTexto + printPe�a(pe�as[i][j], possiveisMovimentos[i][j]);
			}
			armazenaTexto = armazenaTexto + "\n";
		}
		armazenaTexto = armazenaTexto + "  a b c d e f g h\n";
		return armazenaTexto;
	}

	private static String printPe�a(Pe�aXadrez pe�a, boolean corLetra) {
		String armazenaTexto = "";
		if (!corLetra) {
			if (pe�a == null) {
				armazenaTexto = armazenaTexto + "\u26CB";
			} else {
				armazenaTexto = armazenaTexto + pe�a;
			}
		} else {
			if (pe�a == null) {
				armazenaTexto = armazenaTexto + "\u2756";
			} else {
				armazenaTexto = armazenaTexto  + pe�a;
			}
		}
		armazenaTexto = armazenaTexto + " ";
		return armazenaTexto;
	}

	private static String printPe�asCapturadas(List<Pe�aXadrez> capturadas) {
		String armazenaTexto = "";
		List<Pe�aXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.Brancas)
				.collect(Collectors.toList());
		List<Pe�aXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.Pretas)
				.collect(Collectors.toList());
		armazenaTexto = armazenaTexto + "\nPe�as capturadas:\n";
		armazenaTexto = armazenaTexto + "Brancas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(brancas.toArray());
		armazenaTexto = armazenaTexto +"\nPretas: ";
		armazenaTexto = armazenaTexto + Arrays.toString(pretas.toArray());
		return armazenaTexto;
	}
}

