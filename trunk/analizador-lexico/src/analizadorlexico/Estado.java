/*
 * Estado.java
 *
 * Created on 7 de noviembre de 2008, 9:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;
import java.util.ArrayList;

/**
 *
 * @author Huguis
 *AQUI DEFINIMOS UN ESTADO, CON SUS ATRIBUTOS CORRESPONDIENTES
 */
public class Estado {
    
    /** Creates a new instance of Estado */
    public Estado(int id) {
        this.idEstado =id;
        arcos = new ArrayList<Arco>();
    }
    //ID DE UN ESTADO
    private int idEstado;
    
    //EL ESTADO FUE MARCADO O NO?
    private boolean marcado;
    
    //ARCOS Q SALEN Y A DONDE VAN
    private ArrayList<Arco> arcos;
           
    
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public ArrayList<Arco> getArcos() {
        return arcos;
    }

    public void setArcos(ArrayList<Arco> arcos) {
        this.arcos = arcos;
    }
    
    
    
    
}
