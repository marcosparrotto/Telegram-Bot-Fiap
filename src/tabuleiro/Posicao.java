package tabuleiro;

/**
 * Classe Posicao com as coordenadas da matriz que gera o tabuleiro
 * @author Marcos Parrotto
 * @version 1.0
 */
public class Posicao {
	/** linha da matriz*/
	private int linha;
	/** coluna da matriz */
	private int coluna;
	
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	 /**Recupera a propriedade linha
	 * @return linha */
	public int getLinha() {
		return linha;
	}

	/**Configura a propriedade linha
	 * @param linha */
	public void setLinha(int linha) {
		this.linha = linha;
	}

	 /**Recupera a propriedade coluna
		 * @return coluna */
	public int getColuna() {
		return coluna;
	}

	/**Configura a propriedade coluna
	 * @param coluna */
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	/**Configura as propriedades linhae  coluna
	 * @param linha, coluna */
	public void setValores(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
	
}
