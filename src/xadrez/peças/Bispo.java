package xadrez.peças;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Bispo extends PeçaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		if(getCor() == Cor.Brancas) {
			return "\u2657";
		} else {
			return "\u265D";
		}
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// Cima + Direita
		p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
		while (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Cima + Esquerda
		p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
		while (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Baixo + Direita
		p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
		while (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Baixo + Esquerda
		p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
		while (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
