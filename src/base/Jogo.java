//UnoOO
//Disciplina de LPOO 2022.2

package base;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.jogador.Jogador;

/**
 * @author Autores: Daniel Schutz, Felipe Pellissari, Fernanda Pessoa e José
 *         Lucas. Classe destinada a inicializar a partida e verificar seu
 *         término, bem como preparar o jogo - instanciando o baralho inicial,
 *         jogadores e passando estes por parâmero a classe 'Roda'
 */
public class Jogo {
	private static final Logger LOGGER = LoggerFactory.getLogger(Jogo.class);
	private Baralho baralho;
	private ArrayList<Jogador> jogadores = new ArrayList<>();
	public static Roda roda;
	private Jogador jogadorAtual;

	/**
	 * Prepara o jogo, instanciando o baralho e a roda
	 * 
	 * @see Baralho.java
	 * @see Roda.java
	 */
	public void prepararJogo() {
		/**
		 * Inicia o baralho
		 */
		this.baralho = new Baralho(Baralho.INICIAL);

		/**
		 * instanciar a roda e mandar baralho e lista de jogadores
		 */
		LOGGER.info("Instanciando a roda");
		roda = new Roda(this.baralho, this.jogadores);
	}

	/**
	 * Verifica se a quantida de cartas na mão do jogador é igual a 0 e define o
	 * ganhador se for o caso
	 * 
	 * @see Mao.java
	 * @return tipo booleano: caso falso ele encerra o jogo, pois o jogador já não
	 *         possui mais cartas e verdadeiro o jogo continua
	 */
	public boolean confereFim(Jogador jogadorAtual) {
		if (jogadorAtual.getQuantidadeCartas() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * função que faz a execução do jogo descreve o turno em si, com a troca de
	 * jogador e jogada em sua última ação confere se a quantidade de cartas na mão
	 * do jogador é diferente de zero Logger retorna as ações realizadas peo Jogador
	 * 
	 * @see Jogador.java
	 * @see Roda.java
	 */
	public void run() {
		int qtdRodadas = 0;
		while (true) {

			/**
			 * define o próximo jogador
			 */
			LOGGER.trace("Alterando para próximo jogador");
			jogadorAtual = roda.jogadorDaVez();
			/**
			 * Jogador atual realiza a jogada (entra em contato com a roda para ver se há
			 * acumulo e decide se compra, joga ou executa as ações acumuladas
			 * 
			 * @see Jogador.java
			 */
			LOGGER.trace("{} realizando jogada", jogadorAtual);
			jogadorAtual.realizarJogada();
			System.out.println("Rodada:"+qtdRodadas++);

			LOGGER.trace("Conferindo se acabou as cartas na mão do jogador");
			if (confereFim(jogadorAtual) == false) {
				System.out.printf("O jogador %S ganhou.",
						jogadorAtual.getNome()); /* printar o jogador que ficou sem cartas na mão */
				break;
			}

		}
	}

	/**
	 * Método toString()
	 */
	@Override
	public String toString() {
		return String.format("O jogador %s ganhou\n", jogadorAtual.getNome());
	}

	/**
	 * construtor da classe Jogo, chama os jogadores e prepara o jogo conforme a
	 * função prepararJogo
	 * 
	 * @see Jogo.java
	 */
	public Jogo(ArrayList<Jogador> j) {
		this.jogadores = j;
		LOGGER.info("Preparando o jogo");
		prepararJogo();

	}
}
