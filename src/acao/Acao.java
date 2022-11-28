package acao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * SEM DESCR
 * COM WARN
 * @author lucia
 *
 */
public abstract class Acao {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Acao.class);

	/**
	 * Método de funções de ação que precisam ser acumuladas.
	 */
	public abstract void acaoAcumulada();

	/**
	 * Método de funções de ação que são executadas no mesmo momento.
	 */
	public abstract void acaoInstantanea();

}

/**
 * @author Fernando Favaro Moreira
 * @since 07/11/2022
 * @version 5.0
 * @see Roda, Jogada
 */
