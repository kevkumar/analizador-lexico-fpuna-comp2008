/*
 * Estado.java
 *
 * Created on 7 de noviembre de 2008, 9:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Huguis
 *AQUI DEFINIMOS UN ESTADO, CON SUS ATRIBUTOS CORRESPONDIENTES
 */
public class Estado {
    
    /** Creates a new instance of Estado */
    public Estado() {
    }
    //ID DE UN ESTADO
    int idEstado;
    
    //EL ESTADO FUE MARCADO O NO?
    boolean marcado;
    
    //ARCOS Q SALEN Y A DONDE VAN
    Arcos arcos;
    
    
    
}
