/*
 * Afn.java
 *
 * Created on 7 de noviembre de 2008, 9:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Huguis
 *AQUI IMPLEMENTAMOS EL AUTOMATA FINITO NO DETERMINISTA, PARA ESTO UTILIZAMOS
 *LOS OPERADORES DEFINIDOS POR THOMPSON(OTRA CLASE) E IMPLEMENTAMOS SU ALGORITMO.
 *COMO SABEMOS UN AFN CONSTA DE:
 * 1- UNA EXPRESIÓN REGULAR
 * 2- DE UN CONJUNTO DE ESTADOS
 * 3- UN ESTADO INICIAL
 * 4- UN CONJUNTO DE ESTADOS FINALES(solo uno)
 * 5- UN CONJUNTO DE SIMBOLOS DE ENTRADA(ALFABETO)
 * 6- UNA MATRIZ DE ESTADOS VS SIMBOLOS DE ENTRADA, DONDE EL CONTENIDO SON CONJUNTOS DE ESTADOS
 */
public class Afn {
    private String expReg;
    private SetEstados estados;
    private Estado estadoInicial;
    private Estado estadoFinal;
    private Alfabeto alfabeto;
    private Estado [][] matriz;
    
    /** Creates a new instance of Afn */
    public Afn(String expReg, Alfabeto alf) {
        this.expReg = expReg;
        this.alfabeto = alf;
        this.estados = new SetEstados();
    }
    public Afn() {
    }

    public String getExpReg() {
        return expReg;
    }

    public void setExpReg(String expReg) {
        this.expReg = expReg;
    }

    public SetEstados getEstados() {
        return estados;
    }

    public void setEstados(SetEstados estados) {
        this.estados = estados;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(Estado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Estado[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Estado[][] matriz) {
        this.matriz = matriz;
    }
    
    
    
}
