/*
 * Afn.java
 *
 * Created on 7 de noviembre de 2008, 9:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

import analizadorlexico.afd.AFDEquivalente;
import analizadorlexico.afd.ConjuntoDeEstados;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Huguis
 * AQUI IMPLEMENTAMOS EL AUTOMATA FINITO NO DETERMINISTA, PARA ESTO UTILIZAMOS
 * LOS OPERADORES DEFINIDOS POR THOMPSON E IMPLEMENTAMOS SU ALGORITMO.
 * COMO SABEMOS UN AFN CONSTA DE:
 * 1- UNA EXPRESIóN REGULAR
 * 2- DE UN CONJUNTO DE ESTADOS
 * 3- UN ESTADO INICIAL
 * 4- UN CONJUNTO DE ESTADOS FINALES(solo uno)
 * 5- UN CONJUNTO DE SIMBOLOS DE ENTRADA(ALFABETO)
 * 6- UNA MATRIZ DE ESTADOS VS SIMBOLOS DE ENTRADA, DONDE EL CONTENIDO SON CONJUNTOS DE ESTADOS
 */
public class Afn {
    
    private String expReg;
    private SetEstados estados;
    private ArrayList<ConjuntoDeEstados> estadosValidacion;
    private Estado estadoInicial;
    private Estado estadoFinal;
    private Alfabeto alfabeto;
    /*matriz de estados que genera un AFN*/
    private Estado [][] matriz;

    private String grafo;
    /*Cadena a a validar*/
    private String cadenaEntrada;
    private int posCadenaEntrada = -1;

    private String[][] matrizAdyacencia;
    
    /** Creamos una nueva instancia de AFN, donde se recibe la expresiï¿½n 
     *regular de entrada y el alfabeto.
     * @param expReg 
     * @param alf 
     */
    public Afn(String expReg, Alfabeto alf) {
        this.expReg = expReg;
        this.alfabeto = alf;
        this.estados = new SetEstados();
        this.estadosValidacion = new ArrayList<ConjuntoDeEstados>();
        this.estadoInicial = new Estado();
        this.estadoFinal = new Estado();
        
    }
    
    /**
     * Constructor vacio
     */
    public Afn() {        
        this.estados = new SetEstados();
        this.estadosValidacion = new ArrayList<ConjuntoDeEstados>();
        this.estadoInicial = new Estado();
        this.estadoFinal = new Estado();
    }
    
    /*CREAMOS LAS CONSTRUCCIONES DE THOMPSON PARA CADA UNO DE LOS OPERADORES*/
    
    /**
     * 1-REALIZA LA OPERACIï¿½N DE THOMPSON "?" PARA CERO O UNA APARICIï¿½N
     * 2-REALIZA LA OPERACIï¿½N DE THOMPSON "+" 1 O MAS APARICIONES
     * 3-REALIZA LA OPERACIï¿½N DE THOMPSON "*" PARA CERO O UNA APARICIï¿½N
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
            /*aï¿½adimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",eInicialNuevo,eFinalNuevo);
            /*aï¿½adimos el enlace entre el estado inicial nuevo, y el estado final
             *nuevo*/
            eInicialNuevo.getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*aï¿½adimos el enlace entre el estado final viejo, y el estado final
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
            /*aï¿½adimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",this.getEstadoFinal(),this.getEstadoInicial());
            /*aï¿½adimos el enlace entre el estado final viejo, y el estado inicial
             *viejo, esto es para que se de la oportunidad de 1 o mï¿½s veces.*/
            this.getEstadoFinal().getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*aï¿½adimos el enlace entre el estado final viejo, y el estado final
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
            /*aï¿½adimos el enlace entre el estado inicial nuevo y el que paso a ser
             *segundo*/
            eInicialNuevo.getArcos().add(arco1);
            
            /*arco con vacio*/
            Arco arco2 = new Arco("E",this.getEstadoFinal(),this.getEstadoInicial());
            /*aï¿½adimos el enlace entre el estado final viejo, y el estado inicial
             *viejo, esto es para que se de la oportunidad de 1 o mï¿½s veces.*/
            this.getEstadoFinal().getArcos().add(arco2);
            
            /*arco con vacio*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            /*aï¿½adimos el enlace entre el estado final viejo, y el estado final
             *nuevo*/
            this.getEstadoFinal().getArcos().add(arco3);
            
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            this.estados.getEstados().add(this.getEstadoInicial());
            this.estados.getEstados().add(this.getEstadoFinal());   
            
            this.getEstadoInicial().setEInicial(true);
            this.getEstadoFinal().setEFinal(true);
            
            /*ahora aï¿½adimos un enlace vacio entre el inicio nuevo y el fin nuevo
             *para asegurar de que podrï¿½a darse la no apariciï¿½n*/
            Arco arco4 = new Arco("E",this.getEstadoInicial(),this.getEstadoFinal());
            this.getEstadoInicial().getArcos().add(arco4);
            
        }
    }
    /**
     *Lleva a cabo las operaciones binarias de thompson
     * 1-REALIZA LA OPERACIï¿½N DE THOMPSON "|" QUE SERï¿½A UN OR
     * 2-REALIZA LA OPERACIï¿½N DE THOMPSON "ab" QUE SERIA UN a AND b
     * @param afnAUnir sobre el cual se trabaja para unirlo con el afn que llama
     * a la funciï¿½n(this).
     * @param tipo Tipo de operador que se debe realizar, 1 indica |, 2 indica and
     */
    public void thompsonOpsBinarias(Afn afnAUnir, int tipo){
        /*Si el tipo es 1, debemos hacer un or entre los 2 afn's con los que
         *trabajamos*/
        if(tipo == 1){
            Estado eInicialNuevo = new Estado(0);
            Estado eFinalNuevo = new Estado(this.estados.getEstados().size()+ 
                afnAUnir.estados.getEstados().size()+1);
            
            /*Ahora lo que se hace es crear los clï¿½sicos enlaces vacios que
             *propone thompson entre el nuevo inicio y los dos inicios de cada
             *uno de los automatas(this y afnAUnir*/
            Arco arco1 = new Arco("E",eInicialNuevo,this.getEstadoInicial());
            eInicialNuevo.getArcos().add(arco1);
            
            Arco arco2 = new Arco("E",eInicialNuevo,afnAUnir.getEstadoInicial());
            eInicialNuevo.getArcos().add(arco2);
            
            /*Ahora lo que se hace es crear los clï¿½sicos enlaces vacios que
             *propone thompson entre el fin de cada uno de los automatas(this y
             *afnAUnir) y el nuevo estado final.*/
            Arco arco3 = new Arco("E",this.estadoFinal,eFinalNuevo);
            this.estadoFinal.getArcos().add(arco3);
            
            Arco arco4 = new Arco("E",afnAUnir.estadoFinal,eFinalNuevo);
            afnAUnir.estadoFinal.getArcos().add(arco4);
            
            /* Ahora restablecemos quienes son estados finales e iniciales
             * respectivamente, los estados finales e iniciales de los 2 automatas
             * en cuestion ya no los son, ahora los nuevos iniciales son los creados
             * mï¿½s arriba*/
            this.estadoInicial.setEInicial(false);
            this.estadoFinal.setEFinal(false);
            
            afnAUnir.estadoInicial.setEInicial(false);
            afnAUnir.estadoFinal.setEFinal(false);
            
            eInicialNuevo.setEInicial(true);
            eFinalNuevo.setEFinal(true);
            
            /*Nos queda unir el afn this al afn recibido como parametro, para 
             *hacer esto, primero ajustamos los id's de los estados, para que luego
             *de la uniï¿½n estos queden correctamente numerados*/
            this.ajustarId(1);//para compensar el estado inicial
            //para compensar el aï¿½adido del primer automata al inicio de este.
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
            
            /*PONEMOS COMO ESTADOS FINALES A LOS NUEVOS FINALES*/
            this.setEstadoInicial(eInicialNuevo);
            this.setEstadoFinal(eFinalNuevo);
            
         /*Si llega de tipo 2 hacemos un AND entre automatas*/   
        }else if(tipo == 2){
            /*Lo primero que hacemos es ajustar los id's de los estados del afnAUnir
             *para poder juntarlo con this.
             *Lo que hacemos es restar 1 porque el ï¿½ltimo estado de this no se utiliza
             * mï¿½s*/
            afnAUnir.ajustarId(this.estados.getEstados().size() - 1);
            
            /*Tenemos 2 opciones, una es ignorar el ï¿½ltimo estado de this o ignorar
             el primer estado de afnAUnir al construir this, la opciï¿½n que
             usaremos es la primera, para la cual debemos poner todos los enlaces que
             tenï¿½a el inicial del 2ï¿½ automata con el fin del primer automata.
             Es decir, convertimos al nodo final del primer automata en el inicial
             del 2ï¿½. Entonces, cambiamos los enlaces con un iterador*/
            
            
            Iterator<Arco> it = afnAUnir.estadoInicial.getArcos().iterator();
            while(it.hasNext()){
                Arco aux = it.next();
                aux.setOrigen(this.estadoFinal);
                this.estadoFinal.getArcos().add(aux);
            }
            /*Ahora iteramos sobre afnAUnir y metemos los estados en this, con 
             *excepciï¿½n del primero, cuyos enlaces ya fueron cambiados.*/
            Iterator<Estado> itEstados = afnAUnir.estados.getEstados().iterator();
            while(itEstados.hasNext()){
                /*Si algï¿½n estado apuntaba al estado inicial de afnAUnir, lo cambiamos
                 *para que apunte al final de this..*/
                Estado estadoAux = itEstados.next();
                
                Iterator<Arco> itArco = estadoAux.getArcos().iterator();
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
     * ESTA FUNCIï¿½N NOS PERMITE AJUSTAR LA NUMERACIï¿½N DE LOS ESTADOS PARA CUANDO
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
     *Esta funciï¿½n nos permite crear la matriz de estados
     * 
     **/
//    public void cargaMatriz(){
//        int n = this.getEstados().getEstados().size();
//        this.matriz = new Estado[n][n];
//        for (int i = 0; i < n; i++) {
//            Estado tmp = estados.getEstado(i);
//            ConjuntoArcos ca = tmp.getConjuntoArcos();
//            for (int k = 0; k < ca.cantidad(); k++) {
//               Arco a = ca.getArco(k);
//               int m = a.getDestino().getNombre();
//               //matrizAdyacencia[i][m] = a.getNombre();
//               String nom = a.getNombre();
//               if(matrizAdyacencia[i][m] == null)
//                   matrizAdyacencia[i][m] = nom;
//               else
//                   matrizAdyacencia[i][m] = matrizAdyacencia[i][m] + nom;
//            }
//        }
//    }
    public void generarGrafos(String nombreArchivo) {
        try {
            FileWriter fw = new FileWriter(nombreArchivo);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.println("digraph finite_state_machine {\n size=\"8,5\" \n rankdir=LR \n graph [aspect=\"1.333\"] \n node [shape = doublecircle]; 0 10; \n node [shape = circle]; \n" + getGrafo() +"}");
            salida.close();
        } catch (IOException ioex) {
            System.out.println((new StringBuilder()).append("se presento el error: ").append(ioex.toString()).toString());
        }
        cargaMatriz();
    }
    
    
    public void cargaMatriz() {
        int n = getEstados().getEstados().size();
        setGrafo("");
        setMatrizAdyacencia(new String[n][n]);
        this.getEstados().ordenar();
        for (int i = 0; i < n; i++) {
            Estado tmp = (Estado)estados.getEstados().get(i);
            ArrayList ca = tmp.getArcos();
            for (int k = 0; k < ca.size(); k++) {
                Arco a = (Arco)ca.get(k);
                int m = a.getDestino().getIdEstado();
                String nom = a.getIdArco();
                if (getMatrizAdyacencia()[i][m] == null) {
                    getMatrizAdyacencia()[i][m] = nom;
                    setGrafo(getGrafo() + tmp.getIdEstado() + " -> " + m + " [ label = \"" + a.getIdArco()  + "\" ];\n");
                } else {
                    getMatrizAdyacencia()[i][m] = getMatrizAdyacencia()[i][m] + nom;
                }
            }
            
        }
        
        System.out.println((new StringBuilder()).append("Grafo formado \n").append(getGrafo()).toString());
    }
    /**
     * Imprime el afn en cuestiÃ³n
     * */
    public String imprimir() {
        String resp;
        String tabulador = "    ";
        
        resp = "Alfabeto:" + tabulador + this.getAlfabeto().imprimir();
        resp = resp + "\n\nEstado inicial:" + tabulador + this.estadoInicial.getIdEstado();
        resp = resp + "\n\nEstados finales:";
        resp = resp + tabulador + "{ " + this.estadoFinal.getIdEstado() + " }";
        resp = resp + "\n\nConjunto de Estados:" + tabulador + "{ ";
        for (int j=0; j < this.estados.getEstados().size(); j++) {
            resp = resp + (estados.getEstados().get(j).getIdEstado());
            if (! (j == (this.getEstados().getEstados().size() - 1)) ) {
                resp = resp + ", ";
            }
        }
        resp = resp + " }";
        return resp;
    }
    /**
     *Metodo que nos permite validar una cadena de entrada
     *Para empezar debemos setear cual es la expresion que sera analizada
     *dentro de cadenaEntrada. Y pasarle el estado inicial del afn para que comience
     *a ver si puede llegar a un estado final
     */
//    public Estado validacion(Estado estado) {
//        
//        Estado retorno = estado; 
//        int pos = this.getPosCadenaEntrada();
//        if(pos == -1 | pos == this.getCadenaEntrada().length()){
//            return retorno;
//        }
//        String caracter =this.getCadenaEntrada().charAt(pos)+"";
//        Arco enlace = this.buscarEstadoConLetra(caracter,retorno);
//        
//        
//        /*Si el retorno es null, quiere decir que no existe un estado directo
//         *al cual irme, entonces trato con los vacios*/
//        if (enlace == null) {
//            ArrayList<Arco> arcosVacios= estado.getArcosConVacio();
//            
//            for (Arco arco : arcosVacios) {
//                
//                Estado proximoEstado = arco.getDestino();       
//                int sizeActual = this.getEstadosValidacion().getEstados().size();
//                /*Insertamos el estado en que estamos*/
//                this.getEstadosValidacion().getEstados().add(sizeActual,proximoEstado);
//                retorno = this.validacion(proximoEstado);
//                if (retorno != null) {
//                    break;
//                }
//                this.estadosValidacion.getEstados().remove(sizeActual);
//            }
//        } else { 
//            Estado proximoEstado = enlace.getDestino();        
//            int sizeActual = this.getEstadosValidacion().getEstados().size();
//                /*Insertamos el estado en que estamos*/
//            this.getEstadosValidacion().getEstados().add(sizeActual,proximoEstado);
//            this.posCadenaEntrada++;
//            
//            retorno = this.validacion(proximoEstado);
//            
//        }
//        
//        return retorno;         
//    }
    
    private boolean validacion() {
        boolean validado = true; 
        
        AFDEquivalente afdAux = new AFDEquivalente(this);
        ConjuntoDeEstados S = new ConjuntoDeEstados();
        S = afdAux.alcamzablesEstado(this.getEstadoInicial());
        String c = this.cadenaEntrada.charAt(this.getPosCadenaEntrada())+"";
        
        this.estadosValidacion.add(S);
        
        while (c.compareToIgnoreCase("")!=0) {
            S = afdAux.alcanzableSimbolo(S, c);
            S = afdAux.alcamzablesConjunto(S);  
            /*MENSAJE: VER ESTA COMPARACIÓN*/
            if (S == null || S.getLista().size() == 0) {
                validado = false;
                break;
            }
                    
            this.estadosValidacion.add(S);
            
            c = this.cadenaEntrada.charAt(this.getPosCadenaEntrada())+""; 
        }
        
        if (validado) {
            validado = this.esFinal(S);
        }
        
        return validado; 
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
    
    /**
     * 
     * @return 
     */
    public Afn generar(){
        Analizador an = new Analizador(this.expReg, this.alfabeto);
        Afn retorno = an.analizar();
        return retorno;
    }

    public String getGrafo() {
        return grafo;
    }

    public void setGrafo(String grafo) {
        this.grafo = grafo;
    }

    public String[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public void setMatrizAdyacencia(String[][] matrizAdyacencia) {
        this.matrizAdyacencia = matrizAdyacencia;
    }

    private int getPosCadenaEntrada() {
        this.posCadenaEntrada++;
        if(this.posCadenaEntrada > this.getCadenaEntrada().length()){
            return -1;
        }
        return posCadenaEntrada;
    }
    
    private Arco buscarEstadoConLetra(String letra, Estado actual){
        Estado estado = null;
        Arco retorno = null;
        for(int i =0;i<this.getEstados().getEstados().size();i++){
            estado = this.getEstados().getEstados().get(i);
            for(Arco itArco: estado.getArcos()){
                if(itArco.getOrigen().getIdEstado() == actual.getIdEstado() && itArco.getIdArco().equals(letra)){
                    retorno = itArco;
                    return retorno;
                }
            }
        }
        return retorno;
    }

    public ArrayList<ConjuntoDeEstados> getEstadosValidacion() {
        return estadosValidacion;
    }

    public void setEstadosValidacion(ArrayList<ConjuntoDeEstados> estadosValidacion) {
        this.estadosValidacion = estadosValidacion;
    }

    public String getCadenaEntrada() {
        return cadenaEntrada;
    }

    public void setCadenaEntrada(String cadenaEntrada) {
        this.cadenaEntrada = cadenaEntrada;
    }

    private boolean esFinal(ConjuntoDeEstados S) {
        boolean validado = false; 
        for (Estado e : S.getLista() ) {
            validado = e.isEFinal();
            if (validado) {
                break;
            }
        }        
        return validado;

    }
    
}
