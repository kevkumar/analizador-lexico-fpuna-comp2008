/*
 * ExprReg.java
 *
 * Created on 4 de noviembre de 2008, 13:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Propietario
 */
public class ExprReg {
    
    /** Creates a new instance of ExprReg */
    public ExprReg() {
    }
    
    private StringBuffer regex;
    /**
     * Alfabeto sobre el que se construye la expresión regular.
     */
    private Alfabeto alfabeto;
    
    public ExprReg(String exp, Alfabeto alfabeto) {
        this.regex = new StringBuffer(regex);
        this.alfabeto = alfabeto;
    }
    
    
    
    /**
     * Consume y devuelve el siguiente Token detectado en la expresión
     * regular. Retornará un Token de tipo FIN si la expresion regular ha
     * terminado. Si no detecta un Token conocido, devuelve un Token de tipo
     * NONE.
     * @return Siguiente Token detectado.
     * @throws java.lang.Exception Lanza alguna excepción en caso de errores con los Tokens detectados.
     */
    public Token nextToken() throws Exception {
        Token resp = new Token(Token.NONE);
        
        String s = consumir();
        if ( s.equalsIgnoreCase(" ") || s.equalsIgnoreCase("\t") ) {
            //Es un espacio en blanco, avanzamos sin hacer nada
            resp = nextToken();
        } else if ( s.equalsIgnoreCase("(") ) {
            //Es un parentesis izquierdo
            resp = new Token(Token.PARI);
        } else if ( s.equalsIgnoreCase(")") ) {
            //Es un parentesis derecho
            resp = new Token(Token.PARD);
        } else if ( s.equalsIgnoreCase("*") ) {
            //Simbolo de la cerradura de Kleene
            resp = new Token(Token.KLNE);
        } else if ( s.equalsIgnoreCase("+") ) {
            //Simbolo de la cerradura positiva
            resp = new Token(Token.POSI);
        } else if ( s.equalsIgnoreCase("?") ) {
            //Simbolo que indica cero o un caso
            resp = new Token(Token.CEUN);
        } else if ( s.equalsIgnoreCase("|") ) {
            //Simbolo que indica OR
            resp = new Token(Token.OR);
        } else if ( s.equalsIgnoreCase("\\") ) {
            //Caracter de ESC
            resp = new Token(Token.ESC);
        } else if ( s.equalsIgnoreCase("") ) {
            //Fin de la regex
            resp = new Token(Token.FIN);
        } else if ( alfabeto.contiene(s) ) {
            //Es una letra del alfabeto
            resp = new Token(Token.ALFA,s);
        }
        
        //Si no es un token conocido se retornara un token NONE
        return resp;
    }
    
    /**
     * Método que consume un carácter de la expresión regular.
     * Por consumir decimos al proceso de quitar la primera letra de la expresión regular
     * y devolverla como un String.
     * @return Siguiente letra de la expresión regular.
     */
    private String consumir() {
        String resp = "";
        if (regex.length() > 0) {
            resp = Character.toString(regex.charAt(0));
            regex = regex.deleteCharAt(0);
        }
        return resp;
    }
    
}
