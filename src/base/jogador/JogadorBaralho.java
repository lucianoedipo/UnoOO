package base.jogador;

import java.util.ArrayList;

import acao.Acao;
import base.Jogo;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.CartaSemAcao;
import cartas.Cor;

/**
 * Subclasse da superclasse 'Jogador' que representa o jogador criado pelo grupo
 * baralho
 * 
 * @author Guilherme Bispo Cupertino e Jecelen Adriane Campos. Grupo Baralho.
 * 
 */
public class JogadorBaralho extends Jogador {
	/**
	 * Método construtor que recebe como parâmetro o nome do jogador que representa
	 * o grupo baralho e inicializa a mao do jogador.
	 * 
	 * @param nome
	 */
	public JogadorBaralho(String nome) {
		super(nome);
		this.inicializarMao();
		LOGGER.info("JogadorBaralho criado com sucesso\n");
	}

	public Cor maisCor() {
		int quantidadePorCor[] = new int[4];
		quantidadePorCor[0] = quantidadePorCor[1] = quantidadePorCor[2] = quantidadePorCor[3] = 0;
		ArrayList<Carta> conta;
		conta = this.maoJogador.getCartas();

		for (int j = 0; j < conta.size(); j++) { // Irá contar a quantidade de cartas que o jogador possui na mão.
			switch (conta.get(j).getCor()) {
			case AMARELO:
				quantidadePorCor[0] = quantidadePorCor[0] + 1;
				break;
			case AZUL:
				quantidadePorCor[1] = quantidadePorCor[1] + 1;
				break;
			case VERDE:
				quantidadePorCor[2] = quantidadePorCor[2] + 1;
				break;
			case VERMELHO:
				quantidadePorCor[3] = quantidadePorCor[3] + 1;
				break;
			default:
				break;
			}
		}
		Cor maiorCor;
		int mais = quantidadePorCor[0];
		int i, ind_i = 0;
		for (i = 1; i < 4; i++) {
			if (quantidadePorCor[i] > mais) {
				mais = quantidadePorCor[i];
				ind_i = i;
			}
		}
		if (ind_i == 1)
			maiorCor = Cor.AZUL;

		else if (ind_i == 2)
			maiorCor = Cor.VERDE;

		else if (ind_i == 3)
			maiorCor = Cor.VERMELHO;

		else
			maiorCor = Cor.AMARELO;

		return maiorCor;
	}

	/**
	 * Método que retorna qual a cor que o jogador mais possui em sua mão.
	 * 
	 * @param quantidadePorCor[] e maiorCor
	 * @return maiorCor
	 */

	public Carta defineCartaDaJogada() {

		Carta ultCarta = Jogo.roda.getUltimaCarta();

		ArrayList<Jogador> jogadores = Jogo.roda.getJogadores();

		// Caso o jogador possua menos que 5 ou exatamente 5 cartas.
		if (jogadores.get(Jogo.roda.getPosicaoAtual() + 1).getQuantidadeCartas() <= 5) {
			// Irá jogando as Cartas Especiais com Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialComCor))
					continue;

				CartaEspecialComCor cEspecCor = (CartaEspecialComCor) cartas;

				if (cEspecCor.getCor() == ultCarta.getCor() || (ultCarta instanceof CartaEspecialComCor
						&& cEspecCor.getAcao() == ((CartaEspecialComCor) ultCarta).getAcao()))
					return cEspecCor;

			}

			// Depois joga as suas cartas normais
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaNormal))
					continue;

				CartaNormal cNormal = (CartaNormal) cartas;

				if (cNormal.getCor() == ultCarta.getCor())
					return cartas;

				if (ultCarta instanceof CartaNormal && ((CartaNormal) ultCarta).getNumero() == cNormal.getNumero())
					return cartas;
			}

			// E por fim utiliza as Cartas especiais sem Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialSemCor))
					continue;

				return cartas;
			}
		}

		// Caso o jogador possua 3 Cartas ou menos.
		else if (jogadores.get(Jogo.roda.getPosicaoAtual() + 1).getQuantidadeCartas() <= 3) {
			// Caso o jogador esteja com menos do que 3 cartas ele irá jogar primeiro as
			// suas cartas normais.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaNormal))
					continue;

				CartaNormal cNormal = (CartaNormal) cartas;

				if (cNormal.getCor() == ultCarta.getCor())
					return cartas;

				if (ultCarta instanceof CartaNormal && ((CartaNormal) ultCarta).getNumero() == cNormal.getNumero())
					return cartas;
			}
			// Logo após irá jogar as Cartas Especiais com Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialComCor))
					continue;

				CartaEspecialComCor cEspecCor = (CartaEspecialComCor) cartas;

				if (cEspecCor.getCor() == ultCarta.getCor() || (ultCarta instanceof CartaEspecialComCor
						&& cEspecCor.getAcao() == ((CartaEspecialComCor) ultCarta).getAcao()))
					return cEspecCor;
			}

			// Prioriza guardar as Cartas especiais sem Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialSemCor))
					continue;

				return cartas;
			}

		}

		else if (jogadores.get(Jogo.roda.getPosicaoAtual() + 1).getQuantidadeCartas() == 1) {
			LOGGER.info("O JogadorBaralho gritou UNO\n");
			// Prioriza usar as Cartas especiais sem Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialSemCor))
					continue;

				return cartas;
			}
			// Logo após irá jogar as Cartas Especiais com Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialComCor))
					continue;

				CartaEspecialComCor cEspecCor = (CartaEspecialComCor) cartas;

				if (cEspecCor.getCor() == ultCarta.getCor() || (ultCarta instanceof CartaEspecialComCor
						&& cEspecCor.getAcao() == ((CartaEspecialComCor) ultCarta).getAcao()))
					return cEspecCor;
			}
			// Caso o jogador possua apenas 1 carta ele irá utilizar as cartas normais em
			// último caso.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaNormal))
					continue;

				CartaNormal cNormal = (CartaNormal) cartas;

				if (cNormal.getCor() == ultCarta.getCor())
					return cartas;

				if (ultCarta instanceof CartaNormal && ((CartaNormal) ultCarta).getNumero() == cNormal.getNumero())
					return cartas;
			}
		}
		// Caso o jogador possua mais que 5 cartas.
		else {
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaNormal))
					continue;

				CartaNormal cNormal = (CartaNormal) cartas;

				if (cNormal.getCor() == ultCarta.getCor())
					return cartas;

				if (ultCarta instanceof CartaNormal && ((CartaNormal) ultCarta).getNumero() == cNormal.getNumero())
					return cartas;
			}
			// Logo após irá jogar as Cartas Especiais com Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialComCor))
					continue;

				CartaEspecialComCor cEspecCor = (CartaEspecialComCor) cartas;

				if (cEspecCor.getCor() == ultCarta.getCor() || (ultCarta instanceof CartaEspecialComCor
						&& cEspecCor.getAcao() == ((CartaEspecialComCor) ultCarta).getAcao()))
					return cEspecCor;
			}

			// Prioriza guardar as Cartas especiais sem Cor.
			for (Carta cartas : this.getMaoJogador().getCartas()) {
				if (!(cartas instanceof CartaEspecialSemCor))
					continue;

				return cartas;
			}
		}

		// caso nenhuma carta for jogadar, terá de comprar.
		return null;
	}

	/**
	 * Método para se definir que carta será jogada, podendo variar a carta de
	 * acordo com a situação ocorrida.
	 * 
	 * @return irá retornar a carta que será melhor designada, para cada situação de
	 *         jogo.
	 */

	public Jogada realizarJogada() {
		LOGGER.trace("Jogador {} realizando jogada", this.getNome());
		Jogada jogadaRealizada = null;
		Carta carta = null;
		if (Jogo.roda.temAcumulo()) {
			try {
				carta = defineCartaParaAcumulo(Jogo.roda.getUltimaCarta().getAcao());
			} catch (CartaSemAcao e) {
				LOGGER.error("Erro ao tentar definir carta de acúmulo: {}", e);
			}

			if (carta == null) {
				LOGGER.trace("Jogador {} comprando acúmulo", this.getNome());
				comprarCartasAcumuladas(Jogo.roda.desacumular());
				jogadaRealizada = Jogada.COMPRAR_ACUMULADO;
				LOGGER.info("Jogador {} comprou acúmulo. Ficou com {} cartas", this.getNome(),
						this.getQuantidadeCartas());

			} else {
				descartar(carta);
				LOGGER.info("Jogador {} descartou {} para o acúmulo", this.getNome(), carta.toString());
				jogadaRealizada = Jogada.DESCARTAR;
			}
		} else {
			carta = defineCartaDaJogada();

			if (carta != null) {
				if (carta instanceof CartaComAcao) {
					try {
						Acao acaoCarta = carta.getAcao();
						realizarAcaoDaCarta(acaoCarta);
					} catch (CartaSemAcao e) {
						LOGGER.error("ERRO: Carta não possui acao!");
					}
				}
				descartar(carta);
				jogadaRealizada = Jogada.DESCARTAR;

			} else {
				LOGGER.info("Jogador {} precisou comprar uma carta", this.getNome());
				Jogo.roda.comprar(1, this);
				jogadaRealizada = Jogada.COMPRAR;
			}
		}
		LOGGER.info("Jogada do JogadorBaralho realizada\n");
		return jogadaRealizada;

	}

	/**
	 * Retorna a jogada a ser realizada pelo JogadorBaralho com base na última carta
	 * jogada no monte de descarte ou acumulo de cartas e as cartas na mão do
	 * jogador
	 * 
	 * @return a jogada do JogadorBaralho.
	 */

	public Cor sorteiaCor() {
		Cor corSorteada = maisCor();
		LOGGER.trace("A cor escolhida do JogadorBaralho foi {}", corSorteada);
		return corSorteada;
	}
	/**
	 * Método que retorna a cor que o jogador escolheu (que é a cor que ele mais
	 * possui).
	 * 
	 * @return corSorteada
	 */
}
