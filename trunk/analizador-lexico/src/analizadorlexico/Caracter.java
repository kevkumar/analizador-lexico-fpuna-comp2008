/*
 * Caracter.java
 *
 * Created on 4 de noviembre de 2008, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 * Clase que representa un caracter o token del alfabeto.
 * @author Hugo Daniel Meyer - Leopoldo Poletti
 */
public class Caracter {

    private String valor;
    private boolean delAlfabeto = false;
    
    /** Creates a new instance of Caracter */
    public Caracter() {
    }

    /**
     * 
     * @return 
     */
    public String getValor() {
        return valor;
    }

    /**
     * 
     * @param valor 
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * 
     * @return 
     */
    public boolean isDelAlfabeto() {
        return delAlfabeto;
    }

    /**
     * 
     * @param delAlfabeto 
     */
    public void setDelAlfabeto(boolean delAlfabeto) {
        this.delAlfabeto = delAlfabeto;
    }
    
}
