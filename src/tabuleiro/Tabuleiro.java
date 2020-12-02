package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas <1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, necessario pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peça peça(int linha, int coluna) {
		if(!posiçãoExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return peças[linha][coluna];
	}
	
	public Peça peça(Posição posição) {
		return peça(posição.getLinha(),posição.getColuna());
	}
	
	public void lugarPeça(Peça peça, Posição posição) {
		if(existePeça(posição)) {
			throw new TabuleiroException("Já tem uma peca nessa posicao");
		}
		peças[posição.getLinha()][posição.getColuna()] = peça;
		peça.posição = posição;
	}
	
	public Peça removerPeça(Posição posição) {
		if(!posiçãoExiste(posição)) {
			throw new TabuleiroException("Posicao invalida");
		}
		if(peça(posição)==null) {
			return null;
		}
		Peça aux = peça(posição);
		aux.posição = null;
		peças[posição.getLinha()][posição.getColuna()] = null;
		return aux;
	}
	
	public boolean posiçãoExiste(int linha, int coluna) {
		return linha>=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean posiçãoExiste(Posição posição) {
		return posiçãoExiste(posição.getLinha(), posição.getColuna());
	}
	
	public boolean existePeça(Posição posição) {
		if(!posiçãoExiste(posição)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return peça(posição) != null;
	}
}
