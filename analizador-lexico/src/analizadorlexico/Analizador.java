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
 *Esta clase hace uso de la clase AFN para resolver la expresi�n regular y devolver
 *un AFN con sus estados.
 *BNF
 * ExpReg ->  simple Aux1
 * Aux1 -> "|" simple Aux1 | E
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
    /*Posici�n dentro de la Cadena*/
    private int pos;
    /** Creates a new instance of Analizador */
    public Analizador(String expReg, Alfabeto alfabeto) {
        this.setExpReg(expReg);
        this.setAlfabeto(alfabeto);
        this.setAnLexico(new AnLex(this.getExpReg(), this.getAlfabeto()));
        this.setAfn(new Afn());
        this.setPreanalisis(this.getAnLexico().sgteCaracter());
        if(this.getPreanalisis().getValor().compareTo("") == 0){
            System.err.println("Error en el primer caracter de la expresi�n regular");
            System.exit(2);
        }
        this.setPos(0);
    }
    /**
     *DEBERIA AGREGAR MAS DETALLES A LOS ERRORES QUE SE LANZAN.
     */
    public void match(Caracter caracter){
        if(this.getPreanalisis().getValor().compareTo(caracter.getValor()) == 0){
            this.setPreanalisis(this.anLexico.sgteCaracter());
            this.setPos(this.pos++);
        }else{
            System.err.println("Error en la posici�n"+ this.getPos()+"en el Analizador.java");
            System.exit(2);
        }
    }
    
    /* AHORA EMPEZAMOS A RESOLVER CADA UNA DE LAS PRODUCCIONES DEL BNF*/
    
    /**Resuelve la producci�n inicial, llamando adecuadamente a las demas
     *producciones, las cuales utilizan los operadores de thompson */
    public Afn expReg(){
        Afn afn1=null;
        Afn afn2=null;
        afn1 = this.simple();
        afn2 = this.aux1();
        /*Debemos verificar si su resultado no es "E"-vacio, porque
         *si no lo fuera debemos concatenarlo con or con el automata generado en simple()
         *puesto que asi cumplimos con nuestras producc�ones.*/
        if(afn1!=null && afn2!=null){
            afn1.thompsonOpsBinarias(afn2,1);
        }
        this.setAfn(afn1);
        this.afn.setAlfabeto(this.alfabeto);
        /**Seteamos al resultante con la verdadera expresi�n regular resultante*/
        this.afn.setExpReg(this.expReg);
        
        return afn1;
    }
    
    public Afn simple(){
        Afn afns1=null;
        Afn afns2=null;
        afns1 = basico();
        afns2 = aux2();
        if(afns1 !=null && afns2 != null){
            afns1.thompsonOpsBinarias(afns2,2);
        }
        return afns1;
    }
    /*FALTA*/
    public Afn basico(){
        Afn afnb1=null;
        afnb1 = lista();
        /*Al resultado de ejecutar lista le aplicamos alg�n operador unario*/
        if(afnb1 != null){
            String operacion = this.preanalisis.getValor().trim();
            if(operacion.equals("*")){
                afnb1.thompsonOps(afnb1,3);                
            }else if(operacion.equals("+")){
                afnb1.thompsonOps(afnb1,2);
            }else if(operacion.equals("?")){
                afnb1.thompsonOps(afnb1,1);
            }
        }
        return afnb1;
    }
    
    public Afn lista(){
        Afn afnLis1=null;
        if(this.preanalisis.getValor().equals("(")){
            afnLis1 = agrupacion();
        }else{
            afnLis1 = alfabeto();
        }
        return afnLis1;
    }
    
    public Afn agrupacion(){
        Afn afna1=null;
        Caracter parAbierto = new Caracter();
        parAbierto.setValor("(");
        this.match(parAbierto);
        afna1 = expReg();
        
        Caracter parCerrado = new Caracter();
        parCerrado.setValor(")");
        this.match(parCerrado);
        
        return afna1;
    }
    /*VERIFICAR A FONDO ESTA FUNCI�N SI FUNCIONA*/
    public Afn alfabeto(){
        Afn afnAlf1=null;
        if(!this.preanalisis.getValor().trim().equals("$")){
            //LLamamos a la funci�n que crea un AFN que va de un estado
            //a otro por medio de la transici�n de preanalisis.
            afnAlf1=construirAfnSimple(this.preanalisis.getValor());
            this.match(this.preanalisis);
        }
        return afnAlf1;
    }
    
    public Afn aux1(){
        Afn afnAux1=null;
        Caracter or = new Caracter();
        or.setValor("|");
        if (this.preanalisis.equals(or)) {
            this.match(or);
            afnAux1 = expReg();
        }
        return afnAux1;
    }
    
    public Afn aux2(){
        Afn afnAux2=null;
        if ( (!preanalisis.getValor().equals("$")) &&
            (this.alfabeto.getCaracteres().contains(preanalisis.getValor())) ||
            this.preanalisis.getValor().equals("(")){
            
            afnAux2 = simple();
        }
        return afnAux2;
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
    /**
     *Construye un afn simple con 2 estados y un string que los une, o un arco
     *con el valor del string que se recibe.
     **/
    private Afn construirAfnSimple(String valorTransicion) {
        int nombreEstado = 0;
        Afn retorno = new Afn();
        retorno.setAlfabeto(this.alfabeto);
        Estado estado1 = new Estado(0);
        Estado estado2 = new Estado(1);
        Arco arco = new Arco(valorTransicion.trim(),estado1,estado2);
        estado1.getArcos().add(arco);
        retorno.getEstados().getEstados().add(estado1);
        retorno.getEstados().getEstados().add(estado2);
        retorno.setEstadoInicial(estado1);
        retorno.setEstadoFinal(estado2);
        return retorno;
    }
    
    public Afn analizar(){
        this.preanalisis = this.anLexico.sgteCaracter();
        Afn retorno = null;
        while(!this.preanalisis.getValor().equals("$")){
            retorno = this.expReg();
        }
        return retorno;
    }
    
}
