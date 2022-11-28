package base.jogador;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cartas.*;
import base.Jogo;

public class JogadorJogador extends Jogador{
    protected static final Logger LOGGER = LoggerFactory.getLogger(JogadorJogador.class);

    
    /**
     * Construtor que recebe o nome do JogadorJogador
     * e utiliza o construtor da classe SUPER
     * (Jogador) para instanciar o objeto.
     * @param nome - Nome inicial do jogador
     * @see MaoCartas
     * @see Jogador
     */
    public JogadorJogador(String nome){
        super(nome);
    }

    /**
     * Classe que retorna a cor mais possuida
     * pelo jogador, ou 'null'.
     * @see Jogador
     * @see Cor
     * @return maiorCor - Cor mais possuida pelo jogador
     */
    private Cor maiorCor(){
        LinkedHashMap <Cor, Integer> contaCor = new LinkedHashMap<Cor, Integer>();
        for(Carta carta : this.getMaoJogador().getCartas()){
            if(carta instanceof CartaComCor){
                int qtdAtualCor = (contaCor.get(carta.getCor()) != null ? contaCor.get(carta.getCor()) : 0);
                contaCor.put(carta.getCor(), qtdAtualCor+1);
            }
        }
        Map.Entry<Cor,Integer> maiorCor = null;
        for(Map.Entry<Cor,Integer> corAtual : contaCor.entrySet() ){
            if(maiorCor == null || corAtual.getValue() > maiorCor.getValue()){
                maiorCor = corAtual;
            }
        }
        return (maiorCor != null ? maiorCor.getKey() : null);
    }


    /**
     * Realiza a seleção de uma cor
     * para realizar a ação de troca de cor.
     * Seleciona-se a cor em maior quantidade
     * na MaoCartas de JogadorJogador, ou
     * uma cor aleatória.
     * A cor aleatória é obtida a partir do método
     * sorteiaCor da classe Jogador.
     * @return corEscolhida - Cor escolhida para a troca
     * @see Cor
     * @see MaoCartas
     * 
     */
    @Override
    public Cor sorteiaCor(){
        Cor corEscolhida = this.maiorCor();
        corEscolhida = corEscolhida!=null ? corEscolhida : super.sorteiaCor();

        LOGGER.info("Jogador {} escolheu trocar a cor para: {}", this.getNome(), corEscolhida.toString());
        return corEscolhida;
    }
    
    /**
     * Função responsável por buscar carta adequada em
     * MaoCartas de jogador para ser descartada, no caso de não haver acúmulo
     * (cartas de compras acumuladas na roda)
     * 
     * <h4>Sequência de escolha padrão:</h4>
     * <ul>
     * <li>1º carta normal;</li>
     * <li>2º carta especial com cor: bloqueio, reverso, +2;</li>
     * <li>3º carta especial sem cor: trocacor e +4;</li>
     * </ul>
     * 
     * @see MaoCartas
     * @see Jogador
     * @see Roda
     * @return Carta definida para ser jogada (descartada), ou 'null' (caso nenhuma
     * carta adequada seja encontrada)
     */
    @Override
    protected Carta defineCartaDaJogada()
    {
    	Carta ultimo = Jogo.roda.getUltimaCarta();
        Cor corEscolhida = Jogo.roda.getCorEscolhida();    	
        Cor maiorCor = this.maiorCor();
        // Busca cartas normais
        CartaNormal cartaComMesmoNumero = null;
    	for(Carta c : this.getMaoJogador().getCartas())
    	{
    		if(!(c instanceof CartaNormal))
    			continue;
    		
    		CartaNormal cn = (CartaNormal)c;
    		
    		// Se for a mesma cor pode jogar
    		if((ultimo instanceof CartaEspecialSemCor && cn.getCor() == corEscolhida) || cn.getCor() == ultimo.getCor())
    			return c;
    		
    		// Se for o mesmo número também pode jogar
            
    		if(ultimo instanceof CartaNormal && ((CartaNormal)ultimo).getNumero() == cn.getNumero()){
                if(maiorCor == null || cn.getCor() == maiorCor){
                    return c;
                }
                cartaComMesmoNumero = (CartaNormal)cn;
            }
        
    	}
        //Existe uma carta com mesmo número que a ultima da roda,
        //mas que não possui a cor que é a mais possuida pelo jogador.
        if(cartaComMesmoNumero != null) return cartaComMesmoNumero;

    	// Busca bloqueio, reverso e +2
    	for(Carta c : this.getMaoJogador().getCartas())
    	{
    		if(!(c instanceof CartaEspecialComCor))
    			continue;
    		
    		CartaEspecialComCor ca = (CartaEspecialComCor)c;
    		
    		// Verifica se é a mesma cor ou se é a mesma ação pra poder jogar
    		if( (ultimo instanceof CartaEspecialSemCor && ca.getCor() == corEscolhida) || ca.getCor() == ultimo.getCor() || (ultimo instanceof CartaEspecialComCor && ca.getAcao() == ((CartaEspecialComCor)ultimo).getAcao()))
    		{
    			return ca;
    		}
    	}
    
    	
    	// Busca +4 e troca cor
    	for(Carta c : this.getMaoJogador().getCartas())
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
    
}
