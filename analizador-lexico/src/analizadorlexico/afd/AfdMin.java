/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *Esta clase implementa las operaciones necesarias para pasar desde
 * un Afd a un AFD con estados mínimos.
 * @author Hugo Meyer - Leopoldo Polleti
 */
public class AfdMin {
    /*Este afd es la entrada a ser procesada por el afd minimo*/
    private AFDEquivalente afd;
    /* Vienen siendo como el PiInicial y Pi actual*/
    private ArrayList<Grupos> conjuntoInicial;
    private ArrayList<Grupos> conjuntoActual;
    /*Matriz de estados versus alftabeto*/
    //ConjuntoDeEstados [][] matrizEstAlf;
    private int vectorGrupo [];
    /*variable que mantiene el nro de grupo creado*/
    private int contGrupo =0;
    
    public AfdMin(AFDEquivalente afd) {
        this.setAfd(afd);
        this.setConjuntoActual(new ArrayList<Grupos>());
        this.setConjuntoInicial(new ArrayList<Grupos>());
        this.vectorGrupo = new int [afd.getEstadosMarcados().size()];
    }
    
    /**
     *Esta función realiza la minimización del afd en cuestión y devuelve un afd
     *minimizado.
     **/
    public AfdMin minimizacion(){
        
        Grupos finales = new Grupos(contGrupo);
        contGrupo++;
        Grupos noFinales = new Grupos(contGrupo);
        
        ArrayList<String> alfa = (ArrayList<String>) this.afd.getAfn().getAlfabeto().getCaracteres();
        
        finales.setGrupo((ArrayList<ConjuntoDeEstados>) this.afd.getEstadosFinales());
        /* Iteramos sobre los estados marcados, y si no son finales los añadimos
         *al grupo de no finales.*/
        Iterator itConjEstados = this.afd.getEstadosMarcados().iterator();
        
        while(itConjEstados.hasNext()){
            ConjuntoDeEstados aux = (ConjuntoDeEstados) itConjEstados.next();
            if(!aux.isConjFinal()){
                noFinales.getGrupo().add(aux);
                this.vectorGrupo[aux.getInicio()] = 1;
            }else{
                this.vectorGrupo[aux.getInicio()] = 0;
            }
        }
        this.conjuntoInicial.add(finales);
        this.conjuntoInicial.add(noFinales);
        boolean continuar =true;
        
        while(continuar){
            for(Grupos grupo : this.conjuntoInicial){
                if(grupo.getGrupo().size() > 1){
                    Iterator<ConjuntoDeEstados> itElemGrupo = grupo.getGrupo().iterator();
                    while(itElemGrupo.hasNext()){
                        ConjuntoDeEstados conjAux = itElemGrupo.next();
                        /*Tomamos un caracter del alfabeto y lo revisamos*/
                        for(String caracter : alfa){
                            /*TOMAMOS UN ELEMENTO DE NUESTRO GRUPO, Y VEMOS SI NO APUNTA
                             *A ALGUN ELEMENTO QUE ESTA FUERA DE NUESTRO GRUPO, SI ES ASI
                             *LO DEBEMOS PONER EN OTRO GRUPO NUEVO O YA EXISTENTE QUE TENGA
                             *ELEMENTOS QUE APUNTE AL MISMO GRUPO*/
                            for(int j=0; j<grupo.getGrupo().size();j++){
                                if(vectorGrupo[conjAux.getInicio()] != vectorGrupo[j]){
                                    /*CREAR NUEVO GRUPO Y AÑADIRLO AHI, ACTUALIZAR
                                     *ADEMAS EL VECTORGRUPO Y REMOVER AL CONJAUX
                                     *DEL GRUPO ACTUAL EN EL QUE ESTA.*/
                                }
                            }
                        }
                        
                    }
                    
                }
                
            }
        }
        
        
        
        return this;
    }
    
    
    public /*Este afd es la entrada a ser procesada por el afd minimo*/
    AFDEquivalente getAfd() {
        return afd;
    }
    
    public void setAfd(AFDEquivalente afd) {
        this.afd = afd;
    }

    public ArrayList<Grupos> getConjuntoInicial() {
        return conjuntoInicial;
    }

    public void setConjuntoInicial(ArrayList<Grupos> conjuntoInicial) {
        this.conjuntoInicial = conjuntoInicial;
    }

    public ArrayList<Grupos> getConjuntoActual() {
        return conjuntoActual;
    }

    public void setConjuntoActual(ArrayList<Grupos> conjuntoActual) {
        this.conjuntoActual = conjuntoActual;
    }
    
}
