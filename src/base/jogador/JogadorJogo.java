package base.jogador;

import acao.Acao;
import base.Jogo;
import base.Roda;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaComCor;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.CartaSemAcao;
import cartas.Cor;

/**
 * SEM DESCR COM WARN
 * 
 * @author lucia
 *
 */
public class JogadorJogo extends Jogador {

	public static Roda rodaJogador;

	public JogadorJogo(String nome) {
		super(nome);
		this.inicializarMao();
	}

	public Cor maiorQuant()/* escolhe a cor que tem mais cartas desssa cor na mão do jogador */ {
		int[] quantCor = new int[4];
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (c instanceof CartaComCor) {
				if (c.getCor() == Cor.AMARELO) {
					quantCor[0]++;
				}

				else if (c.getCor() == Cor.AZUL) {
					quantCor[1]++;
				}

				else if (c.getCor() == Cor.VERDE) {
					quantCor[2]++;
				}

				else if (c.getCor() == Cor.VERMELHO) {
					quantCor[3]++;
				}
			}
		}
		int maiorQuant = quantCor[0];
		Cor maiorQuantCor = Cor.AMARELO;
		for (int i = 1; i < 3; i++) {
			if (quantCor[i] > maiorQuant) {
				maiorQuant = quantCor[i];
				if (i == 1) {
					maiorQuantCor = Cor.AZUL;
				} else if (i == 2) {
					maiorQuantCor = Cor.VERDE;
				} else if (i == 3) {
					maiorQuantCor = Cor.VERMELHO;
				}

			}
		}

		return maiorQuantCor;
	}

	public Cor sorteiaCor() {
		Cor corSorteada = maiorQuant();
		LOGGER.trace("Cor escolhida: {}", corSorteada);
		return corSorteada;
		// escolhe a cor que a pessoa possui mais cartas
	}

	/**
	 * SEM DESC
	 * VIOLA REGRA
	 */
	protected Carta defineCartaDaJogada() {
		Carta ultimo = Jogo.roda.getUltimaCarta();
		/*
		 * Sequência de uso das cartas:
		 * 
		 */

		// rodaJogador.proximoJogador(); VIOLAÇÃO
		// proximoJogador = rodaJogador.jogadorDaVez();
		if (5 < 4) {
			// se o proximo jogador tiver menos de 4 cartas na mão, dar prioridade a
			// escolher carta de ação

			// Busca +4 e troca cor
			for (Carta c : this.getMaoJogador().getCartas()) {
				if (!(c instanceof CartaEspecialSemCor))
					continue;

				// +4 e troca cor pode ser jogado de qualquer forma
				return c;
			}

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

			// Se não conseguir jogar nenhuma tem que comprar
			return null;
			// return this.defineCartaDaJogada();

		}

		else {
			// se o próximo jogador tiver mais de 3 cartas na mão, seguir a ordem normal da
			// escolha das cartas

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

			// Busca +4 e troca cor
			for (Carta c : this.getMaoJogador().getCartas()) {
				if (!(c instanceof CartaEspecialSemCor))
					continue;

				// +4 e troca cor pode ser jogado de qualquer forma
				return c;
			}

			// Se não conseguir jogar nenhuma tem que comprar
			return null;
			// return this.defineCartaDaJogada();
		}

	}

	public Jogada realizarJogada() {
		LOGGER.trace("Jogador {} realizando jogada", this.getNome());
		Carta carta = null;
		Jogada jogadaRealizada = null;
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
		return jogadaRealizada;
	}

}
