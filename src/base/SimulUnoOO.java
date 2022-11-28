/**
 * 
 */
package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.jogador.Jogador;
import base.jogador.JogadorAcao;
import base.jogador.JogadorBaralho;
import base.jogador.JogadorCarta;
import base.jogador.JogadorJogador;
import base.jogador.JogadorJogo;
import base.jogador.JogadorRoda;

/**
 * Classe main de simulação
 * 
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
	private static Dictionary<String, Integer> contagem = new Hashtable<String, Integer>();

	/**
	 * Realiza args[0] quantidade de simulações, se omitido realiza 1
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		contagem.put("Baralho", 0);
		contagem.put("Jogador", 0);
		contagem.put("Roda", 0);
		contagem.put("Ação", 0);
		contagem.put("Carta", 0);
		contagem.put("Jogo", 0);

		int i = 1;
		try {
			i = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Sem argumento: Simulando com 1000");
		} finally {
			simular(i);
			System.out.println(contagem);
		}

	}

	/**
	 * Realiza n simulações
	 * 
	 * @param n quantidade de smulações
	 */
	private static void simular(int n) {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++) {
			String ganhador = "ERROR";
			int qg = 0;
			criarJogadores();
			partida = new Jogo(jogadores);
			try {
				ganhador = partida.run();
			} finally {
				qg = contagem.get(ganhador);
				qg++;
				contagem.put(ganhador, qg);
			}
		}
	}

	/**
	 * SEM PARAM:Cria jogadores especializados
	 */
	private static void criarJogadores() {
		// TODO Auto-generated method stub
		jogadores = new ArrayList<>();
		jogadores.add(new JogadorBaralho("Baralho"));
		jogadores.add(new JogadorJogador("Jogador"));
		jogadores.add(new JogadorRoda("Roda"));
		jogadores.add(new JogadorAcao("Ação"));// Não Implementou reliazarJogada()
		jogadores.add(new JogadorCarta("Carta"));
		jogadores.add(new JogadorJogo("Jogo"));
	}

	/**
	 * COM PARAM cria nJogadores base
	 * 
	 * @param nJogadores
	 */
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
