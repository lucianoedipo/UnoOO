package cartas;

import acao.Acao;

/**
 * Representa a abstração de uma Carta Especial com Cor
 * 
 * @author grupo Cartas
 *
 */
public class CartaEspecialComCor extends CartaComAcao {

	/**
	 * 
	 * @param c  do tipo Cor
	 * @param ac do tipo Acao
	 */
	public CartaEspecialComCor(Cor c, Acao ac) {

		this.cor = c;
		// super.setNumero(-1);
		this.acao = ac;
	}

	@Override
	public Cor getCor() {
		// TODO Auto-generated method stub
		return super.getCor();
	}

	@Override
	public Acao getAcao() {
		// TODO Auto-generated method stub
		return super.getAcao();
	}

	@Override
	public void setCor(Cor c) {
		// TODO Auto-generated method stub
		super.setCor(c);
	}

	@Override
	public void setAcao(Acao ac) {
		// TODO Auto-generated method stub
		super.setAcao(ac);
	}

}
