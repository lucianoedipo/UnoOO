package cartas;

import acao.Acao;

/**
 * Representa a abstração de uma Carta com Ação, ou seja, não possui número
 * 
 * @author grupo Cartas
 *
 */
public abstract class CartaComAcao extends Carta {

	// Acao do tipo Acao que vai vir do Grupo Ação:
	// Muda desde Carta ou altera apenas aqui ?

	@Override
	public final int getNumero() throws CartaSemNumero {
		// Dar um jeito de tratar essa retorno
		throw new CartaSemNumero("Cartas de Ação não possuem número");
	}

	@Override
	public final void setNumero(int n) throws CartaSemNumero {
		throw new CartaSemNumero("Cartas de Ação não possuem número");
	}

	@Override
	public Acao getAcao() {
		return this.acao;
	}

	@Override
	public void setAcao(Acao ac) {
		this.acao = ac;
	}

	@Override
	public Cor getCor() {
		return this.cor;
	}

	@Override
	public void setCor(Cor c) {
		this.cor = c;
	}

}
