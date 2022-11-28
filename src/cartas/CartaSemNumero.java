package cartas;

/**
 * SEM DESCR
 * @author grupo Cartas
 * 
 */

public class CartaSemNumero extends Exception {

	private static final long serialVersionUID = 1L;

	public CartaSemNumero() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CartaSemNumero(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CartaSemNumero(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CartaSemNumero(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CartaSemNumero(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
