package tabuleiro;

/**
 * Classe com atributos de uma pe�a
 * @author Marcos Parrotto
 * @version 1.0
 */
public abstract class Peca {
	/**Propriedade com a posi��o da pe�a*/
	protected Posicao posicao;
	/** tabuleiro que a pe�a ta inserida*/
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	 /**Recupera a propriedade tabuleiro
	 * @return tabuleiro */
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	/**Formaliza que quando a pe�a for instanciada precisar� criar o metodo de movimentos possiveis */
	public abstract boolean[][] possiveisMovimentos();
	
	/** Verifica se a pe�a pode se mover para determinada posi��o
	 * @param posicao
	 * @return boolean */
	public boolean possivelMovimento(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}

	/** verifica se a pe�a consegue se movimentar 
	 * @return boolean*/
	public boolean haMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
