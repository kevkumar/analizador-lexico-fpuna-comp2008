/*
 * Grupos.java
 *
 * Created on 16 de noviembre de 2008, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import java.util.ArrayList;

/**
 * Grupos que son utilizados dentro del afdminimo
 * @author Huguis
 */
public class Grupos {
    private ArrayList<ConjuntoDeEstados> grupo;
    private int idGrupo;
    private boolean finales;
    /** Creates a new instance of Grupos */
    
    public Grupos(int idgrupo) {
        this.grupo = new ArrayList<ConjuntoDeEstados>();
        this.setIdGrupo(idgrupo);
        this.setFinales(false);
    }

    public ArrayList<ConjuntoDeEstados> getGrupo() {
        return grupo;
    }

    public void setGrupo(ArrayList<ConjuntoDeEstados> grupo) {
        this.grupo = grupo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public boolean isFinales() {
        return finales;
    }

    public void setFinales(boolean finales) {
        this.finales = finales;
    }
    
}
