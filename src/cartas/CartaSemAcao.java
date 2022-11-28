package cartas;

/**
 * SEM DESCR
 * 
 * @author grupo Cartas
 */
public class CartaSemAcao extends Exception {

	private static final long serialVersionUID = 1L;

	public CartaSemAcao() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CartaSemAcao(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CartaSemAcao(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CartaSemAcao(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CartaSemAcao(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
