package xadrez;

import tabuleiro.TabuleiroException;

/**
 * Classe para tratamento de erro de xadrez
 * @author Marcos Parrotto
 * @version 1.0
 */
public class XadrezException extends TabuleiroException{
	private static final long serialVersionUID = 1L;
	
	public XadrezException(String msg) {
		super(msg);
	}

}
