//Somos nós, os amigos de todos vós
package base.jogador;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acao.Acao;
import acao.Bloqueio;
import acao.Inverter;
import acao.Mais4;
import acao.TrocaCor;
import base.Jogo;
import base.Roda;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.CartaSemAcao;
import cartas.Cor;

public class Jogador {// implements Jogada{
	private static final Logger LOGGER = LoggerFactory.getLogger(Jogador.class);
	private String nome;
	private MaoCartas maoJogador;

	/**
	 * Construtor que recebe o nome do jogador e inicializa uma maoJogador (objeto
	 * de MaoCartas) sem nenhuma carta.
	 * 
	 * @param nome - Nome inicial do jogador
	 * @see MaoCartas
	 */
	public Jogador(String nome) {
		LOGGER.trace("Instânciando objeto de Jogador a partir de nome e instanciando MaoCartas vazia em objeto");
		this.nome = nome;
		this.maoJogador = new MaoCartas();
		LOGGER.info("Jogador:({}) Criado", nome);
	}

	/**
	 * Retorna o nome do jogador
	 * 
	 * @return nome - nome atual do jogador
	 */
	public String getNome() {
		LOGGER.trace("Nome retornado: {}", nome);
		return nome;
	}

	/**
	 * Retorna a quantidade de cartas atual do jogador
	 * 
	 * @return Quantidade de cartas que o jogador possui atualmente
	 */
	public int getQuantidadeCartas() {
		int qtdCartas = this.maoJogador.quantCarta();
		LOGGER.trace("{} possui {} cartas", this, qtdCartas);
		return qtdCartas;
	}

	/**
	 * Altera o nome do jogador
	 * 
	 * @param nome (nome a ser atualizado para o jogador)
	 */
	public void setNome(String nome) {
		LOGGER.info("{} alterou o nome para {}", this.getNome(), nome);
		this.nome = nome;
	}

	/**
	 * @deprecated verifique se alguém usa, caso contrário acredito ser
	 *             desnecessário Inicializa a maoJogador a partir de uma lista de
	 *             cartas
	 * 
	 * @param cartasIniciais - Lista de cartas iniciais do jogador
	 * @see MaoCartas
	 **/
	public void inicializarMao(ArrayList<Carta> cartasIniciais) {
		LOGGER.trace("Instanciando objeto de MaoCartas a partir de lista de cartas");
		this.maoJogador = new MaoCartas(cartasIniciais);
		LOGGER.info("MaoCartas iniciada: {}", this.maoJogador.toString());
	}

	/**
	 * @deprecated é privada e nunca é usada localmente, errado! Inicializa a
	 *             maoJogador sem nenhuma carta
	 * 
	 * @see MaoCartas
	 **/
	private void inicializarMao() {
		LOGGER.trace("Instanciando objeto de MaoCartas vazia");

		this.maoJogador = new MaoCartas();
	}

	/**
	 *
	 * Compra uma lista de cartas, adicionando-as a maoJogador
	 * 
	 * @see MaoCartas
	 **/
	// @Override
	public void comprar(ArrayList<Carta> listaCartas) {
		LOGGER.trace("Comprando (recebendo) lista de cartas");
		this.maoJogador.receberCartas(listaCartas);
	}

	/**
	 *
	 * Compra uma carta, adicionando uma carta a maoJogador,
	 * 
	 * @see MaoCartas
	 **/
	// @Override
	public void comprar(Carta carta) {
		this.maoJogador.receberCarta(carta);
		LOGGER.trace("Carta adicionada: {}", carta.toString());
	}

	/**
	 *
	 * Descarta uma carta, retirando uma carta de maoJogador, adicionando-a ao monte
	 * de descarte
	 * 
	 * @see MaoCartas
	 **/
	public void descartar(Carta carta) {
		LOGGER.info("Jogador {} descartando carta: {}", this.getNome(), carta.toString());

		this.maoJogador.descartarCarta(carta);
	}

	/**
	 * Realiza a busca de uma carta de Acao igual a do acúmulo atual em Roda, a fim
	 * de descartá-la, e não ter que acumular as cartas no acúmulo
	 * 
	 * @param acaoDoAcumulo - Ação atual do acúmulo em Roda
	 * @return Carta para ser acumulada, ou 'null' (no caso de nenhuma carta
	 *         compatível com o acúmulo ter sido encontrada)
	 * @see Acao
	 * @see Carta
	 */
	private Carta defineCartaParaAcumulo(Acao acaoDoAcumulo) {
		for (Carta c : this.getMaoJogador().getCartas()) {
			try {
				if (c instanceof CartaComAcao)//n existe carta concreta desse tipo
					if (c.getAcao() == acaoDoAcumulo) {
						return c;
					}
			} catch (CartaSemAcao e) {
				LOGGER.error("Erro ao tentar comparar ação de carta com ação de acúmulo: {}", e);
			}
		}
		return null;
	}

	/**
	 * Realiza a compra de todas as ações acumuladas na roda
	 * 
	 * @param acumulos - Acúmulo da roda
	 */
	private void comprarCartasAcumuladas(ArrayList<Acao> acumulos) {
		for (Acao acumulo : acumulos) {
			acumulo.comprar(Jogo.roda);
		}
	}

	/**
	 * Realiza uma jogada a partir da análise da situação atual da Roda do Jogo. A
	 * função busca uma carta adequada para ser jogada. Se encontra uma carta
	 * adequada, ele a descarta e, caso a carta possua uma Ação, ele a 'realiza'. Se
	 * não encontra nenhuma carta adequada, o jogador adquire uma carta, adiciona
	 * ela em sua MaoCartas, e 'passa a vez'.
	 * 
	 * @see Roda
	 * @see Jogo
	 * @see Acao
	 * @see Carta
	 * @see MaoCartas
	 */
	public void realizarJogada() {
		Carta carta = null;
		if (Jogo.roda.temAcumulo()) {
			try {
				carta = defineCartaParaAcumulo(Jogo.roda.getUltimaCarta().getAcao());
			} catch (CartaSemAcao e) {
				LOGGER.error("Erro ao tentar definir carta de acúmulo: {}", e);
			}
			if (carta == null) {
				LOGGER.warn("{}::Comprando acumulos", this);
				comprarCartasAcumuladas(Jogo.roda.desacumular());
			} else {
				LOGGER.warn("{}::Aculumando::{}", this, carta);
				this.maoJogador.descartarCarta(carta);
			}
		} else {
			carta = defineCartaDaJogada();
			if (carta != null) {
				try {//verificar se é ação instantanea e realizar ****
					Acao acaoCarta = carta.getAcao();
					realizarAcaoDaCarta(acaoCarta);
				} catch (CartaSemAcao e) {
					// Carta não possui acao
				}
				this.maoJogador.descartarCarta(carta);
				LOGGER.warn("{}::ESCOLHEU::{}", this, carta);
			}
		}
	}

	/**
	 * Função responsável por buscar carta adequada em MaoCartas de jogador para ser
	 * descartada, no caso de não haver acúmulo (cartas de compras acumuladas na
	 * roda)
	 * 
	 * <h4>Sequência de escolha padrão:</h4>
	 * <ul>
	 * <li>1º carta especial com cor: bloqueio, reverso, +2;</li>
	 * <li>2º carta normal;</li>
	 * <li>3º carta especial sem cor: trocacor e +4;</li>
	 * </ul>
	 * 
	 * @return Carta definida para ser jogada (descartada), ou 'null' (caso nenhuma
	 *         carta adequada seja encontrada)
	 */
	private Carta defineCartaDaJogada() {
		Carta ultimo = Jogo.roda.getUltimaCarta();
		/*
		 * Sequência de uso das cartas:
		 * 
		 */

		// Busca bloqueio, reverso e +2
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (!(c instanceof CartaEspecialComCor))
				continue;

			CartaEspecialComCor ca = (CartaEspecialComCor) c;

			// Verifica se é a mesma cor ou se é a mesma ação pra poder jogar
			if (ca.getCor() == ultimo.getCor() || (ultimo instanceof CartaEspecialComCor
					&& ca.getAcao() == ((CartaEspecialComCor) ultimo).getAcao())) {
				return ca;
			}
		}

		// Busca cartas normais
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (!(c instanceof CartaNormal))
				continue;

			CartaNormal cn = (CartaNormal) c;

			// Se for a mesma cor pode jogar
			if (cn.getCor() == ultimo.getCor())
				return c;

			// Se for o mesmo número também pode jogar
			if (ultimo instanceof CartaNormal && ((CartaNormal) ultimo).getNumero() == cn.getNumero())
				return c;
		}

		// Busca +4 e troca cor
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (!(c instanceof CartaEspecialSemCor))
				continue;

			// +4 e troca cor pode ser jogado de qualquer forma
			return c;
		}

		// Se não conseguir jogar nenhuma tem que comprar
		Jogo.roda.comprar(1, this);
		return null;
		// return this.defineCartaDaJogada();
	}

	/**
	 * Realiza a ação de uma carta descartada pelo jogador
	 * 
	 * @param acaoCarta - A ação da carta descartada
	 */
	private void realizarAcaoDaCarta(Acao acaoCarta) {
		if (acaoCarta instanceof Bloqueio) {
			acaoCarta.pular(Jogo.roda);
		} else if (acaoCarta instanceof Inverter) {
			acaoCarta.inverter(Jogo.roda);
		} else if (acaoCarta instanceof Mais4 || acaoCarta instanceof TrocaCor) {
			acaoCarta.trocaCor();
			// acaoCarta.trocaCor(sorteiaCor());
		}
		LOGGER.trace("Ação da carta realizada");
	}

	/**
	 * 
	 * Sorteia uma cor válida para realizar a ação de troca de cor.
	 * 
	 * @see Acao
	 * @see TrocaCor
	 * @see Mais4
	 * @return Cor sorteada pela função
	 */
	public Cor sorteiaCor() {
		Random r = new Random();
		Cor[] cores = { Cor.AMARELO, Cor.AZUL, Cor.VERDE, Cor.VERMELHO };
		Cor corSorteada = cores[r.nextInt(4)];
		LOGGER.info("Cor sorteada: {}", corSorteada);
		return corSorteada;
	}

	/**
	 * Retorna a mão de cartas do jogador
	 * 
	 * @see MaoCartas
	 * @return Mão do jogador
	 */
	public MaoCartas getMaoJogador() {
		LOGGER.trace("maoJogador retornada");
		return maoJogador;
	}

	/**
	 * Retorna nome, quantidade de cartas e apresentação de todas as cartas do
	 * objeto de Jogador em uma String
	 * 
	 * @return String com informações de Jogador.
	 */
	@Override
	public String toString() {
		return String.format("[Jogador:(%s), Cartas:(%d)]", this.nome, this.getQuantidadeCartas());
	}

}
