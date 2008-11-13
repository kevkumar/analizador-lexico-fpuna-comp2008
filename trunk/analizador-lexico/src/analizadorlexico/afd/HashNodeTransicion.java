/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorlexico.afd;

/**
 *
 * @author Polo
 */
class HashNodeTransicion {

    
    private String entrada;             
    
    private ConjuntoDeEstados estadosDestinos;

    public HashNodeTransicion() {
    }            
    
    public HashNodeTransicion(String entrada, ConjuntoDeEstados estados) {
        this.entrada = entrada;
        estadosDestinos = estados;
    }    
    
    /**     
     * @return Entrada de la transicion
     */
    public String getEntrada() {
        return entrada;
    }
    
    /**     
     * @return Conjunto de estados destino 
     */
    public ConjuntoDeEstados getEstadosDestino() {
        return estadosDestinos;
    }

}
