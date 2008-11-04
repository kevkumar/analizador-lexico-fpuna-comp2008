/*
 * Alfabeto.java
 *
 * Created on 2 de noviembre de 2008, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

import java.util.List;

/**
 *Esta clase nos permitira definir y cargar datos de los alfabetos que utilizarán
 *las expresiones regulares que luego serán analizadas.
 * @author Huguis
 *
 */
public class Alfabeto {
    
    /**
     * Creates a new instance of Alfabeto
     */
    public Alfabeto() {
    }
    /**
     *Esta es la lista que contiene los caracteres del alfabeto efectivamente.
     *Consideraremos al vacio como un símbolo aparte.
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
    
    
    
}
