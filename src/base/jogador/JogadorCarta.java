package base.jogador;

import acao.*;
import base.*;
import cartas.*;

import java.util.ArrayList;


public class JogadorCarta extends Jogador{
	

	public JogadorCarta(String nome) {
		super(nome);
		this.inicializarMao(); 
	}
	
	/* /**
    *
    * Compra uma carta, adicionando uma carta a maoJogador,
    * @see MaoCartas
    **/
    //@Override
    /*public void comprar(Carta carta){
        this.maoJogador.receberCarta(carta);
        LOGGER.info("Carta adicionada: {}", carta.toString());
    }*/
	
	public Cor CorEscolhida() { 
		
		//System.out.println(this.maoJogador);
		
		ArrayList<Integer> qtdCor = new ArrayList<>();
		
		qtdCor.add(0); //Amarelo pos 0
		qtdCor.add(0); //Azul pos 1
		qtdCor.add(0); //Verde pos 2
		qtdCor.add(0); //Vermelho pos 3
		
	    
	  
		for(Carta c: this.maoJogador.getCartas()) {
			
			if(c instanceof CartaComCor) {
				
				if(c.getCor() == Cor.AMARELO) {
					int amarelo = qtdCor.get(0);
					qtdCor.set(0, amarelo+1);
				}
				
				else if(c.getCor() == Cor.AZUL) {
					int azul = qtdCor.get(1);
					qtdCor.set(1, azul+1);
				}
				
				else if(c.getCor() == Cor.VERDE) {
					int verde = qtdCor.get(2);
					qtdCor.set(2, verde+1);
				}
				
				else if(c.getCor() == Cor.VERMELHO) {
					int vermelho = qtdCor.get(3);
					qtdCor.set(3, vermelho+1);
				}
				
				
			}
			
			
		}
		
		
		//Parte de decidir a cor, com base no for it de cima
		
		Cor corEscolhida = Cor.AMARELO; 
		int MaiorValor = qtdCor.get(0); //Amarelo
		
		for(int i =1; i < qtdCor.size();i++) {
			
			if(qtdCor.get(i) > MaiorValor ) {
				MaiorValor = qtdCor.get(i);
				
				if(i==1) {
					
					corEscolhida = Cor.AZUL;
					
				}
				
				else if(i==2) {
					
					corEscolhida = Cor.VERDE;
				}
				
				else if(i==3) {
					
					corEscolhida = Cor.VERMELHO;
				}
			}
			
		}
		
		return corEscolhida; 
	}
	
	//Parte de escolher a cor, agora, sem sem aleatório
	
    public Cor sorteiaCor(){
     
        Cor corSorteada = CorEscolhida();
        LOGGER.trace("Cor escolhida: {}", corSorteada);
        return corSorteada;
    }
    
    
    //Parte de Definir Carta da Jogada
   
 protected Carta defineCartaDaJogada(){    
    	
        
        Carta ultimo = Jogo.roda.getUltimaCarta();   
		
    	//Tentando fazer com que seja escolhida a cor de maior quantidade; 
    	
    	Cor MelhorCorDescarte = CorEscolhida(); 
    	
    	//Implementação nova, considerando o problema relatado pelo Matheus 
    	// Criando uma Variável para cada atributo de carta 
    	
    	Cor corUltimaCarta = ultimo.getCor(); //Até pega a cor, exeto se for +4 ou TROCACOR 
    	
    	if(ultimo instanceof CartaEspecialSemCor ) { //Considera o +4 ou TROCAR
    		corUltimaCarta = Jogo.roda.getCorEscolhida();
    	}
    	
    	
    	int nUltimaCarta = -1;
    	try {
    		 nUltimaCarta = ultimo.getNumero();
    	
    	}catch (CartaSemNumero e) {
    		LOGGER.trace("Esta carta {} não possui número", ultimo);
    		
    	}
    	
    	
    	Acao acaoUltimaCarta = null; 
    	try {
    		 acaoUltimaCarta = ultimo.getAcao();
    	
    	}catch (CartaSemAcao e) {
    		LOGGER.trace("Esta carta {} não possui ação", ultimo);
    	}
    	
    	
    	// Alterando considerando as váriaiveis 
    	for(Carta c : this.maoJogador.getCartas()) {
    		
    		if(!(c instanceof CartaNormal))
    			continue;
    		
    		if(c.getCor() != MelhorCorDescarte) {
    			//System.out.println(this.getMaoJogador().getCartas());
    			continue;
    			
    		}else {
    			CartaNormal cn = (CartaNormal)c;
    			
    			// Se for a mesma cor pode jogar
        		if(cn.getCor() == corUltimaCarta) {
        			//System.out.println("Entrou no certo deste");
        			return c;
        		}
        		// Se for o mesmo número também pode jogar
        		if(ultimo instanceof CartaNormal && nUltimaCarta == cn.getNumero()) {
        			//System.out.println("Entrou no certo deste");
        			return c;
        		}
        			
        	  }
    			
    	}//Fecha melhor opção de carta
        	
        	
        	
        	// Busca cartas normais caso algo de errado 
        	for(Carta c : this.maoJogador.getCartas())
        	{
        		
        		//System.out.println("Entrou aqui");
        		if(!(c instanceof CartaNormal))
        			continue;
        		
        		CartaNormal cn = (CartaNormal)c;
        		
        		
        		// Se for a mesma cor pode jogar
        		if(cn.getCor() == corUltimaCarta)
        			return c;
        		
        		// Se for o mesmo número também pode jogar
        		if(ultimo instanceof CartaNormal && nUltimaCarta == cn.getNumero())
        			return c;
        	}
        	
  
    	
    	// Busca bloqueio, reverso e +2
    	for(Carta c : this.maoJogador.getCartas())
    	{
    		if(!(c instanceof CartaEspecialComCor))
    			continue;
    		
    		CartaEspecialComCor ca = (CartaEspecialComCor)c;
    		
    		// Verifica se é a mesma cor ou se é a mesma ação pra poder jogar
    		if(ca.getCor() == corUltimaCarta || (ultimo instanceof CartaEspecialComCor && ca.getAcao() == acaoUltimaCarta))
    		{
    			return ca;
    		}
    	}
    	
    	
    	// Busca +4 e troca cor
    	for(Carta c : this.maoJogador.getCartas())
    	{
    		if(!(c instanceof CartaEspecialSemCor))
    			continue;
    		
    		// +4 e troca cor pode ser jogado de qualquer forma
    		return c;
    	}
    	
    	// Se não conseguir jogar nenhuma tem que comprar
        return null;
    	//return this.defineCartaDaJogada();
    }
	
  
    
    
    public Jogada realizarJogada(){
        LOGGER.trace("Jogador {} realizando jogada", this.getNome());
        Carta carta = null;
        Jogada jogadaRealizada = null;
        if(Jogo.roda.temAcumulo()){
            try{
                carta = defineCartaParaAcumulo(Jogo.roda.getUltimaCarta().getAcao());
            }catch (CartaSemAcao e) {
                LOGGER.error("Erro ao tentar definir carta de acúmulo: {}", e);
            }

            if(carta == null){
                LOGGER.trace("Jogador {} comprando acúmulo", this.getNome());
                comprarCartasAcumuladas(Jogo.roda.desacumular());
                jogadaRealizada = Jogada.COMPRAR_ACUMULADO;
                LOGGER.info("Jogador {} comprou acúmulo. Ficou com {} cartas", this.getNome(), this.getQuantidadeCartas());

            }else{
                descartar(carta);
                LOGGER.info("Jogador {} descartou {} para o acúmulo", this.getNome(), carta.toString());
                jogadaRealizada = Jogada.DESCARTAR;
            }
        }else{
            carta = defineCartaDaJogada();

            if(carta != null){
                if(carta instanceof CartaComAcao){
                    try {
                        Acao acaoCarta = carta.getAcao();
                            realizarAcaoDaCarta(acaoCarta);
                    } catch (CartaSemAcao e) {
                        LOGGER.error("ERRO: Carta não possui acao!");
                    }
                }
                descartar(carta);
                jogadaRealizada = Jogada.DESCARTAR;

            }else{
                LOGGER.info("Jogador {} precisou comprar uma carta", this.getNome());
            	Jogo.roda.comprar(1, this);
                jogadaRealizada = Jogada.COMPRAR;
            }
        }
        return jogadaRealizada;
    }
}
