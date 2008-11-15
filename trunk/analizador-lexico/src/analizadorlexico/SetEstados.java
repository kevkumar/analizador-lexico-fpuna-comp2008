/*
 * SetEstados.java
 *
 * Created on 8 de noviembre de 2008, 11:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;
import java.util.ArrayList;

/**
 *
 * @author Huguis
 */
public class SetEstados {
    
    /**
     * Creates a new instance of SetEstados
     */
    private ArrayList<Estado> estados;
    
    public SetEstados() {
        setEstados(new ArrayList<Estado>());
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }
    
    /**
     * ORDENA EL CONJUNTO DE ESTADOS
     */
    public void ordenar() {       
        Estado a[] = new Estado[1]; 
        a = (Estado []) estados.toArray(a);
        Comparator comp = new Comparator();
        java.util.Arrays.sort(a,);
        estados.removeAll(estados);
        for(int i = 0; i < a.length; i++) {
            estados.add(a[i]); 
        }
    }
    
}
