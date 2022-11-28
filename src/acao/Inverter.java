package acao;

import base.Jogo;

/**
 * SEM DESCR COM WARN
 * 
 * @author Pedro;
 * @version 6.0;
 * @see Jogo
 */

public class Inverter extends Acao {
	/**
	 * Ação instantânea que inverte o setido da roda (roda.inverter);
	 *
	 * recebe o sentido e inverte, retornando a jogada para o jogador anterior;
	 */

	public void acaoInstantanea() {
		Jogo.roda.inverter();
		LOGGER.info("Sentido Invertido\n");
		return;
	}

	/**
	 *
	 * Logger para avisar que inverter não pode acumular ação;
	 */

	public void acaoAcumulada() {
		LOGGER.info("Inverter não pode acumular\n");
		return;
	}

	/**
	 * SEM COMM
	 * 
	 * @Override
	 */

	public String toString() {
		return "INVERTER";
	}

}