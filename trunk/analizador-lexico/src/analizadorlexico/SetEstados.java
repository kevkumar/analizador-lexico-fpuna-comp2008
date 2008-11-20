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
import java.util.Comparator;

/**
 * Representa un conjunto de estados.
 * @author Hugo Daniel Meyer - Leopoldo Poletti
 */
public class SetEstados {
    
    /**
     * Creates a new instance of SetEstados
     */
    private ArrayList<Estado> estados;
    
    /**
     * Constructor Vacio que inicializa el array de estados
     */
    public SetEstados() {
        setEstados(new ArrayList<Estado>());
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Estado> getEstados() {
        return estados;
    }

    /**
     * 
     * @param estados 
     */
    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }
    
    /**
     * ORDENA EL CONJUNTO DE ESTADOS
     */
    public void ordenar() {       
        Estado a[] = new Estado[1]; 
        a = (Estado []) estados.toArray(a);
        Comparator comparator = new Comparator(){
            public int compare(Object o1, Object o2) {
                Estado c1 = (Estado) o1;
                Estado c2 = (Estado) o2;
                if(c1 != null && c2 != null){
                    if(c1.getIdEstado() > c2.getIdEstado())
                        return 1;
                    else if(c2.getIdEstado() > c1.getIdEstado())
                        return -1;
                    return 0;
                }
                return 0;
            }
        };
        java.util.Arrays.sort(a,comparator);
        estados.removeAll(estados);
        for(int i = 0; i < a.length; i++) {
            estados.add(a[i]); 
        }
    }
    
}
