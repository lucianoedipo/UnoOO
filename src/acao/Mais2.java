package acao;

import base.Jogo;

/**
 * SEM DESCR COM WARN
 * 
 * @author Felippe
 * @Version 6.0
 */

public class Mais2 extends Acao {

	/**
	 * Mais 2 não tem ação instantanea
	 */
	public void acaoInstantanea() {
		return;
	}

	/**
	 * @param roda é um objeto da Classe Roda que é um vetor um ciclo (roda do
	 *             jogo). A função faz o proximo Jogador da roda comprar 2 cartas.
	 */
	public void acaoAcumulada() {
		Jogo.roda.comprar(2, Jogo.roda.jogadorDaVez());
		LOGGER.info("2 cartas compradas\n");
		return;
	}

	/**
	 * SEM COMM
	 * 
	 * @Override
	 */
	public String toString() {
		return "MAIS2";
	}
}