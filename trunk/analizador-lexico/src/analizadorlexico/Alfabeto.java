/*
 * Alfabeto.java
 *
 * Created on 2 de noviembre de 2008, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

import java.util.ArrayList;
import java.util.List;

/**
 *Esta clase nos permitira definir y cargar datos de los alfabetos que utilizarï¿½n
 *las expresiones regulares que luego serán analizadas.
 * @author Hugo Daniel Meyer-Leopoldo Poletti
 *
 */
public class Alfabeto {
    
    /**
     * Creates a new instance of Alfabeto
     */
    public Alfabeto() {
        this.caracteres = new ArrayList();
    }
    /**
     *Esta es la lista que contiene los caracteres del alfabeto efectivamente.
     *Consideraremos al vacio como un sï¿½mbolo aparte.
     *Los valores del ALFABETO DEBEN SER INTRODUCIDOS SEPARADOS POR COMA
     */
    private List caracteres;
    
    private String vacio = "E";
    
    private boolean hayVacio = false;
    
    public void cargarAlfabeto(String caracteres){
        String[] valores= caracteres.split(",");
        for (int i=0; i < valores.length; i++){
            if(valores[i].trim().equals("E")){
                this.setHayVacio(true);
                break;
            }
            this.getCaracteres().add(valores[i]);
        }
    }

    public List getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(List caracteres) {
        this.caracteres = caracteres;
    }

    public String getVacio() {
        return vacio;
    }

    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    public boolean isHayVacio() {
        return hayVacio;
    }

    public void setHayVacio(boolean hayVacio) {
        this.hayVacio = hayVacio;
    }
    
    /**
     * 
     * @return 
     */
    public String imprimir() {
        String resp = "Alfabeto = { ";
        for (int i = 0; i < this.caracteres.size(); i++) {
            resp = resp + (String) this.getCaracteres().get(i);
            if (!(i == (this.caracteres.size()-1))) {
                resp = resp + ", ";
            }
        }
        resp = resp + " } ";
        return resp;
    }
    
}
