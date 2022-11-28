package base.jogador;

import acao.Acao;
import base.Jogo;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaComCor;
import cartas.CartaEspecialComCor;
import cartas.CartaEspecialSemCor;
import cartas.CartaNormal;
import cartas.CartaSemAcao;
import cartas.Cor;
/**
 * SEM DESCR
 * Não realizou override de realizarJogada()
 * @author lucia
 *
 */
public class JogadorAcao extends Jogador {
	/**
	 * Bot jogador, subclasse de jogador, implementado pelo grupo de Ação
	 * 
	 * @author Grupo Ação
	 * @since 23/11/2022
	 * @version 3.0
	 */

	/**
	 * Método Contrutor para o bot
	 * 
	 * @param nome
	 */
	public JogadorAcao(String nome) {
		super(nome);
		this.inicializarMao();
		LOGGER.info("JogadorRoda criado com sucesso\n");
	}

	/**
	 * Método que cria uma lista com a frequencia das cores Onde a posição da lista
	 * representa uma cor diferente
	 * 
	 * @return
	 */
	public int[] freqCor() {
		int[] lsCor = new int[4];
		lsCor[0] = 0;
		lsCor[1] = 0;
		lsCor[2] = 0;
		lsCor[3] = 0;
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (c instanceof CartaComCor) {
				if (c.getCor() == Cor.AMARELO) {
					lsCor[0]++;
				} else if (c.getCor() == Cor.AZUL) {
					lsCor[1]++;
				} else if (c.getCor() == Cor.VERDE) {
					lsCor[2]++;
				} else if (c.getCor() == Cor.VERMELHO) {
					lsCor[3]++;
				}
			}
		}
		return lsCor;
	}

	/**
	 * O método serve para encontrar a cor com maior frequencia na lista para que o
	 * jogador sempre possa jogar a cor de maior frequencia no jogo quando o jogador
	 * descobrir a cor de maior repetição ele ira deletar essa cor da lista, para
	 * que não haja problemas em consultas repetidas ou algum possivel loop
	 * 
	 * @param lsCor
	 * @return
	 */
	public Cor maiorCor(int[] lsCor) {
		lsCor = new int[4];
		int big = 0;
		Cor cartasCor = null;
		if (big < lsCor[0]) {
			big = lsCor[0];
			cartasCor = Cor.AMARELO;
		} else if (big < lsCor[1]) {
			big = lsCor[1];
			cartasCor = Cor.AZUL;
		} else if (big < lsCor[2]) {
			big = lsCor[2];
			cartasCor = Cor.VERMELHO;
		} else if (big < lsCor[3]) {
			big = lsCor[3];
			cartasCor = Cor.VERDE;
		}
		// descoberta a maior cor
		if (cartasCor == Cor.AMARELO) {
			lsCor[0] = 0;
		} else if (cartasCor == Cor.AZUL) {
			lsCor[1] = 0;
		} else if (cartasCor == Cor.VERMELHO) {
			lsCor[2] = 0;
		} else if (cartasCor == Cor.VERDE) {
			lsCor[3] = 0;
		}
		return cartasCor;
	}

	/**
	 * Método que procura uma carta de compra para jogar, caso a carta que criou
	 * acumulo seja uma CECC o jogador dara prioridade a jogar uma CECC com sua
	 * maior cor disponivel e valida para a jogada
	 */
	public Carta defineCartaParaAcumulo(Acao acaoDoAcumulo) {
		int[] lsCor = freqCor();
		Cor mCor;
		if (Jogo.roda.getUltimaCarta() instanceof CartaEspecialSemCor) {
			for (Carta c : this.getMaoJogador().getCartas()) {
				if (c instanceof CartaComAcao) {
					try {
						if (c.getAcao() == acaoDoAcumulo)
							return c;
					} catch (CartaSemAcao e) {
						LOGGER.error("Erro ao tentar comparar ação de carta com ação de acúmulo: {}", e);
					}
				}
			}
		} else {
			for (Carta c : this.getMaoJogador().getCartas()) {
				for (int cont = 0; cont < 4; cont++) {
					mCor = maiorCor(lsCor);
					if (c instanceof CartaComAcao && c.getCor() == mCor) {
						try {
							if (c.getAcao() == acaoDoAcumulo)
								return c;
						} catch (CartaSemAcao e) {
							LOGGER.error("Erro ao tentar comparar ação de carta com ação de acúmulo: {}", e);
						}
					}
				}
			}
		}
		return null;
	}
	/*
	 * 1 - se só 2 cartas na mão priorizar carta sem cor; (feito) 2 - Cartas sem
	 * cores possuem a menor prioridade; (feito) 3 - Jogar as cartas da cor que o
	 * bot mais possue na mão; (feito)
	 */

	/**
	 * O método serve para o jogador ter uma tomada de decisão ao arremessar uma
	 * carta, onde ele dara maior prioridade a cartas normais, que ele tenha mais
	 * cores disponiveis para jogar, caso ele não encontre ele procura alguma carta
	 * de cor de maior frequencia, caso ele não encontre ele ira procurar a alguma
	 * CESC caso encontre ele a jogara, se jogador tiver duas cartas em sua mão ele
	 * dara uma prioridade maior a cartas CESC para ele poder descarta-las e mudar a
	 * cor do jogo ao seu favor
	 */
	public Carta defineCartaDaJogada() {
		Carta ultimo = Jogo.roda.getUltimaCarta();
		int[] lfreqCor = freqCor();
		Cor corFreq;
		Cor corjogo;
		int cont;
		/*
		 * Correção para problema de leitura de cor onde logo no inicio do algoritmo o
		 * bot já descobre se a ultima carta lançada for uma CESC caso seja o bot vai
		 * usar a função get cor escolhida de roda para conseguir a cor escolhida pelo
		 * ultimo jogador e evitar erros
		 */
		corjogo = ultimo.getCor();
		if (ultimo instanceof CartaEspecialSemCor) {
			corjogo = Jogo.roda.getCorEscolhida();
		}
		/*
		 * Verificar se jogador esta no fim da mão caso sim o jogador vai verificar se
		 * sua ultima carta
		 */
		if (this.getQuantidadeCartas() == 2) {
			for (Carta c : this.getMaoJogador().getCartas()) {
				if (c instanceof CartaEspecialSemCor) {
					LOGGER.trace("JogadorRoda gritou Uno\n");
					return c;
				}
			}
			for (cont = 0; cont < 4; cont++) {
				corFreq = maiorCor(lfreqCor);
				for (Carta c : this.getMaoJogador().getCartas()) {
					if (c.getCor() == corjogo) {
						if (!(c instanceof CartaNormal))
							continue;
						CartaNormal cn = (CartaNormal) c;
						if (cn.getCor() == corjogo) {
							LOGGER.trace("JogadorRoda gritou Uno\n");
							return c;
						}
						if (ultimo instanceof CartaNormal && ((CartaNormal) ultimo).getNumero() == cn.getNumero()) {
							LOGGER.trace("JogadorRoda gritou Uno\n");
							return c;
						}
					}
				}
				for (cont = 0; cont < 4; cont++) {
					corFreq = maiorCor(lfreqCor);
					for (Carta c : this.getMaoJogador().getCartas()) {
						if (c instanceof CartaEspecialComCor) {
							if (c.getCor() == corFreq) {
								if (c.getCor() == corjogo || ultimo instanceof CartaEspecialComCor) {
									LOGGER.trace("JogadorRoda gritou Uno\n");
									return c;
								}
							}
						}
					}
				}
			}
		}
		/*
		 * Jogada padrão do jogador caso ele não tenha poucas cartas ele realizara essa
		 * ação que dara prioridade crescente para descartas cartas especiais sem cor,
		 * especiais com cor e normais caso ele jogue alguma carta diferente de CESC ele
		 * dara prioriadade a jogar suas cores de maior frequencaia
		 */
		else {
			for (cont = 0; cont < 4; cont++) {
				corFreq = maiorCor(lfreqCor);
				for (Carta c : this.getMaoJogador().getCartas()) {
					if (c.getCor() == corjogo) {
						if (!(c instanceof CartaNormal))
							continue;

						CartaNormal cn = (CartaNormal) c;
						if (cn.getCor() == corjogo)
							return c;
						if (ultimo instanceof CartaNormal && ((CartaNormal) ultimo).getNumero() == cn.getNumero())
							return c;
					}
				}
				for (cont = 0; cont < 4; cont++) {
					corFreq = maiorCor(lfreqCor);
					for (Carta c : this.getMaoJogador().getCartas()) {
						if (c instanceof CartaEspecialComCor) {
							if (c.getCor() == corFreq) {
								if (c.getCor() == corjogo || ultimo instanceof CartaEspecialComCor) {
									return c;
								}
							}
						}
					}
				}
				for (Carta c : this.getMaoJogador().getCartas()) {
					if (c instanceof CartaEspecialSemCor) {
						return c;
					}
				}
			}
		}
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (!(c instanceof CartaNormal))
				continue;
			CartaNormal cn = (CartaNormal) c;
			if (cn.getCor() == corjogo)
				return c;
			if (ultimo instanceof CartaNormal && ((CartaNormal) ultimo).getNumero() == cn.getNumero())
				return c;
		}
		for (Carta c : this.getMaoJogador().getCartas()) {
			if (!(c instanceof CartaEspecialSemCor))
				continue;
			return c;
		}
		LOGGER.trace("JogadorRoda não encontrou carta adequada\n");
		return null;
	}

	// Método de sorteio inteligente onde jogador ira sortear uma cor sua que mais
	// se repete no jogo
	public Cor sorteiaCor() {
		int[] lst;
		lst = freqCor();
		Cor maiorCor;
		maiorCor = maiorCor(lst);
		LOGGER.trace("JogadorRoda escolhendo cor {}\n", maiorCor);
		return maiorCor;
	}
}
// defineCartaDaJogada();
// escolhaCor();