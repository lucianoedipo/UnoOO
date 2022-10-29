package base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

import cartas.Carta;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.Cor;

/**
 * Representa a abstração de um conjunto de cartas
 *
 * @author luciano.silva
 *
 */
public class Baralho {
	private static final Logger LOGGER = LoggerFactory.getLogger(Baralho.class);

	public static final boolean NORMAL = false;
	public static final boolean INICIAL = true;

	protected ArrayList<Carta> cartas = new ArrayList<>();
	private boolean tipo;

	/**
	 * 
	 * @param tipo Baralho.INICIAL se for baralho inicial, ou Baralho.Normal para os
	 *             demais tipos de baralho.
	 */
	public Baralho(boolean tipo) {
		LOGGER.info("Criando baralho.");

		this.tipo = tipo;
		if (tipo == Baralho.INICIAL) {
			gerarCartas();
			embaralhar();
		}
	}

	/**
	 * Esse método somente será chamado quando o baralho criado for do tipo inicial.
	 */
	public void gerarCartas() {
		LOGGER.info("Gerando cartas normais. (4 de cada cor).");
		for (int i = 0; i < 2; i++) {
			for (int n = 0; n < 10; n++) {
				cartas.add(new CartaNormal(Cor.AMARELO, n));
				cartas.add(new CartaNormal(Cor.VERDE, n));
				cartas.add(new CartaNormal(Cor.AZUL, n));
				cartas.add(new CartaNormal(Cor.VERMELHO, n));
			}
		}

		LOGGER.info("Gerando cartas especiais. (2 de cada cor).");
		for (int i = 0; i < 2; i++) {
			cartas.add(new CartaEspecialComCor(Cor.AMARELO, Carta.MAIS2));
			cartas.add(new CartaEspecialComCor(Cor.AZUL, Carta.MAIS2));
			cartas.add(new CartaEspecialComCor(Cor.VERDE, Carta.MAIS2));
			cartas.add(new CartaEspecialComCor(Cor.VERMELHO, Carta.MAIS2));

			cartas.add(new CartaEspecialComCor(Cor.AZUL, Carta.INVERTE));
			cartas.add(new CartaEspecialComCor(Cor.AMARELO, Carta.INVERTE));
			cartas.add(new CartaEspecialComCor(Cor.VERDE, Carta.INVERTE));
			cartas.add(new CartaEspecialComCor(Cor.VERMELHO, Carta.INVERTE));

			cartas.add(new CartaEspecialComCor(Cor.VERMELHO, Carta.BLOQ));
			cartas.add(new CartaEspecialComCor(Cor.VERDE, Carta.BLOQ));
			cartas.add(new CartaEspecialComCor(Cor.AZUL, Carta.BLOQ));
			cartas.add(new cartas.CartaEspecialComCor(Cor.AMARELO, Carta.BLOQ));
		}

		LOGGER.info("Gerando cartas especiais sem cor. (4 MAIS4 e 4 TROCACOR).");
		for (int i = 0; i < 4; i++) {
			cartas.add(new CartaEspecialSemCor(Carta.MAIS4));
			cartas.add(new CartaEspecialSemCor(Carta.TROCACOR));
		}
	}
	
	/**
	 * Esse método somente será chamado quando o baralho criado for do tipo inicial.
	 */
	public void embaralhar() {
		LOGGER.info("Embaralhando cartas.");
		Collections.shuffle(cartas);
		LOGGER.info("{} Cartas embaralhadas.", cartas);
	}

	/**
	 * 
	 * @return a primeira carta do baralho
	 */
	public Carta comprarCarta() {
		Carta cartaComprada = cartas.remove(0);
		LOGGER.info("Carta comprada: {}", cartaComprada);
		return cartaComprada;
	}

	/**
	 * 
	 * @param c a carta.
	 */
	public void receberCarta(Carta c) {
		LOGGER.info("Recebendo e adicionando a carta: {} no baralho", c);
		this.cartas.add(c);
	}
	
	/**
	 * 
	 * @return o tamanhho do arraylist cartas
	 */
	public int quantCarta() { // para verificar a quantidade de cartas
		if LOGGER.isDebugEnable() {
			LOGGER.debug("Quantidade de cartas retornada: {}", cartas.size());
		}

		return this.cartas.size();
	}
	
	/**
	 * 
	 * @return a última carta do baralho
	 */
	public Carta ultimaCarta() { // para verificar a última carta
		if LOGGER.isDebugEnable() {
			LOGGER.debug("Última carta retornada: {}", cartas.get(cartas.size() - 1));
		}

		return cartas.get(quantCarta() - 1);
	}

	/**
	 * @return the baralho
	 */
	public ArrayList<Carta> getCartas() {
		if LOGGER.isDebugEnable() {
			LOGGER.debug("Cartas retornadas: {}", cartas);
		}

		return cartas;
	}

	/**
	 * @param cartas the baralho to set
	 */
	public void setCartas(ArrayList<Carta> cartas) {
		LOGGER.info("Cartas setadas: {}", cartas);

		this.cartas = cartas;
	}

	/**
	 * @return the tipo
	 */
	public boolean isTipo() {
		if LOGGER.isDebugEnable() {
			LOGGER.debug("Tipo retornado: {}", tipo);
		}

		return tipo;
	}

}
