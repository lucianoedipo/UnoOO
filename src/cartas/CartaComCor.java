package cartas;

import acao.Acao;
import base.Jogo;

/**
 * Representa a abstração de uma Carta com Cor, que na nossa abstração não ira
 * conter ação
 * 
 * @author grupo Cartas
 *
 */
public abstract class CartaComCor extends Carta {

	@Override
	public final Acao getAcao() throws CartaSemAcao {
		throw new CartaSemAcao("Cartas com cor e número não possuem ação: " + Jogo.roda.jogadorDaVez().getNome());
	}

	@Override
	public final void setAcao(Acao c) throws CartaSemAcao {
		throw new CartaSemAcao("Cartas com cor e número não possuem ação");
	}

}
