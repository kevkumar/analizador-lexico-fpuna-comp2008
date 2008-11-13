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
import analizadorlexico.Arco;
import analizadorlexico.Estado;
import analizadorlexico.Estado;
import analizadorlexico.Estado;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    
    /* Conjunto de estados iniciales */
    private List<ConjuntoDeEstados> estadosIniciales;
    
    /* Conjunto de estados que van siendo marcados*/
    private List<ConjuntoDeEstados> estadosMarcados;
    
    /* Conjunto de estados finales */
    private List<ConjuntoDeEstados> estadosFinales;
            
    /* Transiciones del AFD*/
    private TransicionesAfd transiciones;
            
    public AFDEquivalente() {        
    }
        
    /**
     * @param afn - Afn a partir del cual se genera el AFD equivalente
     */
    public AFDEquivalente(Afn afn) {
        this.afn = afn;        
    }    
    
    /**
     * @param conjunto Conjunto de estados
     * 
     * @param simboloId simbolo con el q se espera transicion desde algun estado
     * 
     * @return ConjuntoDeArcos desde los que hay una transicion con el simbolo de ID
     */
    public ConjuntoDeEstados alcanzableSimbolo (ConjuntoDeEstados conjunto, 
            String simboloId) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();        
        for(Estado fin : conjunto.getLista()){                            
            for (Arco arco : fin.getArcos() ) {                
                if (arco.getIdArco().compareTo(simboloId) == 0 ) {
                    resultado.addEstado(arco.getDestino());
                }
            }
        }        
        return resultado;   
    }        
    
    /**
     * @param conjunto - Conjunto de estado de inicio
     * 
     * @return ConjuntoDeEstados - Estados alcanzables desde conjunto
     */
    public ConjuntoDeEstados alcamzablesConjunto (ConjuntoDeEstados conjunto) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();        
        Stack pila = new Stack();
        Estado fin;        
        pila.addAll((Collection) conjunto);
        resultado.setLista(conjunto.getLista());                
        while (! pila.isEmpty()) {
            fin = (Estado) pila.pop();                        
            for(Estado nuevo : alcamzablesEstado(fin).getLista()) {                
                if(!resultado.contieneEstado(nuevo) ) {
                    resultado.addEstado(nuevo);
                    pila.push(nuevo);
                }
            } 
        }        
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
                // Como hacer para comparar si es vacio */
                // Si es vacio sera -1, esto se puede parametrizar
                if (a.getIdArco().equals("E")){
                    if (! resultado.contieneEstado(nuevo)) {
                        resultado.addEstado(nuevo);
                        pila.push(nuevo);
                    }
                }
            }
        }
        return resultado;
    }        
    
    /**
     * Para contruir transiciones a partir de los conjuntos de estados
     * Aplicando el algoritmo de construccion de sub conjuntos     
     */
    public void construirTransiciones() {        
        transiciones = new TransicionesAfd();                
        ConjuntoDeEstados conjuntoT, conjuntoU;                
        int nombreAsignado = 0;
        /* MENSAJE: Hace falta ordenar? */
        estadoInicial = alcamzablesEstado(afn.getEstadoInicial());        
        estadoInicial.setInicio(nombreAsignado++);  
        estadosIniciales.addAll(estadosIniciales);
        
        String simbolo;
        conjuntoT = getEstadoDesmarcado();
        while (conjuntoT != null){
            for(Object s :  afn.getAlfabeto().getCaracteres()){            
                simbolo = (String) s;
                conjuntoU = alcamzablesConjunto(alcanzableSimbolo(conjuntoT, simbolo));
                if (conjuntoU.getLista().size() > 0) {
                    if (existeConjunto(conjuntoU) ) {
                        conjuntoU.setInicio(getNombre(conjuntoU));
                    } else {
                        conjuntoU.setInicio(nombreAsignado++);
                        estadosIniciales.add(conjuntoU);
                    }
                }
                transiciones.agregarTransiciones(conjuntoT, simbolo, conjuntoU);
            }            
            conjuntoT = getEstadoDesmarcado();
        }
        procesarEstadosFinales(afn.getEstadoFinal());
        construirMatriz();
    }

    private void construirMatriz() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    /** Metodo Privado : para obtener un desmarcado y marcarlo
     * @return Conjunto de estado marcado
     */    
    private ConjuntoDeEstados getEstadoDesmarcado(){
        ConjuntoDeEstados resultado = null;                        
        if (!estadoInicial.getLista().isEmpty()){
            resultado = estadosIniciales.get(0);
            estadosIniciales.remove(0);
            estadosMarcados.add(resultado);
        }        
        return resultado;
    }
    
    /**
     * @param conjunto - Conjunto de estado
     * @return true si existe el conjunto en la lista de estados     
     */
    private boolean existeConjunto(ConjuntoDeEstados conjunto){        
        for(ConjuntoDeEstados estados : estadosIniciales){
            if(estados.equals(conjunto))
                return true;
        }
        for(ConjuntoDeEstados estados : estadosMarcados){
            if(estados.equals(conjunto))
                return true;
        }   
        return false;
    } 
    
    /**
     * @param conjunto - Conjunto de estados
     * 
     * @return Nombre del conjunto
     */
    private int getNombre(ConjuntoDeEstados conjunto){        
        for(ConjuntoDeEstados estados : estadosIniciales){
            if(estados.equals(conjunto))
                return estados.getInicio();
        }
        for(ConjuntoDeEstados estados : estadosMarcados){
            if(estados.equals(conjunto))
                return estados.getInicio();
        }   
        return -1;
    }

    /**
     * @param estadoFinal - Estado final del afn
     */
    private void procesarEstadosFinales(Estado estadoFinal) {
        //throw new UnsupportedOperationException("Not yet implemented");
        for(ConjuntoDeEstados estados : estadosMarcados ){                    
            if (estados.contieneEstado(estadoFinal)) {
                estadosFinales.add(estados);
            }
        }
    }
}
