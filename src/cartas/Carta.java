package cartas;

import acao.Acao;
import acao.Bloqueio;
import acao.Inverter;
import acao.Mais2;
import acao.Mais4;
import acao.TrocaCor;

/**
 * Representa a abstração de uma Carta, tendo em vista o agrupamento de todos os
 * tipos
 * 
 * @author grupo Cartas
 *
 */

public abstract class Carta {

	public final static Acao MAIS2 = new Mais2();
	public final static Acao MAIS4 = new Mais4();
	public final static Acao BLOQ = new Bloqueio();
	public final static Acao INVERTE = new Inverter();
	public final static Acao TROCACOR = new TrocaCor();

	protected Cor cor;
	protected int numero;
	// protected String acao;
	protected Acao acao;

	/**
	 * 
	 * @return a cor da carta
	 */
	public abstract Cor getCor();

	/**
	 * 
	 * @return a ação da carta
	 * @throws Carta Sem Ação caso uma carta sem ação use o getAcao
	 */
	public abstract Acao getAcao() throws CartaSemAcao;

	/**
	 * 
	 * @return o número da carta
	 * @throws Carta Sem Numero caso uma carta sem numero use o getNumero
	 * 
	 */
	public abstract int getNumero() throws CartaSemNumero;

	/**
	 * 
	 * @param c
	 */
	public abstract void setCor(Cor c);

	/**
	 * @param ac
	 * 
	 *
	 * @throws Carta Sem Acao caso uma carta sem Acao use o setAcao
	 */
	public abstract void setAcao(Acao ac) throws CartaSemAcao;

	/**
	 * @param n
	 * 
	 *
	 * @throws Carta Sem Numero caso uma carta sem numero use o setNumero
	 */
	public abstract void setNumero(int n) throws CartaSemNumero;

	@Override
	public String toString() {

		String carta;
		if (this.cor == null) {
			carta = "CESC: " + this.acao;
		} else if (this.acao == null) {
			carta = "CNCC: " + this.numero + " " + this.cor;
		} else {
			carta = "CECC: " + this.acao + " " + this.cor;
		}
		return carta;
	}
}
