package acao;
import base.Roda;
import cartas.*;
import java.util.Scanner;
/**
 * @author Vinicius 
 * @version 3.5
 * @since 26/10/2022
 */

public class TrocaCor extends Acao {
  /**
   * @deprecated 
   *
   *
   * Função recebe uma cor que deseja alterar e retorna ela
   */
  public Cor trocaCor () { // 
  
		/*
		 * Scanner sc; String resposta; sc= new Scanner(System.in);
		 * System.out.println("Que cor?"); resposta=sc.nextLine();
		 * resposta=resposta.toUpperCase();
		 * 
		 * if(resposta=="AMARELO"){ LOGGER.info("Cor trocada para amarelo \n");
		 * sc.close(); return Cor.AMARELO; } else if(resposta=="AZUL"){
		 * LOGGER.info("Cor trocada para azul \n"); sc.close(); return Cor.AZUL; } else
		 * if(resposta=="VERMELHO"){ LOGGER.info("Cor trocada para vermelho \n");
		 * sc.close(); return Cor.VERMELHO; } else if(resposta=="VERDE"){
		 * LOGGER.info("Cor trocada para verde\n"); sc.close(); return Cor.VERDE; }
		 * else{ LOGGER.info("Essa cor não existe"); sc.close(); return Cor.SEMCOR; }
		 */
    return Cor.AMARELO;
  }
  public void pular (Roda roda){
    LOGGER.info("TrocaCor não pode pular a vez");
   }

  public void inverter (Roda roda){
    LOGGER.info("TrocaCor não pode inverter");
   }
  
  public void comprar (Roda roda){
    LOGGER.info("TrocaCor não pode comprar");
  }

  @Override
  public String toString(){
    return "TROCACOR";
  }
}