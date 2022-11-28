package base.jogador;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.Baralho;
import base.JogadaImpossivel;
import base.Jogo;
import base.Roda;
import cartas.Carta;

/**
 * SEM DESCR
 * 
 * @author lucia
 *
 */
public class MaoCartas extends Baralho {
	private static final Logger LOGGER = LoggerFactory.getLogger(MaoCartas.class);
	public static final short tamInicial = 7;

	/**
	 * Construtor que recebe uma lista inicial de cartas para a MaoCartas
	 * 
	 * @param cartasIniciais - Lista de cartas iniciais para MaoCartas
	 */
	public MaoCartas(ArrayList<Carta> cartasIniciais) {
		super(Baralho.NORMAL);
		LOGGER.trace("Instanciando objeto de MaoCartas a partir de lista de cartas inicial");

		this.inicializar(cartasIniciais);
	}

	/**
	 * Construtor que retorna uma MaoCartas totalmente vazia (sem cartas).
	 */
	public MaoCartas() {
		super(Baralho.NORMAL);
		LOGGER.info("Instanciando objeto de MaoCartas com nenhuma carta");
		this.inicializar(new ArrayList<>());
	}

	/**
	 * Retorna a quantidade atual de cartas na lista de cartas de MaoCartas
	 * 
	 * @return
	 */
	public int getQuantidadeCartas() {
		LOGGER.info("Retornando quantidade de cartas em lista de cartas interna");

		return this.cartas.size();
	}

	/**
	 * Realiza a inicialização do objeto de MaoCartas
	 * 
	 * @param cartasIniciais - Lista de cartas iniciais de MaoCartas
	 */
	private void inicializar(ArrayList<Carta> cartasIniciais) {
		LOGGER.trace("Inicializando lista de cartas interna a partir de uma lista de cartas");

		cartas = cartasIniciais;
	}

	/**
	 * Realiza a adição de uma lista de cartas na lista de cartas interna de
	 * MaoCartas.
	 * 
	 * @param listaCartas - Lista de cartas a ser adicionada em MaoCartas
	 */
	public void receberCartas(ArrayList<Carta> listaCartas) {
		LOGGER.trace("Adicionando lista de cartas em lista de cartas interna");

		cartas.addAll(listaCartas);
	}

	/**
	 * Realiza o descarte de uma carta específica, retirando-a da mão de cartas de
	 * Jogador e inserindo-a no baralho de descarte de Roda.
	 * 
	 * @param carta - Carta a ser descartada de MaoCartas
	 * @see Roda
	 */
	public void descartarCarta(Carta carta) throws JogadaImpossivel {
		this.cartas.remove(carta);
		Jogo.roda.descartarCarta(carta);
	}

	/**
	 * Retorna todas as cartas existentes na lista interna de cartas de MaoCartas.
	 * 
	 * @return cartas - ArrayList de cartas existentes na MãoCartas.
	 */
	public ArrayList<Carta> getCartas() {
		return cartas;
	}

}