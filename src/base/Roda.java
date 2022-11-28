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
import cartas.Cor;

public class Roda {
	/**
	 * Logger da classe Roda.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Roda.class);

	/**
	 * Baralho de compra ou seja, de onde as cartas são retiradas e entregues ao
	 * jogador.Apenas em dois momento cartas são inseridas nele: Ao iniciar o jogo,
	 * e ao "Transformar o descarte em compra"
	 */
	private Baralho compra;

	/**
	 * Baralho de descarte, onde as cartas jogadas pelo jogadores são inseridas. O
	 * único momento em que as cartas são retiradas deles é quando o método
	 * "transformaDescarte" é chamado, para que o monte de compra não fique vazia,
	 * as cartas são removidas dele(descarte) e passado para o monte de "compra",
	 * mantendo apenas a última carta jogada, que é a determinante da próxima jogada
	 */
	private Baralho descarte = new Baralho(Baralho.NORMAL);

	/**
	 * Arraylist de jogadores, onde os jogadores armazenados apenas para ter um
	 * controle de suas posições "na roda".
	 */
	private ArrayList<Jogador> jogadores = new ArrayList<>();

	/**
	 * Arraylist de acumulo das ações de +2 e +4, que como não são ações
	 * instantâneas, devem ser guardadas até que possam ser defato realizadas.
	 */
	private ArrayList<Acao> acumulo = new ArrayList<>();

	/**
	 * Sentido da roda (positivo ou negativo).
	 */
	private int sentido = 1;

	/**
	 * Posicao do jogador na roda que deve realizar a jogada do momento.
	 */
	private int posicaoAtual = 0;

	/**
	 * Variável que armazena momentaneamente a cor recebida em
	 * "trocarCor".Inicializada em zero, porque não é possível o jogo começar com um
	 * +4 ou "escolhe cor"
	 */
	private Cor corEscolhida = null;

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
	 * Getter da cor escolhida no caso de um troca cor ou mais 4 ter sido jogado
	 * 
	 * @return A ultima cor que foi escolhida, caso a ultima carta tenha a ação de
	 *         trocar a cor
	 */
	public Cor getCorEscolhida() {
		return corEscolhida;
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
		LOGGER.info("Iniciando distribuição de cartas iniciais");
		for (int i = 0; i < this.jogadores.size(); i++) {/* para cada jogador... */
			for (int j = 0; j < 7; j++) {/* ...distribui 7 cartas */
				Carta cartaAux = entregarCarta();
				Jogador jogadorAux = this.jogadores.get(i);
				jogadorAux.comprar(cartaAux);
				LOGGER.info("Jogador {} recebeu Carta {}", jogadorAux, cartaAux);
			}
		}
		LOGGER.info("Comprando primeira carta do jogo");
		Carta cartaAux;
		do {
			cartaAux = entregarCarta();
			this.descarte.receberCarta(cartaAux);
			if (cartaAux instanceof CartaEspecialSemCor || cartaAux instanceof CartaEspecialComCor)
				LOGGER.info("{} foi a carta retirada, comprando mais uma", cartaAux);
		} while (cartaAux instanceof CartaEspecialSemCor
				|| cartaAux instanceof CartaEspecialComCor);/*
															 * caso a carta não possuir cor ou número, ou seja, for uma
															 * instância da classe "CartaEspecialSemCor" ou da classe
															 * "CartaEspecialComCor", devemos pegar outra carta para que
															 * o primeiro jogador possa fazer sua jogada
															 */
		LOGGER.info("{} é a primeira carta do jogo", cartaAux);

	}

	/**
	 * Verifica a carta jogada e se for compatível, insere uma carta no monte de
	 * descarte, caso contrário, lança uma exceção.
	 * 
	 * @param recebida Carta recebida para inserir no monte de descarte.
	 */
	public void descartarCarta(Carta recebida) throws JogadaImpossivel {
		Carta ultima = getUltimaCarta();/* Ultima carta jogada no monte de descarte */
		int nRecebida = -1;/*
							 * nuemração da jogada atual, inicializado com -1 pois existem cartas com o
							 * número 0
							 */
		int nUltima = -1;/* numeração da ultima carta jogada no monte de descarte */
		Acao aRecebida = null;/* Ação da carta da jogada atual */
		Acao aUltima = null;/* Ação da última carta do monte de descarte */
		Cor cRecebida = recebida.getCor();/* Cor da carta da jogada atual */
		Cor cUltima = ultima.getCor();/* Ação da última carta jogada no monte de descarte */
		boolean status = false;/*
								 * variável que é uma representação se a jogada foi realizada de fato, ou seja,
								 * se a carta é compatível. Caso ela se torne verdadeira, podemos zerar a
								 * "cor temporária - corEscolhida" e notificar, através do LOGGER, que a jogada
								 * foi realizada
								 */

		try {
			nRecebida = recebida.getNumero();/* tentamos pegar o número da carta da jogada */
		} catch (CartaSemNumero a) {/* se não for possível, tratamos a exceção */
			LOGGER.trace("a carta {} não possui número", recebida);
		}
		try {/* tentamos pegar a ação da carta da jogada */
			aRecebida = recebida.getAcao();
		} catch (CartaSemAcao b) {/* se não for possível, tratamos a exceção */
			LOGGER.trace("a carta {} não possui ação", recebida);
		}

		/*
		 * Optamos por sempre atualizarmos os valores da última carta do monte,
		 * primeiro, para que nenhuma informação seja perdida(no caso da
		 * "mudança de cor"),segundo, para envitar possível conflitos na primeira jogada
		 * do jogo, e terceiro, para não termo que criar essa variáveis na classe, sendo
		 * que ela são acessadas apenas por este método específico, que é apenas durante
		 * a verificação
		 */

		try {/* tentamos pegar o número da última carta do monte de descarte */
			nUltima = ultima.getNumero();
		} catch (CartaSemNumero a) {/* se não for possível, tratamos a exceção */
			LOGGER.trace("a última carta do descarte {} não possui número", ultima);
		}
		try {/* tentamos pegar a ação da última carta do monte de descarte */
			aUltima = ultima.getAcao();
		} catch (CartaSemAcao b) {/* se não for possível, tratamos a exceção */
			LOGGER.trace(" última carta do descarte {} não possui ação", ultima);
		}
		if (aUltima == Carta.MAIS4 || aUltima == Carta.TROCACOR) {
			cUltima = corEscolhida;
		}

		/*
		 * Se a carta for um mais 4, ele pode ser jogada a qualquer momento, exceto se a
		 * ultima carta jogada for um +4 e seu acúmulo ainda estiver vigente (é
		 * permitido jogar um +4 em cima de outro quando o anterior já foi comprado)
		 */
		if (aRecebida == Carta.MAIS4 && (aUltima != Carta.MAIS4 || (aUltima == Carta.MAIS4 && temAcumulo() == false))) {
			this.descarte.receberCarta(recebida);
			acumulo.add(aRecebida);
			status = false;
		}

		/*
		 * se a carta for um Escolhe Cor, ela pode ser jogada a qualquer momento, exceto
		 * se houver acúmulo
		 */
		else if (aRecebida == Carta.TROCACOR && temAcumulo() == false) {
			this.descarte.receberCarta(recebida);
			status = false;
		}

		/*
		 * se a carta for numérica, e tiver o mesmo número da última carta vigente, ela
		 * pode ser jogada
		 */
		else if (nRecebida != -1 && nRecebida == nUltima) {
			this.descarte.receberCarta(recebida);
			status = true;
		}

		/*
		 * se a carta tiver cor, e tiver a mesma cor da última carta vigente, ela pode
		 * ser jogada
		 */
		else if (cRecebida != Cor.SEMCOR && cRecebida == cUltima) {
			this.descarte.receberCarta(recebida);
			status = true;
			if (aRecebida == Carta.MAIS2) {
				acumulo.add(aRecebida);
			}
		}

		/*
		 * se a carta possuir ação, e tiver a mesma ação da última carta vigente, ela
		 * pode ser jogada
		 */
		else if (aRecebida != null && aRecebida == aUltima) {
			this.descarte.receberCarta(recebida);
			status = true;
			if (aRecebida == Carta.MAIS2) {
				acumulo.add(aRecebida);
			}
		}

		/*
		 * se a carta não for compatível, uma exceção deev ser lançada para que o
		 * jogador tome alguma providência
		 */
		else {
			throw new JogadaImpossivel("Não foi possível realizar a jogada");
		}

		/*
		 * Se alguma carta foi jogada, podemos zerar a cor temporária, e informamos que
		 * esta ação de jogar a carta foi realizada com sucesso
		 */
		if (recebida instanceof CartaEspecialSemCor) {
			status = false;
		}
		if (status) {
			corEscolhida = null;
			LOGGER.trace("A carta {} foi descartada", recebida);
		}

	}

	/**
	 * Compra uma carta do baralho de compra. Caso o baralho de compra possua
	 * nenhuma carta, realizamos a ação de "transformar" o barralho de descarte no
	 * de compra
	 * 
	 * @return Primeira carta do monte de compra.
	 */
	public Carta entregarCarta() {
		Carta cartaAux;
		cartaAux = this.compra.comprarCarta();
		LOGGER.trace("Entregando carta {}", cartaAux);
		return cartaAux;
	}

	/**
	 * Transforma o baralho de descarte no baralho de compra, deixando apenas a
	 * última carta,que é a carta determinante da próxima jogada.
	 */
	public void transformaDescarte() {
		LOGGER.info("Transformando monte de descarte em monte de compra");
		for (int i = 0; i < this.descarte.getCartas().size() - 2; i++) {
			this.compra.receberCarta(this.descarte.comprarCarta());
		}
		this.compra.embaralhar();
	}

	/**
	 * Altera o sentido do jogo(horário e anti-horário).
	 */
	public void inverter() {
		this.sentido *= -1;
		LOGGER.trace("Sentido invertido: {}", this.sentido);
	}

	/**
	 * Aumenta o incremento de posição, ou seja, ao invés de passar para a próxima
	 * posição, +1, ele passa para a próxima da próxima, +2. Ao selecionar o próximo
	 * jogador, esse valor é alterado para seu valor original, mantendo o fluxo
	 * comum do jogo.
	 */
	public void pular() {
		this.sentido *= 2;
	}

	/**
	 * Determina qual é o jogador responsavel por jogar no turno.
	 * 
	 * @param jogadorRecebido Jogador
	 * @return Qual jogador que deve realizar a jogada
	 */
	public Jogador jogadorDaVez() {
		LOGGER.trace("O jogador da vez é {}", this.jogadores.get(posicaoAtual));
		for (int i = 0; i < this.jogadores.size(); i++) {
			if (i == posicaoAtual) {
				LOGGER.info("-> {}", this.jogadores.get(i).getNome());
			} else {
				LOGGER.info("   {}", this.jogadores.get(i).getNome());
			}
		}

		return this.jogadores.get(posicaoAtual);
	}

	/**
	 * Determina qual é o jogador responsavel por jogar na próxima rodada.
	 */
	public void proximoJogador() {
		posicaoAtual = this.posicaoAtual + this.sentido;
		if (posicaoAtual < 0) {
			posicaoAtual += (jogadores.size());
		} else if (posicaoAtual >= jogadores.size()) {
			posicaoAtual -= (jogadores.size());
		}
		if (Math.abs(sentido) % 2 == 0) {/* caso um jogador tenha sido pulado anteriormente... */
			LOGGER.trace("Um foi jogador pulado");
			this.sentido /= 2;/*
								 * ...retorna o incremento ao seu valor original para voltar o jogo ao fluxo
								 * comum
								 */
		}
	}

	/**
	 * @param Cor determinada pelo jogador, recebida através da ação Recebe uma cor
	 *            e a armazena temporariamente (até que alguma carta seja descartada
	 *            ou comprada)
	 */
	public void mudarCor(Cor recebida) {
		corEscolhida = recebida;
		LOGGER.info("A cor escolhida foi: {}", corEscolhida);
	}

	/**
	 * @return Ultima carta do baralho de descarte.
	 */
	public Carta getUltimaCarta() {
		Carta cartaAux = this.descarte.ultimaCarta();
		if (this.corEscolhida == null) {
			LOGGER.trace("A ultima carta jogada foi {}", cartaAux);
			return cartaAux;
		} else {
			LOGGER.info("A ultima carta jogada foi {} e a cor escolhida foi{}", cartaAux, corEscolhida);
			cartaAux.setCor(corEscolhida);
			return cartaAux;
		}
	}

	/**
	 * Compra um numero de cartas e entrega a um jogador. Foi criada com o intuito
	 * de faciliar quando houver a necessidade de comprar cartas acumuladas, e que
	 * devem ser atribuídas a um jogador específico.Quando a quantidade de cartas a
	 * serem compradas for superior há disponível, transforma o descarte em compra.
	 * Caso ainda não seja possível, executa o mínimo possível e passa para o
	 * próximo
	 * 
	 * @param qtd     Quantidade de cartas a serem compradas.
	 * @param jogador Jogador que vai comprar.
	 */
	public void comprar(int qtd, Jogador jogador) {
		LOGGER.trace("Comprando {} carta(s) ao jogador {}", qtd, jogador);
		if (this.compra.getCartas().size() < qtd) {
			this.transformaDescarte();
		}
		if (this.compra.getCartas().size() < qtd) {
			LOGGER.trace("Só foi possível comprar {} cartas", this.compra.getCartas().size());
			for (int i = 0; i < this.compra.getCartas().size(); i++) {
				jogador.comprar(entregarCarta());
			}
		} else {
			for (int i = 0; i < qtd; i++) {
				jogador.comprar(entregarCarta());
			}
			LOGGER.trace("O jogador {} comprou {} cartas", jogador, qtd);
		}
	}

	/**
	 * salva o acumulo em um ArrayList auxiliar, limpa o acumulo, e retorna o
	 * auxiliar que contém as ações que devem ser executadas
	 * 
	 * @return Acumulo de ações que devem ser executadas
	 */
	public ArrayList<Acao> desacumular() {
		ArrayList<Acao> acumuloAux = this.acumulo;
		this.acumulo.clear();
		LOGGER.trace("O acúmulo foi transferido");
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
