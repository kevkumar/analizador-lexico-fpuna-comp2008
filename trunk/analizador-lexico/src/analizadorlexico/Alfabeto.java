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
 *Esta clase nos permitira definir y cargar datos de los alfabetos 
 *que utilizarán las expresiones regulares que luego serán analizadas.
 * @author Hugo Daniel Meyer-Leopoldo Poletti
 *
 */
public class Alfabeto {
    
    /**
     * Crea un nuevo objeto alfabeto e inicializa el array
     * de caracteres
     */
    public Alfabeto() {
        this.caracteres = new ArrayList();
    }
    /**
     *Esta es la lista que contiene los caracteres del alfabeto efectivamente.
     *Consideraremos al vacio como un símbolo aparte.
     *Los valores del ALFABETO DEBEN SER INTRODUCIDOS SEPARADOS POR COMA
     */
    private List caracteres;
    
    private String vacio = "E";
    
    private boolean hayVacio = false;
    
    /**
     * Recibe el alfabeto como un string y lo carga
     * en el array del alfabeto
     * @param caracteres Alfabeto en un String
     */
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

    /**
     * 
     * @return 
     */
    public List getCaracteres() {
        return caracteres;
    }

    /**
     * 
     * @param caracteres 
     */
    public void setCaracteres(List caracteres) {
        this.caracteres = caracteres;
    }

    /**
     * 
     * @return 
     */
    public String getVacio() {
        return vacio;
    }

    /**
     * 
     * @param vacio 
     */
    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    /**
     * 
     * @return 
     */
    public boolean isHayVacio() {
        return hayVacio;
    }

    /**
     * 
     * @param hayVacio 
     */
    public void setHayVacio(boolean hayVacio) {
        this.hayVacio = hayVacio;
    }
    
    /**
     * Realiza el formateo del alfabeto y lo carga
     * para su impresión
     * @return String que se debe imprimir
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
