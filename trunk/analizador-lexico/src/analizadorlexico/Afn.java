/*
 * Afn.java
 *
 * Created on 7 de noviembre de 2008, 9:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

import java.util.Iterator;

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
            Arco arco1 = new Arco("E",eInicialNuevo,this.estadoInicial);
            /*añadimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",eInicialNuevo,eFinalNuevo);
            /*añadimos el enlace entre el estado inicial nuevo, y el estado final
             *nuevo*/
            eInicialNuevo.getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*añadimos el enlace entre el estado final viejo, y el estado final
             *nuevo*/
            this.getEstadoFinal().getArcos().add(arco3);
            
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            this.estados.getEstados().add(this.getEstadoInicial());
            this.estados.getEstados().add(this.getEstadoFinal());
            this.getEstadoInicial().setEInicial(true);
            this.getEstadoFinal().setEFinal(true);
            
        }else if(tipo ==2){
            /*aumentamos todos los estados en un valor para poder insertar
             *un nuevo estado inicial*/
            this.ajustarId(1);
            
            /*creamos un estado inicial nuevo y uno final nuevo*/
            Estado eInicialNuevo = new Estado(0);
            Estado eFinalNuevo = new Estado(this.estados.getEstados().size()+1);
            this.estadoInicial.setEInicial(false);
            this.estadoFinal.setEFinal(false);
            
            /*arco con vacio*/
            Arco arco1 = new Arco("E",eInicialNuevo,this.estadoInicial);
            /*añadimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",this.getEstadoFinal(),this.getEstadoInicial());
            /*añadimos el enlace entre el estado final viejo, y el estado inicial
             *viejo, esto es para que se de la oportunidad de 1 o más veces.*/
            this.getEstadoFinal().getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*añadimos el enlace entre el estado final viejo, y el estado final
             *nuevo*/
            this.getEstadoFinal().getArcos().add(arco3);
            
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            this.estados.getEstados().add(this.getEstadoInicial());
            this.estados.getEstados().add(this.getEstadoFinal());
            
            this.getEstadoInicial().setEInicial(true);
            this.getEstadoFinal().setEFinal(true);
            
        }else if(tipo == 3){
            /*aumentamos todos los estados en un valor para poder insertar
             *un nuevo estado inicial*/
            this.ajustarId(1);
            
            /*creamos un estado inicial nuevo y uno final nuevo*/
            Estado eInicialNuevo = new Estado(0);
            Estado eFinalNuevo = new Estado(this.estados.getEstados().size()+1);
            this.estadoInicial.setEInicial(false);
            this.estadoFinal.setEFinal(false);
            
            /*arco con vacio*/
            Arco arco1 = new Arco("E",eInicialNuevo,this.estadoInicial);
            /*añadimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",this.getEstadoFinal(),this.getEstadoInicial());
            /*añadimos el enlace entre el estado final viejo, y el estado inicial
             *viejo, esto es para que se de la oportunidad de 1 o más veces.*/
            this.getEstadoFinal().getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*añadimos el enlace entre el estado final viejo, y el estado final
             *nuevo*/
            this.getEstadoFinal().getArcos().add(arco3);
            
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            this.estados.getEstados().add(this.getEstadoInicial());
            this.estados.getEstados().add(this.getEstadoFinal());   
            
            this.getEstadoInicial().setEInicial(true);
            this.getEstadoFinal().setEFinal(true);
            
            /*ahora añadimos un enlace vacio entre el inicio nuevo y el fin nuevo
             *para asegurar de que podría darse la no aparición*/
            Arco arco4 = new Arco("E",this.getEstadoInicial(),this.getEstadoFinal());
            this.getEstadoInicial().getArcos().add(arco4);
            
        }
    }
    /**
     *Lleva a cabo las operaciones binarias de thompson
     * 1-REALIZA LA OPERACIÓN DE THOMPSON "|" QUE SERÍA UN OR
     * 2-REALIZA LA OPERACIÓN DE THOMPSON "ab" QUE SERIA UN a AND b
     * @param afn AFN sobre el cual se trabaja para unirlo con el afn que llama
     * a la función(this).
     * @param tipo Tipo de operador que se debe realizar, 1 indica |, 2 indica and
     */
    public void thompsonOpsBinarias(Afn afnAUnir, int tipo){
        /*Si el tipo es 1, debemos hacer un or entre los 2 afn's con los que
         *trabajamos*/
        if(tipo == 1){
            Estado eInicialNuevo = new Estado(0);
            Estado eFinalNuevo = new Estado(this.estados.getEstados().size()+ 
                afnAUnir.estados.getEstados().size()+1);
            
            /*Ahora lo que se hace es crear los clásicos enlaces vacios que
             *propone thompson entre el nuevo inicio y los dos inicios de cada
             *uno de los automatas(this y afnAUnir*/
            Arco arco1 = new Arco("E",eInicialNuevo,this.getEstadoInicial());
            eInicialNuevo.getArcos().add(arco1);
            
            Arco arco2 = new Arco("E",eInicialNuevo,afnAUnir.getEstadoInicial());
            eInicialNuevo.getArcos().add(arco2);
            
            /*Ahora lo que se hace es crear los clásicos enlaces vacios que
             *propone thompson entre el fin de cada uno de los automatas(this y
             *afnAUnir) y el nuevo estado final.*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            this.estadoFinal.getArcos().add(arco3);
            
            Arco arco4 = new Arco("E",afnAUnir.estadoFinal,eFinalNuevo);
            afnAUnir.estadoFinal.getArcos().add(arco4);
            
            /* Ahora restablecemos quienes son estados finales e iniciales
             * respectivamente, los estados finales e iniciales de los 2 automatas
             * en cuestion ya no los son, ahora los nuevos iniciales son los creados
             * más arriba*/
            this.estadoInicial.setEInicial(false);
            this.estadoFinal.setEFinal(false);
            
            afnAUnir.estadoInicial.setEInicial(false);
            afnAUnir.estadoFinal.setEFinal(false);
            
            eInicialNuevo.setEInicial(true);
            eFinalNuevo.setEFinal(true);
            
            /*Nos queda unir el afn this al afn recibido como parametro, para 
             *hacer esto, primero ajustamos los id's de los estados, para que luego
             *de la unión estos queden correctamente numerados*/
            this.ajustarId(1);//para compensar el estado inicial
            //para compensar el añadido del primer automata al inicio de este.
            afnAUnir.ajustarId(this.estados.getEstados().size()+1);
            
            /* Ahora procedemos a unir ambos automatas para lo cual tomamos cada
             uno de los estados del afnAUnir y los ponemos dentro de this*/
            Iterator it = afnAUnir.estados.getEstados().iterator();
            while(it.hasNext()){
                this.estados.getEstados().add((Estado) it.next());
            }
            /*Insertamos los estados dentro del automata resultante*/
            this.estados.getEstados().add(eInicialNuevo);
            this.estados.getEstados().add(eFinalNuevo);
            
         /*Si llega de tipo 2 hacemos un AND entre automatas*/   
        }else if(tipo == 2){
            /*Lo primero que hacemos es ajustar los id's de los estados del afnAUnir
             *para poder juntarlo con this.
             *Lo que hacemos es restar 1 porque el último estado de this no se utiliza
             * más*/
            afnAUnir.ajustarId(this.estados.getEstados().size() - 1);
            
            /*Tenemos 2 opciones, una es ignorar el último estado de this o ignorar
             el primer estado de afnAUnir al construir this, la opción que
             usaremos es la primera, para la cual debemos poner todos los enlaces que
             tenía el inicial del 2º automata con el fin del primer automata.
             Es decir, convertimos al nodo final del primer automata en el inicial
             del 2º. Entonces, cambiamos los enlaces con un iterador*/
            
            
            Iterator<Arco> it = afnAUnir.estadoInicial.getArcos().iterator();
            while(it.hasNext()){
                Arco aux = it.next();
                aux.setOrigen(this.estadoFinal);
                this.estadoFinal.getArcos().add(aux);
            }
            /*Ahora iteramos sobre afnAUnir y metemos los estados en this, con 
             *excepción del primero, cuyos enlaces ya fueron cambiados.*/
            Iterator<Estado> itEstados = afnAUnir.estados.getEstados().iterator();
            while(itEstados.hasNext()){
                /*Si algún estado apuntaba al estado inicial de afnAUnir, lo cambiamos
                 *para que apunte al final de this..*/
                Estado estadoAux = itEstados.next();
                Iterator<Arco> itArco = itEstados.next().getArcos().iterator();
                if(!estadoAux.isEInicial()){
                    while(itArco.hasNext()){
                        Arco arcoAux= itArco.next();
                        if(arcoAux.getDestino().getIdEstado() == afnAUnir.estadoInicial.getIdEstado()){
                            arcoAux.setDestino(this.estadoFinal);
                        }
                    }
                    this.getEstados().getEstados().add(estadoAux);
                }
            }
            this.estadoFinal.setEFinal(false);
            this.setEstadoFinal(afnAUnir.getEstadoFinal());
            this.getEstadoFinal().setEFinal(true);
        }
        
    }
    
    /**
     * ESTA FUNCIÓN NOS PERMITE AJUSTAR LA NUMERACIÓN DE LOS ESTADOS PARA CUANDO
     * INSERTAMOS ESTADOS Y HACE FALTA RE-ENUMERAR LOS ID DE LOS MISMOS
     * @param valor valor o cantidad que se debe sumar a los id de cada estados,
     * de acuerdo a la cantidad de estados que se agregan antes.
     */
    public void ajustarId(int valor){
        Iterator it = this.getEstados().getEstados().iterator();
        while (it.hasNext()){
            Estado estadoAux = (Estado) it.next();
            estadoAux.setIdEstado(estadoAux.getIdEstado()+ valor);
        }
    }
    /**
     *Esta función nos permite crear la matriz de estados
     * 
     **/
    public void cargaMatriz(){
        System.out.println("No implementado aún");
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
