/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import analizadorlexico.Estado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Polo
 */
public class ConjuntoDeEstados {
    
    private List<Estado> lista;
    
    private int idConjEstados;
    
    private boolean conjFinal;
    /*para usar dentro del afd minimo*/
    private int idGrupo;
    
    public int getInicio() {
        return idConjEstados;
    }

    public void setInicio(int inicio) {
        this.idConjEstados = inicio;
    }

    public ConjuntoDeEstados() {
        this.lista = new ArrayList();
    }

    /**
     * @return List<Estado> - Lista de estados
     */
    public List<Estado> getLista() {
        return lista;
    }

    /**
     * @param lista - Lista de estados
     */
    public void setLista(List<Estado> lista) {
        this.lista = lista;
    }

    public ConjuntoDeEstados(List<Estado> lista) {
        this.lista = lista;
    }
        
    /**
     * 
     * 
     * @param idConjEstados - Estado de idConjEstados
     */
    public void addEstado(Estado inicio) {
        //throw new UnsupportedOperationException("Not yet implemented");
        this.lista.add(inicio);
    }

    /**
     * @param nuevo - Contiene estado?     
     * @return boolean
     */
    public boolean contieneEstado(Estado nuevo) {
        //throw new UnsupportedOperationException("Not yet implemented");
        /* MENSAJE: Ver comportamiento de contains
         EN CASO DE QUE NO FUNCIONE AGREGAR METODO QUE COMPARE CON LOS ID'S
         DE LOS ESTADOS*/        
        return this.lista.contains(nuevo);
    }    
    
    public boolean equals(ConjuntoDeEstados comp){
        if(this.getLista().size() != comp.getLista().size())
            return false;
        boolean respuesta = false;
        for(Estado esteConjunto : this.getLista()){
            for(Estado comparar : comp.getLista()){
                if(esteConjunto.equals(comparar)){
                    respuesta = true;
                    break;
                }
            if(!respuesta)
                return respuesta;    
            }
        }
        return true;
    }

    public boolean isConjFinal() {
        return conjFinal;
    }

    public void setConjFinal(boolean conjFinal) {
        this.conjFinal = conjFinal;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

}
