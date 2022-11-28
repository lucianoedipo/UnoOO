package acao;

import base.Jogo;

/**
 * SEM DESCR COM WARN
 * 
 * @author Vinicius
 * @version 4.0
 * @since 07/11/2022
 */

public class TrocaCor extends Acao {
	/**
	 * Função recebe uma cor que deseja alterar e retorna ela
	 */
	public void acaoInstantanea() { /** Trocar função void para Cor */
		Jogo.roda.mudarCor(Jogo.roda.jogadorDaVez().sorteiaCor());
		LOGGER.info("Cor trocada\n");
		return;
	}

	/**
	 * Explicação? roda Função de compra
	 */
	public void acaoAcumulada() {
		LOGGER.info("Troca Cor não realiza ações acumuladas\n");
		return;
	}

	/**
	 * SEM COMM
	 * 
	 * @Override
	 */
	public String toString() {
		return "TROCACOR";
	}
}