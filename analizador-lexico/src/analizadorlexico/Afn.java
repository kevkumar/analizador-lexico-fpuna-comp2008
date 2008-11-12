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
 * AQUI IMPLEMENTAMOS EL AUTOMATA FINITO NO DETERMINISTA, PARA ESTO UTILIZAMOS
 * LOS OPERADORES DEFINIDOS POR THOMPSON E IMPLEMENTAMOS SU ALGORITMO.
 * COMO SABEMOS UN AFN CONSTA DE:
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
    /*matriz de estados que genera un AFN*/
    private Estado [][] matriz;
    
    /** Creamos una nueva instancia de AFN, donde se recibe la expresión 
     *regular de entrada y el alfabeto.
     */
    
    public Afn(String expReg, Alfabeto alf) {
        this.expReg = expReg;
        this.alfabeto = alf;
        this.estados = new SetEstados();
    }
    
    /**
     * Constructor vacio
     */
    public Afn() {
    }
    
    /*CREAMOS LAS CONSTRUCCIONES DE THOMPSON PARA CADA UNO DE LOS OPERADORES*/
    
    /**
     * 1-REALIZA LA OPERACIÓN DE THOMPSON "?" PARA CERO O UNA APARICIÓN
     * 2-REALIZA LA OPERACIÓN DE THOMPSON "+" 1 O MAS APARICIONES
     * 3-REALIZA LA OPERACIÓN DE THOMPSON "*" PARA CERO O UNA APARICIÓN
     * @param afn AFN sobre el cual se trabaja
     * @param tipo Tipo de operador que se debe realizar, 1 indica ?, 2 indica +, 3 indica *
     */
    public void thompsonOps(Afn afn, int tipo) {
        /*Resolvemos ?*/
        if(tipo ==1){
            
            /*aumentamos todos los estados en un valor para poder insertar
             *un nuevo estado inicial*/
            this.ajustarId(1);
            /*creamos un estado inicial nuevo y uno final nuevo*/
            Estado eInicialNuevo = new Estado(0);
            Estado eFinalNuevo = new Estado(this.estados.getEstados().size()+1);
            this.estadoInicial.setEInicial(false);
            this.estadoFinal.setEFinal(false);
            
            /*arco con vacio*/
            Arco arco = new Arco("E",eInicialNuevo,this.estadoInicial);
            /*añadimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco);
            
            /*arco con vacio*/
            arco = new Arco("E",eInicialNuevo,eFinalNuevo);
            /*añadimos el enlace entre el estado inicial nuevo, y el estado final
             *nuevo*/
            eInicialNuevo.getArcos().add(arco);
            
            /*arco con vacio*/
            arco = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*añadimos el enlace entre el estado final viejo, y el estado final
             *nuevo*/
            this.getEstadoFinal().getArcos().add(arco);
            
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            this.estados.getEstados().add(this.getEstadoInicial());
            this.estados.getEstados().add(this.getEstadoFinal());
            
        }
    }
    /**
     * ESTA FUNCIÓN NOS PERMITE AJUSTAR LA NUMERACIÓN DE LOS ESTADOS PARA CUANDO
     * INSERTAMOS ESTADOS Y HACE FALTA RE-ENUMERAR LOS ID DE LOS MISMOS
     * @param valor valor o cantidad que se debe sumar a los id de cada estados, de acuerdo a la
     * cantidad de estados que se agregan antes.
     */
     public void ajustarId(int valor){
        Iterator it = this.estados.getIterator();
        while (it.hasNext()){
            Estado e = (Estado) it.next();
            e.setId(e.getId()+incremento);
        }

    }
    
    
    /*
     *SETTERS Y GETTERS
     */
    /**
     * 
     * @return 
     */
    public String getExpReg() {
        return expReg;
    }

    /**
     * 
     * @param expReg 
     */
    public void setExpReg(String expReg) {
        this.expReg = expReg;
    }

    /**
     * 
     * @return 
     */
    public SetEstados getEstados() {
        return estados;
    }

    /**
     * 
     * @param estados 
     */
    public void setEstados(SetEstados estados) {
        this.estados = estados;
    }

    /**
     * 
     * @return 
     */
    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    /**
     * 
     * @param estadoInicial 
     */
    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    /**
     * 
     * @return 
     */
    public Estado getEstadoFinal() {
        return estadoFinal;
    }

    /**
     * 
     * @param estadoFinal 
     */
    public void setEstadoFinal(Estado estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    /**
     * 
     * @return 
     */
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    /**
     * 
     * @param alfabeto 
     */
    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    /**
     * 
     * @return 
     */
    public Estado[][] getMatriz() {
        return matriz;
    }

    /**
     * 
     * @param matriz 
     */
    public void setMatriz(Estado[][] matriz) {
        this.matriz = matriz;
    }
    
    
    
}
