package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassant;
	private PecaXadrez promovida;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> capturadas = new ArrayList<>();

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

	public PecaXadrez getEnPassant() {
		return enPassant;
	}
	
	public PecaXadrez getpromovida() {
		return promovida;
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
		Posicao origem = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(origem);
		return tabuleiro.peca(origem).possiveisMovimentos();
	}

	public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = realizeMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("N�o pode se colocar/continuar em Check!");
		}

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
		
		//Promover o pe�o
		promovida = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor()==Cor.Brancas && destino.getLinha()==0) ||
					(pecaMovida.getCor()==Cor.Pretas && destino.getLinha()==7)) {
				promovida = (PecaXadrez) tabuleiro.peca(destino);
				promovida = alterarPecaPromovida("D");
			} 
		}
		
		check = testarCheck(corOponente(jogadorAtual));

		if (testarCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// enPassant
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() + 2
				|| destino.getLinha() == origem.getLinha() - 2)) {
			enPassant = pecaMovida;
		} else {
			enPassant = null;
		}

		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez alterarPecaPromovida(String type) {
		if(promovida == null) {
			throw new IllegalStateException("N�o h� peca promovida");
		}
		if(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")) {
			return promovida;
		}
		
		Posicao pos = promovida.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(type, promovida.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String type, Cor cor) {
		if(type.equals("D")) return new Dama(tabuleiro, cor);
		if(type.equals("T")) return new Torre(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
		
	}

	private Peca realizeMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarcontagemMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			capturadas.add(pecaCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorreRei = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorreRei = new Posicao(destino.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorreRei);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPeca(torre, destinoTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorreDama = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorreDama = new Posicao(destino.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorreDama);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPeca(torre, destinoTorreDama);
		}
		
		//enPassant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada ==null) {
				Posicao peaoPosicao = enPassant.getPosicaoXadrez().toPosicao();
				pecaCapturada = tabuleiro.removerPeca(peaoPosicao);
				pecasNoTabuleiro.remove(pecaCapturada);
				capturadas.add(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarcontagemMovimentos();
		tabuleiro.lugarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			capturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorreRei = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorreRei = new Posicao(destino.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorreRei);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPeca(torre, origemTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorreDama = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorreDama = new Posicao(destino.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorreDama);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPeca(torre, origemTorreDama);
		}
		
		//enPassant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == enPassant) {
				Posicao peaoPosicao = ((PecaXadrez) pecaCapturada).getPosicaoXadrez().toPosicao();
				if(p.getCor() == Cor.Brancas) {
					peaoPosicao = new Posicao(3, peaoPosicao.getColuna());
				} else {
					peaoPosicao = new Posicao(4, peaoPosicao.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(destino);
				tabuleiro.lugarPeca(pecaCapturada, peaoPosicao);
			}
		}

	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezException("N�o h� peca nesta posicao");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if (!tabuleiro.peca(posicao).haMovimentoPossivel()) {
			throw new XadrezException("N�o h� movimentos possiveis para peca escolhida");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida n�o pode se mover para posicao de destino");
		}
	}

	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private Cor corOponente(Cor cor) {
		return cor == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	private PecaXadrez Rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalAccessError("Nao ha Rei da cor" + cor);
	}

	private boolean testarCheck(Cor cor) {
		Posicao reiPosicao = Rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
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

		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPeca('d', 1, new Dama(tabuleiro, Cor.Brancas));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.Brancas, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.Brancas));

		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPeca('d', 8, new Dama(tabuleiro, Cor.Pretas));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.Pretas, this));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.Pretas));
	}
}
