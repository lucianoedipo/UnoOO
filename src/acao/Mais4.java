package acao;
import java.util.Scanner;
import base.Roda;
import cartas.Cor;
/**
 * @author RobertoFortes
 * @since 31/10/2022
 * @version 6.0
 */

public class Mais4 extends Acao {
/**
 * @deprecated
 */


 /**
 * O jogo ficou responsável de fazer a acumulação da quantidade de compras
 *
 * Na função trocaCor, pergunta-se ao jogador a cor que ele desejará e retorna a mesma
 */
  public Cor trocaCor() { 

		/*
		 * Scanner sc; String resposta; sc= new Scanner(System.in);
		 * System.out.println("Que cor?"); resposta=sc.nextLine();
		 * resposta=resposta.toUpperCase();
		 * 
		 * if(resposta=="AMARELO"){ LOGGER.info("Cor trocada"); sc.close(); return
		 * Cor.AMARELO; } else if(resposta=="AZUL"){ LOGGER.info("Cor trocada");
		 * sc.close(); return Cor.AZUL; } else if(resposta=="VERMELHO"){
		 * LOGGER.info("Cor trocada"); sc.close(); return Cor.VERMELHO; } else
		 * if(resposta=="VERDE"){ LOGGER.info("Cor trocada"); sc.close(); return
		 * Cor.VERDE; } else{ System.out.println("Essa cor não existe");
		 * LOGGER.info("Cor trocada"); sc.close(); return Cor.SEMCOR; }
		 */
    return Cor.AMARELO;
  }
  /** 
  @param roda representa objeto do tipo referente a classe Roda
  A função comprar retorna 4 cartas
  */
  public void comprar(Roda roda){
    roda.comprar(4, roda.jogadorDaVez()); /**indica a compra de quatro cartas para o jogador correspondente*/
    LOGGER.info("Jogador compra 4\n");
  }

  /** 
  @param roda representa objeto do tipo referente a classe Roda
  A função pular pula para o próximo jogador
  */
  public void pular(Roda roda){
    roda.pular();
    LOGGER.info("Pulou para o próximo jogador\n");
  }

  /** 
  @param roda representa objeto do tipo referente a classe Roda
  */
  public void inverter(Roda roda){
    LOGGER.info("MAIS4 não inverte\n");
  }
  @Override
  public String toString(){
    return "MAIS4";
  }
}