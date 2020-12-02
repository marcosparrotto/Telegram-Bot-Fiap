package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peça;
import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.peças.Bispo;
import xadrez.peças.Cavalo;
import xadrez.peças.Dama;
import xadrez.peças.Peão;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PeçaXadrez enPassant;
	private PeçaXadrez promovida;

	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> capturadas = new ArrayList<>();

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.Brancas;
		setupInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PeçaXadrez getEnPassant() {
		return enPassant;
	}
	
	public PeçaXadrez getpromovida() {
		return promovida;
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(PosiçãoXadrez posiçãoOrigem) {
		Posição origem = posiçãoOrigem.toPosição();
		validarPosiçãoOrigem(origem);
		return tabuleiro.peça(origem).possiveisMovimentos();
	}

	public PeçaXadrez executarMovimentoXadrez(PosiçãoXadrez posiçãoOrigem, PosiçãoXadrez posiçãoDestino) {
		Posição origem = posiçãoOrigem.toPosição();
		Posição destino = posiçãoDestino.toPosição();
		validarPosiçãoDestino(origem, destino);
		Peça peçaCapturada = realizeMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			throw new XadrezException("Nao pode se colocar/continuar em Check!");
		}

		PeçaXadrez peçaMovida = (PeçaXadrez) tabuleiro.peça(destino);
		
		//Promover o peão
		promovida = null;
		if(peçaMovida instanceof Peão) {
			if((peçaMovida.getCor()==Cor.Brancas && destino.getLinha()==0) ||
					(peçaMovida.getCor()==Cor.Pretas && destino.getLinha()==7)) {
				promovida = (PeçaXadrez) tabuleiro.peça(destino);
				promovida = alterarPeçaPromovida("D");
			} 
		}
		
		check = testarCheck(corOponente(jogadorAtual));

		if (testarCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// enPassant
		if (peçaMovida instanceof Peão && (destino.getLinha() == origem.getLinha() + 2
				|| destino.getLinha() == origem.getLinha() - 2)) {
			enPassant = peçaMovida;
		} else {
			enPassant = null;
		}

		return (PeçaXadrez) peçaCapturada;
	}
	
	public PeçaXadrez alterarPeçaPromovida(String type) {
		if(promovida == null) {
			throw new IllegalStateException("Nao ha peca promovida");
		}
		if(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")) {
			return promovida;
		}
		
		Posição pos = promovida.getPosiçãoXadrez().toPosição();
		Peça p = tabuleiro.removerPeça(pos);
		peçasNoTabuleiro.remove(p);
		
		PeçaXadrez novaPeça = novaPeça(type, promovida.getCor());
		tabuleiro.lugarPeça(novaPeça, pos);
		peçasNoTabuleiro.add(novaPeça);
		
		return novaPeça;
	}
	
	private PeçaXadrez novaPeça(String type, Cor cor) {
		if(type.equals("D")) return new Dama(tabuleiro, cor);
		if(type.equals("T")) return new Torre(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
		
	}

	private Peça realizeMovimento(Posição origem, Posição destino) {
		PeçaXadrez p = (PeçaXadrez) tabuleiro.removerPeça(origem);
		p.incrementarcontagemMovimentos();
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarPeça(p, destino);
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			capturadas.add(peçaCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posição origemTorreRei = new Posição(origem.getLinha(), origem.getColuna() + 3);
			Posição destinoTorreRei = new Posição(destino.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(origemTorreRei);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPeça(torre, destinoTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posição origemTorreDama = new Posição(origem.getLinha(), origem.getColuna() - 4);
			Posição destinoTorreDama = new Posição(destino.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(origemTorreDama);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPeça(torre, destinoTorreDama);
		}
		
		//enPassant
		if(p instanceof Peão) {
			if(origem.getColuna() != destino.getColuna() && peçaCapturada ==null) {
				Posição peaoPosição = enPassant.getPosiçãoXadrez().toPosição();
				peçaCapturada = tabuleiro.removerPeça(peaoPosição);
				peçasNoTabuleiro.remove(peçaCapturada);
				capturadas.add(peçaCapturada);
			}
		}

		return peçaCapturada;
	}

	private void desfazerMovimento(Posição origem, Posição destino, Peça peçaCapturada) {
		PeçaXadrez p = (PeçaXadrez) tabuleiro.removerPeça(destino);
		p.decrementarcontagemMovimentos();
		tabuleiro.lugarPeça(p, origem);
		if (peçaCapturada != null) {
			tabuleiro.lugarPeça(peçaCapturada, destino);
			capturadas.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posição origemTorreRei = new Posição(origem.getLinha(), origem.getColuna() + 3);
			Posição destinoTorreRei = new Posição(destino.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(destinoTorreRei);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPeça(torre, origemTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posição origemTorreDama = new Posição(origem.getLinha(), origem.getColuna() - 4);
			Posição destinoTorreDama = new Posição(destino.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez) tabuleiro.removerPeça(destinoTorreDama);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPeça(torre, origemTorreDama);
		}
		
		//enPassant
		if(p instanceof Peão) {
			if(origem.getColuna() != destino.getColuna() && peçaCapturada == enPassant) {
				Posição peaoPosição = ((PeçaXadrez) peçaCapturada).getPosiçãoXadrez().toPosição();
				if(p.getCor() == Cor.Brancas) {
					peaoPosição = new Posição(3, peaoPosição.getColuna());
				} else {
					peaoPosição = new Posição(4, peaoPosição.getColuna());
				}
				peçaCapturada = tabuleiro.removerPeça(destino);
				tabuleiro.lugarPeça(peçaCapturada, peaoPosição);
			}
		}

	}

	private void validarPosiçãoOrigem(Posição posição) {
		if (!tabuleiro.existePeça(posição)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if (jogadorAtual != ((PeçaXadrez) tabuleiro.peça(posição)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if (!tabuleiro.peça(posição).haMovimentoPossivel()) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}

	private void validarPosiçãoDestino(Posição origem, Posição destino) {
		if (!tabuleiro.peça(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarPeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
		peçasNoTabuleiro.add(peça);
	}

	private Cor corOponente(Cor cor) {
		return cor == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	private PeçaXadrez Rei(Cor cor) {
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			if (p instanceof Rei) {
				return (PeçaXadrez) p;
			}
		}
		throw new IllegalAccessError("Nao ha Rei da cor" + cor);
	}

	private boolean testarCheck(Cor cor) {
		Posição reiPosição = Rei(cor).getPosiçãoXadrez().toPosição();
		List<Peça> peçasOponentes = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Peça p : peçasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosição.getLinha()][reiPosição.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posição origem = ((PeçaXadrez) p).getPosiçãoXadrez().toPosição();
						Posição destino = new Posição(i, j);
						Peça peçaCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, peçaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {

		lugarNovaPeça('a', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('b', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('c', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('d', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('e', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('f', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('g', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('h', 2, new Peão(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPeça('d', 1, new Dama(tabuleiro, Cor.Brancas));
		lugarNovaPeça('e', 1, new Rei(tabuleiro, Cor.Brancas, this));
		lugarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPeça('h', 1, new Torre(tabuleiro, Cor.Brancas));

		lugarNovaPeça('a', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('b', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('c', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('d', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('e', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('f', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('g', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('h', 7, new Peão(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPeça('d', 8, new Dama(tabuleiro, Cor.Pretas));
		lugarNovaPeça('e', 8, new Rei(tabuleiro, Cor.Pretas, this));
		lugarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPeça('h', 8, new Torre(tabuleiro, Cor.Pretas));
	}
}
