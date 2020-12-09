package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

/**
 * Classe da pe�a Dama
 * @author Marcos Parrotto
 * @version 1.0
 */
public class Dama extends PecaXadrez{

	public Dama(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		if(getCor() == Cor.Brancas) {
			return "\u2655";
		} else {
			return "\u265B";
		}
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//Acima
		p.setValores(posicao.getLinha() -1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Direita
		p.setValores(posicao.getLinha(), posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		//Baixo
		p.setValores(posicao.getLinha() +1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// Cima + Direita
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Cima + Esquerda
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// Baixo + Direita
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Baixo + Esquerda
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().existePeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		
			
		
		return mat;
	}
}
