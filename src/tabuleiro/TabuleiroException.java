package tabuleiro;
/**
 * Classe para tratamentos de erros do tabuleiro
 * @author Marcos Parrotto
 * @version 1.0
 */
public class TabuleiroException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public TabuleiroException(String msg) {
		super(msg);
	}
}
