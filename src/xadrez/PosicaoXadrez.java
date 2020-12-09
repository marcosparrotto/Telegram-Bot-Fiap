package xadrez;

import tabuleiro.Posicao;

/**
 * Classe com posicao Xadrez letra+numero, diferente da posição na matriz numero + numero
 * @author Marcos Parrotto
 * @version 1.0
 */
public class PosicaoXadrez {
	/** letra da coluna do xadrez */
	private char coluna;
	/** numero da linha do xadrez */
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha >8) {
			throw new XadrezException("Erro na posicao do xadrez");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	
	protected Posicao toPosicao() {
		return new Posicao(8-linha,coluna-'a');
	}
	
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a'+posicao.getColuna()),8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
