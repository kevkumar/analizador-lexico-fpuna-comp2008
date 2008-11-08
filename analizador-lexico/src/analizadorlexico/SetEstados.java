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
    
}
