package xadrez.peças;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

	private Partida partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		if(getCor() == Cor.Brancas) {
			return "\u2654";
		} else {
			return "\u265A";
		}
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}

	private boolean testarRookpossivel(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p != null && p instanceof Torre && p.getCor() == getCor() && getcontagemMovimentos() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// Acima
		p.setValores(posição.getLinha() - 1, posição.getColuna());
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo
		p.setValores(posição.getLinha() + 1, posição.getColuna());
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Esquerda
		p.setValores(posição.getLinha(), posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Direita
		p.setValores(posição.getLinha(), posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Esquerda
		p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Direita
		p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Esquerda
		p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Direita
		p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Roque
		if (getcontagemMovimentos() == 0 && !partida.getCheck()) {
			// Roque pequeno
			Posição posTorreRei = new Posição(posição.getLinha(), posição.getColuna() + 3);
			if (testarRookpossivel(posTorreRei)) {
				Posição p1 = new Posição(posição.getLinha(), posição.getColuna() + 1);
				Posição p2 = new Posição(posição.getLinha(), posição.getColuna() + 2);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null) {
					mat[posição.getLinha()][posição.getColuna() + 2] = true;
				}
			}

			// Roque grande
			Posição posTorreDama = new Posição(posição.getLinha(), posição.getColuna() - 4);
			if (testarRookpossivel(posTorreDama)) {
				Posição p1 = new Posição(posição.getLinha(), posição.getColuna() - 1);
				Posição p2 = new Posição(posição.getLinha(), posição.getColuna() - 2);
				Posição p3 = new Posição(posição.getLinha(), posição.getColuna() - 3);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null) {
					mat[posição.getLinha()][posição.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}
}