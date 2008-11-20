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
 * Analiza la cadena introducida de manera Léxica, utilizando las
 * funciones de sgteCaracter y consumir
 * @author Leopoldo Poletti - Hugo Daniel Meyer
 */
public class AnLex {
    
    /**
     * Nueva instancia de la clase
     */
    public AnLex() {
    }
    
    private String expr;
    /**
     * Alfabeto sobre el que se construye la expresión regular.
     */
    private Alfabeto alfabeto;
    
    /**
     * Nueva instancia creada a partir de una expresión regular y su 
     * alfabeto.
     * @param expr Expresión regular de entrada
     * @param alfabeto Alfabeto de entrada
     */
    public AnLex(String expr, Alfabeto alfabeto) {
        this.expr = expr;
        this.alfabeto = alfabeto;
    }
    
    /**
     * Aquí realizamos la comparación entre los caracteres que vamos leyendo
     * y los que tenemos disponibles, que son * , +, ? , |, (, ), letras_del_alfabeto, 
     * $(caracter de fin).
     * @return El siguiente caracter luego de consumir el actual
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
    
    /**
     * Borra el caracter consumido y actualiza la cadena.
     * @return La cadena sin el caracter que ya fue consumido
     */
    public String consumir() {
        String caracter = "";
        if (!expr.isEmpty()){
            caracter = this.expr.substring(0,1).trim();
            this.expr = this.expr.substring(1).trim();
        }
        return caracter;
    }
    
}
