package tabuleiro;
/**
 * Classe tabuleiro
 * @author Marcos Parrotto
 * @version 1.0
 */
public class Tabuleiro {
	/** quantidade de linhas do tabuleiro */
	private int linhas;
	/** quantidade de colunas do tabuleiro */
	private int colunas;
	/** matriz com as peças do tabuleiro */
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas <1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, necessario pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	 /**Recupera a propriedade 
	 * @return linhas */
	public int getLinhas() {
		return linhas;
	}

	 /**Recupera a propriedade colunas
	 * @return colunas */
	public int getColunas() {
		return colunas;
	}
	
	/**Recupera uma peça que esta em uma determinada linha e coluna no tabuleiro
	 * @param linha, coluna
	 * @return Peca */
	public Peca peca(int linha, int coluna) {
		if(!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return pecas[linha][coluna];
	}
	
	/**Recupera uma peça que esta em uma determinada posição no tabuleiro
	 * @param posicao
	 * @return Peca */
	public Peca peca(Posicao posicao) {
		return peca(posicao.getLinha(),posicao.getColuna());
	}
	
	/** coloca uma peça numa determinada posição do tabuleiro
	 * @param peca, posicao */
	public void lugarPeca(Peca peca, Posicao posicao) {
		if(existePeca(posicao)) {
			throw new TabuleiroException("Ja tem uma peca nessa posicao");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	/** Remove uma peca de uma determinada posicao e retorna a peca removida (capturada)
	 * @return Peca */
	public Peca removerPeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao invalida");
		}
		if(peca(posicao)==null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	/** verifica as coordenadas informadas existem no tabuleiro */
	public boolean posicaoExiste(int linha, int coluna) {
		return linha>=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	/** verifica a posição informada existe no tabuleiro */
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	/** verifica a posição contem peça */
	public boolean existePeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return peca(posicao) != null;
	}
}
