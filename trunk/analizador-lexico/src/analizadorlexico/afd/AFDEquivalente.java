/*
 * AFDEquivalente.java
 *
 * Created on 4 de noviembre de 2008, 19:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import analizadorlexico.Afn;
import analizadorlexico.Arco;
import analizadorlexico.Estado;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

/**
 * Clase que representa el AFD Equivalente que se obtiene a partir del AFN Equivalente
 * @author Polo
 */   
public class AFDEquivalente {
    
    /*Atributos de la clase*/
        
    /* Afn de donde se parte para generar el AFD equivalente */
    private Afn afn;
    
    /* Matriz de adyacencia */
    private String adyacencia [][];
    
    /* AFD inicial */
    private ConjuntoDeEstados estadoInicial;
    
    /* Conjunto de estados iniciales */
    private List<ConjuntoDeEstados> estadosIniciales;
    
    /* Conjunto de estados que van siendo marcados*/
    private List<ConjuntoDeEstados> estadosMarcados;
    
    /* Conjunto de estados finales */
    private List<ConjuntoDeEstados> estadosFinales;
            
    /* Transiciones del AFD*/
    private TransicionesAfd transiciones;

    public String getGrafo() {
        return grafo;
    }

    public void setGrafo(String grafo) {
        this.grafo = grafo;
    }
    private String grafo;
            
    public AFDEquivalente() {        
        this.setEstadosIniciales(new ArrayList<ConjuntoDeEstados>());
        this.setEstadosFinales(new ArrayList<ConjuntoDeEstados>());
        this.setEstadosMarcados(new ArrayList<ConjuntoDeEstados>());
    }
        
    /**
     * @param afn - Afn a partir del cual se genera el AFD equivalente
     */
    public AFDEquivalente(Afn afn) {
        this.setAfn(afn);    
        this.setEstadosIniciales(new ArrayList<ConjuntoDeEstados>());
        this.setEstadosFinales(new ArrayList<ConjuntoDeEstados>());
        this.setEstadosMarcados(new ArrayList<ConjuntoDeEstados>());
    }    
    
    /**
     * @param conjunto Conjunto de estados
     * 
     * @param simboloId simbolo con el q se espera transicion desde algun estado
     * 
     * @return ConjuntoDeArcos desde los que hay una transicion con el simbolo de ID
     */
    public ConjuntoDeEstados alcanzableSimbolo (ConjuntoDeEstados conjunto, 
            String simboloId) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();        
        for(Estado fin : conjunto.getLista()){                            
            for (Arco arco : fin.getArcos() ) {                
                if (arco.getIdArco().compareTo(simboloId) == 0 ) {
                    resultado.addEstado(arco.getDestino());
                }
            }
        }        
        return resultado;   
    }        
    
    /**
     * @param conjunto - Conjunto de estado de inicio
     * 
     * @return ConjuntoDeEstados - Estados alcanzables desde conjunto
     */
    public ConjuntoDeEstados alcamzablesConjunto (ConjuntoDeEstados conjunto) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();        
        Stack pila = new Stack();
        Estado fin;        
        pila.addAll(conjunto.getLista());
        resultado.setLista(conjunto.getLista());                
        while (! pila.isEmpty()) {
            fin = (Estado) pila.pop();                        
            for(Estado nuevo : alcamzablesEstado(fin).getLista()) {                
                if(!resultado.contieneEstado(nuevo) ) {
                    resultado.addEstado(nuevo);
                    pila.push(nuevo);
                }
            } 
        }        
        return resultado;        
    }
    
    /**      
     * @param inicio - Estado de inicio
     * @return ConjuntoDeEstados - Conjunto de estados a los que se puede 
     * llegar desde el estado s
     */
    public ConjuntoDeEstados alcamzablesEstado (Estado inicio) {
        ConjuntoDeEstados resultado = new ConjuntoDeEstados();
        Stack pila = new Stack();
        Estado fin;                
        pila.push(inicio);
        resultado.addEstado(inicio);
        
        while (! pila.isEmpty()) {
            fin = (Estado) pila.pop();            
            for (Arco a: fin.getArcos()) {                
                Estado nuevo = a.getDestino();
                // Como hacer para comparar si es vacio */
                // Si es vacio sera -1, esto se puede parametrizar
                if (a.getIdArco().equals("E")){
                    if (! resultado.contieneEstado(nuevo)) {
                        resultado.addEstado(nuevo);
                        pila.push(nuevo);
                    }
                }
            }
        }
        return resultado;
    }        
    
    /**
     * Para contruir transiciones a partir de los conjuntos de estados
     * Aplicando el algoritmo de construccion de sub conjuntos     
     */
    public void construirTransiciones() {        
        transiciones = new TransicionesAfd();                
        ConjuntoDeEstados conjuntoT, conjuntoU;                
        int nombreAsignado = 0;
        /* MENSAJE: Hace falta ordenar? */
        estadoInicial = alcamzablesEstado(getAfn().getEstadoInicial());        
        estadoInicial.setInicio(nombreAsignado++);  
        getEstadosIniciales().add(estadoInicial);
        
        String simbolo;
        conjuntoT = getEstadoDesmarcado();
        while (conjuntoT != null){
            for(Object s :  getAfn().getAlfabeto().getCaracteres()){            
                simbolo = (String) s;
                conjuntoU = alcamzablesConjunto(alcanzableSimbolo(conjuntoT, simbolo));
                if (conjuntoU.getLista().size() > 0) {
                    if (existeConjunto(conjuntoU) ) {
                        conjuntoU.setInicio(getNombre(conjuntoU));
                    } else {
                        conjuntoU.setInicio(nombreAsignado++);
                        getEstadosIniciales().add(conjuntoU);
                    }
                }
                transiciones.agregarTransiciones(conjuntoT, simbolo, conjuntoU);
            }            
            conjuntoT = getEstadoDesmarcado();
        }
        procesarEstadosFinales(getAfn().getEstadoFinal());
        construirMatriz();
    }

    /**
     * Metodo privado para contruir la matriz de adyacencia
     */    
    private void construirMatriz() {
        //throw new UnsupportedOperationException("Not yet implemented");
        setAdyacencia(new String[getEstadosMarcados().size()][getEstadosMarcados().size()]);
        ConjuntoDeEstados conjunto;
        ArrayList valores;
        Enumeration enumeracion = this.transiciones.getClaves(); 
        HashNodeTransicion nodo;        
        int origen;
        int destino;
        String elemento = "";
        
        while(enumeracion.hasMoreElements()){
            conjunto = (ConjuntoDeEstados) enumeracion.nextElement();
            valores = (ArrayList) transiciones.getValue(conjunto); 
                        
            origen = conjunto.getInicio();
            
            for(Object iteracion : valores){
                
                nodo = (HashNodeTransicion)iteracion;                
                destino = nodo.getEstadosDestino().getInicio(); 
                if (destino != -1 && nodo.getEstadosDestino().getLista().size() > 0) {
                    elemento = getAdyacencia()[origen][destino];
                    if(elemento == null) 
                        elemento = "";
                    else
                        if(!nodo.getEntrada().equalsIgnoreCase("")){
                            getAdyacencia()[origen][destino] = null;
                            continue;
                        }
                    getAdyacencia()[origen][destino] = elemento + nodo.getEntrada(); 
                }
                
            }
            
        }
    }
    
    /** Metodo Privado : para obtener un desmarcado y marcarlo
     * @return Conjunto de estado marcado
     */    
    private ConjuntoDeEstados getEstadoDesmarcado(){
        ConjuntoDeEstados resultado = null;                        
        if (!getEstadosIniciales().isEmpty()){
            resultado = getEstadosIniciales().get(0);
            getEstadosIniciales().remove(0);
            getEstadosMarcados().add(resultado);
        }        
        return resultado;
    }
    
    /**
     * @param conjunto - Conjunto de estado
     * @return true si existe el conjunto en la lista de estados     
     */
    private boolean existeConjunto(ConjuntoDeEstados conjunto){        
        for(ConjuntoDeEstados estados : estadosIniciales){
            if(estados.equals(conjunto))
                return true;
        }
        for(ConjuntoDeEstados estados : estadosMarcados){
            if(estados.equals(conjunto))
                return true;
        }   
        return false;
    } 
    
    /**
     * @param conjunto - Conjunto de estados
     * 
     * @return Nombre del conjunto
     */
    private int getNombre(ConjuntoDeEstados conjunto){        
        for(ConjuntoDeEstados estados : estadosIniciales){
            if(estados.equals(conjunto))
                return estados.getInicio();
        }
        for(ConjuntoDeEstados estados : estadosMarcados){
            if(estados.equals(conjunto))
                return estados.getInicio();
        }   
        return -1;
    }

    /**
     * @return Imprime estados finales
     */
    private String imprimirEstadosFinales() {        
        //throw new UnsupportedOperationException("Not yet implemented");
        String respuesta = "";
        int indice = 0;
        for(ConjuntoDeEstados estados : estadosFinales){                    
            indice++;
            respuesta = respuesta + estados.getInicio();
            if (!(indice == (getEstadosFinales().size()))) {
                respuesta = respuesta + ", ";
            }
        }
        return respuesta;
    }

    /**
     * @param estadoFinal - Estado final del afn
     */
    private void procesarEstadosFinales(Estado estadoFinal) {
        //throw new UnsupportedOperationException("Not yet implemented");
        for(ConjuntoDeEstados estados : estadosMarcados ){                    
            if (estados.contieneEstado(estadoFinal)) {
                estados.setConjFinal(true);
                getEstadosFinales().add(estados);
            }
        }
    }
    
    
    /**
     * 
     * @return Imprime el AFD
     */
    public String imprimir() {
        String respuesta = "";
        String espacio = "    ";
        
        respuesta = "Estado inicial:" + espacio + estadoInicial.getInicio();
        respuesta = respuesta + "\n\nEstados finales:" + espacio + "{ ";
        respuesta = respuesta + imprimirEstadosFinales() + " }";
        respuesta = respuesta + "\n\nConjunto de Estados:" + espacio + "{ ";
        int indice = 0;
        for(ConjuntoDeEstados conjunto : estadosMarcados){        
            indice++;
            respuesta = respuesta + conjunto.getInicio();
            if (!(indice == (getEstadosMarcados().size()))) {
                respuesta = respuesta + ", ";
            }
        }
        respuesta = respuesta + " }";
        
        return respuesta;        
    }        
    
    /**
     * Generar el archivo .dot para el graphviz
     * @param  nombreArchivo Nombre del archivo de salida
     */
    public void generarGrafo(String nombreArchivo){        
        try {
            FileWriter fw = new FileWriter(nombreArchivo);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);            
            String impr = "digraph finite_state_machine {\n" +
                    " size=\"8,5\" \n" +
                    " rankdir=LR \n" +
                    " graph [aspect=\"1.333\"] \n" +                                        
                    grafo +"}";
            System.out.println(" grafo " + grafo);
            System.out.println(" salida " + impr);
            salida.println(impr);
            salida.close();
            }
                catch (IOException ioex) {
                System.out.println("se presento el error: " + ioex);
            }

    }    
    
    /**
     * Para generar el grafo con el formato de salida
     */
    public void cargarGrafo(){                
        grafo = "node [shape = doublecircle]; " + estadoInicial.getIdGrupo();
        for(ConjuntoDeEstados estados : estadosFinales){
            grafo = grafo + " " + estados.getInicio();
        }
        grafo = grafo + "; \n" +
                " node [shape = circle]; \n ";
                
        for(int i = 0; i < adyacencia.length; i++){
            for(int j = 0; j < adyacencia.length; j++){                
                if(adyacencia[i][j] != null){
                    System.out.println(" __" + i + j );
                    grafo = grafo + i + " -> " + j + " [ label = \""+ adyacencia[i][j] +"\" ];\n";
                }
                System.out.println(" _______ " + adyacencia[i][j]);
            }
        }
    }
    
    public String imprimirMatriz(AfdMin min) {
        String respuesta = "";
        String espacio = "             ";
        
        respuesta = "Estado inicial:" + espacio + estadoInicial.getInicio();
        respuesta = respuesta + "<br>Estados finales:" + espacio + "{ ";
        respuesta = respuesta + imprimirEstadosFinales() + " }";
        respuesta = respuesta + "<br><br><br>Matriz de estados:" + espacio + "<br>";
        
        respuesta = respuesta + "<table width=\"200\" border=\"1\">";
  
        respuesta = respuesta + "<tr> <td> <blockquote>&nbsp;</blockquote></td>";    
        for(int i = 0; i < min.alfa.size(); i++){
            respuesta = respuesta + "<td>" + min.alfa.get(i) + "</td>";
        }        
        respuesta = respuesta + "</tr>";    
        for(int i = 0; i < adyacencia.length; i++){
            respuesta = respuesta + "<br>" + "<tr><td>" + i + "</td>";
            for(int j = 0; j < min.alfa.size(); j++){
                if(min.getMatrizEstAlf()[i][j] == null)
                    respuesta = respuesta + "<td>" + "-" + "</td>";
                else
                    respuesta = respuesta + "<td>" + min.getMatrizEstAlf()[i][j].getIdGrupo() + "</td>";                
            }
            respuesta = respuesta + "</tr>";
        }                        
        respuesta = respuesta + "</table>";
        return respuesta;        
    }        
    
    /**
     * Para validar la cadena si pertenece al lenguaje
     * @param Cadena - Cadena de entrada a validar
     * @return Retorna el id del estado final o -1
     */
    public int validarCadena(String cadena){        
        boolean valido = false;
        int nodo = 0;
        int resultado = -1;        
        String arco;
        for(int i = 0; i < cadena.length(); i++){
            valido = false;
            arco = cadena.charAt(i) + "";
            for(int j = 0; j < adyacencia.length; j++){
                if(adyacencia[nodo][j] != null && adyacencia[nodo][j].equalsIgnoreCase(arco)){
                    nodo = j;
                    valido = true;
                    break;
                }                    
            }
            if(!valido)
                return -1;
        }
        for(ConjuntoDeEstados estados : estadosFinales){
            if(estados.getInicio() == nodo)                
               resultado = nodo;
        }
        return resultado;
    }
    
    
    
    

    public List<ConjuntoDeEstados> getEstadosIniciales() {
        return estadosIniciales;
    }

    public void setEstadosIniciales(List<ConjuntoDeEstados> estadosIniciales) {
        this.estadosIniciales = estadosIniciales;
    }

    public List<ConjuntoDeEstados> getEstadosMarcados() {
        return estadosMarcados;
    }

    public void setEstadosMarcados(List<ConjuntoDeEstados> estadosMarcados) {
        this.estadosMarcados = estadosMarcados;
    }

    public List<ConjuntoDeEstados> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(List<ConjuntoDeEstados> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public Afn getAfn() {
        return afn;
    }

    public void setAfn(Afn afn) {
        this.afn = afn;
    }

    public String[][] getAdyacencia() {
        return adyacencia;
    }

    public void setAdyacencia(String[][] adyacencia) {
        this.adyacencia = adyacencia;
    }
}
