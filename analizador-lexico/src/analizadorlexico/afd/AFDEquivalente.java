/*
 * AFDEquivalente.java
 *
 * Created on 4 de noviembre de 2008, 19:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import analizadorlexico.Afn;
import analizadorlexico.Arco;
import analizadorlexico.Estado;
import java.util.Stack;

/**
 * Clase que representa el AFD Equivalente que se obtiene a partir del AFN Equivalente
 * @author Polo
 */   
public class AFDEquivalente {
    
    /*Atributos de la clase*/
        
    /* Afn de donde se parte para generar el AFD equivalente */
    private Afn afn;
    
    /* Matriz de adyacencia */
    private String adyacencia [][];
    
    /* AFD inicial */
    private ConjuntoDeEstados estadoInicial;
    
    /* Estados del AFD*/
    private EstadosAfd estados;
    
    /* Transiciones del AFD*/
    private TransicionesAfd transiciones;
            
    public AFDEquivalente() {
        estados = new EstadosAfd();        
    }
        
    /**
     * @param afn - Afn a partir del cual se genera el AFD equivalente
     */
    public AFDEquivalente(Afn afn) {
        this.afn = afn;
        estados = new EstadosAfd();        
    }    
    
    /**
     * @param conjunto - Conjunto de estado de inicio
     * 
     * @return ConjuntoDeEstados - Estados alcanzables desde conjunto
     */
    public ConjuntoDeEstados alcamzablesConjunto (ConjuntoDeEstados conjunto) {
        ConjuntoDeEstados resultado = null;
        return resultado;        
    }
    
    /**      
     * @param inicio - Estado de inicio
     * @return ConjuntoDeEstados - Conjunto de estados a los que se puede 
     * llegar desde el estado s
     */
    public ConjuntoDeEstados alcamzablesEstado (Estado inicio) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();
        Stack pila = new Stack();
        Estado fin;                
        pila.push(inicio);
        resultado.addEstado(inicio);
        
        while (! pila.isEmpty()) {
            fin = (Estado) pila.pop();            
            for (Arco a: fin.getArcos()) {                
                Estado nuevo = a.getDestino();
                /* Como hacer para comparar si es vacio¿¿¿
                if (a.getIdArco() afn.getAlfabeto().getSimboloVacio()) == 0) {
                    if (! resultado.contiene(nuevo)) {
                        resultado.addEstado(nuevo);
                        pila.push(nuevo);
                    }
                }*/
            }
        }
        return resultado;
    }
    
    
}
