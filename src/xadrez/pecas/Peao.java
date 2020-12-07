package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private Partida partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		if(getCor() == Cor.Brancas) {
			return "\u2659";
		} else {
			return "\u265F";
		}
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Brancas
		if (getCor() == Cor.Brancas) {

			// Andar uma casa para cima
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para cima
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if ((getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posicaoExiste(p2) && !getTabuleiro().existePeca(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peca diagonal cima + esquerda
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peca diagonal cima + direita
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// enPassant Brancas
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && haPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && haPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}

		} /* Cor Preta */ else {

			// Andar uma casa para baixo
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para baixo
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 2, posicao.getColuna());
			if ((getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posicaoExiste(p2) && !getTabuleiro().existePeca(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peca diagonal baixo + esquerda
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peca diagonal baixo + direita
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// enPassant Pretas
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && haPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && haPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}

		return mat;
	}

}
