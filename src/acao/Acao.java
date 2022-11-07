package acao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.Roda;
import cartas.Cor;

public abstract class Acao {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Acao.class);

	/**
	 * Função que serve para avançar para o próximo jogador
	 * 
	 * @param roda
	 */
	public abstract void pular(Roda roda);

	/**
	 * Função que serve para inverter o sentido da roda
	 * 
	 * @param roda
	 */
	public abstract void inverter(Roda roda);

	/**
	 * Função que serve para adicionar n cartas do monte à mão do jogador, caso
	 * chamado por um mais 2 será realizado duas compras para o jogador, e caso
	 * chamado por mais 4 realizara quatro compras
	 * 
	 * @param roda
	 */
	public abstract void comprar(Roda roda);

	/**
	 * Troca cor, o método se for chamado para as ações +4 ou troca cor, ira pedir
	 * ao usuário que informe uma cor entre, amarelo, azul, vermelho ou verde, e ira
	 * retornar esta Cor, se a ação não for de +4 ou troca cor, ele ira alertar ao
	 * usuário que a ação escolhida não consegue mudar a cor, caso o usuário não
	 * informe uma cor valida o método ira alertar que a cor é invalida e fechara.
	 * Obs o metodo aceita a cor independente se as letras forem maiusculas ou
	 * minusculas
	 * 
	 * @return Cor - cor escolhida pelo jogador
	 */
	public abstract Cor trocaCor();
}

/**
 * @author Fernando Favaro Moreira
 * @since 31/10/2022
 * @version 5.0
 * @see Roda, Jogada
 */