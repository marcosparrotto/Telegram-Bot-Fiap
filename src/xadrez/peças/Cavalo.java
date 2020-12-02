package xadrez.peças;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Cavalo extends PeçaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		if(getCor() == Cor.Brancas) {
			return "\u2658";
		} else {
			return "\u265E";
		}
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		p.setValores(posição.getLinha() - 1, posição.getColuna() + 2);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 1, posição.getColuna() + 2);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 1, posição.getColuna() - 2);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 1, posição.getColuna() - 2);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 2, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() - 2, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 2, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posição.getLinha() + 2, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}