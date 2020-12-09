package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

/**
 * Classe com atributos de uma peça de xadrez
 * @author Marcos Parrotto
 * @version 1.0
 */
public abstract class PecaXadrez extends Peca {
	/**Cor da peça*/
	private Cor cor;
	/**quantos movimentos a peça fez, para saber se ela ainda esta na posição inicial (roque e 2 casas para o peão) */
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
	
	 /** Verifica se ha peça oponente em uma determinada posição 
	  * @param posicao
	 * @return boolean */
	protected boolean haPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}


	
	
}
