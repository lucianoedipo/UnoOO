/**
 * 
 */
package base;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.jogador.Jogador;
import cartas.Carta;

/**
 * @author Luciano
 *
 */
public class SimulUnoOO {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulUnoOO.class);
	private static ArrayList<String> nomes = new ArrayList<String>(
			Arrays.asList("Astra", "Halley", "Luna", "Lyra", "Stella", "Atlas", "Cosmos", "Rigel", "Aster", "Vulcano",
					"Andoria", "Bajor", "Kaminar", "Midos", "Pacifica", "Kronos", "Risa"));
	private static ArrayList<Jogador> jogadores;
	private static Jogo partida;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LOGGER.info("Criando jogadores");

		criarJogadores(2);
		partida = new Jogo(jogadores);
		System.out.println("Iniciando Partida!");
		partida.run();

	}

	public static void testVerificarJogadores() {
		for (Jogador j : Jogo.roda.getJogadores()) {
			LOGGER.info("{} tem {} Cartas:", j.getNome(), j.getQuantidadeCartas());
			for (Carta c : j.getMaoJogador().getCartas()) {
				System.out.println(c);
			}
		}
	}

	public static void testPrepararRoda() {
		Baralho b = Jogo.roda.getCompra();
		for (Carta c : b.getCartas()) {
			System.out.println(c);
		}
		LOGGER.info("Há {} no baralho de compras da roda", b.getCartas().size());
		LOGGER.info("Carta Inicial: {}", Jogo.roda.getUltimaCarta());
	}

	public static void criarJogadores(int nJogadores) {
		String nome;
		jogadores = new ArrayList<>();
		for (int i = 0; i < nJogadores; i++) {
			int rando = (int) ((Math.random() * nomes.size()));
			nome = nomes.remove(rando);
			LOGGER.info("Nome escolhido para jogador {}: {}", i, nome);
			jogadores.add(new Jogador(nome));

		}
	}

}
