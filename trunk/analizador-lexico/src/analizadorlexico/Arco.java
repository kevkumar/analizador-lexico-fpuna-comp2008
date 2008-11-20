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
 * Esta clase representa un arco entre estados, teniendo
 * su origen y su destino, ademas del id del arco, el cual
 * sería la letra del alfabeto o E con la cual se hace la transcisión
 * @author Leopoldo Poletti - Hugo Daniel Meyer
 */
public class Arco {
    //IDENTIFICADOR DEL ARCO
    private String idArco;
    
    //ORIGEN DEL ESTADO(GENERALMENTE EL QUE LO INSTANCIA)
    private Estado origen;
    
    //DESTINO DEL ARCO
    private Estado destino;
    
    /**
     * Nuevo Objeto
     * @param idArco String que indica con que letra nos movemos
     * @param origen Estado Origen
     * @param destino Estado Destino
     */
    public Arco(String idArco, Estado origen, Estado destino) {
        this.setIdArco(idArco);
        this.setOrigen(origen);
        this.setDestino(destino);
    }

    /**
     * 
     * @return 
     */
    public String getIdArco() {
        return idArco;
    }

    /**
     * 
     * @param idArco 
     */
    public void setIdArco(String idArco) {
        this.idArco = idArco;
    }

    /**
     * 
     * @return 
     */
    public Estado getOrigen() {
        return origen;
    }

    /**
     * 
     * @param origen 
     */
    public void setOrigen(Estado origen) {
        this.origen = origen;
    }

    /**
     * 
     * @return 
     */
    public Estado getDestino() {
        return destino;
    }

    /**
     * 
     * @param destino 
     */
    public void setDestino(Estado destino) {
        this.destino = destino;
    }
    
}
