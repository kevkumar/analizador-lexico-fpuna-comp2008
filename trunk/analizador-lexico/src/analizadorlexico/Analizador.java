/*
 * Analizador.java
 *
 * Created on 9 de noviembre de 2008, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Huguis
 *Esta clase hace uso de la clase AFN para resolver la expresión regular y devolver
 *un AFN con sus estados.
 *BNF
 * ExpReg ->  simple Aux
 * Aux -> "|" simple Aux | E
 * simple -> basico Aux2
 * Aux2 -> basico Aux2 | E
 * basico -> lista op
 * op -> * | + | ? | E
 * lista ->agrupacion | alfabeto
 * agrupacion -> "(" ExpReg ")"
 * alfabeto -> [letras del alfabeto]
 */
public class Analizador {
    private String expReg;
    private Alfabeto alfabeto;
    private AnLex anLexico;
    private Afn afn;
    private Caracter preanalisis;
    /*Posición dentro de la Cadena*/
    private int pos;
    /** Creates a new instance of Analizador */
    public Analizador(String expReg, Alfabeto alfabeto) {
        this.setExpReg(expReg);
        this.setAlfabeto(alfabeto);
        this.setAnLexico(new AnLex(this.getExpReg(), this.getAlfabeto()));
        this.setAfn(new Afn());
        this.setPreanalisis(this.getAnLexico().sgteCaracter());
        if(this.getPreanalisis().getValor().compareTo("") == 0){
            System.err.println("Error en el primer caracter de la expresión regular");
            System.exit(2);
        }
        this.setPos(0);
    }
    
    public void match(Caracter caracter){
        if(this.getPreanalisis().getValor().compareTo(caracter.getValor()) == 0){
            this.setPreanalisis(this.anLexico.sgteCaracter());
            this.setPos(this.pos++);
        }else{
            System.err.println("Error en la posición"+ this.getPos()+"en el Analizador.java");
            System.exit(2);
        }
    }
    
    /* AHORA EMPEZAMOS A RESOLVER CADA UNA DE LAS PRODUCCIONES DEL BNF*/
    
    /**Resuelve la producción inicial, llamando adecuadamente a las demas
     *producciones, las cuales utilizan los operadores de thompson */
    public void expReg(){
        
    }
    

    public String getExpReg() {
        return expReg;
    }

    public void setExpReg(String expReg) {
        this.expReg = expReg;
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    public AnLex getAnLexico() {
        return anLexico;
    }

    public void setAnLexico(AnLex anLexico) {
        this.anLexico = anLexico;
    }

    public Afn getAfn() {
        return afn;
    }

    public void setAfn(Afn afn) {
        this.afn = afn;
    }

    public Caracter getPreanalisis() {
        return preanalisis;
    }

    public void setPreanalisis(Caracter preanalisis) {
        this.preanalisis = preanalisis;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
}
