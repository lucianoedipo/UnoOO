package cartas;

import acao.Acao;

/**
 * Representa a abstração de uma Carta Especial Sem Cor
 * 
 * @author grupo Cartas
 *
 */
public class CartaEspecialSemCor extends CartaComAcao {

	/**
	 * 
	 * @param ac do tipo Acao
	 */
	public CartaEspecialSemCor(Acao ac) {// Carta especial sem cor
		this.acao = ac;
	}

	@Override
	public Cor getCor() {
		return Cor.SEMCOR;
	}
}
