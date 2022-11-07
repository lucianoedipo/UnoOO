package base.jogador;

import java.util.ArrayList;

import acao.Acao;
import base.Jogo;
import base.Roda;
import cartas.Carta;

public interface Jogada {

	// Descrição Antiga >>> jogada de compra: caso o jogador não detiver de nenhuma
	// carta que combine com a que está na
	// mesa, ou outro jogador anterior lançar uma das cartas especiais (+2 ou +4),
	// deve ser retirado uma
	// carta do baralho de compra e adicionada ao baralho do jogador em questão.

	/**
	 *
	 * Compra uma lista de cartas, adicionando-as a maoJogador
	 * 
	 * @see MaoCartas
	 **/
	public void comprar(ArrayList<Carta> listaCartas);

	/**
	 *
	 * Compra uma carta, adigitcionando uma carta a maoJogador,
	 * 
	 * @see MaoCartas
	 **/
	public void comprar(Carta carta);

	// Descrição Antiga >>> jogada de descarte: caso o jogador detiver de alguma
	// carta que combine com a que foi jogada
	// anteriormente, a carta deve ser selecionada e retirada do baralho do jogador
	// e colocada no baralho
	// de descarte, caso o jogador detiver de mais uma carta que combine com a da
	// mesa, ele deve
	// selecionar um das duas.
	/**
	 *
	 * Descarta uma carta, retirando uma carta de maoJogador, adicionando-a ao monte
	 * de descarte
	 * 
	 * @see MaoCartas
	 **/
	public void descartar();

	/**
	 * Realiza uma jogada a partir da analize da situação atual da Roda do Jogo.
	 * 
	 * @see Roda
	 * @see Jogo
	 * @see Acao
	 */
	public void realizarJogada();

}