package base.jogador;

import java.util.ArrayList;

import acao.Acao;
import base.Jogo;
import cartas.Carta;
import cartas.CartaComAcao;
import cartas.CartaSemAcao;
import cartas.CartaSemNumero;
import cartas.Cor;

/**
 * SEM DESC
 * 
 * @author lucia
 *
 */
public class JogadorRoda extends Jogador {

	private ArrayList<Carta> verificar;

	private ArrayList<Carta> candidatas = new ArrayList<>();

	private Carta temp;

	int cores[] = new int[4];// posicação 0 = qtd cartas da cor amarelo, 1 da azul, 2 da verde, 3 da vermelha

	public JogadorRoda(String nome) {
		super(nome);
	}

	@Override
	public Jogada realizarJogada() {
		LOGGER.trace("Jogador {} realizando jogada", this.getNome());
		Carta carta = null;
		Jogada jogadaRealizada = null;
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
			if (Jogo.roda.temAcumulo()) {
				LOGGER.trace("Jogador {} comprando acúmulo", this.getNome());
				comprarCartasAcumuladas(Jogo.roda.desacumular());
				jogadaRealizada = Jogada.COMPRAR_ACUMULADO;
				LOGGER.info("Jogador {} comprou acúmulo. Ficou com {} cartas", this.getNome(),
						this.getQuantidadeCartas());
			} else {
				LOGGER.info("Jogador {} precisou comprar uma carta", this.getNome());
				Jogo.roda.comprar(1, this);
				jogadaRealizada = Jogada.COMPRAR;
			}
		}
		return jogadaRealizada;
	}

	/**
	 * SEM DESCR
	 */
	protected Carta defineCartaDaJogada() {
		verificar = this.maoJogador.getCartas();
		cores[0] = 0;
		cores[1] = 0;
		cores[2] = 0;
		cores[3] = 0;
		for (int i = 0; i < verificar.size(); i++) {// conta quantas cartas de cada cor exitem na mão
			if (verificar.get(i).getCor() == Cor.AMARELO) {// conta quantas cartas amarelas tem na mão
				cores[0] += 1;
			} else if (verificar.get(i).getCor() == Cor.AZUL) {// conta quantas cartas azuis tem na mão
				cores[1] += 1;
			} else if (verificar.get(i).getCor() == Cor.VERDE) {// conta quantas cartas verdes tem na mão
				cores[2] += 1;
			} else if (verificar.get(i).getCor() == Cor.VERMELHO) {// conta quantas cartas vermelhas tem na mão
				cores[3] += 1;
			}
		}

		Carta ultima = Jogo.roda.getUltimaCarta();
		int nUltima = -1;/*
							 * nuemração da jogada atual, inicializado com -1 pois existem cartas com o
							 * número 0
							 */
		Acao aUltima = null;/* Ação da última carta do monte de descarte */
		Cor cUltima = ultima.getCor();/* Ação da última carta jogada no monte de descarte */

		int nComparada = -1;/*
							 * nuemração da jogada atual, inicializado com -1 pois existem cartas com o
							 * número 0
							 */
		Acao aComparada = null;/* Ação da última carta do monte de descarte */
		Cor cComparada = Cor.SEMCOR;/* Ação da última carta jogada no monte de descarte */

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
			cUltima = Jogo.roda.getCorEscolhida();
		}

		for (int k = 0; k < verificar.size(); k++) {
			try {/* tentamos pegar o número da carta a ser comparada */
				nComparada = verificar.get(k).getNumero();
			} catch (CartaSemNumero a) {/* se não for possível, tratamos a exceção */
				LOGGER.trace("a carta comparada {} não possui número", ultima);
			}
			try {/* tentamos pegar a ação da carta a ser comparada */
				aComparada = verificar.get(k).getAcao();
			} catch (CartaSemAcao b) {/* se não for possível, tratamos a exceção */
				LOGGER.trace(" a carta comparada {} não possui ação", ultima);
			}
			// pegamos a cor da carta a ser comparada
			cComparada = verificar.get(k).getCor();
			if (nComparada == nUltima && nComparada != -1) {
				candidatas.add(verificar.get(k));
			}

			else if (cComparada == cUltima && cComparada != Cor.SEMCOR) {
				candidatas.add(verificar.get(k));
			}

			else if (aComparada == aUltima && aComparada != null) {
				candidatas.add(verificar.get(k));
			} else if (aComparada == Carta.MAIS4 || aComparada == Carta.TROCACOR) {
				candidatas.add(verificar.get(k));
			}
			aComparada = null;
			nComparada = -1;
			cComparada = Cor.SEMCOR;
		}

		int maior = cores[0];// encontra a cor que tem mais cartas na mão
		for (int i = 1; i < 4; i++) {
			if (cores[i] > maior) {
				maior = cores[i];
			}
		}
		for (int n = 0; n < candidatas.size(); n++) {
			if (candidatas.get(n).getCor() == Cor.AMARELO && maior == cores[0]) {
				temp = candidatas.get(n);
				candidatas.clear();
				return temp;
			} else if (candidatas.get(n).getCor() == Cor.AZUL && maior == cores[1]) {
				temp = candidatas.get(n);
				candidatas.clear();
				return temp;
			} else if (candidatas.get(n).getCor() == Cor.VERDE && maior == cores[2]) {
				temp = candidatas.get(n);
				candidatas.clear();
				return temp;
			} else if (candidatas.get(n).getCor() == Cor.VERMELHO && maior == cores[3]) {
				temp = candidatas.get(n);
				candidatas.clear();
				return temp;
			}

		}
		if (candidatas.size() > 0) {
			temp = candidatas.get(0);
			candidatas.clear();
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * SEM DESCR
	 * 
	 * @Override
	 */
	public Cor sorteiaCor() {
		int cores[] = new int[4];// posicação 0 = qtd cartas da cor amarelo, 1 da azul, 2 da verde, 3 da vermelha
		ArrayList<Carta> verificar;
		verificar = this.maoJogador.getCartas();
		cores[0] = 0;
		cores[1] = 0;
		cores[2] = 0;
		cores[3] = 0;
		for (int i = 0; i < verificar.size(); i++) {// conta quantas cartas de cada cor exitem na mão
			if (verificar.get(i).getCor() == Cor.AMARELO) {
				cores[0] += 1;
			}
			if (verificar.get(i).getCor() == Cor.AZUL) {
				cores[1] += 1;
			}
			if (verificar.get(i).getCor() == Cor.VERDE) {
				cores[2] += 1;
			}
			if (verificar.get(i).getCor() == Cor.VERMELHO) {
				cores[3] += 1;
			}
		}
		int maior = cores[0];
		for (int i = 1; i < 4; i++) {
			if (cores[i] > maior) {
				maior = cores[i];
			}
		}
		if (maior == cores[0]) {
			LOGGER.trace("Cor escolhida: {}", Cor.AMARELO);
			return Cor.AMARELO;
		} else if (maior == cores[1]) {
			LOGGER.trace("Cor escolhida: {}", Cor.AZUL);
			return Cor.AZUL;
		} else if (maior == cores[2]) {
			LOGGER.trace("Cor escolhida: {}", Cor.VERDE);
			return Cor.VERDE;
		} else {
			LOGGER.trace("Cor escolhida: {}", Cor.VERMELHO);
			return Cor.VERMELHO;
		}
	}

}
