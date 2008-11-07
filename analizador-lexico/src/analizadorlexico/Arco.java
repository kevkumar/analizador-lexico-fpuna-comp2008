/*
 * Arco.java
 *
 * Created on 7 de noviembre de 2008, 10:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Propietario
 */
public class Arco {
    //IDENTIFICADOR DEL ARCO
    private int idArco;
    
    //ORIGEN DEL ESTADO(GENERALMENTE EL QUE LO INSTANCIA)
    private Estado origen;
    
    //DESTINO DEL ARCO
    private Estado destino;
    
    /** Creates a new instance of Arco */
    public Arco(int idArco, Estado origen, Estado destino) {
        this.setIdArco(idArco);
        this.setOrigen(origen);
        this.setDestino(destino);
    }

    public int getIdArco() {
        return idArco;
    }

    public void setIdArco(int idArco) {
        this.idArco = idArco;
    }

    public Estado getOrigen() {
        return origen;
    }

    public void setOrigen(Estado origen) {
        this.origen = origen;
    }

    public Estado getDestino() {
        return destino;
    }

    public void setDestino(Estado destino) {
        this.destino = destino;
    }
    
}
