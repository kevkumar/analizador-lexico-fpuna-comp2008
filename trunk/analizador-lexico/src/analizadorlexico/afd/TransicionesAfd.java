/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Hugo Daniel Meyer - Leopoldo Poletti.
 */
class TransicionesAfd {

    /**
     * Hashtable que se utiliza para la tabla 
     * de transiciones del AFD.
     */
    private Hashtable transiciones;
    
    public TransicionesAfd() {
        transiciones = new Hashtable();
    }
    
    /**     
     * @param origen Estados de origen.
     * @param entrada Entrada 
     * @param destino Estados destinos.
     */
    public void agregarTransiciones(ConjuntoDeEstados origen, String entrada, ConjuntoDeEstados destino){
        HashNodeTransicion nodo = new HashNodeTransicion(entrada, destino);
        
        ArrayList lista;
        lista = (ArrayList) transiciones.get(origen);
        
        if(lista == null){
            lista = new ArrayList();
            transiciones.put(origen, lista);
        }
        
        lista.add(nodo);        
    }
    
    /**     
     * @return Conjunto de claves del Hashtable 
     */
    public Enumeration getClaves() {
        return transiciones.keys();
    }
    
    /**     
     * @return Valor del Hashtable 
     * @param clave Clave del Hashtable
     */
    public Object getValue(Object clave) {
        return transiciones.get(clave);
    }
    
}
