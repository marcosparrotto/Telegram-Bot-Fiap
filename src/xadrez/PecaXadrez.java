package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

/**
 * Classe com atributos de uma pe�a de xadrez
 * @author Marcos Parrotto
 * @version 1.0
 */
public abstract class PecaXadrez extends Peca {
	/**Cor da pe�a*/
	private Cor cor;
	/**quantos movimentos a pe�a fez, para saber se ela ainda esta na posi��o inicial (roque e 2 casas para o pe�o) */
	private int contagemMovimentos;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	 /**Recupera a propriedade cor
	 * @return cor */
	public Cor getCor() {
		return cor;
	}
	
	 /**Recupera a propriedade contagemMovimentos
	 * @return contagemMovimentos */
	public int getcontagemMovimentos() {
		return contagemMovimentos;
	}
	
	 /**Configura a propriedade contagemMovimentos
		 * @param contagemMovimentos*/
	public void incrementarcontagemMovimentos() {
		contagemMovimentos++;
	}
	
	 /**Configura a propriedade contagemMovimentos
	 * @param contagemMovimentos*/
	public void decrementarcontagemMovimentos() {
		contagemMovimentos--;
	}

	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
	 /** Verifica se ha pe�a oponente em uma determinada posi��o 
	  * @param posicao
	 * @return boolean */
	protected boolean haPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}


	
	
}
