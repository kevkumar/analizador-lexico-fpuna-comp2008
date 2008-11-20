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
 * Se define un estado con sus atributos correspondientes y necesarios.
 * @author Leopoldo Poletti - Hugo Daniel Meyer
 */
public class Estado {

    /**
     * Constructor Vacio
     */
    public Estado() {
    }
    
    
    
    /**
     * Construye un estado con el Id del estado que le pasemos
     * @param id Id del estado a crear
     */
    public Estado(int id) {
        this.idEstado =id;
        arcos = new ArrayList<Arco>();
    }
    //ID DE UN ESTADO
    private int idEstado;
    
    //EL ESTADO FUE MARCADO O NO?
    private boolean marcado =false;
    
    //ARCOS Q SALEN Y A DONDE VAN
    private ArrayList<Arco> arcos;
    
    //ES ESTADO FINAL?
    private boolean eFinal=false;
    
    //ES ESTADO INICIAL?
    private boolean eInicial=false;
           
    
    /**
     * 
     * @return 
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * 
     * @param idEstado 
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * 
     * @return 
     */
    public boolean isMarcado() {
        return marcado;
    }

    /**
     * 
     * @param marcado 
     */
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Arco> getArcos() {
        return arcos;
    }

    /**
     * 
     * @param arcos 
     */
    public void setArcos(ArrayList<Arco> arcos) {
        this.arcos = arcos;
    }

    /**
     * 
     * @return 
     */
    public boolean isEFinal() {
        return eFinal;
    }

    /**
     * 
     * @param eFinal 
     */
    public void setEFinal(boolean eFinal) {
        this.eFinal = eFinal;
    }

    /**
     * 
     * @return 
     */
    public boolean isEInicial() {
        return eInicial;
    }

    /**
     * 
     * @param eInicial 
     */
    public void setEInicial(boolean eInicial) {
        this.eInicial = eInicial;
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Arco> getArcosConVacio() {
        ArrayList<Arco> arcosVacios = new ArrayList();
        for(Arco arco:this.arcos){
            if(arco.getIdArco().trim().equals("E")){
                arcosVacios.add(arco);
            }
        }
        return arcosVacios;
    }
    
    
    
    
}
