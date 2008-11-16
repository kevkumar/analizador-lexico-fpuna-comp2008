/*
 * Main.java
 *
 * Created on 1 de noviembre de 2008, 12:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package analizadorlexico;

/**
 *
 * @author Huguis
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here                
        Alfabeto alf = new Alfabeto();
        alf.cargarAlfabeto("a,b,E");
        alf.setHayVacio(true);                
        
        Afn a = new Afn("aab|ba*$", alf);
        a = a.generar();
        
        System.out.println(a.imprimir());
    }
    
}
