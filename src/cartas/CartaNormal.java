package cartas;

/**
 * Representa a abstração de uma Carta Normal, com apenas Cor e Número
 * 
 * @author grupo Cartas
 *
 */

public class CartaNormal extends CartaComCor {
	/**
	 * 
	 * @param c do tipo Cor
	 * @param n do tipo int
	 */
	public CartaNormal(Cor c, int n) {// Carta normal com cor
		this.cor = c;
		this.numero = n;
	}

	@Override
	public int getNumero() {
		return this.numero;
	}

	@Override
	public void setCor(Cor c) {
		this.cor = c;

	}

	@Override
	public void setNumero(int n) throws CartaSemNumero {
		this.numero = n;

	}

	@Override
	public Cor getCor() {
		return this.cor;
	}

}
