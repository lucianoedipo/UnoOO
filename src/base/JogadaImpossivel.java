package base;

/**
 * FORA PADRAO JAVADOC Exceção gerada quando um jogada é incorreta, ou seja, a
 * carta jogada não é compatível com a última carta do monte de descarte.
 * Teoricamente, esta exceção retorna ao jogador, que deve tomar alguma
 * providência a respeito
 */
public class JogadaImpossivel extends Exception {
	/**
	 * SEM DESCR
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * SEM DESCR
	 */
	public JogadaImpossivel() {
	}

	/**
	 * @param message
	 */
	public JogadaImpossivel(String message) {
		super(message);
	}

	/**
	 * DESC?
	 * 
	 * @param cause
	 */
	public JogadaImpossivel(Throwable cause) {
		super(cause);
	}

	/**
	 * DESC?
	 * 
	 * @param message
	 * @param cause
	 */
	public JogadaImpossivel(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * DESC?
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JogadaImpossivel(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}