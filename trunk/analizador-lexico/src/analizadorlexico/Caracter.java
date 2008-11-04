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
 *
 * @author Huguis
 */
public class Caracter {

    private String valor;
    private boolean delAlfabeto = false;
    
    /** Creates a new instance of Caracter */
    public Caracter() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isDelAlfabeto() {
        return delAlfabeto;
    }

    public void setDelAlfabeto(boolean delAlfabeto) {
        this.delAlfabeto = delAlfabeto;
    }
    
}
