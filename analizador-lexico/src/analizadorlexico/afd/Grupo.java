/*
 * Grupo.java
 *
 * Created on 16 de noviembre de 2008, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import java.util.ArrayList;

/**
 * Grupo que son utilizados dentro del afdminimo
 * 
 * @author Hugo Daniel Meyer - Leopoldo Poletti
 */
public class Grupo {
    private ArrayList<ConjuntoDeEstados> grupo;
    private int idGrupo;
    private boolean finales;
    private String enlace;
    private ArrayList<Grupo> destino;
    /**
     * Creates a new instance of Grupo
     */
    
    public Grupo(int idgrupo) {
        this.grupo = new ArrayList<ConjuntoDeEstados>();
        this.setIdGrupo(idgrupo);
        this.setFinales(false);
        this.destino = new ArrayList<Grupo>();
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

    public ArrayList<Grupo> getDestino() {
        return destino;
    }

    public void setDestino(ArrayList<Grupo> destino) {
        this.destino = destino;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
    
}
