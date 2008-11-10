/*
 * AnLex.java
 *
 * Created on 4 de noviembre de 2008, 13:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

/**
 *
 * @author Propietario
 */
public class AnLex {
    
    /**
     * Creates a new instance of AnLex
     */
    public AnLex() {
    }
    
    private String expr;
    /**
     * Alfabeto sobre el que se construye la expresión regular.
     */
    private Alfabeto alfabeto;
    
    public AnLex(String expr, Alfabeto alfabeto) {
        this.expr = expr;
        this.alfabeto = alfabeto;
    }
    
    /**
     *Ak realizamos la comparación entre los caracteres que vamos leyendo
     *y los que tenemos disponibles, que son * , +, ? , |, (, ), letras_del_alfabeto, 
     *$(caracter de fin).
     */
    public Caracter sgteCaracter() {
        
        Caracter token = new Caracter();
        token.setValor("");
        
        String valorActual = consumir();

        if ( valorActual.equals(" ") ) {
            token = sgteCaracter();
            
        } else if ( valorActual.equals("*")) {
            token.setValor("*".trim());
            
        }  else if ( valorActual.equals("+")) {
            token.setValor("+".trim());
            
        } else if ( valorActual.equals("?")) {
            token.setValor("?".trim());
            
        } else if ( valorActual.equals("|")) {
            token.setValor("|".trim());
            
        } else if ( valorActual.equals("(")) {
            token.setValor("(".trim());
            
        } else if ( valorActual.equals(")")) {
            token.setValor(")".trim());
            /*
             *Si es una letra del alfabeto seteamos a true la variable "delAlfabeto"
             *para poder identificarlo de manera simple
             */
        } else if (alfabeto.getCaracteres().contains(valorActual)) {
            token.setValor(valorActual.trim());
            token.setDelAlfabeto(true);
            
        } else if ( valorActual.equals("$")) {
            token.setValor("$".trim());
        }
        //EN CASO DE NO ENTRAR EN NINGUN IF, ENVIAMOS COMO RETORNO UNA 
        //CADENA VACIA
        return token;
    }
    
    public String consumir() {
        String caracter = "";
        if (!expr.isEmpty()){
            caracter = this.expr.substring(0,1).trim();
            this.expr = this.expr.substring(1).trim();
        }
        return caracter;
    }
    
}
