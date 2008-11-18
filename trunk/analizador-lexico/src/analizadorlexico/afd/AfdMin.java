/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizadorlexico.afd;

import analizadorlexico.Alfabeto;
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
    private ArrayList<Grupo> conjuntoInicial;
    private ArrayList<Grupo> conjuntoActual;
    /*Matriz de estados versus alftabeto*/
    private ConjuntoDeEstados [][] matrizEstAlf;
    private int vectorGrupo [];
    /*variable que mantiene el nro de grupo creado*/
    private int contGrupo =0;
    
    public AfdMin(AFDEquivalente afd) {
        this.setAfd(afd);
        this.setConjuntoActual(new ArrayList<Grupo>());
        this.setConjuntoInicial(new ArrayList<Grupo>());
        this.vectorGrupo = new int [afd.getEstadosMarcados().size()];
    }
    
    /**
     *Esta función realiza la minimización del afd en cuestión y devuelve un afd
     *minimizado.
     **/
    public AfdMin minimizacion(){
        
        Grupo finales = new Grupo(contGrupo);
        contGrupo++;
        Grupo noFinales = new Grupo(contGrupo);
        Grupo grupoAIntroducir;
        
        
        ArrayList<String> alfa = (ArrayList<String>) this.afd.getAfn().getAlfabeto().getCaracteres();
        
        finales.setGrupo((ArrayList<ConjuntoDeEstados>) this.afd.getEstadosFinales());
        finales.setFinales(true);
        /* Iteramos sobre los estados marcados, y si no son finales los añadimos
         *al grupo de no finales.*/
        Iterator itConjEstados = this.afd.getEstadosMarcados().iterator();
        
        while(itConjEstados.hasNext()){
            
            ConjuntoDeEstados aux = (ConjuntoDeEstados) itConjEstados.next();
            if(!aux.isConjFinal()){
                aux.setIdGrupo(1);
                noFinales.getGrupo().add(aux);
                this.vectorGrupo[aux.getInicio()] = 1;
            }else{
                aux.setIdGrupo(0);
                this.vectorGrupo[aux.getInicio()] = 0;
            }
        }
        this.conjuntoInicial.add(finales);
        this.conjuntoInicial.add(noFinales);
        boolean continuar =true;
        /*guarda el valor del caracter leido de la matriz*/
        String valorCaracter;
        /*SIMPLE CONTADOR PARA VERIFICAR LA CANTIDAD DE ACIERTOS DE LAS LETRAS*/
        int cont =0;
        this.inicializarMatriz();
        while(continuar){
            int tamañoConjInicial = this.conjuntoInicial.size();
            for(Grupo grupo : this.conjuntoInicial){
                if(grupo.getGrupo().size() > 1){
                    
                    boolean seCreoNuevoGrupo = true;
                    contGrupo++;
                    grupoAIntroducir = new Grupo(contGrupo);
                    Iterator<ConjuntoDeEstados> itElemGrupo = grupo.getGrupo().iterator();
                    
                    while(itElemGrupo.hasNext()){
                        ConjuntoDeEstados conjAux = itElemGrupo.next();
                        /*Si es un elemento ya añadido al grupo actual, lo remuevo,
                         *sino creo un nuevo grupo, dado que los que ya fueron
                         *cargados en grupo a introducir son todos los que apuntan a un mismo
                         *grupo*/
                        if(grupoAIntroducir.getGrupo().contains(conjAux)){
                            /*Luego revisamos si el grupo ya no fue cargado en el conjunto actual*/
                            itElemGrupo.remove();
                            continue;
                        }else if(grupoAIntroducir.getGrupo().size() > 0){
                            this.conjuntoActual.add(grupoAIntroducir);
                            contGrupo++;
                            grupoAIntroducir = new Grupo(contGrupo);
                        }
                        /*TOMADO EL ESTADO LO REMUEVO*/
                        itElemGrupo.remove();
                        grupoAIntroducir.getGrupo().add(conjAux);
                        /*CREO UN NUEVO ITERADOR SOBRE LOS ELEMENTOS RESTANTES EN EL GRUPO*/
                        Iterator<ConjuntoDeEstados> itNextEGrupo = grupo.getGrupo().iterator();
                        
                        /*SI YA NO AGARRAMOS MAS ESTADOS PORQUE YA ES EL ULTIMO, CARGAMOS
                         *SU GRUPO EN CONJUNTO ACTUAL*/
                        if(!itNextEGrupo.hasNext()){
                            this.conjuntoActual.add(grupoAIntroducir);
                        }
                        while(itNextEGrupo.hasNext()){
                            ConjuntoDeEstados conjAux2 = itNextEGrupo.next();
                            cont = 0;
                            for(String caracter : alfa){
                                /*verificamos si apuntan al mismo grupo ambos con la misma variable*/
                                boolean aComparar =this.matrizEstAlf[conjAux.getInicio()][conseguirIdLetra(caracter)].getIdGrupo() == this.matrizEstAlf[conjAux2.getInicio()][conseguirIdLetra(caracter)].getIdGrupo();
                                if(aComparar){
                                    cont++;
                                }
                            }
                            /*VERIFICAMOS SI SE CUMPLIO QUE APUNTAN AL MISMOS GRUPOS CON LAS MISMAS LETRAS*/
                            if(cont == alfa.size()){
                                conjAux2.setIdGrupo(grupoAIntroducir.getIdGrupo());
                                grupoAIntroducir.getGrupo().add(conjAux2);
                                itNextEGrupo.remove();
                                itElemGrupo = grupo.getGrupo().iterator();
                                
                            }
                        }
                        conjAux.setIdGrupo(grupoAIntroducir.getIdGrupo());
                        /*Tomamos un caracter del alfabeto y lo revisamos*/
//                        for(String caracter : alfa){
//                            /*TOMAMOS UN ELEMENTO DE NUESTRO GRUPO, Y VEMOS SI NO APUNTA
//                             *A ALGUN ELEMENTO QUE ESTA FUERA DE NUESTRO GRUPO, SI ES ASI
//                             *LO DEBEMOS PONER EN OTRO GRUPO NUEVO O YA EXISTENTE QUE TENGA
//                             *ELEMENTOS QUE APUNTE AL MISMO GRUPO*/
//                            
//                            
//                            for(int j=0; j<grupo.getGrupo().size()+1;j++){
//                                
//                                valorCaracter = this.afd.getAdyacencia()[conjAux.getInicio()][j];
//                                /*Si el valor de la matriz de adyacencia es igual al caracter que 
//                                 *estamos examinando entoncs vemos a que grupo apunta nuestro
//                                 *conj aux con ese caracter*/
//                                if(caracter.equalsIgnoreCase(valorCaracter)){
//                                    
//                                    if(vectorGrupo[conjAux.getInicio()] != vectorGrupo[j]){
//                                        
//                                        
//                                    /*CREAR NUEVO GRUPO Y AÑADIRLO AHI, ACTUALIZAR
//                                     *ADEMAS EL VECTORGRUPO Y REMOVER AL CONJAUX
//                                     *DEL GRUPO ACTUAL EN EL QUE ESTA.*/
//                                        seCreoNuevoGrupo =true;
//                                        grupoAIntroducir.getGrupo().add(conjAux);
//                                        //grupo.getGrupo().remove(conjAux);
//                                        itElemGrupo.remove();
//                                        //vectorGrupo[conjAux.getInicio()] = contGrupo;
//                                    }
//                                }
//                            }
//                        }
                    }
                    
                    /*
                     *Revisamos si quedo algo que no se cargo y quedo en
                     *el grupo a añadir.
                     */
                    if(grupoAIntroducir.getGrupo().size() > 0 &&
                            !this.conjuntoActual.contains(grupoAIntroducir)){
                        
                        this.conjuntoActual.add(grupoAIntroducir);
                        contGrupo++;
                        grupoAIntroducir = new Grupo(contGrupo);
                        
                    }
                    /*Si no se creo un nuevo grupo lo que hacemos es regresar
                     *el valor del contador de grupo, si se creo un nuevo
                     *grupo lo añadimos a conjunto inicial.*/
                    //this.conjuntoActual.add(grupoAIntroducir);
//                    if(!seCreoNuevoGrupo){
//                        contGrupo--;
//                    }else{
//                        this.conjuntoInicial.add(grupoAIntroducir);
//                        seCreoNuevoGrupo = false;
//                    }
                    /*CONTROLAR QUE NO SEA DE SIZE ==0*/
                }else{
                    this.conjuntoActual.add(grupo);
                }
                
            }
           // this.actualizarVector();
            /*Si no se crearon nuevos grupos, terminamos el algoritmo*/
            if(tamañoConjInicial == this.conjuntoActual.size()){
                continuar=false;
                conjuntoInicial = conjuntoActual;
            }else if(tamañoConjInicial > this.conjuntoActual.size()){
                continuar=false;
            }else{
                conjuntoInicial = conjuntoActual;
                conjuntoActual = new ArrayList<Grupo>();
            }
            
            /*AK FALTA UNA FUNCIÓN ACTUALIZAR GRUPOS, QUE ACTUALICE LOS VALORES
             *DE LA MATRIZ*/
        }
        
        return this;
    }
    
    public void inicializarMatriz(){
        int tamanoFila= this.afd.getEstadosMarcados().size();
        int tamanoCol = this.afd.getAfn().getAlfabeto().getCaracteres().size();
        this.matrizEstAlf = new ConjuntoDeEstados [tamanoFila][tamanoCol];
        String letra;
        for(int i=0; i<tamanoFila;i++){
            for(int j=0; j<tamanoFila;j++){
                letra =this.afd.getAdyacencia()[i][j];
                if (letra != null){
                   // this.conjuntoInicial
                    if(this.getAfd().getEstadosMarcados().get(j).isConjFinal()){
                        this.getAfd().getEstadosMarcados().get(j).setIdGrupo(0);
                    }else{
                        this.getAfd().getEstadosMarcados().get(j).setIdGrupo(1);
                    }
                    this.matrizEstAlf[i][this.conseguirIdLetra(letra)] = this.getAfd().getEstadosMarcados().get(j);
                }
            }
        }
    }
    public int conseguirIdLetra(String letra){
        int pos =-1;
        for(String caracter :(ArrayList<String>) this.afd.getAfn().getAlfabeto().getCaracteres()){
            pos++;
            if(caracter.equalsIgnoreCase(letra)){
                return pos;
            }
        }
        return -1;
    }
    
    public void actualizarVector(){
        for(Grupo grupo : this.conjuntoInicial){
            Iterator<ConjuntoDeEstados> itElemGrupo = grupo.getGrupo().iterator();
            while(itElemGrupo.hasNext()){
                this.vectorGrupo[itElemGrupo.next().getInicio()] = grupo.getIdGrupo();
            }
        }
    }
    public /*Este afd es la entrada a ser procesada por el afd minimo*/
    AFDEquivalente getAfd() {
        return afd;
    }
    
    public void setAfd(AFDEquivalente afd) {
        this.afd = afd;
    }

    public ArrayList<Grupo> getConjuntoInicial() {
        return conjuntoInicial;
    }

    public void setConjuntoInicial(ArrayList<Grupo> conjuntoInicial) {
        this.conjuntoInicial = conjuntoInicial;
    }

    public ArrayList<Grupo> getConjuntoActual() {
        return conjuntoActual;
    }

    public void setConjuntoActual(ArrayList<Grupo> conjuntoActual) {
        this.conjuntoActual = conjuntoActual;
    }

    public ConjuntoDeEstados[][] getMatrizEstAlf() {
        return matrizEstAlf;
    }

    public void setMatrizEstAlf(ConjuntoDeEstados[][] matrizEstAlf) {
        this.matrizEstAlf = matrizEstAlf;
    }
    
}
