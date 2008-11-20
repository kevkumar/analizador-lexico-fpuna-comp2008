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
 * Esta clase hace uso de la clase AFN para resolver la expresión regular y devolver
 * un AFN con sus estados.
 * BNF
 * ExpReg ->  simple Aux1
 * Aux1 -> "|" simple Aux1 | E
 * simple -> basico Aux2
 * Aux2 -> basico Aux2 | E
 * basico -> lista op
 * op -> * | + | ? | E
 * lista ->agrupacion | alfabeto
 * agrupacion -> "(" ExpReg ")"
 * alfabeto -> [letras del alfabeto]
 * @author Hugo Daniel Meyer - Leopoldo Poletti
 */
public class Analizador {
    private String expReg;
    private Alfabeto alfabeto;
    private AnLex anLexico;
    private Afn afn;
    private Caracter preanalisis;
    /*Posiciï¿½n dentro de la Cadena*/
    private int pos;
    /**
     * Crea una nueva instancia de la clase
     * @param expReg Expresión Regular
     * @param alfabeto Alfabeto del Lenguaje
     */
    public Analizador(String expReg, Alfabeto alfabeto) {
        this.setExpReg(expReg);
        this.setAlfabeto(alfabeto);
        this.setAnLexico(new AnLex(this.getExpReg(), this.getAlfabeto()));
        this.setAfn(new Afn());
        this.setPreanalisis(this.getAnLexico().sgteCaracter());
        if(this.getPreanalisis().getValor().compareTo("") == 0){
            System.err.println("Error en el primer caracter de la expresiï¿½n regular");
            System.exit(2);
        }
        this.setPos(0);
    }
    /**
     * Realiza el match de la expresión regular contra el valor actual
     * de preanalisis.
     * @param caracter Caracter con el cual hacer el match
     */
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
    
    /**
     * Resuelve la producción inicial, llamando adecuadamente a las demas
     * producciones, las cuales utilizan los operadores de thompson
     * @return Afn resultante luego del desarrollo de la producción
     */
    public Afn expReg(){
        Afn afn1=null;
        Afn afn2=null;
        afn1 = this.simple();
        afn2 = this.aux1();
        /*Debemos verificar si su resultado no es "E"-vacio, porque
         *si no lo fuera debemos concatenarlo con or con el automata generado en simple()
         *puesto que asi cumplimos con nuestras produccï¿½ones.*/
        if(afn1!=null && afn2!=null){
            afn1.thompsonOpsBinarias(afn2,1);
        }
        this.setAfn(afn1);
        this.afn.setAlfabeto(this.alfabeto);
        /**Seteamos al resultante con la verdadera expresiï¿½n regular resultante*/
        this.afn.setExpReg(this.expReg);
        
        return afn1;
    }
    
    /**
     * Resuelve la producción:
     * simple -> basico Aux2
     * @return AFN resultante
     */
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
    
    /**
     * Resuelve la producción:
     * basico -> lista op
     * @return AFN resultante de aplicar las operaciones
     */
    public Afn basico(){
        Afn afnb1=null;
        afnb1 = lista();
        /*Al resultado de ejecutar lista le aplicamos algï¿½n operador unario*/
        if(afnb1 != null){
            String operacion = this.preanalisis.getValor().trim();
            if(operacion.equals("*")){
                afnb1.thompsonOps(afnb1,3);                
                this.match(this.preanalisis);
            }else if(operacion.equals("+")){
                afnb1.thompsonOps(afnb1,2);
                this.match(this.preanalisis);
            }else if(operacion.equals("?")){
                afnb1.thompsonOps(afnb1,1);
                this.match(this.preanalisis);
            }
        }
        return afnb1;
    }
    
    /**
     * Resuelve la producción:
     * lista ->agrupacion | alfabeto
     * @return AFN resultante de aplicar las operaciones
     */
    public Afn lista(){
        Afn afnLis1=null;
        if(this.preanalisis.getValor().equals("(")){
            afnLis1 = agrupacion();
        }else{
            afnLis1 = alfabeto();
        }
        return afnLis1;
    }
    
    /**
     * Resuelve la producción
     * agrupacion -> "(" ExpReg ")"
     * Para esto hace matcheo para revisar los parentesis abiertos y
     * cerrados y llama a la producción inicial de vuelta.
     * @return AFN resultante
     */
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
    
    /**
     * Resuelve la producción:
     * alfabeto -> [letras del alfabeto]
     * Para esto utiliza una función que construye un afn
     * simple con la letra del alfabeto que corresponda
     * @return AFN resultante
     */
    public Afn alfabeto(){
        Afn afnAlf1=null;
        if(!this.preanalisis.getValor().trim().equals("$")){
            //LLamamos a la funciï¿½n que crea un AFN que va de un estado
            //a otro por medio de la transiciï¿½n de preanalisis.
            afnAlf1=construirAfnSimple(this.preanalisis.getValor());
            this.match(this.preanalisis);
        }
        return afnAlf1;
    }
    
    /**
     * Resuelve la producción:
     * Aux1 -> "|" simple Aux1 | E
     * Si encuentra el or, llama de vuelta a la producción inicial
     * @return AFN Resultante
     */
    public Afn aux1(){
        Afn afnAux1=null;
        Caracter or = new Caracter();
        or.setValor("|");
        if (this.preanalisis.getValor().trim().equals(or.getValor())) {
            this.match(this.preanalisis);
            afnAux1 = expReg();
        }
        return afnAux1;
    }
    
    /**
     * Resuelve la producción
     * Aux2 -> basico Aux2 | E
     * Si se encuentra una agrupación o letra del alfabeto
     * se llama de vuelta a simple.
     * @return AFN resultante
     */
    public Afn aux2(){
        Afn afnAux2=null;
        if ( (!preanalisis.getValor().equals("$")) &&
            (this.alfabeto.getCaracteres().contains(preanalisis.getValor())) ||
            this.preanalisis.getValor().equals("(")){
            
            afnAux2 = simple();
        }
        return afnAux2;
    }
    
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
    public AnLex getAnLexico() {
        return anLexico;
    }

    /**
     * 
     * @param anLexico 
     */
    public void setAnLexico(AnLex anLexico) {
        this.anLexico = anLexico;
    }

    /**
     * 
     * @return 
     */
    public Afn getAfn() {
        return afn;
    }

    /**
     * 
     * @param afn 
     */
    public void setAfn(Afn afn) {
        this.afn = afn;
    }

    /**
     * 
     * @return 
     */
    public Caracter getPreanalisis() {
        return preanalisis;
    }

    /**
     * 
     * @param preanalisis 
     */
    public void setPreanalisis(Caracter preanalisis) {
        this.preanalisis = preanalisis;
    }

    /**
     * 
     * @return 
     */
    public int getPos() {
        return pos;
    }

    /**
     * 
     * @param pos 
     */
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
        estado1.setEInicial(true);
        estado1.setEFinal(false);
        estado2.setEInicial(false);
        estado2.setEFinal(true);
        retorno.setEstadoInicial(estado1);
        retorno.setEstadoFinal(estado2);
        return retorno;
    }
    
    /**
     * Llama a la producción inicial, mientras haya algo x consumir.
     * @return 
     */
    public Afn analizar(){
        Afn retorno = null;
        while(!this.preanalisis.getValor().equals("$")){
            retorno = this.expReg();
        }
        return retorno;
    }
    
}
