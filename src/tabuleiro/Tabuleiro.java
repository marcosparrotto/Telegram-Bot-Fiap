package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas <1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, necessario pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if(!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		return peca(posicao.getLinha(),posicao.getColuna());
	}
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if(existePeca(posicao)) {
			throw new TabuleiroException("J� tem uma peca nessa posicao");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
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
	
	public boolean posicaoExiste(int linha, int coluna) {
		return linha>=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean existePeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return peca(posicao) != null;
	}
}
