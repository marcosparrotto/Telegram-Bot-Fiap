package xadrez;

import tabuleiro.Peça;
import tabuleiro.Posição;
import tabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {
	private Cor cor;
	private int contagemMovimentos;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getcontagemMovimentos() {
		return contagemMovimentos;
	}
	
	public void incrementarcontagemMovimentos() {
		contagemMovimentos++;
	}
	
	public void decrementarcontagemMovimentos() {
		contagemMovimentos--;
	}
	
	public PosiçãoXadrez getPosiçãoXadrez() {
		return PosiçãoXadrez.fromPosição(posição);
	}
	
	protected boolean haPeçaOponente(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p != null && p.getCor() != cor;
	}


	
	
}
