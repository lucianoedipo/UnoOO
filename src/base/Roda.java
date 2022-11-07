package base;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acao.Acao;
import base.jogador.Jogador;
import cartas.Carta;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaSemAcao;
import cartas.CartaSemNumero;

public class Roda {
	/**
	 * Logger da classe Roda.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Roda.class);

	/**
	 * Baralho de compra.
	 */
	private Baralho compra;

	/**
	 * Baralho de descarte.
	 */
	private Baralho descarte = new Baralho(Baralho.NORMAL);

	/**
	 * Arraylist de jogadores.
	 */
	private ArrayList<Jogador> jogadores = new ArrayList<>();

	/**
	 * Arraylist de acumulo de +2 e +4.
	 */
	private ArrayList<Acao> acumulo = new ArrayList<>();

	/**
	 * ****Não inicializou
	 * Sentido da roda (positivo ou negativo).
	 */
	private int sentido = 1;

	/**
	 * Posicao atual da roda.
	 */
	private int posicaoAtual;

	/**
	 * Getter do baralho de compra.
	 * 
	 * @return Baralho de compra da roda.
	 */
	public Baralho getCompra() {
		return this.compra;
	}

	/**
	 * Getter do baralho de descarte.
	 * 
	 * @return Baralho de descarte da roda.
	 */
	public Baralho getDescarte() {
		return this.descarte;
	}

	/**
	 * Getter do arraylist dos jogadores.
	 * 
	 * @return Arraylist de jogadores da roda.
	 */
	public ArrayList<Jogador> getJogadores() {
		return this.jogadores;
	}

	/**
	 * Getter do sentido.
	 * 
	 * @return Sentido da roda.
	 */
	public int getSentido() {
		return this.sentido;
	}

	/**
	 * Getter da Posicao Atual.
	 * 
	 * @return Posicao Atual da roda.
	 */
	public int getPosicaoAtual() {
		return this.posicaoAtual;
	}

	/**
	 * Construtor: Instancia a roda, distribui as cartas iniciais aos jogadores e
	 * joga a primeira carta ao monte de descarte.
	 * 
	 * @param recebido           Baralho inicial recebido pelo jogo.
	 * @param jogadoresRecebidos ArrayList<Jogador> jogadores recebidos pelo jogo.
	 */
	public Roda(Baralho recebido, ArrayList<Jogador> jogadoresRecebidos) {
		LOGGER.info("Iniciando roda");
		this.compra = recebido;
		LOGGER.info("{} Cartas foram recebidas do baralho inicial", recebido.quantCarta());
		this.jogadores = jogadoresRecebidos;
		LOGGER.info("Há {} Jogadores na roda", jogadoresRecebidos.size());
		this.posicaoAtual = 0;
		LOGGER.info("Iniciando distribuição de cartas iniciais");
		for (int i = 0; i < this.jogadores.size(); i++) {
			for (int j = 0; j < 7; j++) {
				Carta cartaAux = entregarCarta();
				Jogador jogadorAux = this.jogadores.get(i);
				jogadorAux.comprar(cartaAux);
				LOGGER.trace("{} recebeu Carta {}", jogadorAux, cartaAux);
			}
		}
		LOGGER.info("Comprando primeira carta do jogo");
		Carta cartaAux;
		do {
			cartaAux = entregarCarta();
			this.descarte.receberCarta(cartaAux);
			if (cartaAux instanceof CartaEspecialSemCor || cartaAux instanceof CartaEspecialComCor)
				LOGGER.info("{} foi a carta retirada, comprando mais uma", cartaAux);
		} while (cartaAux instanceof CartaEspecialSemCor || cartaAux instanceof CartaEspecialComCor);
		LOGGER.info("{} é a primeira carta do jogo", cartaAux);

	}

	/**
	 * Verifica a carta jogada e se for compatível, insere uma carta no monte de
	 * descarte.
	 * 
	 * @param recebida Carta recebida para inserir no monte de descarte.
	 */
	public void descartarCarta(Carta recebida) {
		LOGGER.info("{} Descartando carta: {}",this.jogadorDaVez(), recebida);
		Carta ultima = getUltimaCarta();
		try {
			if (recebida.getNumero() == ultima.getNumero() || recebida.getCor() == ultima.getCor()) {
				this.descarte.receberCarta(recebida);
			}
		} catch (CartaSemNumero a) {
			try {
				Acao acaoAux = recebida.getAcao();
				if (acaoAux == ultima.getAcao() || recebida.getCor() == ultima.getCor()
						|| recebida instanceof CartaEspecialSemCor) {
					this.descarte.receberCarta(recebida);
					if (acaoAux == Carta.MAIS2 || acaoAux == Carta.MAIS4) {
						this.acumulo.add(acaoAux);
					}
				}
			} catch (CartaSemAcao b) {
				if (recebida.getCor() == ultima.getCor()) {
					this.descarte.receberCarta(recebida);
				}
			}
		} finally {
			ultima = getUltimaCarta();
			if (recebida != ultima) {
				LOGGER.info("Jogada impossível. {} não compatível com {}", recebida, ultima);
Z				throw new RuntimeException("Jogada impossível");
			}
		}
	}

	/**
	 * Compra uma carta do baralho de compra.
	 * 
	 * @return Primeira carta do monte de compra.
	 */
	public Carta entregarCarta() {
		Carta cartaAux;
		if (this.compra.getCartas().size() < 1) {
			this.transformaDescarte();
		}
		cartaAux = this.compra.comprarCarta();
		LOGGER.info("Entregando carta {}", cartaAux);
		return cartaAux;
	}

	/**
	 * Transforma o baralho de descarte no baralho de compra.
	 */
	public void transformaDescarte() {
		LOGGER.info("Monte de compras acabou\nTransformando Monte de descarte em monta de compras");
		for (int i = 0; i < this.descarte.getCartas().size() - 1; i++) {
			this.compra.receberCarta(this.descarte.comprarCarta());
		}
		this.compra.embaralhar();
	}

	/**
	 * Altera o sentido do jogo(horário e anti-horário).
	 */
	public void inverter() {
		this.sentido *= -1;
		LOGGER.warn("Sentido invertido: {}", this.sentido);
	}

	/**
	 * Dobra o sentido.
	 */
	public void pular() {
		this.sentido *= 2;
		LOGGER.warn("Sentido dobrado: {}", this.sentido);
	}

	/**
	 * Determina qual é o jogador responsavel por jogar no turno.
	 * 
	 * @param jogadorRecebido Jogador
	 * @return Posição do jogador na roda
	 */
	public Jogador jogadorDaVez() {
		int proxPosicao;
		proxPosicao = (this.posicaoAtual + this.sentido) % this.jogadores.size();
		System.out.println(proxPosicao);
		/*
		 * if (sentido % 2 == 0) { LOGGER.trace("Sentido voltou ao normal");
		 * this.sentido /= 2; }
		 */
		Jogador jogadorAux = this.jogadores.get(proxPosicao);
		LOGGER.info("O jogador da vez é {}", jogadorAux);
		this.posicaoAtual = proxPosicao;//eita lele
		return jogadorAux;
	}

	/**
	 * @return Ultima carta do baralho de descarte.
	 */
	public Carta getUltimaCarta() {
		Carta cartaAux = this.descarte.ultimaCarta();
		LOGGER.trace("A ultima carta jogada foi {}", cartaAux);
		return cartaAux;
	}

	/**
	 * Compra um numero de cartas e entrega a um jogador.
	 * 
	 * @param qtd     Quantidade de cartas a serem compradas.
	 * @param jogador Jogador que vai comprar.
	 */
	public void comprar(int qtd, Jogador jogador) {
		LOGGER.info("Comprando {} carta(s) ao jogador {}", qtd, jogador);
		for (int i = 0; i < qtd; i++) {
			jogador.comprar(entregarCarta());
		}
	}

	/**
	 * salva o acumulo em um ArrayList auxiliar, limpa o acumulo, e retorna o
	 * auxiliar
	 * 
	 * @return Acumulo de ações
	 */
	public ArrayList<Acao> desacumular() {
		ArrayList<Acao> acumuloAux = this.acumulo;
		this.acumulo.clear();
		LOGGER.info("O acúmulo foi transferido");
		return acumuloAux;
	}

	/**
	 * Verifica o tamanho do acumulo
	 * 
	 * @return Se houver acúmulo retorna true, senão, retorna false
	 */
	public boolean temAcumulo() {
		int tamanho = this.acumulo.size();
		if (tamanho > 0) {
			LOGGER.trace("Há {} cartas no acúmulo", tamanho);
			return true;
		} else {
			LOGGER.trace("Não tem acúmulo");
			return false;
		}
	}

	/**
	 * Método sobrescrito que adiciona informaçoes importantes sobre a Roda.
	 * 
	 * @return String com informações sobre um objeto da classe Roda.
	 */
	@Override
	public String toString() {
		String sent;
		if (this.sentido > 0)
			sent = "Positivo";
		else
			sent = "Negativo";
		return "Roda: Posição atual: " + this.posicaoAtual + ", Sentido: " + sent + ", Num cartas compra: "
				+ this.compra.quantCarta() + ", Num cartas descarte: " + this.descarte.quantCarta();
	}
}
