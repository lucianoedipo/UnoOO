package base;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cartas.Carta;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.Cor;

/**
 * Representa a abstração de um conjunto de cartas.
 *
 * @author Jecelen Adriane Campos, Guilherme Bispo Cupertino, Dener Fernandes,
 *         Joao P. Karpinski e Deivid Schiitz. Integrantes do grupo Baralho.
 *
 */
public class Baralho {
	/**
	 * ATT SEM DESC
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Baralho.class);
	public static final boolean NORMAL = false;
	public static final boolean INICIAL = true;
	protected ArrayList<Carta> cartas = new ArrayList<>();
	private boolean tipo;

	/**
	 * Forma o conjunto de cartas de acordo com o paramento enviado pelas classe
	 * 'Jogo' que instancia o baralho.
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
	 * Esse método somente será chamado quando o baralho instanciando for do tipo
	 * inicial. Instancia as cartas da classe 'Carta' e forma o conjunto abstrato
	 * inicial do UNO com a quantidade de cada uma predefinida por regras de jogo.
	 * São geradas 112 cartas que compõe o baralho inicial.
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
	 * O conjunto de cartas gerado no baralho inicial é embaralhado. O conjunto
	 * enviado à classe mensageira não encontra-se mais ordenado.
	 */
	public void embaralhar() {
		LOGGER.info("Embaralhando cartas.");
		Collections.shuffle(cartas);
		LOGGER.info("{} Cartas embaralhadas.", cartas);
	}

	/**
	 * Função que retorna a carta comprada no monte pela classe 'Jogador'. Nesta
	 * função a primeira carta é removida do conjunto e seu valor é retornado a
	 * classe mensageira.
	 * 
	 * @return a primeira carta do baralho
	 */
	public Carta comprarCarta() {
		Carta cartaComprada = cartas.remove(0);
		LOGGER.info("Carta comprada: {}", cartaComprada);
		return cartaComprada;
	}

	/**
	 * Recebe as cartas descartadas no monte de descarte, uma a uma, pela classe
	 * 'Jogador'. Nesta função o parametro (carta) é enviado pela classe que
	 * solicitou a função.
	 * 
	 * @param c a carta.
	 */
	public void receberCarta(Carta c) {
		LOGGER.info("Recebendo e adicionando a carta: {} no baralho", c);
		cartas.add(c);
	}

	/**
	 * Função que verifica a quantidade de cartas no baralho. Utilizada para
	 * perceber quando o monte de compras chega ao fim e para conseguir retornar o
	 * valor da ultima carta na fução 'UltimaCarta' desta classe.
	 * 
	 * @return o tamanhho do arraylist cartas
	 */
	public int quantCarta() { // para verificar a quantidade de cartas
		LOGGER.trace("Quantidade de cartas retornada: {}", cartas.size());

		return cartas.size();
	}

	/**
	 * Utiliza a função 'quantCarta' desta classe para realizar a busca pela última
	 * carta e retornar o valor dela para a classe 'Roda' quando solicitado.
	 * Descobre o valor da ultima carta no monte de descarte.
	 * 
	 * @return a última carta do baralho
	 */
	public Carta ultimaCarta() { // para verificar a última carta
		LOGGER.info("Última carta retornada: {}", cartas.get(cartas.size() - 1));

		return cartas.get(quantCarta() - 1);
	}

	/**
	 * Função que retorna o conjunto de cartas gerado.
	 * 
	 * @return o baralho.
	 */
	public ArrayList<Carta> getCartas() {
		LOGGER.info("Cartas retornadas: {}", cartas);

		return cartas;
	}

	/**
	 * A função recebe o conjunto gerado como parâmetro
	 * 
	 * @param c representa o baralho.
	 */
	public void setCartas(ArrayList<Carta> c) {
		LOGGER.info("Cartas setadas: {}", c);

		this.cartas = c;
	}

	/**
	 * função que retorna o tipo do baralho recebido: Inicial ou Normal para a
	 * realização das funções associadas a cada um dos tipos.
	 * 
	 * @return the tipo
	 */
	public boolean isTipo() {
		LOGGER.info("Tipo retornado: {}", tipo);

		return tipo;
	}

	/**
	 * método toString
	 * 
	 * @return cartas do baralho
	 */
	public String toString() {
		String s = "Baralho\n";
		for (Carta carta : cartas) {
			s += "[" + carta + "]\n";
		}
		return s;
	}

}
